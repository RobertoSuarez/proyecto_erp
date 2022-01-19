package com.seguridad.dao;

import com.global.config.AES;
import com.global.config.Conexion;
import com.seguridad.models.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO {

    List<Usuario> lsitaUsuarios;
    boolean isTrue = true;
    Usuario usuario = new Usuario();
    Conexion conexion = new Conexion();
    private final AES encryptAES;
    ResultSet result;

    public UsuarioDAO() {
        encryptAES = new AES();
    }

    public void registrarUsuario(Usuario user) {

        try {
            this.conexion.Conectar();

            String sentencia = "SELECT public.registrarusuario('"
                    + user.getNombre() + "',"
                    + "'" + user.getApellido() + "','" + user.getEmail() + "',"
                    + "'" + user.getUsername() + "','"
                    + encryptAES.getAESEncrypt(user.getPassword()) + "')";
            conexion.Ejecutar2(sentencia);
            conexion.cerrarConexion();

        } catch (SQLException e) {
            e.toString();
        } finally {
            conexion.cerrarConexion();
        }
    }

    // RETURNS TABLE(code integer, reslt character varying, iduser integer, name character varying, surname character varying, usrnm character varying, pssword character varying, mail character varying) 
    public Usuario iniciarSesion(Usuario u) {
        String sentencia = "";
        if (conexion.isEstado()) {
            try {
                sentencia = String.
                        format("SELECT * "
                                + "from public.iniciarsesion('%1$s','%2$s')",
                                u.getUsername(),
                                encryptAES.getAESEncrypt(
                                        u.getPassword()));
                result = conexion.ejecutarConsulta(sentencia);

                while (result.next()) {
                    usuario = new Usuario(
                            result.getInt("code"),
                            result.getString("reslt"),
                            result.getString("usrnm"),
                            result.getString("pswrd")
                    );

                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return usuario;
    }

}
