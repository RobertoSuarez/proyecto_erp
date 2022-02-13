/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.ArticulosInventario;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimmy
 */
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
                        resultSet.getInt("cat_cod"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_tipo"),
                        resultSet.getInt("cod"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("id_bodega"),
                        resultSet.getInt("min_stock"),
                        resultSet.getInt("max_stock"),
                        resultSet.getInt("cantidad"),
                        resultSet.getInt("costo"),
                        resultSet.getInt("iva"),
                        resultSet.getInt("ice") ));
             }
             
             
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }finally{
            conexion.desconectar();
        }
        return ListaInv;
         }
 
    
    
}
