/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.EntradaInventario;
import com.inventario.models.EntradaProducto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */
public class EntradaProductoDao {

    Conexion conexion = new Conexion();
    private EntradaProducto entradaProducto;
    private ResultSet result;
    private List<EntradaProducto> listaEntradas;
    private List auxlista = new ArrayList<>();

    public EntradaProductoDao() {
        entradaProducto = new EntradaProducto();
        conexion = new Conexion();
        listaEntradas = new ArrayList<>();
    }

    public EntradaProductoDao(EntradaProducto ep) {
        conexion = new Conexion();
        this.entradaProducto = ep;
    }

    public List<EntradaProducto> ListarEntradas() {
        if (conexion.isEstado()) {
            try {
                String consulta = "select p.num_comprobante, p.fecha, pr.nombre as proveedor,b.nombre_bodega\n"
                        + "from entrada as p inner join proveedor pr on p.id_proveedor=pr.idproveedor\n"
                        + "inner join bodega b on p.id_bodega=b.cod";
                result = conexion.ejecutarSql(consulta);
                System.out.println("Entrada: " + result.toString());
                while (result.next()) {
                    listaEntradas.add(new EntradaProducto(result.getString("num_comprobante"),
                            result.getObject("fecha", LocalDate.class),
                            result.getString("proveedor"), result.getString("nombre_bodega")
                    ));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.desconectar();
            }

        }
        return listaEntradas;
    }

    public List<EntradaProducto> llenarDetalle(String comprobante) {
        listaEntradas.clear();
        if (conexion.isEstado()) {
            try {
                String consulta = "select e.id_entrada_detalle,e.cod_articulo,a.nombre,a.descripcion,e.cant,a.costo\n"
                        + "from entrada_detalle e inner join entrada en on e.id_entrada= en.cod inner join articulos a on e.cod_articulo=a.id\n"
                        + "where en.num_comprobante='" + comprobante + "'";
                result = conexion.ejecutarSql(consulta);
                while (result.next()) {
                    listaEntradas.add(new EntradaProducto(result.getInt("id_entrada_detalle"),
                            result.getInt("cod_articulo"), result.getString("nombre"), result.getString("descripcion"),
                            result.getInt("cant"), result.getFloat("costo"),result.getFloat("costo")));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.desconectar();
            }
        }
        return listaEntradas;
    }
}

