package com.contabilidad.controllers;

import com.contabilidad.dao.DiarioDAO;
import com.contabilidad.models.Diario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class DiarioManagedBean implements Serializable {

    private List<Diario> diariosContables = new ArrayList<>();
    private DiarioDAO diarioAccess = new DiarioDAO();
    private Diario onSelectedDiaio = new Diario();

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        diariosContables = diarioAccess.getDiariosContables();
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", message);
    }

    public void openNewDiario() {
        onSelectedDiaio = new Diario();
    }

    public void saveDiario() {
        System.out.println(onSelectedDiaio.getIdDiario());
        if (onSelectedDiaio.getIdDiario() == 0) {
            if (!validateDiario(onSelectedDiaio.getNombre())) {
                if (onSelectedDiaio.getNombre().isEmpty() && onSelectedDiaio.getFechaApertura() == null
                        && onSelectedDiaio.getFechaCierre() == null && onSelectedDiaio.getDescripcion().isEmpty() || onSelectedDiaio.getDescripcion() == "") {
                    showWarn("Uno o mas datos no han sido ingresados");
                } else {
                    boolean result = diarioAccess.addNewDiario(onSelectedDiaio);
                    if (result) {
                        showInfo("Registro exitoso");
                        diariosContables = diarioAccess.getDiariosContables();
                        openNewDiario();
                        closeDialogModal();
                    } else {
                        showWarn("Hubo un error al registrar los datos");
                    }
                }
            } else {
                showWarn("El nombre que ingreso ya existe");
            }
        } else {
            if (!validateDiario()) {
                boolean result = diarioAccess.updateDiario(onSelectedDiaio);
                if (result) {
                    showInfo("Cambios realizados exitosamente");
                    diariosContables = diarioAccess.getDiariosContables();
                    openNewDiario();
                    closeDialogModal();
                } else {
                    showWarn("Hubo un error al registrar los datos");
                }
            } else {
                showWarn("No se han detectado cambio");
            }
        }
    }

    public void deleteDiario() {
        String result = diarioAccess.deleteDiario(onSelectedDiaio.getIdDiario());
        if (result.equals("Eliminacion Exitosa")) {
            showInfo("Diario Eliminado exitosamente");
            diariosContables = diarioAccess.getDiariosContables();
        } else {
            showWarn(String.format("El Diario %1$s tiene Asientos registrados", onSelectedDiaio.getNombre()));
        }
    }

    public boolean validateDiario() {
        String actualNombre = onSelectedDiaio.getNombre();
        Diario oldDiario = diarioAccess.getDiarioById(onSelectedDiaio.getIdDiario());
        if (actualNombre.equals(oldDiario.getNombre()) && onSelectedDiaio.getFechaApertura().equals(oldDiario.getFechaApertura())
                && onSelectedDiaio.getFechaCierre().equals(oldDiario.getFechaCierre())) {
            return true;
        } else {
            return false;
        }
    }

    public void closeDialogModal() {
        PrimeFaces.current().executeScript("PF('diarioFormDialog').hide()");
    }

    public boolean validateDiario(String nombre) {
        String oldNombre = diarioAccess.findDiarioByNombre(nombre).getNombre();
        if (nombre.equals(oldNombre)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Diario> getDiariosContables() {
        return diariosContables;
    }

    public void setDiariosContables(List<Diario> diariosContables) {
        this.diariosContables = diariosContables;
    }

    public Diario getOnSelectedDiaio() {
        return onSelectedDiaio;
    }

    public void setOnSelectedDiaio(Diario onSelectedDiaio) {
        this.onSelectedDiaio = onSelectedDiaio;
    }
}
