/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.Clientes_Con_Sin_DeudaDAO;
import com.cuentasporcobrar.models.Clientes_Con_Sin_Deuda;
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

/**
 * Una clase Clientes_Con_Sin_DeudaController que se va a encargar de la 
 * lógica de negocio que lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */

@Named(value = "clientes_Con_Sin_DeudaController")
@ViewScoped
public class Clientes_Con_Sin_DeudaController implements Serializable {
    
    //Se Declaran las clases Clientes_Con_Sin_Deuda y Clientes_Con_Sin_DeudaDAO.
    Clientes_Con_Sin_Deuda clientes_Con_Sin_Deuda;
    Clientes_Con_Sin_DeudaDAO clientes_Con_Sin_DeudaDAO;

    /*Declaro mi lista de Clientes con y sin Deudas que van hacer cargadas en el
      datatable.*/
    List<Clientes_Con_Sin_Deuda> lista_Clientes_con_sin_deudas;
    
    //Declaro una lista para clientes con deudas.
    List<Clientes_Con_Sin_Deuda> lista_Clientes_Con_Deudas;
    
    //Declaro una lista para clientes sin deudas.
    List<Clientes_Con_Sin_Deuda> lista_Clientes_Sin_Deudas;

    /**
     * Constructor que instancia mis clases declaradas.
     */
    public Clientes_Con_Sin_DeudaController() {
        
        /*Para que carguen en el data table todos los cliente que tengan y no 
          tengan deudas.*/
        clientes_Con_Sin_DeudaDAO = new Clientes_Con_Sin_DeudaDAO();
        lista_Clientes_con_sin_deudas = new ArrayList<>();
        lista_Clientes_con_sin_deudas = clientes_Con_Sin_DeudaDAO.obtenerClientesConSinDeudas();

        //Lista con los todos los cliente CON deudas.
        lista_Clientes_Con_Deudas=new ArrayList<>();
        lista_Clientes_Con_Deudas=clientes_Con_Sin_DeudaDAO.obtenerClientesConDeudas();
        
        //Lista con los todos los cliente SIN deudas.
        lista_Clientes_Sin_Deudas=new ArrayList<>();
        lista_Clientes_Sin_Deudas=clientes_Con_Sin_DeudaDAO.obtenerClientesSinDeudas();

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
                + "filename=ReporteListaDeClientesConSinDeuda.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("titulo", "Empresa S.A");
            
            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/ListadoClienteAdeudados.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.lista_Clientes_con_sin_deudas)
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
    public List<Clientes_Con_Sin_Deuda> getLista_Clientes_con_sin_deudas() {
        return lista_Clientes_con_sin_deudas;
    }

    public void setLista_Clientes_con_sin_deudas(List<Clientes_Con_Sin_Deuda> lista_Clientes_con_sin_deudas) {
        this.lista_Clientes_con_sin_deudas = lista_Clientes_con_sin_deudas;
    }

    public List<Clientes_Con_Sin_Deuda> getLista_Clientes_Con_Deudas() {
        return lista_Clientes_Con_Deudas;
    }

    public void setLista_Clientes_Con_Deudas(List<Clientes_Con_Sin_Deuda> lista_Clientes_Con_Deudas) {
        this.lista_Clientes_Con_Deudas = lista_Clientes_Con_Deudas;
    }

    public List<Clientes_Con_Sin_Deuda> getLista_Clientes_Sin_Deudas() {
        return lista_Clientes_Sin_Deudas;
    }

    public void setLista_Clientes_Sin_Deudas(List<Clientes_Con_Sin_Deuda> lista_Clientes_Sin_Deudas) {
        this.lista_Clientes_Sin_Deudas = lista_Clientes_Sin_Deudas;
    }

    //Fin 
    
    /**
     * Método que se encarga de cargar clientes con deudas.
     */
     public void cargarClientesConDeudas(){
        try{
            lista_Clientes_con_sin_deudas=lista_Clientes_Con_Deudas;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que se encarga de cargar clientes sin deudas.
     */
    public void cargarClientesSinDeudas(){
        try{
            lista_Clientes_con_sin_deudas=lista_Clientes_Sin_Deudas;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
