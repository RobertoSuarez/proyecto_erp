/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.EntradaDetalleInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */
public class EntradaDetalleDAO {

    Conexion conexion = new Conexion();
    private EntradaDetalleInventario entradaDetalleInventario;
    private ResultSet resultSet;
    private List<EntradaDetalleInventario> listaArticulos;
    private List auxlista = new ArrayList<>();

    public EntradaDetalleDAO() {
        entradaDetalleInventario = new EntradaDetalleInventario();
        conexion = new Conexion();
        listaArticulos = new ArrayList<>();
    }

    public EntradaDetalleDAO(EntradaDetalleInventario entradaDetalleInventario) {
        conexion = new Conexion();
        this.entradaDetalleInventario = entradaDetalleInventario;
    }

    public List<EntradaDetalleInventario> getEntradasDetalle() {
        List<EntradaDetalleInventario> ListEntrada = new ArrayList<>();
        String sql = String.format("Select * FROM entrada_detalle");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListEntrada.add(new EntradaDetalleInventario(resultSet.getInt("cod_articulo"),
                        resultSet.getInt("id_entrada_detalle"),
                        resultSet.getInt("id_entrada"),
                        resultSet.getInt("cant"),
                        resultSet.getShort("costo"),
                        resultSet.getInt("iva"),
                        resultSet.getInt("ice")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListEntrada;
    }

    public int GuardarEntrada(EntradaDetalleInventario entradaDetalleInventario) {
        try {
            ResultSet rs = null;

            this.conexion.abrirConexion();
            rs = this.conexion.consultar("select cod from public.entrada_Detalle order by cod desc limit 1;");
            int codigo = 1;

            //Asignar los valores de la siguiente venta y secuencia.
            while (rs.next()) {
                codigo = rs.getInt(1) + 1;
            }
            entradaDetalleInventario.setIdEntradaDetalle(codigo);
            System.out.println("Entrada: " + entradaDetalleInventario.getIdEntradaDetalle());

            //Insertar nueva venta
            String query = "INSERT INTO public.entrada("
                    + "cod_articulo, id_entrada_detalle, id_entrada, cant, costo, iva, ice)"
                    + "VALUES(" + entradaDetalleInventario.getIdEntradaDetalle() + ",'" + entradaDetalleInventario.getIdEntradaDetalle() + "', " + entradaDetalleInventario.getCant() + ", " + entradaDetalleInventario.getCosto() + ", " + entradaDetalleInventario.getIva() + ", " + entradaDetalleInventario.getIce() + ")";
            System.out.println(query);
            this.conexion.consultar(query);

            this.conexion.cerrarConexion();

            System.out.println("Entrada Guardada exitosamente");

            return entradaDetalleInventario.getIdEntradaDetalle();
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.cerrarConexion();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.desconectar();
        }
        return 0;
    }

    public void RegistrarProductos(int idVenta, int idProducto, double cantidad, double precio) {
        try {
            int idDetalle = 1;
            ResultSet rs = null;
            this.conexion.abrirConexion();

            //Recibir siguiente código de detalle venta
            String query = "select iddetalleventa from public.detalleventa order by iddetalleventa desc limit 1;";
            rs = this.conexion.consultar(query);

            while (rs.next()) {
                idDetalle = rs.getInt(1) + 1;
            }

            //insertar detalle venta
            query = "insert into public.detalleventa(iddetalleventa, idventa, codprincipal, cantidad, descuento, precio) values(" + idDetalle + "," + idVenta + ","
                    + idProducto + "," + cantidad + "," + precio + ")";
            System.out.println(query);
            this.conexion.consultar(query);

            //Reducir stock
            int cantidadActual = 0;
            query = "select cantidad from public.productos where codprincipal = " + idProducto + ";";
            rs = this.conexion.consultar(query);
            while (rs.next()) {
                cantidadActual = rs.getInt(1);
            }
            query = "update public.productos set cantidad = " + (cantidadActual - cantidad) + " where codprincipal = " + idProducto + ";";
            this.conexion.ejecutar(query);

            this.conexion.cerrarConexion();
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.cerrarConexion();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.cerrarConexion();
        }
    }

}
