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
                ListaInv.add(new ArticulosInventario(resultSet.getInt("id"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("id_tipo"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("id_bodega"),
                        resultSet.getInt("cantidad"),
                        resultSet.getInt("costo"),
                        resultSet.getInt("iva"),
                        resultSet.getInt("ice")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaInv;
    }

    public ArticulosInventario ObtenerProducto(int id) {
        ResultSet rs;
        ArticulosInventario temp = new ArticulosInventario();

        try {
            String code = String.valueOf(id);
            conexion.conectar();
            rs = conexion.ejecutarSql("select * from public.buscarproductocodigo(" + code.trim() + ")");

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

    public List<Categoria> getCategoria() {
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

}
