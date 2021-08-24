
package com.contabilidad.controllers;

import com.contabilidad.dao.BalanceGeneralDAO;
import com.contabilidad.models.BalanceGeneral;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

@Named(value = "balanceGeneralMB")
@ViewScoped
public class BalanceGeneralManagedBean implements Serializable {
    private List<BalanceGeneral> balanceGeneral;
    private BalanceGeneralDAO balanceGeneralDAO;
    private SimpleDateFormat dateFormat;
    private Date fecha;
    private double pasivoPatrimonio;
    
    private PDFOptions pdfOpt;
    
    
    public BalanceGeneralManagedBean() {
        balanceGeneral = new ArrayList<>();
        balanceGeneralDAO = new BalanceGeneralDAO();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    }
    
    @PostConstruct
    public void init() {
        fecha = new Date();
        balanceGeneral = balanceGeneralDAO.generateBalanceGeneral(dateFormat.format(fecha));
        pasivoPatrimonio =balanceGeneralDAO.sumaPasivoPatrimonio(dateFormat.format(fecha));
    }
    
    public void recibiendoFecha() {
        balanceGeneral = balanceGeneralDAO.generateBalanceGeneral(dateFormat.format(fecha));
        pasivoPatrimonio = balanceGeneralDAO.sumaPasivoPatrimonio(dateFormat.format(fecha));
    }
    
    public void customizeLibroMayor() {
        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#CFFFFF");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("12");
        pdfOpt.setOrientation(PDFOrientationType.PORTRAIT);
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();

        pdf.addTitle("Informe Balance General: ");
        pdf.setPageSize(PageSize.A4);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "headerbalance.png";

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
    
    
    public boolean getBold(String cuenta) {
        return cuenta.split(" ")[0].length() <= 5;
    }

    public List<BalanceGeneral> getBalanceGeneral() {
        return balanceGeneral;
    }

    public void setBalanceGeneral(List<BalanceGeneral> balanceGeneral) {
        this.balanceGeneral = balanceGeneral;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPasivoPatrimonio() {
        return pasivoPatrimonio;
    }

    public void setPasivoPatrimonio(double pasivoPatrimonio) {
        this.pasivoPatrimonio = pasivoPatrimonio;
    }
    
    
}
