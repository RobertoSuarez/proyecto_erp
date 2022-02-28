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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    private SimpleDateFormat dateFormat;
    private Date fecha2;
    PeriodosActivosFijosDAO periodosactivosfijosdao = new PeriodosActivosFijosDAO();

    List<ListaPeriodosActivosFijos> listaPeriodosActivosFijos;

    public PeriodosActivosFijosMB() {
    }

    @PostConstruct
    public void init() {

        fecha2 = new Date();
        try {
            this.listaPeriodosActivosFijos = periodosactivosfijosdao.listarperiodosactivofijo();
            for (int x = 0; x < listaPeriodosActivosFijos.size(); x++) {
                System.out.println(listaPeriodosActivosFijos.get(x));;
            }
        } catch (Exception ex) {
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
    public void exportpdf() throws IOException, JRException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte generado"));
        //RequestContext requestContext = RequestContext.getCurrentInstance();
        //requestContext.execute("window.print();");
        //PrimeFaces.current().executeScript("reportebalanceactivosfijos("+anio+");");

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        for (int x = 0; x < listaPeriodosActivosFijos.size(); x++) {
            System.err.println(listaPeriodosActivosFijos.get(x).getDetalle_de_activo()+"<---->");
        }
        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; filename=Reporte.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {
            ManagerCalendario mc = new ManagerCalendario();

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("nombreEmpresa", "ERP Contable");
            parametros.put("fecha2", LocalDate.now().toString());

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/Simple_Blue_Table_Based.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.listaPeriodosActivosFijos)
            );

            // exportamos a pdf.
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

            stream.flush();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            // enviamos la respuesta.
            fc.responseComplete();

            System.out.println("fin proccess");
        }
    }

}
