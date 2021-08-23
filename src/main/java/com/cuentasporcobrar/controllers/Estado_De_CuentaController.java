
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.Estado_De_CuentaDAO;
import com.cuentasporcobrar.models.Estado_De_Cuenta;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

@Named(value = "estado_De_CuentaController")
@ViewScoped
public class Estado_De_CuentaController implements Serializable {
    
    //Se Declaran las clases Estado_De_Cuenta y Estado_De_CuentaDAO
    Estado_De_Cuenta estado_De_Cuenta;
    Estado_De_CuentaDAO estado_De_CuentaDAO;

    //Declaro mi lista con el estado de cuenta general 
    //que van hacer cargadas en el datatable
    List<Estado_De_Cuenta> lista_Estado_De_Cuenta;

    //Declaro mi lista con el estado de cuenta de UN CLIENTE
    //que van hacer cargadas en el datatable
    List<Estado_De_Cuenta> lista_Estado_De_Cuenta_Cliente;//POSIBLEMENTE SE ELIMINE
    //POR QUE NECESITA QUE SE CARGUE EN LA MISMA TABLA DEL GENERAL.

    //Procedimiento principal(Se ejecuta una vez se llame al controlador)
    public Estado_De_CuentaController() {
        
        try{
        //Para que se carguen en el data table el estado de cuenta general 
        estado_De_CuentaDAO =new Estado_De_CuentaDAO();
        lista_Estado_De_Cuenta=new ArrayList<>();
        lista_Estado_De_Cuenta=estado_De_CuentaDAO.obtenerTodosLosEstadosCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }        
    }
    
    public void exportarPDF() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; filename=ReporteEstadoDeCuenta.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
//            Map<String, Object> parametros = new HashMap<String, Object>();
//            parametros.put("titulo", "Reporte desde java");
//            parametros.put("fecha", LocalDate.now().toString());

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/EstadoDeCuenta.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    null,
                    new JRBeanCollectionDataSource(this.lista_Estado_De_Cuenta)
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

    //Getters y Setters de las Listas
    //Inicio
    public List<Estado_De_Cuenta> getLista_Estado_De_Cuenta() {
        return lista_Estado_De_Cuenta;
    }

    public void setLista_Estado_De_Cuenta(List<Estado_De_Cuenta> lista_Estado_De_Cuenta) {
        this.lista_Estado_De_Cuenta = lista_Estado_De_Cuenta;
    }

    public List<Estado_De_Cuenta> getLista_Estado_De_Cuenta_Cliente() {
        return lista_Estado_De_Cuenta_Cliente;
    }

    public void setLista_Estado_De_Cuenta_Cliente(List<Estado_De_Cuenta> lista_Estado_De_Cuenta_Cliente) {
        this.lista_Estado_De_Cuenta_Cliente = lista_Estado_De_Cuenta_Cliente;
    }
    
    //Fin
}
