/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.Proveedor;
import com.cuentasporpagar.models.Condiciones;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ebert Guaranga
 * @see Conexion
 * @see Proveedor
 */

public class CondicionesDAO implements Serializable {

     Conexion conexion = new Conexion();
     private final Proveedor proveedor;
     List<Condiciones> lista;

     /**
      * Constructor en donde tendremos instaciada las variables lista, proveedor
      */
     public CondicionesDAO() {
          proveedor = new Proveedor();
          lista = new ArrayList<>();
     }

     /**
      * Funcion llenarP en donde listara el proveedor segun el parametro recibido aplicamos throws
      *
      * @param n variable
      * @return lista List
      * @throws SQLException validador
      */
     public List<Condiciones> llenarP(boolean n) throws SQLException {
          this.conexion.conectar();
          if (conexion.isEstado()) {
               try {
                    String sentencia = "SELECT c.descuento,c.diasneto,c.diasdescuento,\n"
                            + "c.cantdiasvencidos,c.descripcion,\n"
                            + "p.idproveedor, p.codigo,p.razonsocial,p.ruc,p.nombre,\n"
                            + "p.direccion,p.email,p.webpage,p.contacto,\n"
                            + "p.telefono,p.estado FROM condiciones c \n"
                            + "INNER JOIN proveedor p ON p.idproveedor = c.idproveedor "
                            + "where p.estado = " + n + " order by p.idproveedor";
                    ResultSet result = conexion.ejecutarSql(sentencia);
                    while (result.next()) {
                         Proveedor p = new Proveedor();
                         Condiciones c = new Condiciones();
                         c.setDescuento(result.getDouble("descuento"));
                         c.setDiasNeto(result.getInt("diasneto"));
                         c.setDiasDescuento(result.getInt("diasdescuento"));
                         c.setCantDiasVencidos(result.getInt("cantdiasvencidos"));
                         c.setDescripcion(result.getString("descripcion"));
                         p.setIdProveedor(result.getInt("idproveedor"));
                         p.setCodigo(result.getString("codigo"));
                         p.setRazonSocial(result.getString("razonsocial"));
                         p.setRuc(result.getString("ruc"));
                         p.setNombre(result.getString("nombre"));
                         p.setDireccion(result.getString("direccion"));
                         p.setEmail(result.getString("email"));
                         p.setWebPage(result.getString("webpage"));
                         p.setContacto(result.getString("contacto"));
                         p.setTelefono(result.getString("telefono"));
                         p.setEstado(result.getBoolean("estado"));
                         c.setProveedor(p);
                         lista.add(c);
                    }
               } catch (SQLException e) {
                    throw e;
               } finally {
                    conexion.desconectar();
               }
          }
          return lista;
     }

     /**
      * Habilitamos o deshabilitamos un proveedor segun su estado aplicamos throws
      *
      * @param d variable tipo string
      * @param n variable boleana
      */
     public void deshabilitar(String d, boolean n) {
          if (conexion.isEstado()) {
               try {
                    String cadena = "select habilitarproveedor(" + n + ",'" + d + "')";
                    conexion.ejecutarSql(cadena);                   
               } catch (Exception e) {
                    throw e;

               } finally {
                    conexion.desconectar();
               }

          }
     }

     /**
      * M??todo para insertar las condicones del proveedor aplica throws
      *
      * @throws SQLException validador
      * @param c ojeto
      */
     public void insertarCondiciones(Condiciones c) throws Exception {
          try {
               this.conexion.conectar();
               String sentencia = "INSERT INTO public.condiciones(descuento,"
                       + " diasneto, diasdescuento, cantdiasvencidos,"
                       + " descripcion, idproveedor)\n"
                       + " VALUES (0," + c.getDiasNeto() + ",0,"
                       + "" + c.getCantDiasVencidos() + ",'" + c.getDescripcion() + "',"
                       + "(SELECT idproveedor FROM proveedor ORDER BY idproveedor DESC LIMIT 1));";

               conexion.ejecutarSql(sentencia);
          } catch (Exception e) {
              System.out.println(e);
               throw e;
          } finally {
               this.conexion.desconectar();
          }
     }
     /**
      * M??todo para actualizar las condicones del proveedor aplica throws
      *
      * @throws SQLException validador
      * @param c objeto Condiciones
      * @param codigo variable tipo int
      */
     public void updateCondiciones(Condiciones c, int codigo) throws SQLException {

          try {
               this.conexion.conectar();
               String cadena = "UPDATE public.condiciones set "
                       + "diasneto = " + c.getDiasNeto() + ", "
                       + "cantdiasvencidos = " + c.getCantDiasVencidos() + ", "
                       + "descripcion = '" + c.getDescripcion() + "' "
                       + "WHERE idproveedor = " + codigo + "";
               conexion.ejecutarSql(cadena);
          } catch (Exception e) {
              throw  e;
          } finally {
               this.conexion.desconectar();
          }
     }
}
