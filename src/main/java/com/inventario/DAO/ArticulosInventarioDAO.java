/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Categoria;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticulosInventarioDAO {

    Conexion conexion = new Conexion();
    private ArticulosInventario articulosInventario;
    private ResultSet resultSet;
    private String sentenciaSql;
    private List<ArticulosInventario> listaArticulos;
    private List auxlista = new ArrayList<>();

    public ArticulosInventarioDAO() {
        articulosInventario = new ArticulosInventario();
        conexion = new Conexion();
        listaArticulos = new ArrayList<>();
    }

    public ArticulosInventarioDAO(ArticulosInventario articulosInventario) {
        conexion = new Conexion();
        this.articulosInventario = articulosInventario;
    }

    public List<ArticulosInventario> getArticulos() {
        List<ArticulosInventario> ListaInv = new ArrayList<>();
        String sql = String.format("Select * FROM articulos");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ArticulosInventario articulo = new ArticulosInventario();
                articulo.setId(resultSet.getInt("id"));
                articulo.setId_categoria(resultSet.getInt("id_categoria"));
                articulo.setNombre(resultSet.getString("nombre"));
                articulo.setId_tipo(resultSet.getInt("id_tipo"));
                articulo.setDescripcion(resultSet.getString("descripcion"));
                articulo.setId_bodega(resultSet.getInt("id_bodega"));
                articulo.setCantidad(resultSet.getInt("cantidad"));
                articulo.setCosto(resultSet.getInt("costo"));
                articulo.setIva(resultSet.getInt("iva"));
                articulo.setIce(resultSet.getInt("ice"));
                articulo.setMax_stock(resultSet.getInt("max_stock"));
                ListaInv.add(articulo);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaInv;
    }

        public Categoria getCategoria(int id) {
        ResultSet rs;
        Categoria temp = new Categoria();

        try {
            String code = String.valueOf(id);
            conexion.conectar();
            //rs = conexion.ejecutarSql("select * from public.buscarproductocodigo(" + code.trim() + ")");
            rs = conexion.ejecutarSql("select cod,nom_categoria,descripcion from public.categoria where cod=" + code.trim());

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");

                while (rs.next()) {
                    System.out.print("Producto " + rs.getInt(1));
                    temp.setIdCat(rs.getInt(1));
                    temp.setNom_categoria(rs.getString(2));
                    temp.setDescripcion_categoria(rs.getString(3));

                }
            }
            conexion.desconectar();

            return temp;
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
        } finally {
            conexion.desconectar();
        }

        return null;
    }

    public ArticulosInventario ObtenerProducto(int id) {
        ResultSet rs;
        ArticulosInventario temp = new ArticulosInventario();

        try {
            String code = String.valueOf(id);
            conexion.conectar();
            //rs = conexion.ejecutarSql("select * from public.buscarproductocodigo(" + code.trim() + ")");
            rs = conexion.ejecutarSql("select codigo,codigoaux,stock,description,price,subsi,t_ice,t_iva,t_descuento,max_stock from public.buscarproductocodigo(" + code.trim()
                    + ")inner join articulos on public.articulos.id = codigo ");
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");

                while (rs.next()) {
                    System.out.print("Producto " + rs.getInt(1));
                    temp.setCod(rs.getInt(1));
                    temp.setCat_cod(rs.getInt(2));
                    temp.setNombre(rs.getString(3));
                    temp.setDescripcion(rs.getString(4));
                    temp.setId_bodega(rs.getInt(5));
                    temp.setCosto(rs.getInt(6));
                    temp.setIce(rs.getInt(7));
                    temp.setIva(rs.getInt(8));
                    temp.setMax_stock(rs.getInt(9));
                }
            }
            conexion.desconectar();

            return temp;
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
        } finally {
            conexion.desconectar();
        }

        return null;
    }

    public List<Categoria> getCategorias() {
        List<Categoria> ListaCategoria = new ArrayList<>();
        String sql = String.format("Select * FROM categoria");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaCategoria.add(new Categoria(resultSet.getInt("cod"),
                        resultSet.getString("nom_categoria"),
                        resultSet.getString("descripcion")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaCategoria;
    }

    //insertar nuevo articulo
    public void insertarAriculo(ArticulosInventario articulo) {
        try {
            ResultSet rs = null;
            this.conexion.conectar();
            rs = this.conexion.ejecutarSql("select id from articulos order by id desc limit 1;");
            int cod = 1;

            while (rs.next()) {
                cod = rs.getInt(1) + 1;
            }
            articulo.setId(cod);
            System.out.println("Articulo: " + articulo.getId());

            sentenciaSql = "INSERT INTO public.articulos(id,nombre, id_categoria,id_tipo,descripcion,id_bodega,cantidad,costo,iva,ice)\n"
                    + "	VALUES (" + articulo.getId() + "'" + articulo.getNombre() + "', " + articulo.getId_categoria() + ", " + articulo.getId_tipo() + ",'" + articulo.getDescripcion()
                    + "'," + articulo.getId_bodega() + "," + articulo.getCantidad() + "," + articulo.getCosto() + ",12,0)";
            //enviamos la sentencia
            conexion.ejecutarSql(sentenciaSql);
            this.conexion.desconectar();

            System.out.println("Entrada Guardada exitosamente");

        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            conexion.desconectar();
        }
    }

}
