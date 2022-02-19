/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.models;

import com.global.config.Conexion;
import interfaces.DAOCalendario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elect
 * Dao implement DAOCalendario
 */
public class ManagerCalendario extends Conexion implements DAOCalendario{

    
    private String Titulo;
    private LocalDate Fecha; // fecha del corte, que se genero el reporte
    // lista de las factuas a los cuales hay que pagar.
    private List<ReporteFactura> listFacturas;

    
    public ManagerCalendario() {
        //this.listCuotas = new ArrayList<Cuotas>();
        this.listFacturas = new ArrayList<>();
        try {
            this.listFacturas = this.listaFactura();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate Fecha) {
        this.Fecha = Fecha;
    }

    public List<ReporteFactura> getListFacturas() {
        return listFacturas;
    }

    public void setListFacturas(List<ReporteFactura> listFacturas) {
        this.listFacturas = listFacturas;
    }
    
    // getListFacturasFilter retorna las facturas que este dentro del rango
    // de las fechas que se le pasa
    public List<ReporteFactura> getListFacturasFilter(LocalDate inicio, LocalDate fin, boolean sinFecha) {
        System.out.println("lista de factura con filtro");
        List<ReporteFactura> resultado; // Lista donde se almacena las factura que pasan los parametros.
        resultado = new ArrayList<>();
        listFacturas.forEach(fac -> {
            // Condicionamos si es con fecha, o sin los para metros de fechas.
            if (sinFecha) {
                resultado.add(fac);
            }else {
                if (fac.getFechaVencimiento().isAfter(inicio) && fac.getFechaVencimiento().isBefore(fin)) {
                    resultado.add(fac);
                }
            }
            
        });
        
        //resultado.add(new ReporteFactura(LocalDate.now(), "ultimo", "003", 22, LocalDate.now().plusDays(2), "Contable"));
        return resultado;
    }

    @Override
    public ManagerCalendario Selecionar() throws Exception {
        try {
            this.conectar();
            PreparedStatement st = this.connection.prepareStatement("SELECT * FROM facturas");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                
            }
            
        } catch(Exception e) {
            throw e;
        } finally {
            this.desconectar();
        }
        
        return null;
    }

    @Override
    public List<ReporteFactura> listaFactura() throws Exception {
        List<ReporteFactura> lista = new ArrayList<>();
        System.out.println("Conectado a la db");
        try {
            
            this.conectar();
            // Consulta.
            PreparedStatement st = this.connection.prepareStatement(
                    "SELECT idfactura, nfactura, descripcion, importe, pagado, fecha, vencimiento, estado, idproveedor, idasiento\n" +
                    "	FROM public.factura;");
            // Ejecución
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                ReporteFactura reporteFactura = 
                        new ReporteFactura(
                                rs.getObject("fecha", LocalDate.class), 
                                "root", 
                                rs.getString("nfactura"), 
                                rs.getFloat("importe"), 
                                rs.getObject("vencimiento", LocalDate.class), 
                                "Contable"
                        );
                lista.add(reporteFactura);
            }
            
        } catch(Exception e) {
            throw e;
        } finally {
            this.desconectar();
        }
        
        return lista;
    }
    
    
}
