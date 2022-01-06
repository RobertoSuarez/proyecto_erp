package com.registroUsuario.dao;

import com.global.config.Conexion;
import com.registroUsuario.models.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    List<Usuario> lsitaUsuarios;
    boolean isTrue = true;
    Usuario usuario = new Usuario();
    Conexion conexion = new Conexion();
    
    public void registrarUsuario(Usuario user) {
        
        try {
            this.conexion.Conectar();
            
            String sentencia = "SELECT public.registrarusuario('" + user.getNombre() + "',"
                    + "'" + user.getApellido() + "','" + user.getEmail() + "',"
                    + "'" + user.getUsername() + "','" + user.getPassword() + "')";
            conexion.Ejecutar2(sentencia);
            conexion.cerrarConexion();
            
        } catch (SQLException e) {
            e.toString();
        } finally {
            
            conexion.cerrarConexion();
            
        }
        
    }
    /*

    public void iniciarSesionS(String usuario, String password) {
        String sentencia = "";
        try {
            this.conexion.Conectar();

            sentencia = "SELECT public.iniciarsesion"
                    + "('" + usuario + "',"
                    + "'" + password + "')";
            System.out.println(usuario + "l:j" + password);
            conexion.Ejecutar2(sentencia);
            conexion.cerrarConexion();

            System.out.print(sentencia);
        } catch (SQLException e) {
            e.toString();
            System.out.print(sentencia);
        } finally {

            conexion.cerrarConexion();
        }
    }
/*/
    ResultSet result;
    // RETURNS TABLE(code integer, reslt character varying, iduser integer, name character varying, surname character varying, usrnm character varying, pssword character varying, mail character varying) 

    public List<Usuario> iniciarSesion(Usuario u) {
        lsitaUsuarios = new ArrayList<>();
        
        String sentencia = "";
        if (conexion.isEstado()) {
            try {
                sentencia = "SELECT public.iniciarsesion"
                        + "('" + u.getUsername() + "',"
                        + "'" + u.getPassword() + "')";
                result = conexion.ejecutarConsulta(sentencia);
                
                while (result.next()) {
                    
                    lsitaUsuarios.add(new Usuario(result.getInt(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getString(7),
                            result.getString(8)
                    ));

                    // System.out.print(lsitaUsuarios.toString());
                }
                
            } catch (SQLException e) {
                lsitaUsuarios.toString();
                for (int x = 0; x < lsitaUsuarios.size(); x++) {
                    System.out.print(lsitaUsuarios.get(x) + "Guaranga");
                }
                
                System.out.println(e.toString() + "EBERT");
                return lsitaUsuarios;
            } finally {
                
                conexion.cerrarConexion();
            }
        }
        return lsitaUsuarios;
    }
    
    public void verifica(String user, String pass) {
        lsitaUsuarios = new ArrayList<>();
        lsitaUsuarios = iniciarSesion(usuario);
        lsitaUsuarios.get(2).toString();
    }
}
