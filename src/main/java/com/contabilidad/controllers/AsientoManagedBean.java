package com.contabilidad.controllers;

import com.contabilidad.dao.AsientoDAO;
import com.contabilidad.dao.DiarioDAO;
import com.contabilidad.dao.MovimientoDAO;
import com.contabilidad.models.Asiento;
import com.contabilidad.models.Diario;
import com.contabilidad.models.Movimiento;
import com.contabilidad.models.SubCuenta;
import com.seguridad.models.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class AsientoManagedBean implements Serializable {

    private List<Asiento> asientos;
    private Asiento currentAsiento;

    private AsientoDAO asientoDAO;
    private MovimientoDAO movimientoDAO;
    private DiarioDAO diarioAccess;

    private Date fechaCreacion;
    private Date fechaCierre;
    private List<SubCuenta> subCuentas = new ArrayList<>();
    private Movimiento selectedMovimiento;
    private double totalDebe;
    private double totalHaber;
    List<Diario> diarios = new ArrayList<>();

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
    private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

    @PostConstruct
    public void main() {
        asientoDAO = new AsientoDAO();
        movimientoDAO = new MovimientoDAO();
        diarioAccess = new DiarioDAO();
        asientos = new ArrayList<>();
        currentAsiento = new Asiento();
        loadElements();

        System.out.println("########## Pasa algo");
        try {
            Usuario userResult = (Usuario) httpSession.getAttribute("username");
            System.out.println("########## " + userResult.getNombre());
        } catch (Exception e) {
            try {
                ec.redirect("/proyecto_erp");
            } catch (Exception ex) {
                
            }
            System.out.println("########## Error al traer los datos");
        }
    }

    @Asynchronous
    public void loadElements() {
        asientos = asientoDAO.getAsientosContables();
        List<Movimiento> movimientos = movimientoDAO.getAllMovimientos();
        asientos.forEach(a -> orderMovimientoByAsiento(a, movimientos));
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void orderMovimientoByAsiento(Asiento asientosContables, List<Movimiento> movimientos) {
        List<Movimiento> movimientoAux = new ArrayList<>();
        movimientos.forEach(m -> {
            if (m.getIdAsiento() == asientosContables.getIdAsiento()) {
                movimientoAux.add(m);
                asientosContables.setMovimientos(movimientoAux);
            }
        });
    }

    public void setMovimientos() {
        currentAsiento.setMovimientos(movimientoDAO.getMovimientoByAsiento(0));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    public void newAsientoContable() {
        if (currentAsiento.getIdAsiento() == 0) {
            if (!currentAsiento.getDocumento().isEmpty() && !currentAsiento.getDetalle().isEmpty()
                    && currentAsiento.getFechaCreacion() != null && currentAsiento.getFechaCierre() != null
                    && currentAsiento.getMovimientos().size() > 1 && currentAsiento.getIdDiario() != 0
                    && !verifyMovimientos(currentAsiento.getMovimientos())) {
                if (totalDebe == totalHaber && totalDebe != 0 && totalHaber != 0) {
                    currentAsiento.setTotal(Double.toString(totalDebe));
                    asientoDAO.addAsientoContable(currentAsiento);
                    currentAsiento.getMovimientos().forEach(m -> movimientoDAO.updateMovimientos(m, movimientoDAO.setMovimientoDefault()));
                    currentAsiento = new Asiento();
                    fechaCreacion = new Date();
                    fechaCierre = new Date();
                    showInfo("Se ha registrado un nuevo Asiento");
                    openNewAsiento();
                    loadElements();
                } else {
                    showWarn("Los valores del debe y el haber deben coincidir, y ser diferentes a cero");
                }

            } else {
                showWarn("Uno o mas datos no han sido registrados");
            }
        } else {
            if (totalDebe == totalHaber) {
                if (ValidateChange()) {
                    currentAsiento.setTotal(Double.toString(totalDebe));
                    asientoDAO.editAsientoContable(currentAsiento, currentAsiento.getIdAsiento());
                    currentAsiento.getMovimientos().forEach(m -> movimientoDAO.updateMovimientos(m, currentAsiento.getIdAsiento()));
                    showInfo("Cambios Realizados Correctamente");
                    closeDialogModal();
                    openNewAsiento();
                    loadElements();
                } else {
                    showWarn("No se detectaron cambios");
                    closeDialogModal();
                    openNewAsiento();
                    loadElements();
                }
            } else {
                showWarn("Los valores totales deben Coincidir");
            }
        }
    }

    public String nombreCuenta(Movimiento movimiento) {
        SubCuenta cuenta = asientoDAO.getCuentaByID(movimiento.getIdSubcuenta());

        String codigo = cuenta.getCodigo();
        String nombre = cuenta.getNombre();

        if (cuenta.getCodigo() == null) {
            codigo = "Seleccione";
            nombre = "una Subcuenta";
        }
        return codigo + " " + nombre;
    }

    public void openNewAsiento() {
        currentAsiento = new Asiento();
        subCuentas = asientoDAO.getCuentasContables();
        diarios = diarioAccess.getDiariosContables();
        currentAsiento.setNumero(generateNumeroAsiento());
        currentAsiento.setMovimientos(new ArrayList<>());
        totalDebe = 0;
        totalHaber = 0;
    }

    private String generateNumeroAsiento() {
        int num = asientoDAO.getCountAsientos();
        if (num > 8) {
            return "ASC-" + (num + 1);
        } else {
            return "ASC-0" + (num + 1);
        }
    }

    public void onFechaCreacionSelected(SelectEvent<Date> event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(event.getObject());
        try {
            Date fechaCre = format.parse(date);
            this.currentAsiento.setFechaCreacion(fechaCre);
        } catch (Exception e) {

        }
    }

    public void onFechaCierreSelected(SelectEvent<Date> event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(event.getObject());
        try {
            Date fechaCierr = format.parse(date);
            this.currentAsiento.setFechaCierre(fechaCierr);
        } catch (Exception e) {

        }
    }

    public void updateTotalDebe() {
        double td = 0;
        for (int i = 0; i < currentAsiento.getMovimientos().size(); i++) {
            td += currentAsiento.getMovimientos().get(i).getDebe();
        }
        totalDebe = td;
    }

    public void updateTotalHaber() {
        double th = 0;
        for (int i = 0; i < currentAsiento.getMovimientos().size(); i++) {
            th += currentAsiento.getMovimientos().get(i).getHaber();
        }
        totalHaber = th;
    }

    public void cleanNotChangeAsientos() {
        currentAsiento.getMovimientos().forEach((m) -> {
            if (m.getIdMovimiento() == 0) {
                currentAsiento.getMovimientos().remove(m);
            }
        });
    }

    public void addNewFila() {
        Movimiento movimiento = new Movimiento();
        movimiento.setIdSubcuenta(0);
        movimiento.setIdMovimiento(0);
        currentAsiento.getMovimientos().add(movimiento);
        updateTotalDebe();
        updateTotalHaber();
    }

    public void deleteFila() {
        currentAsiento.getMovimientos().remove(this.selectedMovimiento);
        updateTotalDebe();
        updateTotalHaber();
    }

    public void calculateTotal() {
        updateTotalDebe();
        updateTotalHaber();
        subCuentas = asientoDAO.getCuentasContables();
        diarios = diarioAccess.getDiariosContables();
    }

    public void closeDialogModal() {
        PrimeFaces.current().executeScript("PF('detalleAsiento').hide()");
    }

    public boolean ValidateChange() {
        Asiento oldAsiento = asientoDAO.getAsientoById(currentAsiento.getIdAsiento());
        oldAsiento.setMovimientos(movimientoDAO.getMovimientoByAsiento(oldAsiento.getIdAsiento()));
        if (oldAsiento.getDetalle().equals(currentAsiento.getDetalle()) && oldAsiento.getFechaCierre().equals(currentAsiento.getFechaCierre())
                && oldAsiento.getFechaCreacion().equals(currentAsiento.getFechaCreacion())
                && oldAsiento.getIdDiario() == currentAsiento.getIdDiario()
                && oldAsiento.getEstado().equals(currentAsiento.getEstado()) && compareMovimientos(oldAsiento.getMovimientos())) {
            return false;
        } else {
            return true;
        }
    }

    public boolean verifyMovimientos(List<Movimiento> movimientos) {
        int counter = 0;
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).getTipoMovimiento() == null || movimientos.get(i).getIdSubcuenta() == 0) {
                counter++;
            }
        }
        return counter > 0;
    }

    public boolean compareMovimientos(List<Movimiento> movimientos) {
        int counter = 0;
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).getIdSubcuenta() == currentAsiento.getMovimientos().get(i).getIdSubcuenta()
                    && movimientos.get(i).getDebe() == currentAsiento.getMovimientos().get(i).getDebe()
                    && movimientos.get(i).getHaber() == currentAsiento.getMovimientos().get(i).getHaber()
                    && movimientos.get(i).getTipoMovimiento().equals(currentAsiento.getMovimientos().get(i).getTipoMovimiento())) {
                counter++;
            }
        }
        return counter >= movimientos.size();
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public Asiento getCurrentAsiento() {
        return currentAsiento;
    }

    public void setCurrentAsiento(Asiento currentAsiento) {
        this.currentAsiento = currentAsiento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public List<SubCuenta> getSubCuentas() {
        return subCuentas;
    }

    public void setSubCuentas(List<SubCuenta> subCuentas) {
        this.subCuentas = subCuentas;
    }

    public double getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(double totalDebe) {
        this.totalDebe = totalDebe;
    }

    public double getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(double totalHaber) {
        this.totalHaber = totalHaber;
    }

    public Movimiento getSelectedMovimiento() {
        return selectedMovimiento;
    }

    public void setSelectedMovimiento(Movimiento selectedMovimiento) {
        this.selectedMovimiento = selectedMovimiento;
    }

    public List<Diario> getDiarios() {
        return diarios;
    }

    public void setDiarios(List<Diario> diarios) {
        this.diarios = diarios;
    }
}
