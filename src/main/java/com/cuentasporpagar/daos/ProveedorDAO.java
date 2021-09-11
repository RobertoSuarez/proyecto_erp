/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.Proveedor;
import java.sql.SQLException;

/**
 *
 * @author ebert
 * @see Conexion
 * @see Proveedor
 */
public class ProveedorDAO extends Conexion {

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
      * Método para insertar un proveedor recibiendo un parámetro Dicha clase implementa try and
      * catch
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
               System.out.print(cadena);
               conexion.Ejecutar2(cadena);
               conexion.cerrarConexion();

          } catch (SQLException e) {

          } finally {
               conexion.cerrarConexion();
          }
     }

     /**
      * Método para actualizar un proveedor recibiendo dos parametro Dicha clase implementa throws
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

}
