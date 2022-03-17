
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.Estado_De_CuentaDAO;
import com.cuentasporcobrar.models.Estado_De_Cuenta;
import com.cuentasporcobrar.models.Persona;
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
import org.primefaces.event.SelectEvent;

@Named(value = "estado_De_CuentaController")
@ViewScoped
/**
 * Clase de tipo Controlador para los Estados de Cuenta. Se encarga de llevar toda
 * la lógica del negocio con respecto a los Estados de Cuenta.
 */
public class Estado_De_CuentaController implements Serializable {
    
    //Se Declaran las clases Estado_De_Cuenta y Estado_De_CuentaDAO
    Estado_De_Cuenta estado_De_Cuenta;
    Estado_De_CuentaDAO estado_De_CuentaDAO;
    Persona persona;
    
    //Declaro una variable para guardar el nombre de la empresa.
    private String empresa; 
    
    //Variable con la identificacion;
    String identificacion = "";

    //Declaro mi lista con el estado de cuenta general 
    //que van hacer cargadas en el datatable
    List<Estado_De_Cuenta> lista_Estado_De_Cuenta;

    //Declaro mi lista con el estado de cuenta de UN CLIENTE
    //que van hacer cargadas en el datatable
    List<Estado_De_Cuenta> lista_Estado_De_Cuenta_Cliente;//POSIBLEMENTE SE ELIMINE
    //POR QUE NECESITA QUE SE CARGUE EN LA MISMA TABLA DEL GENERAL.

    /**
     * Constructor que inicializa algunas variables declaradas.
     */
    public Estado_De_CuentaController() {
        
        try{
        //Para que se carguen en el data table el estado de cuenta general 
        estado_De_CuentaDAO =new Estado_De_CuentaDAO();
        lista_Estado_De_Cuenta=new ArrayList<>();
        persona = new Persona();
        empresa = EmpresaMatrizDAO.getEmpresa().getNombre();
        lista_Estado_De_Cuenta=estado_De_CuentaDAO.obtenerTodosLosEstadosCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }        
    }
    
    public void CargarValoresPorCliente(){
        lista_Estado_De_Cuenta=estado_De_CuentaDAO.obtenerEstadosCuentaDeCliente(persona.getIdCliente());
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
                + "filename=ReporteEstadoDeCuenta.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("titulo", empresa);

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/estado_cuenta.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
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
    
    public void onRowSelect(SelectEvent<Persona> event) {
        try {
            this.identificacion = event.getObject().getIdentificacion();
            persona.setIdCliente(event.getObject().getIdCliente());
            persona=event.getObject();
            System.out.println(this.identificacion);
            System.out.println(persona.getIdCliente());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
}
