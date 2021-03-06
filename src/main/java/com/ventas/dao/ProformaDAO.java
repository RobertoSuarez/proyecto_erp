/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.ProductoVenta;
import com.ventas.models.Proforma;
import com.ventas.models.DetalleProforma;
import com.ventas.models.Venta;
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
import javax.faces.application.FacesMessage;

public class ProformaDAO {

    Conexion con;
    Proforma proforma = new Proforma();
    DetalleProforma detalleprof;

    ClienteVentaDao clienteDao = new ClienteVentaDao();
    ClienteVenta cliente = new ClienteVenta();
    VentaDAO ventaDao = new VentaDAO();
    Venta venta = new Venta();

    ProductoVentaDAO productoDao = new ProductoVentaDAO();

    public ProformaDAO() {
        con = new Conexion();
    }

    public void IngresarProforma(Proforma ProformaDetalle) throws SQLException {
        String procedimiento;
        int estado;
        con.desconectar();
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
            con.ejecutarSql(procedimiento);
            con.desconectar();

        } catch (Exception e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
    }

    public void ingresarDetalleProforma(DetalleProforma prod, Proforma ProformaDetalle) throws SQLException {
        String procedimiento;
        ResultSet rs;
        int estado, codigo;
        con.desconectar();
        try {
            codigo = codigodetalleproforma();
            estado = 0;
            procedimiento = "INSERT INTO public.detalleproforma("
                    + "	iddetalleproforma, idproforma, codprincipal, cantidad, descuento, precio, subtotal)"
                    + "	VALUES (" + codigo + "," + ProformaDetalle.getId_proforma() + "," + prod.getCodigoProducto()
                    + "," + prod.getCantidad() + "," + prod.getDescuento() + "," + prod.getPrice() + "," + prod.getSubtotal() + ");";
            rs = con.ejecutarSql(procedimiento);
            if (rs == null) {
                System.out.println("Detalle de proforma incorrectamente");
            } else {
                System.out.println("Detalle proforma ingresado correctamente");
            }
            con.desconectar();
        } catch (Exception e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
    }

    public List<DetalleProforma> getDetalleProforma(int idProforma) {
        List<DetalleProforma> lista = new ArrayList<>();
        try {
            String query = "select * from public.detalleproforma where idproforma = " + idProforma + ";";
            System.out.println("Detalle: " + query);
            ResultSet rs = con.ejecutarSql(query);
            while (rs.next()) {
                lista.add(new DetalleProforma(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getDouble(6), productoDao.ObtenerProducto(rs.getInt("codprincipal")), rs.getDouble(7)));
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return null;
    }

    public int codigoproforma() {
        ResultSet rs = null;
        int idVenta = 1;
        try {
            this.con.desconectar();
            rs = this.con.ejecutarSql("select * from public.proforma order by idproforma desc limit 1");
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
            }
            this.con.desconectar();
            return idVenta;
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
            return idVenta;
        }
    }

    public int codigodetalleproforma() {
        ResultSet rs = null;
        int idVenta = 1;
        try {
            this.con.desconectar();
            rs = this.con.ejecutarSql("select * from public.detalleproforma order by iddetalleproforma ASC");
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
            }
            this.con.desconectar();
            return idVenta;
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
            return idVenta;
        }
    }

    public List<Proforma> retornarProformasPendientes() {
        ResultSet rs;
        String consulta, estado;
        con.desconectar();
        List<Proforma> listadocs = new ArrayList<>();
        try {
            consulta = "SELECT * FROM public.proforma where estado = 'P' and fechaexpiracion >= CURRENT_DATE order by fechacreacion desc, idproforma desc";
            rs = con.ejecutarSql(consulta);
            con.connection.close();
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                while (rs.next()) {
                    Proforma prof = new Proforma();
                    this.cliente = new ClienteVenta();
                    this.clienteDao = new ClienteVentaDao();
                    prof.setId_proforma(rs.getInt("idproforma"));
                    prof.setId_cliente(rs.getInt("idcliente"));
                    this.cliente = this.clienteDao.BuscarClientePorId(prof.getId_cliente());
                    prof.setNombreCliente(this.cliente.getNombre());
                    prof.setId_empleado(rs.getInt("id_empleado"));
                    prof.setFecha_creacion(rs.getString("fechacreacion"));
                    prof.setFecha_actualizacion(rs.getString("fechaactualizacion"));
                    prof.setFecha_expiracion(rs.getString("fechaexpiracion"));
                    prof.setProforma_terminada(rs.getBoolean(7));
                    prof.setAceptacion_cliente(rs.getBoolean(8));
                    prof.setEstado(rs.getString("estado"));
                    prof.setFecha_autorizacion(rs.getString("fechaautorizacion"));
                    prof.setBase12(rs.getFloat("base12"));
                    prof.setBase0(rs.getFloat("base0"));
                    prof.setBase_excento_iva(rs.getFloat("baseexcentoiva"));
                    prof.setIva12(rs.getFloat("iva12"));
                    prof.setIce(rs.getFloat("ice"));
                    prof.setTotalproforma(rs.getFloat("totalproforma"));
                    listadocs.add(prof);
                }
            }
            con.desconectar();
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
        return listadocs;
    }
    
    public void aceptarProforma(int idProforma){
        ResultSet rs;
        String consulta, estado;
        List<Proforma> listadocs = new ArrayList<>();
        try {
            consulta = "update public.proforma set estado = 'A' where idproforma = '" + idProforma + "';";
            rs = con.ejecutarSql(consulta);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            con.desconectar();
        }
    }
    
    public void setProformaFacturada(int idProforma){
        ResultSet rs;
        String consulta, estado;
        List<Proforma> listadocs = new ArrayList<>();
        try {
            consulta = "update public.proforma set estado = 'F' where idproforma = '" + idProforma + "';";
            rs = con.ejecutarSql(consulta);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            con.desconectar();
        }
    }
    
    public void rechazarProforma(int idProforma){
        ResultSet rs;
        String consulta, estado;
        List<Proforma> listadocs = new ArrayList<>();
        try {
            consulta = "update public.proforma set estado = 'R' where idproforma = '" + idProforma + "';";
            rs = con.ejecutarSql(consulta);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            con.desconectar();
        }
    }
    
    public List<Proforma> retornarProformasAprobadas() {
        ResultSet rs;
        String consulta, estado;
        con.desconectar();
        List<Proforma> listadocs = new ArrayList<>();
        try {
            consulta = "SELECT * FROM public.proforma where estado = 'A' and fechaexpiracion >= CURRENT_DATE order by fechacreacion desc, idproforma desc";
            rs = con.ejecutarSql(consulta);
            con.connection.close();
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                while (rs.next()) {
                    Proforma prof = new Proforma();
                    this.cliente = new ClienteVenta();
                    this.clienteDao = new ClienteVentaDao();
                    prof.setId_proforma(rs.getInt("idproforma"));
                    prof.setId_cliente(rs.getInt("idcliente"));
                    this.cliente = this.clienteDao.BuscarClientePorId(prof.getId_cliente());
                    prof.setNombreCliente(this.cliente.getNombre());
                    prof.setId_empleado(rs.getInt("id_empleado"));
                    prof.setFecha_creacion(rs.getString("fechacreacion"));
                    prof.setFecha_actualizacion(rs.getString("fechaactualizacion"));
                    prof.setFecha_expiracion(rs.getString("fechaexpiracion"));
                    prof.setProforma_terminada(rs.getBoolean(7));
                    prof.setAceptacion_cliente(rs.getBoolean(8));
                    prof.setEstado(rs.getString("estado"));
                    prof.setFecha_autorizacion(rs.getString("fechaautorizacion"));
                    prof.setBase12(rs.getFloat("base12"));
                    prof.setBase0(rs.getFloat("base0"));
                    prof.setBase_excento_iva(rs.getFloat("baseexcentoiva"));
                    prof.setIva12(rs.getFloat("iva12"));
                    prof.setIce(rs.getFloat("ice"));
                    prof.setTotalproforma(rs.getFloat("totalproforma"));
                    listadocs.add(prof);
                }
            }
            con.desconectar();
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
        return listadocs;
    }

    public Proforma getProformaById(int idProforma) {
        try {
            ResultSet rs = con.ejecutarSql("select * from public.proforma where idproforma = " + idProforma + ";");
            Proforma prof = new Proforma();
            while (rs.next()) {
                this.cliente = new ClienteVenta();
                this.clienteDao = new ClienteVentaDao();
                prof.setId_proforma(rs.getInt("idproforma"));
                prof.setId_cliente(rs.getInt("idcliente"));
                this.cliente = this.clienteDao.BuscarClientePorId(prof.getId_cliente());
                prof.setNombreCliente(this.cliente.getNombre());
                prof.setId_empleado(rs.getInt("id_empleado"));
                prof.setFecha_creacion(rs.getString("fechacreacion"));
                prof.setFecha_actualizacion(rs.getString("fechaactualizacion"));
                prof.setFecha_expiracion(rs.getString("fechaexpiracion"));
                prof.setProforma_terminada(rs.getBoolean(7));
                prof.setAceptacion_cliente(rs.getBoolean(8));
                prof.setEstado(rs.getString("estado"));
                prof.setFecha_autorizacion(rs.getString("fechaautorizacion"));
                prof.setBase12(rs.getFloat("base12"));
                prof.setBase0(rs.getFloat("base0"));
                prof.setBase_excento_iva(rs.getFloat("baseexcentoiva"));
                prof.setIva12(rs.getFloat("iva12"));
                prof.setIce(rs.getFloat("ice"));
                prof.setTotalproforma(rs.getFloat("totalproforma"));
            }
            return prof;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            con.desconectar();
        }
        return null;
    }

    public List<DetalleProforma> listaDetalleProforma(int id) throws SQLException {
        ResultSet rs;
        String consulta;
        DetalleProforma details;
        List<DetalleProforma> listaitems = new ArrayList<>();
        consulta = "Select * from public.detalleproforma where idproforma=" + id + ";";
        this.con.desconectar();
        try {
            rs = this.con.ejecutarSql(consulta);
            if (rs == null) {
                System.out.print("No existe ninguna proforma en la Base de datos");
            } else {
                while (rs.next()) {
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
            this.con.connection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
        return listaitems;

    }

    public void cambiarEstadoProforma(String estado, int idproforma) throws SQLException {
        String consulta;
        ResultSet rs;
        con.desconectar();
        try {
            consulta = "UPDATE public.proforma SET estado= '" + estado + "' WHERE idproforma = " + idproforma + ";";
            this.con.ejecutarSql(consulta);
            con.desconectar();
        } catch (Exception e) {
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
    }

    public int aceptarProforma(String estado, Proforma pr, String fecha) throws SQLException {
        List<DetalleProforma> detalle = new ArrayList<>();
        int codigoventa = 0;
        this.venta = new Venta();
        String consulta;
        con.desconectar();
        try {
            consulta = "UPDATE public.proforma SET estado= '" + estado + "' WHERE idproforma = " + pr.id_proforma + ";";
            this.con.ejecutarSql(consulta);
            this.venta.setBase0(pr.getBase0());
            this.venta.setBase12(pr.getBase12());
            this.venta.setIdCliente(pr.getId_cliente());
            this.venta.setIdEmpleado(1);
            this.venta.setIdFormaPago(2);
            this.venta.setIdDocumento(0);
            this.venta.setSucursal(1);
            this.venta.setFechaVenta(fecha);
            this.venta.setPuntoEmision(1);
            this.venta.setSecuencia(0);
            this.venta.setAutorizacion("849730964");
            this.venta.setFechaEmision(fecha);
            this.venta.setFechaAutorizacion(fecha);
            this.venta.setIva(pr.getIva12());
            this.venta.setIce(pr.getIce());
            this.venta.setTotalFactura(pr.totalproforma);
            codigoventa = this.ventaDao.GuardarVenta(this.venta);
            detalle = listaDetalleProforma(pr.id_proforma);
            int listSize = 0;
            if (codigoventa == 0) {
                System.out.println("No se pudo ingresar Factura #" + codigoventa);
            } else {
                System.out.println("Venta realizada con Factura #" + codigoventa);
                DetalleVentaDAO daoDetail = new DetalleVentaDAO();
                while (listSize < detalle.size()) {
                    int codProd = detalle.get(listSize).getCodigoProducto();
                    double qty = detalle.get(listSize).getCantidad();
                    double dsc = detalle.get(listSize).getDescuento();
                    double price = detalle.get(listSize).getPrice();
                    double sbttl = detalle.get(listSize).getSubtotal();

                    System.out.println(detalle.get(listSize).getCodigoProducto());
                    System.out.println(codigoventa + "-" + codProd + "-" + qty + "-" + dsc + "-" + price);
                    daoDetail.RegistrarProductos(codigoventa, codProd, qty, dsc, price, sbttl);
                    listSize += 1;
                }
            }
            con.desconectar();
        } catch (SQLException e) {
            if (con.isEstado()) {
                con.desconectar();
            }
        } finally {
            con.desconectar();
        }
        this.ventaDao.insertasiento(this.venta);
        return codigoventa;
    }

}
