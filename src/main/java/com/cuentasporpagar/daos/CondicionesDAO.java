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

/**
 *
 * @author ebert
 */
public class CondicionesDAO implements Serializable {

     Conexion conexion = new Conexion();
     private Proveedor proveedor;

     public ArrayList<Condiciones> llenarCondiciones() throws Exception {
          ArrayList<Condiciones> lista = new ArrayList<Condiciones>();
          try {
               this.conexion.Conectar();

               String sentencia = "SELECT c.descuento,c.diasneto,c.diasdescuento,"
                       + "c.cantdiasvencidos,c.descripcion,"
                       + "p.idproveedor, p.codigo,p.razonsocial,p.ruc,p.nombre,"
                       + "p.direccion,p.email,p.webpage,p.contacto,"
                       + "p.telefono,p.estado FROM condiciones c "
                       + "INNER JOIN proveedor p ON p.idproveedor = c.idproveedor order by p.idproveedor";
               PreparedStatement prs = conexion.getCnx().prepareStatement(sentencia);
               ResultSet result = prs.executeQuery();
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
               this.conexion.cerrarConexion();
          }
          return lista;
     }
     
     public void insertarCondiciones(Condiciones c) throws Exception {
          try {this.conexion.Conectar();
               String sentencia = "INSERT INTO public.condiciones(descuento,"
                       + " diasneto, diasdescuento, cantdiasvencidos,"
                       + " descripcion, idproveedor)\n"
                       + " VALUES ("+c.getDescuento()+","+c.getDiasNeto()+","+c.getDiasDescuento()+","
                       + ""+c.getCantDiasVencidos()+",'"+c.getDescripcion()+"',"
                       + "(SELECT idproveedor FROM proveedor ORDER BY idproveedor DESC LIMIT 1));";
               
               conexion.insertar(sentencia);
               System.out.print(sentencia);

          } catch (SQLException e) {
               throw e;

          } finally {
               this.conexion.cerrarConexion();
          }

     }
     public void updateCondiciones(Condiciones c, int codigo) throws SQLException{
           System.out.println("ENTRANDO A EDITAR CONDICIONES");
          try{
               this.conexion.Conectar();
               String cadena = "UPDATE public.condiciones set "
                       + "descuento = "+c.getDescuento()+", "
                       + "diasneto = "+c.getDiasNeto()+", "
                       +"diasdescuento = "+c.getDiasDescuento()+","
                       + "cantdiasvencidos = "+c.getCantDiasVencidos()+", "
                       + "descripcion = '"+c.getDescripcion()+"' "
                       + "WHERE idproveedor = "+codigo+"";
               conexion.ejecutar(cadena);
               System.out.print(cadena);
          }catch( SQLException e){
             
               System.err.print(e);
          }
          finally{
               this.conexion.cerrarConexion();
          }
     }
}
