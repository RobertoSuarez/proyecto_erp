/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.Precios;
import com.ventas.models.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 *
 * @author ninat
 */
public class PreciosDAO {

    private List<Precios> listaprecios;
    private List<Producto> listaproducto;
    private List<SelectItem> listatipos;
    private Producto producto;
    private Precios precios;
    Conexion conexion;
    ResultSet result;

    public PreciosDAO() {
        precios = new Precios();
        producto = new Producto();
        conexion = new Conexion();
        listaprecios = new ArrayList<>();
        listatipos = new ArrayList<>();
        listaproducto = new ArrayList<>();
    }

    public List<Precios> mostrarPrecios() {
        try {
            String Sentencia = "select p.idlistaprecios,p.idtipocliente,"
                    + "t.tipocliente,t.descripcion,p.descuento from listaprecios "
                    + "p inner join tipocliente t on p.idtipocliente = t.idtipocliente";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                listaprecios.add(new Precios(result.getInt("idlistaprecios"),
                        result.getInt("idtipocliente"),
                        result.getString("tipocliente"),
                        result.getString("descripcion"),
                        result.getDouble("descuento")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return listaprecios;
    }

    public List<SelectItem> llenarTipos() {
        try {
            String Sentencia = "select * from tipocliente";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                SelectItem Agregar = new SelectItem(result.getInt("idtipocliente"), result.getString("tipocliente"));
                listatipos.add(Agregar);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return listatipos;
    }

    public List<Producto> llenarProducto(int id) {
        try {
            String Sentencia = " select d.codprincipal, p.descripcion from "
                    + "descuentocliente d inner join productos p on \n"
                    + "d.codprincipal = p.codprincipal where d.idlistaprecios = "
                    + "(select idlistaprecios from listaprecios where idtipocliente = "+id+")";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                Producto p = new Producto();
                p.setCodigo(result.getInt("codprincipal"));
                p.setDescripcion(result.getString("descripcion"));
                listaproducto.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return listaproducto;
    }

    public boolean opciones(int id) {
        boolean dato = true;
        try {
            String Sentencia = "select tipo from listaprecios where idtipocliente = '" + id + "'";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                dato = result.getBoolean("tipo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return dato;
    }

    public int insert(Precios precios, boolean radio) {
        int existe = 1;
        try {

            String Sentencia = "select public.insertar_listaprecios('"
                    + precios.getIdtipocliente() + "','" + precios.getDescuento() + "'," + radio + ")";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                existe = result.getInt("insertar_listaprecios");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return existe;
    }

    public int insertProduct(int idtc, List<Producto> listaproduc) {
        int existe = 1;
        try {
            for (int i = 0; i < listaproduc.size(); i++) {
                String Sentencia = "select public.insertar_descuento('"
                        + idtc + "'," + listaproduc.get(i).getCodigo() + ")";
                result = conexion.ejecutarConsulta(Sentencia);
                while (result.next()) {
                    existe = result.getInt("insertar_descuento");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return existe;
    }

    public int deleteInsert(int idtc) {
        int existe = 1;
        try {
            String Sentencia = "select public.delete_listaprecios_detalle('" + idtc + "')";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                existe = result.getInt("delete_listaprecios_detalle");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return existe;
    }

    public int deleteProduct(int idtc) {
        int existe = 1;
        try {
            String Sentencia = "select public.delete_descuento('"
                    + idtc + "')";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                existe = result.getInt("delete_descuento");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return existe;
    }

    public int update(Precios precios, boolean radio) {
        int existe = 1;
        try {

            String Sentencia = "select public.update_listaprecio('"
                    + precios.getIdtipocliente() + "','" + precios.getDescuento() + "'," + radio + ")";
            result = conexion.ejecutarConsulta(Sentencia);
            while (result.next()) {
                existe = result.getInt("update_listaprecio");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.cerrarConexion();
        }
        return existe;
    }
}
