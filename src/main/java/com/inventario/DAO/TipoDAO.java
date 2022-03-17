/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.Categoria;
import com.inventario.models.Tipo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class TipoDAO {

    Conexion conexion;
    private Categoria categoria;
    private ResultSet resultSet;
    private List<Tipo> listaTipo;

    public TipoDAO() {
        categoria = new Categoria();
        conexion = new Conexion();
        listaTipo = new ArrayList<>();
    }
    public List<Tipo> getTipoArticulo() {
        List<Tipo> ListaTipo= new ArrayList<>();
        String sql = String.format("select *from tipo");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaTipo.add(new Tipo(resultSet.getInt("cod"),
                        resultSet.getString("tipo"),
                        resultSet.getString("descripcion")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            conexion.desconectar();
        }
        return ListaTipo;
    }

   

}
