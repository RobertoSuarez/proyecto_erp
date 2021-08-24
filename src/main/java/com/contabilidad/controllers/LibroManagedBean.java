package com.contabilidad.controllers;

import com.contabilidad.dao.DiarioDAO;
import com.contabilidad.dao.ImformeContableDAO;
import com.contabilidad.models.Diario;
import com.contabilidad.models.Libro;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

@Named
@ViewScoped
public class LibroManagedBean implements Serializable {

    private List<Libro> libros = new ArrayList<>();
    private ImformeContableDAO imformes = new ImformeContableDAO();
    private DiarioDAO diarioDAO = new DiarioDAO();

    private double totalSaldoDebe;
    private double totalSaldoHaber;
    private double saldoTotal;

    private Diario onSelectedDiario;
    private List<Diario> diarios;

    //Opciones para creacion de pdf
    private PDFOptions pdfOpt;
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

    @PostConstruct
    public void mainLibroMayor() {
        llenarLibro();
        loadDiarios();
        customizeLibroMayor();
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
        Date fecha = new Date();
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

    public void customizeLibroMayor() {
        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#CFFFFF");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("12");
        pdfOpt.setOrientation(PDFOrientationType.PORTRAIT);
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        System.out.println("Entra a esta instancia");
        Document pdf = (Document) document;

        pdf.open();

        pdf.addTitle("Libro Mayor: " + getDateNow());
        pdf.setPageSize(PageSize.A4);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "headerlibro.png";

        Image img = Image.getInstance(logo);
        img.scalePercent(30);
        
        pdf.add(img);
    }

    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }

    public void setPdfOpt(PDFOptions pdfOpt) {
        this.pdfOpt = pdfOpt;
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
