/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.KardexEntradasSalidas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author angul
 */
public class KardexEntradaSalidaDAO {

    Conexion conexion;
    private KardexEntradasSalidas kardexEntradasSalidas;
    private ResultSet resultSet;
    private List<KardexEntradasSalidas> listaKardex;
    private List auxlista = new ArrayList<>();

    public KardexEntradaSalidaDAO() {
        kardexEntradasSalidas = new KardexEntradasSalidas();
        conexion = new Conexion();
        listaKardex = new ArrayList<>();
    }

    public KardexEntradaSalidaDAO(KardexEntradasSalidas k) {
        conexion = new Conexion();
        this.kardexEntradasSalidas = k;
    }

    public KardexEntradasSalidas getKardexEntradasSalidas() {
        return kardexEntradasSalidas;
    }

    public void setKardexEntradasSalidas(KardexEntradasSalidas kardexEntradasSalidas) {
        this.kardexEntradasSalidas = kardexEntradasSalidas;
    }

    public List<KardexEntradasSalidas> getKardexES() {
        List<KardexEntradasSalidas> ListaKardex = new ArrayList<>();

        String sql = String.format("select * from(\n"
                + "\n"
                + "select e.cod, e.fecha, e.observacion, ed.cant as entradas , 0 as salidas, a.costo from articulos a inner join entrada_detalle ed on a.id = ed.cod_articulo\n"
                + "full join entrada e on e.cod = ed.id_entrada \n"
                + "	\n"
                + "union\n"
                + "select s.cod, s.fecha, s.observacion, 0 as entradas, sd.cant as cantidad, a.costo from articulos a inner join salida_detalle sd on a.id = sd.cod_articulo\n"
                + "full join salida s on s.cod = sd.id_salida\n"
                + "\n"
                + ") as kardex");
        try {
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                ListaKardex.add(new KardexEntradasSalidas(resultSet.getInt("cod"),
                        resultSet.getDate("fecha"),
                        resultSet.getString("observacion"),
                        resultSet.getDouble("entradas"),
                        resultSet.getDouble("salidas"),
                        resultSet.getDouble("costo")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ListaKardex;
    }

    public List<KardexEntradasSalidas> getKardexES(int idArticulo, Date fechaInicial, Date fechaFinal) {
        List<KardexEntradasSalidas> ListaKardex = new ArrayList<>();

        String sql = String.format("select * from (\n"
                + "select 'ENT-' || e.cod::text as cod, e.fecha, e.observacion, ed.cant as entradas , 0 as salidas, a.costo,"
                + "ed.costo * ed.cant as saldoEntradas , 0 as saldoSalidas"
                + " from articulos a inner join entrada_detalle ed on a.id = ed.cod_articulo\n"
                + "full join entrada e on e.cod = ed.id_entrada \n"
                + "where a.id = " + idArticulo + " and fecha between '" + fechaInicial + "' and '" + fechaFinal + "'\n"
                + "union\n"
                + "select 'SAL-' || s.cod::text, s.fecha, s.observacion, 0 as entradas, sd.cant as cantidad, a.costo,"
                + "0 as saldoEntradas , sd.costo * sd.cant as saldoSalidas"
                + " from articulos a inner join salida_detalle sd on a.id = sd.cod_articulo\n"
                + "full join salida s on s.cod = sd.id_salida\n"
                + "where a.id = " + idArticulo + " and fecha between '" + fechaInicial + "'  and '" + fechaFinal + "'\n"
                + "order by fecha ) as kardex");
        try {
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                KardexEntradasSalidas kardexES = new KardexEntradasSalidas();
                kardexES.setCodigo(resultSet.getString("cod"));
                kardexES.setFecha(resultSet.getDate("fecha"));
                kardexES.setObservacion(resultSet.getString("observacion"));
                kardexES.setEntradas(resultSet.getDouble("entradas"));
                kardexES.setSalidas(resultSet.getDouble("salidas"));
                kardexES.setCosto(resultSet.getDouble("costo"));
                kardexES.setSaldoIngresos(resultSet.getDouble("saldoEntradas"));
                kardexES.setSaldoSalidas(resultSet.getDouble("saldoSalidas"));
                ListaKardex.add(kardexES);
            }

            double existencia = 0.0;
            double precioEntrada = 0.0;
            double precioSalidas = 0.0;
            double saldosUnidades = 0.0;
            double costoUnitario = 0;
            double saldo = 0;
            for (KardexEntradasSalidas kardex : ListaKardex) {
                if (kardex.getEntradas() != 0) {
                    saldosUnidades += kardex.getEntradas();
                    kardex.setSaldoUnidades(saldosUnidades);
                }
                if (kardex.getSaldoSalidas() != 0) {
                    saldosUnidades -= kardex.getSalidas();
                    kardex.setSaldoUnidades(saldosUnidades);
                }
                saldo = saldo + kardex.getSaldoIngresos() - kardex.getSaldoSalidas();

                costoUnitario = saldo / saldosUnidades;

                kardex.setCosto(costoUnitario);
                kardex.setSaldo(saldo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ListaKardex;
    }

}
