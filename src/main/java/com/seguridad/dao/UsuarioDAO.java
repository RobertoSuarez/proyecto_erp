package com.seguridad.dao;

import com.global.config.AES;
import com.global.config.Conexion;
import com.seguridad.models.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    List<Usuario> lsitaUsuarios;
    boolean isTrue = true;
    Usuario usuario = new Usuario();
    Conexion conexion = new Conexion();
    private AES encryptAES;
    
    public UsuarioDAO(){
        encryptAES = new AES();
    }
    
    public void registrarUsuario(Usuario user) {
        
        try {
            this.conexion.Conectar();
            
            String sentencia = "SELECT public.registrarusuario('" + user.getNombre() + "',"
                    + "'" + user.getApellido() + "','" + user.getEmail() + "',"
                    + "'" + user.getUsername() + "','" + encryptAES.getAESEncrypt(user.getPassword()) + "')";
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
                sentencia = String.format("SELECT * from public.iniciarsesion('%1$s','%2$s')", u.getUsername(), encryptAES.getAESEncrypt(u.getPassword())) ;
                result = conexion.ejecutarConsulta(sentencia);
                
                while (result.next()) {
                    
                    lsitaUsuarios.add(new Usuario(result.getInt("code"),
                            result.getString("reslt"),
                            result.getInt("iduser"),
                            result.getString("name"),
                            result.getString("surname"),
                            result.getString("usrnm"),
                            result.getString("pswrd"),
                            result.getString("mail")
                    ));

                    // System.out.print(lsitaUsuarios.toString());
                }
                
            } catch (SQLException e) {
                System.out.println(e.toString() + "EBERT");
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