/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.Producto;
import com.ventas.models.Proforma;
import com.ventas.models.DetalleProforma;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Asynchronous;
import com.ventas.dao.ClienteVentaDao;
import com.ventas.models.ClienteVenta;

public class ProformaDAO {

    Conexion con;
    Proforma proforma = new Proforma();
    DetalleProforma detalleprof;
    
    ClienteVentaDao clienteDao = new ClienteVentaDao();
    ClienteVenta cliente = new ClienteVenta();
    

    public ProformaDAO() {
        con = new Conexion();
    }

    public void IngresarProforma(Proforma ProformaDetalle) throws SQLException {
        String procedimiento;
        int estado;
        con.abrirConexion();
        try {
            procedimiento = "INSERT INTO public.proforma(idproforma, idcliente, id_empleado, fechacreacion, fechaactualizacion, fechaexpiracion, proformaterminada, aceptacioncliente, estado, fechaautorizacion, base12, base0, baseexcentoiva, iva12, ice, totalproforma)VALUES(" + ProformaDetalle.getId_proforma()
                    + "," + ProformaDetalle.getId_cliente() + "," + ProformaDetalle.getId_empleado()
                    + ",'" + ProformaDetalle.getFecha_creacion() + "','" + ProformaDetalle.getFecha_actualizacion()
                    + "','" + ProformaDetalle.getFecha_expiracion() + "'," + ProformaDetalle.isProforma_terminada()
                    + "," + ProformaDetalle.isAceptacion_cliente() + ",'" + ProformaDetalle.getEstado()
                    + "','" + ProformaDetalle.getFecha_autorizacion() + "'," + ProformaDetalle.getBase12()
                    + "," + ProformaDetalle.getBase0() + "," + ProformaDetalle.getBase_excento_iva()
                    + "," + ProformaDetalle.getIva12() + "," + ProformaDetalle.getIce()
                    + "," + ProformaDetalle.getTotalproforma() + ")";
            con.ejecutarConsulta(procedimiento);
            System.out.println("Proforma correctamente ingresada");
            con.cerrarConexion();

        } catch (Exception e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        } finally {
            con.cerrarConexion();
        }
    }

    public void ingresarDetalleProforma(Producto prod, Proforma ProformaDetalle) throws SQLException {
        String procedimiento;
        ResultSet rs;
        int estado,codigo;
        con.abrirConexion();
        try {
            codigo= codigodetalleproforma();
            estado = 0;
            procedimiento = "INSERT INTO public.detalleproforma(" +
"	iddetalleproforma, idproforma, codprincipal, cantidad, descuento, precio)" +
"	VALUES ("+ codigo +","+ ProformaDetalle.getId_proforma() + ","+ prod.getCodigo() 
                    + ","+ prod.getStock() +","+ prod.getDescuento() +","+ prod.getPrecioUnitario() +");";
            rs = con.ejecutarConsulta(procedimiento);
            if (rs == null) {
                System.out.println("Detalle de proforma incorrectamente");
            } else {
                System.out.println("Detalle proforma ingresado correctamente");
            }
            con.cerrarConexion();
        } catch (Exception e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        } finally {
            con.cerrarConexion();
        }
    }

    public int codigoproforma() {
        ResultSet rs = null;
        int idVenta = 1;
        try {
            this.con.abrirConexion();
            rs = this.con.ejecutarConsulta("select * from public.proforma order by idproforma desc limit 1");
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
            }
            this.con.cerrarConexion();
            return idVenta;
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        } finally {
            con.cerrarConexion();
            return idVenta;
        }
    }
    
    public int codigodetalleproforma() {
        ResultSet rs = null;
        int idVenta = 1;
        try {
            this.con.abrirConexion();
            rs = this.con.ejecutarConsulta("select * from public.detalleproforma order by iddetalleproforma ASC");
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
            }
            this.con.cerrarConexion();
            return idVenta;
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        } finally {
            con.cerrarConexion();
            return idVenta;
        }
    }

    public List<Proforma> retornarProformas() throws SQLException {
        ResultSet rs;
        String consulta,estado;
        con.abrirConexion();
        List<Proforma> listadocs= new ArrayList<>();
        try {
            consulta="SELECT * FROM public.proforma ORDER BY idproforma ASC ";
            rs=con.consultar(consulta);
            con.conex.close();
             if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");
                
                while (rs.next()) {
                    Proforma prof = new Proforma();
                    this.cliente = new ClienteVenta();
                    this.clienteDao=new ClienteVentaDao();
                    prof.setId_proforma(rs.getInt(1));
                    prof.setId_cliente(rs.getInt(2));
                    this.cliente=this.clienteDao.BuscarClientePorId(prof.getId_cliente());
                    prof.setNombreCliente(this.cliente.getNombre());
                    prof.setId_empleado(rs.getInt(3));
                    prof.setFecha_creacion(rs.getString(4));
                    prof.setFecha_actualizacion(rs.getString(5));
                    prof.setFecha_expiracion(rs.getString(6));
                    prof.setProforma_terminada(rs.getBoolean(7));
                    prof.setAceptacion_cliente(rs.getBoolean(8));
                    estado="P";
                    if(rs.getString(9).trim().equals(estado)){
                        estado="Pendiente";
                        prof.setEstado(estado);
                    }
                    else{
                        estado="A";
                        if(rs.getString(9).trim().equals(estado)){
                            estado="Aceptada";
                            prof.setEstado(estado);
                        }
                        else{
                            estado="R";
                            if(rs.getString(9).trim().equals(estado)){
                                estado="Rechazado";
                                prof.setEstado(estado);
                            }
                            else{
                                System.out.println("Fallo al cargar estado");
                                prof.setEstado(rs.getString(9));
                            }
                        }
                    }
                    prof.setFecha_autorizacion(rs.getString(10));
                    prof.setBase12(rs.getFloat(11));
                    prof.setBase0(rs.getFloat(12));
                    prof.setBase_excento_iva(rs.getFloat(13));
                    prof.setIva12(rs.getFloat(14));
                    prof.setIce(rs.getFloat(15));
                    prof.setTotalproforma(rs.getFloat(16));
                    listadocs.add(prof);
                    System.out.println("Proforma en lista");
                }
            }
             con.cerrarConexion();
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        } finally {
            con.cerrarConexion();
        }
        return listadocs;
    }
    
    public List<DetalleProforma> listaDetalleProforma(Proforma prof) throws SQLException{
        ResultSet rs;
        String consulta;
        DetalleProforma details;
        List<DetalleProforma> listaitems= new ArrayList<>();
        consulta="Select * from public.detalleproforma where idproforma="+ prof.id_proforma;
        con.abrirConexion();
        try{
            rs=con.ejecutarConsulta(consulta);
            if(rs==null){
                System.out.print("No existe ninguna proforma en la Base de datos");
            }
            else{
                while(rs.next()){
                    details = new DetalleProforma();
                    details.setIdDetalle(rs.getInt(1));
                    details.setIdProforma(rs.getInt(2));
                    details.setCodigoProducto(rs.getInt(3));
                    details.setCantidad(rs.getInt(4));
                    details.setDescuento(rs.getFloat(5));
                    details.setPrice(rs.getFloat(6));
                    listaitems.add(details);
                }
            }
            con.cerrarConexion();
        }
        catch(Exception e){
            if(con.isEstado()){
                con.cerrarConexion();
            }
        }
        finally{
            con.cerrarConexion();
        }
        con.cerrarConexion();
        return listaitems;
        
    }
    
    public void cambiarEstadoProforma(String estado, int idproforma) throws SQLException{
        String consulta;
        ResultSet rs;
        con.abrirConexion();
        try{
            consulta="UPDATE public.proforma SET estado= '"+ estado.toString() +"' WHERE idproforma = "+ idproforma +";";
            rs = con.ejecutarConsulta(consulta);
            if(rs==null){
                System.out.print("No se pudo cambiar el estado");
            }
            else{
                System.out.print("Estado cambiado con exito");
            }
        }
        catch(Exception e){
            if(con.isEstado()){
                con.cerrarConexion();
            }
        }
        finally{
            con.cerrarConexion();
        }
        con.cerrarConexion();
    }

}
