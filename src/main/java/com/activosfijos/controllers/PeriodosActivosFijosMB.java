/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.controllers;

import com.activosfijos.dao.IntangibleDAO;
import com.activosfijos.dao.NoDepreciableDAO;
import com.activosfijos.dao.PeriodosActivosFijosDAO;
import com.activosfijos.model.ListaNoDepreciable;
import com.activosfijos.model.ListaPeriodosActivosFijos;
import com.activosfijos.model.ListarIntangible;
import com.cuentasporpagar.models.ManagerCalendario;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author desta
 */
@ManagedBean(name = "periodosactivosfijosMB")
@ViewScoped
public class PeriodosActivosFijosMB implements Serializable {

    PeriodosActivosFijosDAO periodosactivosfijosdao = new PeriodosActivosFijosDAO();
    NoDepreciableDAO depreciableDAO = new NoDepreciableDAO();
    IntangibleDAO intangibleDAO = new IntangibleDAO();
    List<ListaPeriodosActivosFijos> listaDepreciablesActivosFijos;
    List<ListaNoDepreciable> listaNoDepreciablesActivosFijos;
    List<ListarIntangible> listaIntangible;

    public PeriodosActivosFijosMB() {
    }

    @PostConstruct
    public void init() {

        try {
            this.listaDepreciablesActivosFijos = periodosactivosfijosdao.listarActivosDepreciablesReporte();
            this.listaNoDepreciablesActivosFijos = depreciableDAO.ListarNodepreciableReporte();
            this.listaIntangible = intangibleDAO.listaIntangiblesReporte();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<ListaPeriodosActivosFijos> getListaDepreciablesActivosFijos() {
        return listaDepreciablesActivosFijos;
    }

    public void setListaDepreciablesActivosFijos(List<ListaPeriodosActivosFijos> listaDepreciablesActivosFijos) {
        this.listaDepreciablesActivosFijos = listaDepreciablesActivosFijos;
    }

    public PeriodosActivosFijosDAO getPeriodosactivosfijosdao() {
        return periodosactivosfijosdao;
    }

    public void setPeriodosactivosfijosdao(PeriodosActivosFijosDAO periodosactivosfijosdao) {
        this.periodosactivosfijosdao = periodosactivosfijosdao;
    }

    // Metodo funcional para exportar pdf de ACTIVOS DEPRECIABLES
    public void exportPdfDepreciable() throws IOException, JRException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte generado"));

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        for (int x = 0; x < listaDepreciablesActivosFijos.size(); x++) {
            System.err.println(listaDepreciablesActivosFijos.get(x).getDetalle_de_activo() + "<---->");
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
            JasperPrint jasperPrint = JasperFillManager.fillReport(filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.listaDepreciablesActivosFijos)
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
//Metodo funcional para exportar pdf de ACTIVOS NO DEPRECIABLES

    public void exportPdfNoDepreciable() throws IOException, JRException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte generado"));

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        for (int x = 0; x < listaNoDepreciablesActivosFijos.size(); x++) {
            System.err.println(listaNoDepreciablesActivosFijos.get(x).getDetalle_de_activo() + "<---->");
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
                    .getRealPath("/PlantillasReportes/Simple_Blue_1.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.listaNoDepreciablesActivosFijos)
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
//Metodo funcional para exportar pdf de ACTIVOS INTANGIBLES

    public void exportPdfIntangible() throws IOException, JRException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte generado"));

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        for (int x = 0; x < listaIntangible.size(); x++) {
            System.err.println(listaIntangible.get(x).getDetalle_de_activo() + "<---->");
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
                    .getRealPath("/PlantillasReportes/Simple_Blue_2.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.listaIntangible)
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
