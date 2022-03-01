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
            this.conexion.conectar();
            String cadena = "INSERT INTO public.proveedor(\n"
                    + "	codigo, razonsocial, ruc, nombre, direccion, email, webpage, contacto, telefono, estado)\n"
                    + "	VALUES ('" + p.getCodigo() + "','" + p.getRazonSocial() + "'"
                    + ",'" + p.getRuc() + "','" + p.getNombre() + "','" + p.getDireccion() + "'"
                    + ",'" + p.getEmail() + "','" + p.getWebPage() + "','" + p.getContacto() + "',"
                    + " '" + p.getTelefono() + "','" + p.isEstado() + "');";

            conexion.Ejecutar2(cadena);
            conexion.desconectar();

        } catch (Exception e) {

        } finally {
            conexion.desconectar();
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
            this.conexion.conectar();
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
            conexion.ejecutarSql(cadena);
            conexion.desconectar();
        } catch (Exception e) {
            throw e;
        } finally {
            this.conexion.desconectar();
        }

    }

    public List<Proveedor> ListarProveedor() {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            this.conexion.conectar();
            String query = "select * from proveedor;";
            ResultSet rs = this.conexion.ejecutarSql(query);
            //conexion.conex.close();
            while (rs.next()) {
                this.proveedor = new Proveedor();
                this.proveedor.setIdProveedor(rs.getInt(1));
                this.proveedor.setCodigo(rs.getString(2));
                this.proveedor.setRazonSocial(rs.getString(3));
                this.proveedor.setRuc(rs.getString(4));
                this.proveedor.setNombre(rs.getString(5));
                this.proveedor.setDireccion(rs.getString(6));
                this.proveedor.setEmail(rs.getString(7));
                this.proveedor.setWebPage(rs.getString(8));
                this.proveedor.setContacto(rs.getString(9));
                this.proveedor.setTelefono(rs.getString(10));
                this.proveedor.setEstado(rs.getBoolean(11));
                
                                

                
                proveedores.add(this.proveedor);
            }
            this.conexion.desconectar();
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.desconectar();
        }
        return proveedores;
    }

    public Proveedor BuscarProveedor(String identificacion) {
        ResultSet rs = null;
        Proveedor temp = new Proveedor();
        try {
            conexion.conectar();
            
            rs = conexion.ejecutarSql("select * from proveedor where where ruc = '" + identificacion.trim() + "'");

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    temp.setIdProveedor(rs.getInt(1));
                    temp.setCodigo(rs.getString(2));
                    temp.setRazonSocial(rs.getString(3));
                    temp.setRuc(rs.getString(4));
                    temp.setNombre(rs.getString(5));
                    temp.setDireccion(rs.getString(6));
                    temp.setEmail(rs.getString(7));
                    temp.setWebPage(rs.getString(8));
                    temp.setContacto(rs.getString(9));
                    temp.setTelefono(rs.getString(10));
                    temp.setEstado(rs.getBoolean(11));
                    
                }
            }
            conexion.desconectar();

            return temp;
        } catch (Exception e) {
            System.out.println(e.toString());
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
        } finally {
            conexion.desconectar();
        }
        return null;
    }
    public Proveedor BuscarProveedor(int idProveedor){
        
         ResultSet rs = null;
        Proveedor temp = new Proveedor();
        try {
            conexion.conectar();
            
            rs = conexion.ejecutarSql("select * from proveedor where idproveedor = " + idProveedor + "");

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    temp.setIdProveedor(rs.getInt(1));
                    temp.setCodigo(rs.getString(2));
                    temp.setRazonSocial(rs.getString(3));
                    temp.setRuc(rs.getString(4));
                    temp.setNombre(rs.getString(5));
                    
                    temp.setDireccion(rs.getString(6));
                    temp.setEmail(rs.getString(7));
                    temp.setWebPage(rs.getString(8));
                    temp.setContacto(rs.getString(9));
                    temp.setTelefono(rs.getString(10));
                    temp.setEstado(rs.getBoolean(11));
                    
                }
            }
            conexion.desconectar();

            return temp;
        } catch (Exception e) {
            System.out.println(e.toString());
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
        } finally {
            conexion.desconectar();
        }
        return null;  
    }
}
