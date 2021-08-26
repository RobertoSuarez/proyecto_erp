package com.contabilidad.controllers;

import com.contabilidad.dao.CuentaDAO;
import com.contabilidad.dao.SubGrupoDAO;
import com.contabilidad.models.Cuenta;
import com.contabilidad.models.SubGrupo;
import com.primefaces.Messages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named(value = "cuentaMB")
@ViewScoped
public class CuentaManagedBean implements Serializable {

    private CuentaDAO cuentaDAO;
    private List<Cuenta> listaCuentas;
    private List<SubGrupo> subGrupos = new ArrayList<>();
    private Cuenta onselectedCuenta;
    private SubGrupoDAO subGrupoDAO;

    public CuentaManagedBean() {
        cuentaDAO = new CuentaDAO();
        listaCuentas = new ArrayList<>();
        subGrupoDAO = new SubGrupoDAO();
    }

    @PostConstruct
    public void init() {
        listaCuentas = cuentaDAO.getCuentas();
        subGrupos = subGrupoDAO.getSubGrupos();
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

    public void newCuenta() {
        onselectedCuenta = new Cuenta();
        subGrupos = subGrupoDAO.getSubGrupos();
    }

    public void saveCuentaContable() {
        if (onselectedCuenta.getIdcuenta() == 0) {
            if (!onselectedCuenta.getNombre().isEmpty() && !onselectedCuenta.getCodigo().equals("")) {
                boolean result = cuentaDAO.insert(onselectedCuenta);
                if (result) {
                    showInfo("Registro de cuenta Exitoso");
                    listaCuentas = cuentaDAO.getCuentas();
                    closeDialog("cuentaFormDialog");
                } else {
                    showWarn("Hubo un error al registrar la cuenta contable");
                }
            } else {
                showWarn("Uno o mas datos no han sido ingresados");
            }
        } else {
            if (!validateCuenta()) {
                if (!onselectedCuenta.getNombre().isEmpty() && !onselectedCuenta.getCodigo().equals("")) {
                    boolean result = cuentaDAO.update(onselectedCuenta);
                    if (result) {
                        showInfo("Cambios realizado exitosamente");
                        listaCuentas = cuentaDAO.getCuentas();
                        closeDialog("cuentaFormDialog");
                    } else {
                        showWarn("Hubo un error al registrar la cuenta contable");
                    }
                } else {
                    showWarn("Uno o mas datos no han sido ingresados");
                }
            }else {
                showWarn("No se han detectado cambio");
            }
        }
    }

    public void deleteCuenta() {
        if (cuentaDAO.delete(onselectedCuenta.getIdcuenta())) {
            showInfo("Eliminacion exitosa");
            listaCuentas.remove(onselectedCuenta);
        } else {
            showWarn("No se puede eliminar la cuenta, existen uno o mas datos referenciados");
        }
    }

    public boolean validateCuenta() {
        String actualNombre = onselectedCuenta.getNombre();
        Cuenta oldCuenta = cuentaDAO.getCuentaById(onselectedCuenta.getIdcuenta());
        if (actualNombre.equals(oldCuenta.getNombre())) {
            return true;
        } else {
            return false;
        }
    }
    
    public void form() {
        if (cuentaDAO.isReference(onselectedCuenta.getIdcuenta())) {
            System.out.println("no se puede modificar");
            Messages.showWarn("No se puede modificar, porque ya esta "
                    + "referenciado a otras cuentas");
            return;
        }
        PrimeFaces.current().executeScript("PF('cuentaFormDialog').show();");
    }

    private void closeDialog(String widget) {
        PrimeFaces.current().executeScript(String.format("PF('%1$s').hide()", widget));
    }

    public void onChangeSubgrupo() {
        if (onselectedCuenta.getIdsubgrupo() != 0) {
            subGrupos.forEach((sg) -> {
                if (sg.getId() == onselectedCuenta.getIdsubgrupo()) {
                    onselectedCuenta.setCodigo(sg.getCodigo());
                }
            });
            int result = getCountCuentasReference(onselectedCuenta.getCodigo().trim());
            onselectedCuenta.setCodigo(onselectedCuenta.getCodigo().trim() + "." + result);
        } else {
            onselectedCuenta.setCodigo("");
        }
    }

    public int getCountCuentasReference(String codigo) {
        int count = 0;
        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getCodigo().substring(0, 3).equals(codigo)) {
                count++;
            }
        }
        return count + 1;
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public Cuenta getOnselectedCuenta() {
        return onselectedCuenta;
    }

    public void setOnselectedCuenta(Cuenta onselectedCuenta) {
        this.onselectedCuenta = onselectedCuenta;
    }

    public List<SubGrupo> getSubGrupos() {
        return subGrupos;
    }

    public void setSubGrupos(List<SubGrupo> subGrupos) {
        this.subGrupos = subGrupos;
    }

}
