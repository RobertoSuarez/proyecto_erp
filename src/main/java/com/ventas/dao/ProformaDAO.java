/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.Producto;
import com.ventas.models.Proforma;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Asynchronous;

public class ProformaDAO {
    Conexion con;
    Proforma proforma = new Proforma();

    public ProformaDAO() {
        con = new Conexion();
    }
    
    public void IngresarProforma(Proforma ProformaDetalle) throws SQLException{
        String procedimiento;
        int estado;
        con.abrirConexion();
        try{
           procedimiento = "INSERT INTO public.proforma(idproforma, idcliente, id_empleado, fechacreacion, fechaactualizacion, fechaexpiracion, proformaterminada, aceptacioncliente, estado, fechaautorizacion, base12, base0, baseexcentoiva, iva12, ice, totalproforma)VALUES("+ ProformaDetalle.getId_proforma()
                   +","+ ProformaDetalle.getId_cliente() +","+ ProformaDetalle.getId_empleado()
                   +",'"+ ProformaDetalle.getFecha_creacion() +"','"+ ProformaDetalle.getFecha_actualizacion()
                   +"','"+ ProformaDetalle.getFecha_expiracion() +"',"+ ProformaDetalle.isProforma_terminada()
                   +","+ ProformaDetalle.isAceptacion_cliente() +",'"+ ProformaDetalle.getEstado()
                   +"','"+ ProformaDetalle.getFecha_autorizacion() +"',"+ ProformaDetalle.getBase12()
                   +","+ ProformaDetalle.getBase0() +","+ ProformaDetalle.getBase_excento_iva()
                   +","+ ProformaDetalle.getIva12() +","+ ProformaDetalle.getIce()
                   +","+ ProformaDetalle.getTotalproforma() +")";
           con.ejecutarConsulta(procedimiento);
               System.out.println("Proforma correctamente ingresada");
           
        }catch(Exception e){
            System.out.println(e.toString());
            if(con.isEstado())
                con.cerrarConexion();
        }
        finally{
            con.cerrarConexion();
        }
    }
        

    
    public void ingresarDetalleProforma(Producto prod,Proforma ProformaDetalle) throws SQLException{
        String procedimiento;
        int estado;
        con.abrirConexion();
        try{
          estado=0;
               procedimiento = "call insertarproductoproforma("+ ProformaDetalle.getDetalleproformacodigo()
                       +","+ProformaDetalle.getId_proforma()+","+prod.getCodigo()
                       +","+prod.getStock()+","+prod.getDescuento()+","+prod.getPrecioUnitario()+")";
               estado = con.ejecutarProcedimiento(procedimiento);
               if(estado>0){
                   System.out.println("Detalle de proforma ingresado correctamente");
               }
               else{
                   System.out.println("Problema al ingresar detalle proforma");
               }
        }catch(Exception e){
            System.out.println(e.toString());
            if(con.isEstado())
                con.cerrarConexion();
        }
        finally{
            con.cerrarConexion();
        }
    }
    public int codigoproforma(){
        ResultSet rs = null;
        int idVenta = 1;
        Proforma proformaactual = new Proforma();
        try{
            this.con.abrirConexion();
            rs = this.con.ejecutarConsulta("select * from public.proforma order by idproforma desc limit 1");
            while (rs.next()) {
                idVenta = rs.getInt(1) + idVenta+1;
            }
            return idVenta;
        }
        catch(Exception e){
             System.out.println(e.toString());
             if(con.isEstado())
                con.cerrarConexion();
        }
        finally{
            con.cerrarConexion();
            return idVenta;
        }
    }
    
}
