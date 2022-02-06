package com.seguridad.dao;

import com.global.config.AES;
import com.global.config.Conexion;
import com.seguridad.models.Roles;
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
    List<Roles> listaRoles;
    ResultSet result;

    public UsuarioDAO() {
        encryptAES = new AES();
    }

    public void registrarUsuario(Usuario user) {

        try {
            this.conexion.Conectar();

            String sentencia = "SELECT public.registrarusuario('" + user.getNombre() + "',"
                    + "'" + user.getApellido() + "','" + user.getEmail() + "',"
                    + "'" + user.getUsername()
                    + "','" + encryptAES.getAESEncrypt(user.getPassword()) + "')";
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

        Usuario usuarioAcceso = null;

        String sentencia = "";
        if (conexion.isEstado()) {
            try {
                sentencia = String.format(
                        "SELECT * from public.iniciarsesion('%1$s','%2$s')",
                        u.getUsername(), encryptAES.getAESEncrypt(u.getPassword()));
                result = conexion.ejecutarConsulta(sentencia);

                while (result.next()) {

                    usuarioAcceso = new Usuario(
                            result.getInt("code"),
                            result.getString("reslt"),
                            result.getInt("iduser"),
                            result.getString("name"),
                            result.getString("surname"),
                            result.getString("usrnm"),
                            result.getString("pswrd"),
                            result.getString("mail")
                    );

                }

            } catch (SQLException e) {
                System.out.println(e.toString() + "EBERT");
            } finally {
                conexion.cerrarConexion();
            }
            this.usuario = usuarioAcceso;
        }
        return usuarioAcceso;
    }

    //------------------IMPLEMENTADO POR RECURSOS HUMANOS------\\
    public List<Roles> rolRRHH(int user) {
        listaRoles = new ArrayList<>();
        String sentencia = "";
        int id = 0;
        try {
            this.conexion.Conectar();
            sentencia = "select rol.\"idRol\",rol.\"nombreRol\",rol.\"detalleRol\" from public.usuario inner join public.\"rolUsuario\" on usuario.\"idUsuario\" = \"rolUsuario\".\"idUsuario\" \n"
                    + "	inner join public.rol on \"rolUsuario\".\"idRol\" = rol.\"idRol\" where usuario.\"idUsuario\" =" + user;
            result = conexion.ejecutarConsulta(sentencia);
            while (result.next()) {
                listaRoles.add(new Roles(
                        result.getInt("idRol"),
                        result.getString("nombreRol"),
                        result.getString("detalleRol")));
            }
            conexion.cerrarConexion();
        } catch (SQLException e) {
            e.toString();
            conexion.cerrarConexion();
            System.out.print(sentencia);
        } finally {
            conexion.cerrarConexion();
        }
        return listaRoles;
    }

    public void verifica(String user, String pass) {
        lsitaUsuarios = new ArrayList<>();
        usuario = iniciarSesion(usuario);
        lsitaUsuarios.get(2).toString();
    }

    public String url() {
        Roles roles = new Roles();
        roles.setURL("/proyecto_erp/View/Global/Main.xhtml");
        return roles.getURL();
    }
}
