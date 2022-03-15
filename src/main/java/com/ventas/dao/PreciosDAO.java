/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.Precios;
import com.ventas.models.ProductoVenta;
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
    private List<ProductoVenta> listaproducto;
    private List<SelectItem> listatipos;
    private ProductoVenta producto;
    private Precios precios;
    Conexion conexion;
    ResultSet result;

    public PreciosDAO() {
        precios = new Precios();
        producto = new ProductoVenta();
        conexion = new Conexion();
        listaprecios = new ArrayList<>();
        listatipos = new ArrayList<>();
        listaproducto = new ArrayList<>();
    }

    public List<Precios> mostrarPrecios() {
        try {
            String Sentencia = "select p.idlistaprecios, p.idtipocliente, t.tipocliente, t.descripcion, p.descuento, \n" +
                                "CASE WHEN p.idarticulo is null then -1 else p.idarticulo end as idarticulo \n" +
                                "from listaprecios p inner join tipocliente t on p.idtipocliente = t.idtipocliente \n" +
                                "left join articulos a on a.id = p.idarticulo;";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                Precios nuevoPrecio = new Precios();
                nuevoPrecio.setIdprecio(result.getInt("idlistaprecios"));
                nuevoPrecio.setIdtipocliente(result.getInt("idtipocliente"));
                nuevoPrecio.setTipoCliente(result.getString("tipocliente"));
                nuevoPrecio.setDescripcionTipoCliente(result.getString("descripcion"));
                nuevoPrecio.setDescuento(result.getDouble("descuento"));
                
                int idProdAux = result.getInt("idarticulo");
                if(idProdAux > 0){
                    ProductoVenta producto = new ProductoVentaDAO().ObtenerProducto(idProdAux);
                    nuevoPrecio.setIdproducto(idProdAux);
                    nuevoPrecio.setProducto(producto);
                }
                
                listaprecios.add(nuevoPrecio);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return listaprecios;
    }

    public List<SelectItem> llenarTipos() {
        try {
            String Sentencia = "select * from tipocliente";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                SelectItem Agregar = new SelectItem(result.getInt("idtipocliente"), result.getString("tipocliente"));
                listatipos.add(Agregar);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return listatipos;
    }

    public List<ProductoVenta> llenarProducto(int id) {
        try {
            String Sentencia = " select d.codprincipal, p.descripcion from "
                    + "descuentocliente d inner join productos p on \n"
                    + "d.codprincipal = p.codprincipal where d.idlistaprecios = "
                    + "(select idlistaprecios from listaprecios where idtipocliente = "+id+")";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                ProductoVenta p = new ProductoVenta();
                p.setCodigo(result.getInt("codprincipal"));
                p.setDescripcion(result.getString("descripcion"));
                listaproducto.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return listaproducto;
    }

    public boolean opciones(int id) {
        boolean dato = true;
        try {
            String Sentencia = "select tipo from listaprecios where idtipocliente = '" + id + "'";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                dato = result.getBoolean("tipo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return dato;
    }
    
    public boolean opcionesVentas(int id) {
        boolean dato = true;
        try {
            String Sentencia = "select tipo from listaprecios where idtipocliente = '" + id + "'";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                dato = result.getBoolean("tipo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return dato;
    }

    public int insertarGeneral(Precios precios) {
        int existe = 1;
        try {

            String Sentencia = "select public.insertar_listaprecios('"
                    + precios.getIdtipocliente() + "','" + precios.getDescuento() + "', true)";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                existe = result.getInt("insertar_listaprecios");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return existe;
    }

    public int insertProduct(Precios precio, List<ProductoVenta> listaproduc) {
        int existe = 1;
        try {
            for (int i = 0; i < listaproduc.size(); i++) {
                String Sentencia = "select public.insertar_listaprecios_productos("
                        + precio.getIdtipocliente() + "," + precio.getDescuento() + ", false, " + listaproduc.get(i).getCodigo() + ")";
                result = conexion.ejecutarSql(Sentencia);
                while (result.next()) {
                    existe = result.getInt("insertar_listaprecios_productos");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return existe;
    }

    public int deleteInsert(int idtc) {
        int existe = 1;
        try {
            String Sentencia = "select public.delete_listaprecios_detalle('" + idtc + "')";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                existe = result.getInt("delete_listaprecios_detalle");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return existe;
    }

    public int deleteProduct(int idtc) {
        int existe = 1;
        try {
            String Sentencia = "select public.delete_descuento('"
                    + idtc + "')";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                existe = result.getInt("delete_descuento");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return existe;
    }

    public int update(Precios precios, boolean radio) {
        int existe = 1;
        try {

            String Sentencia = "select public.update_listaprecio('"
                    + precios.getIdtipocliente() + "','" + precios.getDescuento() + "'," + radio + ")";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                existe = result.getInt("update_listaprecio");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return existe;
    }
    
    public double descuento(int idtipo){
        double des = 0;
        try {

            String Sentencia = "select descuento from listaprecios where idtipocliente = '"
                    + idtipo+ "'";
            result = conexion.ejecutarSql(Sentencia);
            while (result.next()) {
                des = result.getInt("descuento");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return des;
    }
    
    public int idtipocliente(String id){
        int idtipo = 2;
        try {
            if (id.length() <= 10) {
                result = conexion.ejecutarSql("select * from public.buscarclientenatural('" + id.trim() + "')");
            } else if (id.length() > 10) {
                result = conexion.ejecutarSql("select * from public.buscarclientejuridico('" + id.trim() + "')");
            } else {
                result = null;
            }
            while (result.next()) {
                idtipo = result.getInt("id_tipocliente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }
        return idtipo;
    }
}
