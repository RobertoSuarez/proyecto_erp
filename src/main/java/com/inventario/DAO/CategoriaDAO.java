/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.Categoria;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Jimmy
 */
@ManagedBean
@RequestScoped
public class CategoriaDAO {

    Conexion conexion;
    private Categoria categoria;
    private ResultSet resultSet;
    private List<Categoria> listaCategoria;

    public CategoriaDAO() {
        categoria = new Categoria();
        conexion = new Conexion();
        listaCategoria = new ArrayList<>();
    }


    public List<Categoria> getCategoria() {
        List<Categoria> ListaCate= new ArrayList<>();
        String sql = String.format("select *from categoria");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaCate.add(new Categoria(resultSet.getInt("cod"),
                        resultSet.getString("nom_categoria"),
                        resultSet.getString("decripcion")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            conexion.desconectar();
        }
        return ListaCate;
    }

   

}
