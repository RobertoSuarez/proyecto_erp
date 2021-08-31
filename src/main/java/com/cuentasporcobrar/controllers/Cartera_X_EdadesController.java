/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.Cartera_X_EdadesDAO;
import com.cuentasporcobrar.daos.PersonaDAO;
import com.cuentasporcobrar.models.Cartera_X_Edades;
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
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Una clase Cartera_X_EdadesController que se va a encargar de la lógica de
 * negocio que lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */

@Named(value = "cartera_X_EdadesController")
@ViewScoped
public class Cartera_X_EdadesController implements Serializable {

    //Se Declaran las clases Cartera_X_Edades y Cartera_X_EdadesDAO.
    Cartera_X_Edades cartera_X_Edades;
    Cartera_X_EdadesDAO cartera_X_EdadesDAO;
    
    //Declaro una variable para guardar el nombre de la empresa.
    private String empresa; 

    //Declaro mi lista de la cartera por edades de todos los clientes.
    List<Cartera_X_Edades> lista_Cartera_X_Edades;

    /*Esta clase nos permitirá reutilizar el codigo para cargar los clientes en
      el componente select one de la vista CarteraxEdadesDeVencimiento. */
    Persona persona;
    PersonaDAO personaDAO;

    //Lista con todos los clientes.
    List<SelectItem> listaCliente;

    //Declaro mi lista con la sumatoria.
    List<Cartera_X_Edades> listaSum_Cartera_X_Edades;

    /**
     * Constructor que instancia mis clases declaradas.
     */
    public Cartera_X_EdadesController() {

        //Inicializo mi clase Cartera_X_EdadesDAO.
        cartera_X_EdadesDAO = new Cartera_X_EdadesDAO();

        /*Inicializo mi listaCliente y guardo todos lo clientes
              que obtengo de un metodo en mi clase cartera_X_EdadesDAO.*/
        lista_Cartera_X_Edades = new ArrayList<>();
        lista_Cartera_X_Edades = cartera_X_EdadesDAO.obtenerCarteraxEdades();

        //Carga la sumatoria de todas las ventas.
        listaSum_Cartera_X_Edades = new ArrayList<>();

        /*Recibe un parámetros que será el id del cliente, en caso de ser -1 
          se carga la suma de todos los clientes. */
        listaSum_Cartera_X_Edades = cartera_X_EdadesDAO.obtenerSumCarteraxEdades(-1);
        empresa = EmpresaMatrizDAO.getEmpresa().getNombre();
    }

    /**
     * Se carga el listado de los clientes en un componente de primefaces 
     * (select one).
     * @return Listado de los clientes.
     */
    public List<SelectItem> getListaCliente() {
        try {
            
            //Se inicializa el listaCliente.
            listaCliente = new ArrayList<>();
            
            //Se inicializa la clase PersonaDAO.
            personaDAO = new PersonaDAO();
            
            //Se instancia una nueva lista auxiliar.
            List<Persona> p = personaDAO.obtenerNombresClientes();
            listaCliente.clear();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaCliente;
    }

    /**
     * Método para exportar en pdf.
     * @throws IOException
     * @throws JRException
     */
    public void exportarPDF() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; "
                + "filename=ReporteCarteraxEdadesDeVencimiento.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("titulo", empresa);

            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/CarteraxEdades.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.lista_Cartera_X_Edades)
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
    public List<Cartera_X_Edades> getLista_Cartera_X_Edades() {
        return lista_Cartera_X_Edades;
    }

    public void setLista_Cartera_X_Edades(List<Cartera_X_Edades> lista_Cartera_X_Edades) {
        this.lista_Cartera_X_Edades = lista_Cartera_X_Edades;
    }

    public List<Cartera_X_Edades> getListaSum_Cartera_X_Edades() {
        return listaSum_Cartera_X_Edades;
    }

    public void setListaSum_Cartera_X_Edades(List<Cartera_X_Edades> listaSum_Cartera_X_Edades) {
        this.listaSum_Cartera_X_Edades = listaSum_Cartera_X_Edades;
    }

    //FIN
}
