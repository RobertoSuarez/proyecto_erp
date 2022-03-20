/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.ReporteSimple;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author jaime
 */
public class ReporteVentaDAO implements Serializable {

    private Conexion con;
    private ResultSet result;

    public ReporteVentaDAO() {
        this.con = new Conexion();
    }

    public List<ReporteSimple> getProductosMasVendidos(String fecha1, String fecha2) {
        List<ReporteSimple> report = new ArrayList<>();
        try {
            String query = "select a.nombre, sum(dv.subtotal)::float(5) as Venta\n" +
                            "from public.venta v inner join detalleventa dv on v.idventa = dv.idventa\n" +
                            "inner join articulos a on a.id = dv.codprincipal\n" +
                            "where v.fechaventa BETWEEN '" + fecha1 + "' AND '" + fecha2 + "'\n" +
                            "group by a.nombre order by sum(dv.subtotal) desc;";
            result = con.ejecutarSql(query);

            while (result.next()) {
                ReporteSimple temp = new ReporteSimple();
                temp.setDato1(result.getString("nombre"));
                temp.setValor1(result.getFloat("venta"));
                report.add(temp);
            }
            
            return report;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }
        return null;
    }
    
    public List<ReporteSimple> getClientesTop(String fecha1, String fecha2) {
        List<ReporteSimple> report = new ArrayList<>();
        try {
            String query = "select cl.nombre_cliente, sum(v.totalfactura)::float(2)\n" +
                            "from public.venta v inner join detalleventa dv on v.idventa = dv.idventa\n" +
                            "inner join (select * from getclientesnaturales() union select * from getclientesjuridicos()) cl on cl.id_clientebuscado = v.idcliente\n" +
                            "where v.fechaventa BETWEEN '" + fecha1 + "' AND '" + fecha2 + "'\n" +
                            "group by cl.nombre_cliente order by sum(v.totalfactura) desc;";
            result = con.ejecutarSql(query);

            while (result.next()) {
                ReporteSimple temp = new ReporteSimple();
                temp.setDato1(result.getString("nombre_cliente"));
                temp.setValor1(result.getFloat("sum"));
                report.add(temp);
            }
            
            return report;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }
        return null;
    }
    
    public List<ReporteSimple> getCategoriasMasVendidos(String fecha1, String fecha2) {
        List<ReporteSimple> report = new ArrayList<>();
        try {
            String query = "select c.nom_categoria, sum(dv.subtotal)::float(5) as Venta\n" +
                            "from public.venta v inner join detalleventa dv on v.idventa = dv.idventa\n" +
                            "inner join articulos a on a.id = dv.codprincipal\n" +
                            "inner join categoria c on c.cod = a.id_categoria\n" +
                            "where v.fechaventa BETWEEN '" + fecha1 + "' AND '" + fecha2 + "'\n" +
                            "group by c.nom_categoria order by sum(dv.subtotal) desc;";
            result = con.ejecutarSql(query);

            while (result.next()) {
                ReporteSimple temp = new ReporteSimple();
                temp.setDato1(result.getString("nom_categoria"));
                temp.setValor1(result.getFloat("venta"));
                report.add(temp);
            }
            
            return report;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }
        return null;
    }

}
