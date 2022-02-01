/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.Bodega;
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
public class BodegaDAO {

    Conexion conexion;
    private Bodega bodega;
    private ResultSet resultSet;
    private List<Bodega> listaBodegas;
    private List auxlista = new ArrayList<>();

    public BodegaDAO() {
        bodega = new Bodega();
        conexion = new Conexion();
        listaBodegas = new ArrayList<>();
    }

    public BodegaDAO(Bodega b) {
        conexion = new Conexion();
        this.bodega = b;
    }

    public Bodega getBod() {
        return bodega;
    }

    public void setBod(Bodega b) {
        this.bodega = b;
    }

    public List<Bodega> getBodega() {
        List<Bodega> ListaBodega = new ArrayList<>();
        String sql = String.format("select cod, nombre_bodega,cod_ciudad, c.nombre, direccion, telefono\n"
                + "from bodega b inner join ciudad c on b.cod_ciudad=c.id_ciudad");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaBodega.add(new Bodega(resultSet.getInt("cod"),
                        resultSet.getString("nombre_bodega"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getInt("cod_ciudad"),
                        resultSet.getString("nombre")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ListaBodega;
    }

    public Bodega obtenerBodega(int codigo) {
        ResultSet rs;
        Bodega temp = new Bodega();
        try {
            String id = String.valueOf(codigo);
            conexion.abrirConexion();
            rs = conexion.ejecutarConsulta("select*from bodega where cod= " + id.trim());

            if (rs == null) {
                System.out.println("No existe registro");
            } else {
                System.out.println("Existen registros");
                
                while (rs.next()){
                    System.out.print("bodega "+rs.getInt(1));
                    temp.setCod(rs.getInt(1));
                    temp.setNomBodega(rs.getString(2));
                    temp.setDireccion(rs.getString(3));
                    temp.setCodCiudad(rs.getInt(4));
                    temp.setTelefono(rs.getString(5));
                }
                
            }
            conexion.cerrarConexion();
            return temp;

        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.cerrarConexion();
            }
        } finally {
            conexion.cerrarConexion();
        }
        return null;
    }

}
