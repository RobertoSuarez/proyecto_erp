/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.controllers;

import com.activosfijos.dao.PeriodosActivosFijosDAO;
import com.activosfijos.model.ListaPeriodosActivosFijos;
import com.cuentasporpagar.daos.FacturaDAO;
import com.cuentasporpagar.models.ManagerCalendario;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;

/**
 *
 * @author desta
 */
@ManagedBean(name = "periodosactivosfijosMB")
@ViewScoped
public class PeriodosActivosFijosMB implements Serializable {
    
    PeriodosActivosFijosDAO periodosactivosfijosdao =new PeriodosActivosFijosDAO();
    
    List<ListaPeriodosActivosFijos> listaPeriodosActivosFijos ;

    public PeriodosActivosFijosMB() {
    }

    @PostConstruct
    public void init() {
        
        try {
            this.listaPeriodosActivosFijos = periodosactivosfijosdao.listarperiodosactivofijo();
        } 
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    public List<ListaPeriodosActivosFijos> getListaPeriodosActivosFijos() {
        return listaPeriodosActivosFijos;
    }

    public void setListaPeriodosActivosFijos(List<ListaPeriodosActivosFijos> listaPeriodosActivosFijos) {
        this.listaPeriodosActivosFijos = listaPeriodosActivosFijos;
    }
    
 

    public PeriodosActivosFijosDAO getPeriodosactivosfijosdao() {
        return periodosactivosfijosdao;
    }

    public void setPeriodosactivosfijosdao(PeriodosActivosFijosDAO periodosactivosfijosdao) {
        this.periodosactivosfijosdao = periodosactivosfijosdao;
    }
    
    
        // Metodo funcional para exportar pdf
    public void exportpdf(int anio) throws IOException, JRException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte generado"));
        //RequestContext requestContext = RequestContext.getCurrentInstance();
        //requestContext.execute("window.print();");
        //PrimeFaces.current().executeScript("reportebalanceactivosfijos("+anio+");");

        ListaPeriodosActivosFijos periodo = new ListaPeriodosActivosFijos();
        listaPeriodosActivosFijos.forEach(p -> {
            if (anio == p.getAnio()) {
                periodo.setAnio(p.getAnio());
                periodo.setMonto_total(p.getMonto_total());
                periodo.setInicio(p.getInicio());
                periodo.setFin(p.getFin());
                periodo.setTotal_depreciables(p.getTotal_depreciables());
                periodo.setTotal_no_depreciables(p.getTotal_no_depreciables());
                periodo.setTotal_agotables(p.getTotal_agotables());
                periodo.setTotal_intangibles(p.getTotal_intangibles());
            }
        });
        
        List<ListaPeriodosActivosFijos> lista = listaPeriodosActivosFijos.stream().filter(p -> p.getAnio()==anio).collect(Collectors.toList());
    
        
        
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; filename=Reporte.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {
            ManagerCalendario mc = new ManagerCalendario();

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("titulo", "Reporte desde java");
            parametros.put("fecha", LocalDate.now().toString());
            parametros.put("año", periodo.getAnio());

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/activos_fijos.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(lista)
            );

            // exportamos a pdf.
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

            stream.flush();
            stream.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            // enviamos la respuesta.
            fc.responseComplete();

            System.out.println("fin proccess");
        }
    }
    
    
}
