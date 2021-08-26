package com.contabilidad.controllers;

import com.contabilidad.dao.PlanContableDAO;
import com.contabilidad.models.Cuenta;
import com.contabilidad.models.CuentaContable;
import com.contabilidad.models.Grupo;
import com.contabilidad.models.SubCuenta;
import com.contabilidad.models.SubGrupo;
import com.primefaces.Messages;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named
@SessionScoped
public class PlanCuentas implements Serializable {

    private List<CuentaContable> cuentasContables;
    private List<Grupo> grupos;
    private List<SubGrupo> subgrupos;
    private List<Cuenta> cuentas;
    private CuentaContable cuentaContable;

    private String onSeletedGrupo;
    private String onSeletedSubgrupo;
    private String onSeletedCuenta;
    private String onSeletedSaldo;

    private Grupo grupo;
    private SubGrupo subGrupo;
    private SubCuenta subCuenta;
    private Cuenta cuenta;
    private PlanContableDAO contableDAO;
    private String codigo;

    // propiedades para registrar clasificación
    private Grupo intoGrupo;
    private SubGrupo intoSubgrupo;
    private Cuenta intoCuenta;

    // filter
    private String action;

    public PlanCuentas() {
        cuentasContables = new ArrayList<>();
        grupos = new ArrayList<>();
        subgrupos = new ArrayList<>();
        cuentas = new ArrayList<>();
        contableDAO = new PlanContableDAO();
        subCuenta = new SubCuenta();
        intoGrupo = new Grupo();
        intoSubgrupo = new SubGrupo();
        intoCuenta = new Cuenta();
    }

    @PostConstruct
    public void init() {
        cuentasContables = contableDAO.getSubCuentas();
        grupos = contableDAO.getGrupos();
        subCuenta = new SubCuenta();
    }

    public void onGrupoChange() {
        System.out.println("onSeletedGrupo: " + onSeletedGrupo);
        if (onSeletedGrupo != null && !onSeletedGrupo.trim().equals("0")) {
            System.out.println("########################Grupo: ");
            codigo = onSeletedGrupo;
            System.out.println("Codigo: " + codigo);
            System.out.println("param: " + (Integer.parseInt(codigo.trim()) - 1));
            grupo = grupos.get(Integer.parseInt(codigo.trim()) - 1);
            subgrupos = contableDAO.getSubGrupos(codigo);
        } else {
            codigo = "";
            subgrupos = new ArrayList<>();
            cuentas = new ArrayList<>();
            onSeletedCuenta = "0";
            onSeletedSubgrupo = "0";
        }
    }

    public void onSubGrupoChange() {
        System.out.println("onSeletedSubgrupo: " + onSeletedSubgrupo);
        if (onSeletedSubgrupo != null && !onSeletedSubgrupo.trim().equals("0")) {
            subgrupos.forEach(g -> {
                if (g.getId() == Integer.parseInt(onSeletedSubgrupo)) {
                    codigo = g.getCodigo();
                }
            });
            cuentas = contableDAO.getCuentas(onSeletedSubgrupo.trim());
        } else {
            if (codigo.length() > 1) {
                codigo = codigo.substring(0, 1);
            }
            onSeletedSubgrupo = "0";
            onSeletedCuenta = "0";
            cuentas = new ArrayList<>();
        }
    }

    public void onCuentaChange() {
        String codAux = codigo;
        if (onSeletedCuenta != null && !onSeletedCuenta.trim().equals("0")) {
            System.out.println("######## CuentaCodigo:" + onSeletedCuenta);
            cuentas.forEach(g -> {
                if (g.getIdcuenta() == Integer.parseInt(onSeletedCuenta)) {
                    codigo = g.getCodigo();
                }
            });
            int plus = contableDAO.getCountSubCuentas(onSeletedCuenta.trim()) + 1;
            codigo = codigo.trim() + "." + plus;
            //cuentas = contableDAO.getCuentas(codigo.substring(2));
        } else {
            codigo = codigo.substring(0, 3);
        }
    }

    public void registrarSubCuenta() {
        System.out.println("########## registrarsubcuenta");
        System.out.println("#### registrarsubcuenta: " + subCuenta.toString());
        if (!subCuenta.getNombre().isEmpty() && subCuenta.getTipo() != null) {
            String cuentaCodigo = onSeletedCuenta.trim();
            subCuenta.setCodigo(codigo);
            subCuenta.setCuenta(Integer.parseInt(cuentaCodigo));
            System.out.println("#### registrarsubcuenta: " + subCuenta.toString());
            contableDAO.insertSubCuenta(subCuenta);
            subCuenta = new SubCuenta();
            codigo = "";
            onSeletedGrupo = "0";
            subgrupos = new ArrayList<>();
            cuentas = new ArrayList<>();
            cuentasContables = contableDAO.getSubCuentas();
            showInfo("Se ha registrado la cuenta");
        } else {
            showWarn("Faltan rellenar campos");
        }
    }

    public void actualizarSubCuenta() {
        if (!subCuenta.getNombre().isEmpty()) {
            subCuenta.setId(cuentaContable.getId());
            if (contableDAO.updateSubCuenta(subCuenta)) {
                subCuenta = new SubCuenta();
                Messages.showInfo("Se ha actualizado la cuenta");
                cuentasContables = contableDAO.getSubCuentas();
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect("planCuentas.xhtml");
                } catch (IOException ex) {
                    System.out.println("Error al redirigir la pagina" + ex.getMessage());
                }
            }
        } else {
            Messages.showWarn("Faltan rellenar campos");
        }
    }

    /* Cambiar el nombre de la funcion por formAgregarGrupo */
    public void formAgregarGrupo() {
        System.out.println("############ Form Agregar Grupo ##########");
        if (Integer.parseInt(onSeletedGrupo.trim()) <= 0) {
            intoGrupo = new Grupo();
            intoGrupo.setCodigo(String.valueOf(grupos.size() + 1));
            PrimeFaces.current().executeScript("PF('dialogFormGrupo').show();");
        } else {
            showWarn("Ya tiene seleccionado un Grupo");
        }
    }

    public void formAgregarSubGrupo() {
        System.out.println("############ formAgregarSubGrupo ##########");
        System.out.println("Subgrupo: " + onSeletedSubgrupo.trim());

        if (onSeletedSubgrupo.trim().equals("0") && !onSeletedGrupo.trim().equals("0")) {
            System.out.println("pasamos al subgrupoooooo");
            intoSubgrupo = new SubGrupo();
            intoSubgrupo.setCodigo(onSeletedGrupo.trim() + "." + String.valueOf(subgrupos.size() + 1));
            intoSubgrupo.setGrupo(Integer.parseInt(onSeletedGrupo.trim()));
            PrimeFaces.current().executeScript("PF('dialogFormSubGrupo').show();");
        } else {
            Messages.showWarn("Ya tiene seleccionado un Subgrupo ó no ha seleccionado un Grupo");
        }
    }

    public void formAgregarCuenta() {
        System.out.println("############ formAgregarCuenta ##########");
        System.out.println("Cuenta: " + onSeletedCuenta.trim());

        if (onSeletedCuenta.trim().equals("0") && !onSeletedSubgrupo.trim().equals("0")) {
            System.out.println("pasamos a la cuentaaaaaaa");
            intoCuenta = new Cuenta();
            intoCuenta.setCodigo(codigo.trim() + "." + String.valueOf(cuentas.size() + 1));
            intoCuenta.setIdsubgrupo(Integer.parseInt(onSeletedSubgrupo.trim()));
            subgrupos.forEach(g -> {
                if (g.getId() == Integer.parseInt(onSeletedSubgrupo.trim())) {
                    subGrupo = g;
                }
            });
            PrimeFaces.current().executeScript("PF('dialogFormCuenta').show();");
        } else {
            showWarn("Ya tiene seleccionado una Cuenta ó no ha seleccionado un SubGrupo");
        }
    }

    public void registrarGrupo() {
        // que intoGrupo no este vacio
        if (!intoGrupo.getNombre().isEmpty()) {
            // verificar que no existe en la lista
            long count = grupos.stream().filter(
                    g -> g.getNombre().trim().toLowerCase()
                            .equals(intoGrupo.getNombre().trim().toLowerCase())
            ).count();
            if (count == 0) {
                // si no existe, agregarlo en la base de datos
                if (contableDAO.insertGrupo(intoGrupo) == 1) {
                    // si lo agrega actualizar la lista
                    grupos = contableDAO.getGrupos();
                    Messages.showInfo("Se ha registrado el grupo");
                    PrimeFaces.current().executeScript("PF('dialogFormGrupo').hide();");
                } else {
                    showWarn("Hubo un problema al registrar");
                }
            } else {
                showWarn("Ya existe el grupo " + intoGrupo.getNombre());
            }
        } else {
            showWarn("Campo nombre esta vacío");
        }
    }

    public void registrarSubGrupo() {
        System.out.println("############ Registrar subgrupo");
        System.out.println("Nombre: " + intoSubgrupo.getNombre());
        // que intoSubgrupo no este vacio
        if (!intoSubgrupo.getNombre().trim().isEmpty()) {
            // verificar que no existe en la lista
            long count = subgrupos.stream().filter(
                    g -> g.getNombre().trim().toLowerCase()
                            .equals(intoSubgrupo.getNombre().trim().toLowerCase())
            ).count();
            if (count == 0) {
                //si no existe, agregarlo en la base de datos
                if (contableDAO.insertSubGrupo(intoSubgrupo) == 1) {
                    //si lo agrega actualizar la lista
                    subgrupos = contableDAO.getSubGrupos(codigo);
                    Messages.showInfo("Se ha registrado el subgrupo");
                    PrimeFaces.current().executeScript("PF('dialogFormSubGrupo').hide();");
                } else {
                    showWarn("Hubo un problema al registrar");
                }
            } else {
                showWarn("Ya existe el subgrupo " + intoSubgrupo.getNombre());
            }
        } else {
            showWarn("Campo nombre esta vacío");
        }
    }

    public void registrarCuenta() {
        System.out.println("############ Registrar cuenta");
        System.out.println("Nombre: " + intoCuenta.getNombre());
        // que intoSubgrupo no este vacio
        if (!intoCuenta.getNombre().trim().isEmpty()) {
            // verificar que no existe en la lista
            long count = cuentas.stream().filter(
                    g -> g.getNombre().trim().toLowerCase()
                            .equals(intoCuenta.getNombre().trim().toLowerCase())
            ).count();
            if (count == 0) {
                //si no existe, agregarlo en la base de datos
                if (contableDAO.insertCuenta(intoCuenta) == 1) {
                    //si lo agrega actualizar la lista
                    cuentas = contableDAO.getCuentas(onSeletedSubgrupo.trim());
                    Messages.showInfo("Se ha registrado la cuenta");
                    PrimeFaces.current().executeScript("PF('dialogFormCuenta').hide();");
                } else {
                    //showWarn("Hubo un problema al registrar");
                }
            } else {
                showWarn("Ya existe el grupo " + intoGrupo.getNombre());
            }
        } else {
            showWarn("Campo nombre esta vacío");
        }
    }

    public String editarCuentaContable(CuentaContable cuentaContable) {
        if (!contableDAO.isReferenceSubCuenta(cuentaContable.getId())) {
            this.cuentaContable = cuentaContable;
            this.codigo = cuentaContable.getCodigo();
            this.onSeletedGrupo = cuentaContable.getGrupo();
            this.onSeletedSubgrupo = cuentaContable.getSubgrupo();
            this.onSeletedCuenta = cuentaContable.getCuenta();
            this.subCuenta.setNombre(cuentaContable.getSubcuenta());
            return "agregarCuenta";
        }
        Messages.showWarn("No se puede modificar, la subcuenta ya tiene movimientos");
        return "";
    }

    public String formAgregarSubCuenta() {
        codigo = "";
        cuentaContable = new CuentaContable();
        subCuenta = new SubCuenta();
        return "agregarCuenta";
    }

    public void abrirModal(String outcome) {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", false);
        PrimeFaces.current().dialog().openDynamic(outcome, options, null);
    }

    public void onActSelect(SelectEvent event) {
        grupos.add((Grupo) event.getObject());
        intoGrupo = new Grupo();
    }

    public void onActSelectSubgrupo(SelectEvent event) {
        System.out.println("######### Recibiendo: ");
        System.out.println(((SubGrupo) event.getObject()).toString());
        subgrupos.add((SubGrupo) event.getObject());
        intoSubgrupo = new SubGrupo();
    }

    public void onActSelectCuenta(SelectEvent event) {
        System.out.println("######### Recibiendo: ");
        System.out.println(((Cuenta) event.getObject()).toString());
        cuentas.add((Cuenta) event.getObject());
        intoCuenta = new Cuenta();
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<CuentaContable> getCuentasContables() {
        return cuentasContables;
    }

    public void setCuentasContables(List<CuentaContable> cuentasContables) {
        this.cuentasContables = cuentasContables;
    }

    public CuentaContable getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(CuentaContable cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getOnSeletedGrupo() {
        return onSeletedGrupo;
    }

    public void setOnSeletedGrupo(String onSeletedGrupo) {
        this.onSeletedGrupo = onSeletedGrupo;
    }

    public List<SubGrupo> getSubgrupos() {
        return subgrupos;
    }

    public void setSubgrupos(List<SubGrupo> subgrupos) {
        this.subgrupos = subgrupos;
    }

    public SubGrupo getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(SubGrupo subGrupo) {
        this.subGrupo = subGrupo;
    }

    public String getOnSeletedSubgrupo() {
        return onSeletedSubgrupo;
    }

    public void setOnSeletedSubgrupo(String onSeletedSubgrupo) {
        this.onSeletedSubgrupo = onSeletedSubgrupo;
    }

    public String getOnSeletedCuenta() {
        return onSeletedCuenta;
    }

    public void setOnSeletedCuenta(String onSeletedCuenta) {
        this.onSeletedCuenta = onSeletedCuenta;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public String getOnSeletedSaldo() {
        return onSeletedSaldo;
    }

    public void setOnSeletedSaldo(String onSeletedSaldo) {
        this.onSeletedSaldo = onSeletedSaldo;
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }

    public Grupo getIntoGrupo() {
        return intoGrupo;
    }

    public void setIntoGrupo(Grupo intoGrupo) {
        this.intoGrupo = intoGrupo;
    }

    public SubGrupo getIntoSubgrupo() {
        return intoSubgrupo;
    }

    public void setIntoSubgrupo(SubGrupo intoSubgrupo) {
        this.intoSubgrupo = intoSubgrupo;
    }

    public Cuenta getIntoCuenta() {
        return intoCuenta;
    }

    public void setIntoCuenta(Cuenta intoCuenta) {
        this.intoCuenta = intoCuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
