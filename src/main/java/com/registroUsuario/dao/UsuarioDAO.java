package com.registroUsuario.dao;

import com.global.config.Conexion;
import com.registroUsuario.models.Usuario;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO {

    List<Usuario> lsitaUsuarios;
    boolean isTrue = true;
    Conexion conexion = new Conexion();
   

    public void registrarUsuario(Usuario user) {

        try {
            this.conexion.Conectar();
            String sentenciaRegtsUser = "INSERT INTO public.usuario(\n"
                    + "	\"idUsuario\", nombre, apellido, username, password, \"fechaCreacion\", habilitado)\n"
                    + "	VALUES ('" + user.getIdUsuario() + "','" + user.getNombre()
                    + "','" + user.getApellido() + "','" + user.getUsername() + "','" + user.getPassword()
                    + "','" + user.getFehcaCreacion() + "','" + user.isHabilitado() + "');";
            conexion.Ejecutar2(sentenciaRegtsUser);
            conexion.cerrarConexion();

        } catch (SQLException e) {
            e.toString();
        } finally {

            conexion.cerrarConexion();

        }

    }
}
