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
import java.time.LocalDate;
import java.util.List;

public class VentaDAO {

    private Venta venta;
    private Conexion con;

    public VentaDAO() {
        this.con = new Conexion();
    }

    public VentaDAO(Venta venta) {
        this.venta = venta;
        this.con = new Conexion();
    }

    public int GuardarVenta(Venta ventaActual) {
        try {
            ResultSet rs = null;

            this.con.abrirConexion();
            rs = this.con.consultar("select * from public.venta order by idventa desc limit 1");

            int idVenta = 1;
            int secuenciaActual = 1;
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
                secuenciaActual = rs.getInt(2) + 1;
            }
            ventaActual.setSecuencia(secuenciaActual);
            ventaActual.setIdVenta(idVenta);

            System.out.println(ventaActual.getIdVenta());

            String query = "INSERT INTO public.venta("
                    + "idventa, idcliente, id_empleado, idformasdepago, idestadodocumento, id_sucursal, fechaventa, puntoemision, secuencia,"
                    + "autorizacion, fechaemision, fechaautorizacion, base12, base0, baseexcentoiva, iva12, ice, totalfactura) "
                    + "VALUES(" + ventaActual.getIdVenta() + ", " + ventaActual.getIdCliente() + ", " + ventaActual.getIdEmpleado() + ", " + ventaActual.getIdFormaPago() + ", 1, 1, '" + ventaActual.getFechaVenta()
                    + "', 1, " + ventaActual.getSecuencia() + ", " + ventaActual.getAutorizacion() + ", '" + ventaActual.getFechaEmision() + "', '" + ventaActual.getFechaAutorizacion()
                    + "', " + ventaActual.getBase12() + ", " + ventaActual.getBase0() + ", 0, " + ventaActual.getIva() + ", " + ventaActual.getIce() + ", " + ventaActual.getTotalFactura() + ")";
            System.out.println(query);

            this.con.consultar(query);

            if (ventaActual.getIdFormaPago() == 1) {
                query = "select ingresar_plan_de_pago(" + ventaActual.getIdVenta() + ", " + ventaActual.getDiasCredito() + ", '" + ventaActual.getFechaVenta() + "', " 
                        + ventaActual.getTotalFactura() + ", 0.1)";
                System.out.println(query);
                this.con.consultar(query);
            }
            
            
            this.con.cerrarConexion();

            System.out.println("Venta Guardada exitosamente");

            return ventaActual.getIdVenta();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.cerrarConexion();
        }
        return 0;
    }

}
