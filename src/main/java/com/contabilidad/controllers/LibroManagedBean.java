package com.contabilidad.controllers;

import com.contabilidad.dao.DiarioDAO;
import com.contabilidad.dao.ImformeContableDAO;
import com.contabilidad.models.Diario;
import com.contabilidad.models.Libro;
import com.empresa.global.EmpresaMatrizDAO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

@Named
@ViewScoped
public class LibroManagedBean implements Serializable {

    private List<Libro> libros = new ArrayList<>();
    private ImformeContableDAO imformes = new ImformeContableDAO();
    private DiarioDAO diarioDAO = new DiarioDAO();
    private static DecimalFormat toTwoDecimal = new DecimalFormat("#.##");
    
    private String empresa = EmpresaMatrizDAO.getEmpresa().getNombre();

    private double totalSaldoDebe;
    private double totalSaldoHaber;
    private double saldoTotal;

    private Diario onSelectedDiario;
    private List<Diario> diarios;

    //Opciones para creacion de pdf
    
    @PostConstruct
    public void mainLibroMayor() {
        llenarLibro();
        loadDiarios();
    }

    public void loadDiarios() {
        onSelectedDiario = new Diario();
        diarios = diarioDAO.getDiariosContables();
    }

    private void llenarLibro() {
        libros = imformes.getImformeLibroMayor();
        saldoTotal = getSaldoTotal(libros);
    }

    public double calculateSaldoCuenta(String codigo) {
        double saldoDeudor = 0;
        double saldoAcreedor = 0;
        for (Libro item : libros) {
            if (item.getCodigo().equals(codigo)) {
                saldoDeudor += item.getDebe();
                saldoAcreedor += -item.getHaber();
            }
        }
        return saldoDeudor + saldoAcreedor;
    }

    public String ConverTwoDecimal(double value) {
        return toTwoDecimal.format(value);
    }

    public double getSaldoTotal(List<Libro> libro) {
        totalSaldoDebe = 0;
        totalSaldoHaber = 0;
        saldoTotal = 0;
        libros.forEach((l) -> {
            totalSaldoDebe += l.getDebe();
            totalSaldoHaber += l.getHaber();
            l.setSaldo(l.getDebe() + l.getHaber());
        });
        return totalSaldoDebe - totalSaldoHaber;
    }

    public LocalDate getDateNow() {
        LocalDate date = LocalDate.now();
        return date;
    }

    public void filtrateLibroMayor() {
        if (onSelectedDiario.getIdDiario() != 0) {
            libros = imformes.filtrateLibroByDiario(onSelectedDiario.getIdDiario());
            saldoTotal = getSaldoTotal(libros);
        } else {
            llenarLibro();
        }
    }

    public void exportpdf() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition",String.format("attachment; filename=LibroMayor.pdf"));

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("totaldebe", ConverTwoDecimal(totalSaldoDebe));
            parametros.put("totalhaber", ConverTwoDecimal(totalSaldoHaber));
            parametros.put("saldototal", ConverTwoDecimal(saldoTotal));
            parametros.put("nombreempresa", empresa);

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/imformelibromayor.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.libros)
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
        }
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    public List<Libro> getLibros() {
        return libros;
    }

    public double getTotalSaldoDebe() {
        return totalSaldoDebe;
    }

    public void setTotalSaldoDebe(double totalSaldoDebe) {
        this.totalSaldoDebe = totalSaldoDebe;
    }

    public double getTotalSaldoHaber() {
        return totalSaldoHaber;
    }

    public void setTotalSaldoHaber(double totalSaldoHaber) {
        this.totalSaldoHaber = totalSaldoHaber;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public Diario getOnSelectedDiario() {
        return onSelectedDiario;
    }

    public void setOnSelectedDiario(Diario onSelectedDiario) {
        this.onSelectedDiario = onSelectedDiario;
    }

    public List<Diario> getDiarios() {
        return diarios;
    }

    public void setDiarios(List<Diario> diarios) {
        this.diarios = diarios;
    }

}
