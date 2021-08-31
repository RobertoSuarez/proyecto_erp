
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.Facturas_PendientesDAO;
import com.cuentasporcobrar.models.Facturas_Pendientes;
import com.empresa.global.EmpresaMatrizDAO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named(value = "facturas_PendientesController")
@ViewScoped
/**
 * Clase de tipo Controlador para las Facturas Pendientes. Se encarga de llevar toda
 * la lógica del negocio con respecto a las Facturas Pendientes.
 */
public class Facturas_PendientesController implements Serializable {

    //Declaro una variable para guardar el nombre de la empresa.
    private String empresa; 

    //Declaro mis Facturas_Pendientes y Facturas Pendientes DAO
    Facturas_Pendientes facturas_Pendientes;
    Facturas_PendientesDAO facturas_PendientesDAO;

    //Declaro mi lista de facturas pendientes
    List<Facturas_Pendientes> listaFacturas_Pendientes;

    //Declaro un arreglo con el total de la venta [0] y la cartera pendiente[1]
    double[] totalVentaCartera;

    /**
     * Constructor donde inicializamos nuestras variables declaradas.
     */
    public Facturas_PendientesController() {

        try {
            //Para cargar el data table con los datos de las facturas pendientes.
            facturas_PendientesDAO = new Facturas_PendientesDAO();
            listaFacturas_Pendientes = new ArrayList<>();
            listaFacturas_Pendientes = facturas_PendientesDAO.obtenerFacturasPendientes();

            //Para cargar el total de las ventas y la cartera pendiente.
            totalVentaCartera = facturas_PendientesDAO.obtenerTotalVentayCarteraPendiente();
            
            empresa = EmpresaMatrizDAO.getEmpresa().getNombre();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Método para exportar un PDF de las facturas pendientes.
     * @throws IOException Excepción que no controla un programador.
     * @throws JRException Expeción del JasperReport.
     */
    public void exportarPDF() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; "
                + "filename=ReporteFacturaPendiente.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {
            
            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("titulo", empresa);
            
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/FacturaPendiente.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.listaFacturas_Pendientes)
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

//Getters y Setters de las Listas
//Inicio
    public List<Facturas_Pendientes> getListaFacturas_Pendientes() {
        return listaFacturas_Pendientes;
    }

    public void setListaFacturas_Pendientes(List<Facturas_Pendientes> listaFacturas_Pendientes) {
        this.listaFacturas_Pendientes = listaFacturas_Pendientes;
    }

    public double[] getTotalVentaCartera() {
        return totalVentaCartera;
    }

    public void setTotalVentaCartera(double[] totalVentaCartera) {
        this.totalVentaCartera = totalVentaCartera;
    }

    //Fin
}
