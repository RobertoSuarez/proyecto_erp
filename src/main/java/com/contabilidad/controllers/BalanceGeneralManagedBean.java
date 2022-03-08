package com.contabilidad.controllers;

import com.contabilidad.dao.BalanceGeneralDAO;
import com.contabilidad.models.BalanceGeneral;
import com.empresa.global.EmpresaMatrizDAO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Named;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named(value = "balanceGeneralMB")
@ViewScoped
public class BalanceGeneralManagedBean implements Serializable {

    private List<BalanceGeneral> balanceGeneral;
    private BalanceGeneralDAO balanceGeneralDAO;
    private SimpleDateFormat dateFormat;
    private Date fecha;
    private double pasivoPatrimonio;
    private String empresa;

    public BalanceGeneralManagedBean() {
        balanceGeneral = new ArrayList<>();
        balanceGeneralDAO = new BalanceGeneralDAO();
    }

    @PostConstruct
    public void init() {
        fecha = new Date();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        empresa = EmpresaMatrizDAO.getEmpresa().getNombre();
        balanceGeneral = balanceGeneralDAO.generateBalanceGeneral(dateFormat.format(fecha));
        pasivoPatrimonio = balanceGeneralDAO.sumaPasivoPatrimonio(dateFormat.format(fecha));
    }

    public void recibiendoFecha() {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        balanceGeneral = balanceGeneralDAO.generateBalanceGeneral(dateFormat.format(fecha));
        pasivoPatrimonio = balanceGeneralDAO.sumaPasivoPatrimonio(dateFormat.format(fecha));
    }

    public void exportpdf() throws IOException, JRException {
        List<BalanceGeneral> balanceGeneralString = new ArrayList<>();
        balanceGeneralString = this.balanceGeneral;

        for (int x = 0; x < balanceGeneralString.size(); x++) {
            double saldo = balanceGeneralString.get(x).getSaldo();
            String mySalgo = Double.toString(saldo);
            balanceGeneralString.get(x).setSaltoString(mySalgo);
        }
        for (int x = 0; x < balanceGeneralString.size(); x++) {
            if (balanceGeneralString.get(x).getNombre().charAt(1) == ' ') {
                double saldo = balanceGeneralString.get(x).getSaldo();

                String mySalgo = Double.toString(saldo);

                balanceGeneralString.get(x).setSaltoString("                                                       " + mySalgo);
            }
            if (balanceGeneralString.get(x).getNombre().charAt(3) == ' ') {
                double saldo = balanceGeneralString.get(x).getSaldo();
                String mySalgo = Double.toString(saldo);
             
balanceGeneralString
                .get(x).setSaltoString("                                   " + mySalgo);
            }
            if (balanceGeneralString.get(x).getNombre().charAt(5) == ' ') {
                double saldo = balanceGeneralString.get(x).getSaldo();
                String mySalgo = Double.toString(saldo);
                balanceGeneralString.get(x).setSaltoString("                    " + mySalgo);
            }
            if (balanceGeneralString.get(x).getNombre().charAt(7) == ' ') {
                double saldo = balanceGeneralString.get(x).getSaldo();
                String mySalgo = Double.toString(saldo);
                balanceGeneralString.get(x).setSaltoString(mySalgo);
            }

        }

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; "
                + "filename=balance-general-" + LocalDateTime.now().toString() + ".pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy",
                    new Locale("es_ES"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fecha", dateFormat.format(fecha));
            parametros.put("sumPasivoPatrimonio", pasivoPatrimonio + "");
            parametros.put("nombreEmpresa", empresa);

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/BalanceGeneral.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(balanceGeneralString)
            );

// exportamos a pdf.
            JasperExportManager
                    .exportReportToPdfStream(jasperPrint, stream);
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
        return cuenta.split(" ")[0].length() <= 1;
    }

    public boolean getBoldSub(String cuenta) {
        return cuenta.split(" ")[0].length() == 3;
    }

    public boolean getBoldE(String cuenta) {
        return cuenta.split(" ")[0].length() == 5;
    }

    public boolean getBoldEX(String cuenta) {
        return cuenta.split(" ")[0].length() > 5;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String estado() {
        String css = "";
        List<BalanceGeneral> bgs = new ArrayList<>();
        bgs = balanceGeneralDAO.getCalculoGrupo();
        double Activo = bgs.get(0).getSaldo();
        System.out.println(Activo + "----ESTADO----");

        if (Activo == pasivoPatrimonio) {
            css = "hidden";
        } else {
            css = "visible";
        }
        System.out.println(css + "-----" + Activo);
        return css;
    }
}
