
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CostoDAO {
    private Conexion conexion;
    private ResultSet resultSet;

    public CostoDAO() {
        conexion = new Conexion();
    }
    
    public List<Costo> getCosto(){
        List<Costo> costo = new ArrayList<>();
        String sql = String.format("SELECT * FROM public.costos;");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_costos"), resultSet.getString("nombre"),
                resultSet.getString("descripcion"), resultSet.getString("tipo"), resultSet.getString("identificador")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
            conexion.desconectar();
        }
        return costo;
    }

    public void insertarCosto(Costo costo) {
        try {

            String sql = "INSERT INTO public.costos(nombre, descripcion, tipo, identificador)\n"
                    + "	VALUES ('" + costo.getNombre_subcuenta() + "', '" + costo.getDescripcion_subgrupo() + "', '" + costo.getTipo_cuenta()+ "', '" + costo.getIdentificador_subcuenta() + "')";

            String sql2 = "Insert into public.costos (nombre, descripcion, tipo, identificador) \n"
                    + "values('" + costo.getNombre_subcuenta() + "', '" + costo.getDescripcion_subgrupo() + "', '" + costo.getTipo_cuenta()+ "','" + costo.getIdentificador_subcuenta() + "')";
            //enviamos la sentencia
            conexion.Ejecutar2(sql2);

        } catch (Exception e) {
        } finally {
            conexion.desconectar();
        }
    }

    public void updateCosto(Costo costo) throws SQLException {
        try {

            String sql = "UPDATE public.costos\n"
                    + "	SET nombre='" + costo.getNombre_subcuenta() + "', descripcion='" + costo.getDescripcion_subgrupo() + "', identificador='" + costo.getIdentificador_subcuenta() + "'\n"
                    + "	WHERE identificador = '" + costo.getIdentificador_subcuenta()+ "'";
            //enviamos la sentencia
            conexion.ejecutarSql(sql);

        } finally {
            conexion.desconectar();
        }
    }

    public void deletecosto(Costo costo, String aux) throws SQLException{
        try {
            String sql = ("DELETE FROM public.costos WHERE identificador = '" + aux + "'");
            //enviamos la sentencia
            conexion.ejecutarSql(sql);

        } catch (Exception e) {
            throw e;
        }finally{
            conexion.desconectar();
        }
    }
}
