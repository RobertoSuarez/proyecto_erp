/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.Proveedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ebert
 * @see Conexion
 * @see Proveedor
 */
public class ProveedorDAO extends Conexion {

       
    private ResultSet result;
    private List<Proveedor> listaProveedor;
    
    Conexion conexion = new Conexion();
    private Proveedor proveedor;

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Constructor en donde tendremos los metodos conexion proveedor
     */
    public ProveedorDAO() {
        conexion = new Conexion();
        proveedor = new Proveedor();
    }

    /**
     * Método para insertar un proveedor recibiendo un parámetro Dicha clase
     * implementa try and catch
     *
     * @param p objeto proveedor
     */
    public void insertarp(Proveedor p) {
        try {
            this.conexion.Conectar();
            String cadena = "INSERT INTO public.proveedor(\n"
                    + "	codigo, razonsocial, ruc, nombre, direccion, email, webpage, contacto, telefono, estado)\n"
                    + "	VALUES ('" + p.getCodigo() + "','" + p.getRazonSocial() + "'"
                    + ",'" + p.getRuc() + "','" + p.getNombre() + "','" + p.getDireccion() + "'"
                    + ",'" + p.getEmail() + "','" + p.getWebPage() + "','" + p.getContacto() + "',"
                    + " '" + p.getTelefono() + "','" + p.isEstado() + "');";

            conexion.Ejecutar2(cadena);
            conexion.cerrarConexion();

        } catch (SQLException e) {

        } finally {
            conexion.cerrarConexion();
        }
    }

    /**
     * Método para actualizar un proveedor recibiendo dos parametro Dicha clase
     * implementa throws
     *
     * @param proveedor obejto proveedor
     * @param codigo variable tipo int
     * @throws SQLException validador
     */
    public void update(Proveedor proveedor, int codigo) throws SQLException {
        try {
            this.conexion.Conectar();
            String cadena = "UPDATE public.proveedor\n"
                    + "	SET  razonsocial= '" + proveedor.getRazonSocial() + "',"
                    + " ruc='" + proveedor.getRuc() + "', "
                    + "nombre='" + proveedor.getNombre() + "',"
                    + " direccion='" + proveedor.getDireccion() + "', "
                    + "email='" + proveedor.getEmail() + "', "
                    + "webpage='" + proveedor.getWebPage() + "', "
                    + "contacto='" + proveedor.getContacto() + "',"
                    + " telefono='" + proveedor.getTelefono() + "',"
                    + " estado='" + proveedor.isEstado() + "' "
                    + " Where idproveedor = " + codigo + "";
            conexion.ejecutar(cadena);
            conexion.cerrarConexion();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }

    }

    public List<Proveedor> ListarProveedor() {
        List<Proveedor> clientes = new ArrayList<>();
        try {
            this.conexion.abrirConexion();
            String query = "select cl.idcliente, T.* from "
                    + "(select pn.idpersonanatural as IdNatural, null as IdJuridico, "
                    + "pr.id_persona, pn.nombre1||' '||pn.nombre2||' '||pn.apellido1||' '||pn.apellido2 as Nombre, pr.identificacion "
                    + "from public.persona_natural pn inner join public.persona pr on pn.id_persona = pr.id_persona UNION "
                    + "select null as IdNatural, pj.id_persona_juridica as IdJuridico, pr.id_persona, pj.razon_social as Nombre, pr.identificacion "
                    + "from public.persona_juridica pj inner join public.persona pr on pj.id_persona = pr.id_persona) as T "
                    + "inner join public.clientes cl on (cl.idpersonanatural = T.idnatural or cl.id_persona_juridica = T.idjuridico);";
            ResultSet rs = this.conexion.ejecutarConsulta(query);
            conexion.conex.close();
            while (rs.next()) {
                this.proveedor = new Proveedor();
                this.proveedor.setIdProveedor(rs.getInt(1));
                this.proveedor.setNombre(rs.getString(5));
                this.proveedor.setRazonSocial(rs.getString(6));
                clientes.add(this.proveedor);
            }
            this.conexion.cerrarConexion();
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.cerrarConexion();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.cerrarConexion();
        }
        return clientes;
    }

    public Proveedor BuscarProveedor(String id) {
        ResultSet rs = null;
        Proveedor temp = new Proveedor();
        try {
            conexion.abrirConexion();
            if (id.length() <= 10) {
                rs = conexion.ejecutarConsulta("select * from public.buscarclientenatural('" + id.trim() + "')");
            } else if (id.length() > 10) {
                rs = conexion.ejecutarConsulta("select * from public.buscarclientejuridico('" + id.trim() + "')");
            } else {
                rs = null;
            }

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    temp.setIdProveedor(rs.getInt(1));
                    temp.setRazonSocial(rs.getString(2));
                    temp.setNombre(rs.getString(3));
  
                 
                    temp.setDireccion(rs.getString(6));
                    temp.setContacto(rs.getString(7));
                }
            }
            conexion.cerrarConexion();

            return temp;
        } catch (Exception e) {
            System.out.println(e.toString());
            if (conexion.isEstado()) {
                conexion.cerrarConexion();
            }
        } finally {
            conexion.cerrarConexion();
        }
        return null;
    }

}
