/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.controllers;

import com.contabilidad.dao.EstadoResultadoDAO;
import com.contabilidad.models.EstadoResultado;
import com.empresa.global.EmpresaMatrizDAO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
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
 *
 * @author User
 */
@Named(value = "estadoResultadolMB")
@ViewScoped
public class EstadoResultadoManageBean implements Serializable {

    private List<EstadoResultado> estadoResultadoIn;
    private List<EstadoResultado> estadoResultadoEg;
    private List<EstadoResultado> estadoResultadoVen;
    private EstadoResultadoDAO estadoResultadoDAO;
    private ArrayList<EstadoResultado> informacionER;
    private List<EstadoResultado> infomacion;
    private SimpleDateFormat dateFormat;
    private Date fecha;
    private Date fecha2;
    private double ingresos;
    private double egresos;
    private double ventas;
    private double total;
    private String empresa;

    /**
     * Constructor de la clase en donde se inicializa las listas a
     * llenarteniendo un total de 4 listas
     */
    public EstadoResultadoManageBean() {
        estadoResultadoVen = new ArrayList<>();
        estadoResultadoEg = new ArrayList<>();
        estadoResultadoIn = new ArrayList<>();
        estadoResultadoDAO = new EstadoResultadoDAO();
        infomacion = new ArrayList<>();

    }

    /**
     * PosConstructor de la clase en donde se inicializa las listas a
     * llenarteniendo un total de 4 listas
     */
    @PostConstruct
    public void init() {
        fecha = new Date();
        fecha2 = new Date();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        empresa = EmpresaMatrizDAO.getEmpresa().getNombre();
//Ingresos, llena la lista dada por el dao
        estadoResultadoIn = estadoResultadoDAO.generateEstadoResultante(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));
//Egresos,llena la lista dada por el dao 
        estadoResultadoEg = estadoResultadoDAO.generateEstadoResultadoEg(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));
//Ventas, llena la lista dada por el dao
        estadoResultadoVen = estadoResultadoDAO.estadoResultadoVen(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));

//Resultado total de ingresos
        ingresos = estadoResultadoDAO.
                sumaIngresos(
                        dateFormat.format(fecha),
                        dateFormat.format(fecha2));
//Resultado total de egresos
        egresos = estadoResultadoDAO.
                sumaegresos(
                        dateFormat.format(fecha),
                        dateFormat.format(fecha2));
//Resultado total de ventas
        ventas = estadoResultadoDAO.
                costoventa(
                        dateFormat.format(fecha),
                        dateFormat.format(fecha2));
//Resultado total
        total = ingresos - (ventas + egresos);
        infomacion = estadoResultadoDAO.informacion(dateFormat.format(fecha), dateFormat.format(fecha2));
        System.out.println("Inicio");
        for (int x = 0; x < infomacion.size(); x++) {
            System.out.println(infomacion.get(x));
        }
        System.out.println("Fin");

    }

    /**
     * Metodo para recibir las fechas correspondientes para generar la
     * informacion de los estados resultantes
     */
    public void recibiendoFecha() {
        /**
         * damos el formato simple a nuestra fecha para que solo reciba el dia,
         * mes y año
         */
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//Ingresos, llena la lista dada por el dao
        estadoResultadoIn = estadoResultadoDAO.generateEstadoResultante(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));
//Egresos, llena la lista dada por el dao
        estadoResultadoEg = estadoResultadoDAO.generateEstadoResultadoEg(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));
//Ventas, llena la lista dada por el dao
        estadoResultadoVen = estadoResultadoDAO.estadoResultadoVen(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));
//Resultado total de Ingresos
        ingresos = estadoResultadoDAO.sumaIngresos(
                dateFormat.format(fecha),
                dateFormat.format(fecha2));
//Resultado total de Egresos
        egresos = estadoResultadoDAO.
                sumaegresos(
                        dateFormat.format(fecha),
                        dateFormat.format(fecha2));
//Resultado total de Ventas
        ventas = estadoResultadoDAO.
                costoventa(
                        dateFormat.format(fecha),
                        dateFormat.format(fecha2));
//Resultado total 
        total = ingresos - (ventas + egresos);
        infomacion = estadoResultadoDAO.informacion(dateFormat.format(fecha), dateFormat.format(fecha2));
        for (int x = 0; x < infomacion.size(); x++) {
            System.out.println(infomacion.get(x));
        }

    }

    public void exportpdf() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; "
                + "filename=estado-resultado-"
                + LocalDateTime.now().toString() + ".pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es_ES"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fecha", dateFormat.format(fecha));
            parametros.put("fecha2", dateFormat.format(fecha2));
            parametros.put("nombreEmpresa", empresa);

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/EstadoResultados.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrintIn = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.infomacion)
            );

            // exportamos a pdf.
            JasperExportManager.exportReportToPdfStream(
                    jasperPrintIn, stream);
            //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

            stream.flush();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            // enviamos la respuesta.
            fc.responseComplete();
        }
    }

    public boolean getBold(String cuenta) {
        return cuenta.split(" ")[0].length() <= 5;
    }

    public List<EstadoResultado> getInfomacion() {
        return infomacion;
    }

    public void setInfomacion(List<EstadoResultado> infomacion) {
        this.infomacion = infomacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public double getEgresos() {
        return egresos;
    }

    public void setEgresos(double egresos) {
        this.egresos = egresos;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public List<EstadoResultado> getEstadoResultadoIn() {
        return estadoResultadoIn;
    }

    public void setEstadoResultadoIn(List<EstadoResultado> estadoResultadoIn) {
        this.estadoResultadoIn = estadoResultadoIn;
    }

    public List<EstadoResultado> getEstadoResultadoEg() {
        return estadoResultadoEg;
    }

    public void setEstadoResultadoEg(List<EstadoResultado> estadoResultadoEg) {
        this.estadoResultadoEg = estadoResultadoEg;
    }

    public List<EstadoResultado> getEstadoResultadoVen() {
        return estadoResultadoVen;
    }

    public void setEstadoResultadoVen(List<EstadoResultado> estadoResultadoVen) {
        this.estadoResultadoVen = estadoResultadoVen;
    }

    public ArrayList<EstadoResultado> getInformacionER() {
        return informacionER;
    }

    public void setInformacionER(ArrayList<EstadoResultado> informacionER) {
        this.informacionER = informacionER;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
