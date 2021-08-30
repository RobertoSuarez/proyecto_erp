package com.empresa.global;

import com.global.config.Conexion;
import java.sql.ResultSet;

public class EmpresaMatrizDAO {
    private static Conexion conexion = new Conexion();
    private static ResultSet result;
    
    public static EmpresaMatriz getEmpresa(){
        String sql = "SELECT * FROM public.empresa_matriz";
        EmpresaMatriz empresa = new EmpresaMatriz();
        try {
            conexion.conectar();
            result = conexion.ejecutarSql(sql);
            while (result.next()) {                
                empresa = new EmpresaMatriz(result.getInt("id_matriz"),result.getString("ruc"), 
                        result.getString("nombre"),result.getString("razon_social"), result.getString("detalle"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            conexion.desconectar();
        }
        return empresa;
    }
}
