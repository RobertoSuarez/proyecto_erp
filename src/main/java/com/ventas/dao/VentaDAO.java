/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Venta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VentaDAO {

    private Venta venta;
    private Conexion con;

    public VentaDAO() {
    }

    public VentaDAO(Venta venta) {
        this.venta = venta;
        this.con = new Conexion();
    }

    public Venta BuscarVenta(int id) {
        ResultSet rs;
        Venta venta = new Venta();
        try {
            this.con.abrirConexion();
            rs = con.ejecutarConsulta("select * from public.venta where idVenta = " + id + " LIMIT 1;");

            if (rs != null) {
                System.out.println("Venta nula");
            } else {
                while (rs.next()) {
                    venta.setIdVenta(rs.getInt(1));
                    venta.setIdCliente(rs.getInt(2));
                    venta.setIdEmpleado(rs.getInt(3));
                    venta.setIdFormaPago(rs.getInt(4));
                    venta.setIdDocumento(rs.getInt(5));
                    venta.setSucursal(rs.getInt(6));
                    venta.setFechaVenta(rs.getTimestamp(7));
                    venta.setPuntoEmision(rs.getInt(8));
                    venta.setSecuencia(rs.getInt(9));
                    venta.setAutorizacion(rs.getString(10));
                    venta.setFechaEmision(rs.getTimestamp(11));
                    venta.setFechaAutorizacion(rs.getTimestamp(12));
                    venta.setBase12(rs.getDouble(13));
                    venta.setBase0(rs.getDouble(14));
                    venta.setIce(rs.getDouble(17));
                    venta.setTotalFactura(rs.getDouble(18));
                }
                this.con.cerrarConexion();
                return venta;
            }
        } catch (Exception e) {

        }finally{
            this.con.cerrarConexion();
        }
        return null;
    }

    public void GuardarVenta(Venta v) throws SQLException {
        
        this.con.abrirConexion();
        System.out.println("Guardando venta . . .");
//        int idNuevo = 0;
//        ResultSet rs = null;
//
//        rs = con.ejecutarConsulta("select idventa from public.venta order by idventa desc limit 1");
//        while (rs.next()) {
//            idNuevo = rs.getInt(1);
//        }
//
//        idNuevo += 1;
//
//        if (idNuevo <= 0) {
//            System.out.println("No se obtuvo el id de venta");
//        } else {
//            rs = null;
//            String sql1 = "INSERT INTO public.venta(idventa, idcliente, id_empleado, idformasdepago, idestadodocumento, id_sucursal, fechaventa, puntoemision, secuencia,autorizacion, fechaemision, fechaautorizacion, base12, base0, iva12, ice, totalfactura)";
//            String sql2 = String.format(" VALUES (%s, %2s, %3s, %4s, %5s, %6s, '%7s', %8s, %9s, '%10s', '%11s', '%12s', %13s, %14s, %15s, %16s, %17s;", idNuevo, venta.getCliente().getIdCliente(),
//                    v.getIdEmpleado(), v.getIdFormaPago(), v.getIdDocumento(), v.getSucursal(), v.getFechaVenta().toLocalDateTime().toLocalDate().toString(),
//                    v.getPuntoEmision(), v.getSecuencia(), v.getAutorizacion(), v.getFechaEmision().toLocalDateTime().toLocalDate().toString(),
//                    v.getFechaAutorizacion().toLocalDateTime().toLocalDate().toString(), v.getBase12(), v.getBase0(), v.getIva(), v.getIce(), v.getTotalFactura());
//            System.out.println(sql1 + sql2);
        //rs = con.ejecutarConsulta(sql1 + sql2);
        this.con.cerrarConexion();
    }
}
