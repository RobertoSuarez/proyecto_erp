package com.contabilidad.controllers;

import com.contabilidad.dao.GrupoDAO;
import com.contabilidad.dao.SubGrupoDAO;
import com.contabilidad.models.Grupo;
import com.contabilidad.models.SubGrupo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import com.primefaces.Messages;
import javax.faces.view.ViewScoped;

@Named(value = "subGrupoMB")
@ViewScoped
public class SubGrupoManagedBean implements Serializable {

    private SubGrupoDAO subGrupoDAO;
    private List<SubGrupo> listaSubGrupos;
    private SubGrupo subGrupo;
    private Grupo grupo;
    private String action;

    private GrupoDAO grupoDAO;
    private List<Grupo> listaGrupos;
    private String onSeletedGrupo;

    public SubGrupoManagedBean() {
        subGrupoDAO = new SubGrupoDAO();
        grupoDAO = new GrupoDAO();
        listaSubGrupos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        subGrupo = new SubGrupo();
        grupo = new Grupo();
    }

    @PostConstruct
    public void init() {
        listaSubGrupos = subGrupoDAO.getSubGrupos();
        listaGrupos = grupoDAO.getGrupoCuenta();
    }

    public void create() {
        System.out.println("Create ####");
        System.out.println("Subgrupo: " + subGrupo);
        if (!subGrupo.getNombre().isEmpty() && subGrupo.getGrupo() > 0) {
            // validar que no este en la lista - pendiente
            if (subGrupoDAO.store(subGrupo)) {
                PrimeFaces.current().executeScript("PF('dialogFormSubGrupo').hide();");
                listaSubGrupos = subGrupoDAO.getSubGrupos();
                Messages.showInfo("Registro de SubGrupo exitoso");
            } else {
                Messages.showError("Hubo un problema al registrar");
            }
        } else {
            Messages.showWarn("Faltan rellenar campos");
        }
    }
    
    public void edit() {
        System.out.println("Editar ####");
        System.out.println(subGrupo);
        SubGrupo subGrupoDB = subGrupoDAO.getGrupoById(subGrupo.getId());
        // valida que no este vacio
        if (!subGrupo.getNombre().isEmpty()) { 
            // se valida que no sea el mismo nombre que ya tiene
            if (!subGrupo.getNombre().trim().toLowerCase()
                    .equals(subGrupoDB.getNombre().trim().toLowerCase())) {
                // se valida que no este en la lista - pendiente
                if (subGrupoDAO.update(subGrupo)) {
                    PrimeFaces.current().executeScript("PF('dialogFormSubGrupo').hide();");
                    Messages.showInfo("El subgrupo se ha atualizado");
                }
            } else {
                Messages.showWarn("No se han detectado cambios");
            }
        } else {
            Messages.showWarn("Campo nombre vacio");
        }
    }
    
    public void destroy() {
        if (subGrupoDAO.delete(subGrupo.getId())) {
            listaSubGrupos.remove(subGrupo);
            Messages.showInfo("Subgrupo " + subGrupo.getNombre() + " fue eliminado");
        } else {
            Messages.showError("El Subgrupo tiene cuentas referenciadas, no se puede eliminar");
        }
    }

    public void form(boolean editable) {
        if (!editable) {
            subGrupo = new SubGrupo();
            subGrupo.setGrupo(0);
        } else {
            if (subGrupoDAO.isReference(subGrupo.getId())) {
                System.out.println("no se puede modificar");
                Messages.showWarn("No puede modificar el subgrupo, "
                        + "ya esta referenciado con otras cuentas");
                return;
            }
        }
        PrimeFaces.current().executeScript("PF('dialogFormSubGrupo').show();");
    }

    public void onGrupoChange() {
        if (subGrupo.getGrupo() != 0) {
            // primero obtener el codigo del grupo
            String codigoGrupo = grupoDAO.getGrupoById(subGrupo.getGrupo())
                    .getCodigo();
            // obtener la cantidad de subgrupos de acuerdo al codigo
            int proximoCodigo = subGrupoDAO.getProximoCodigo(subGrupo.getGrupo());
            if (proximoCodigo > 0) {
                subGrupo.setCodigo(codigoGrupo + '.' + proximoCodigo);
            }
        } else {
            subGrupo.setCodigo("");
        }
    }

    public List<SubGrupo> getListaSubGrupos() {
        return listaSubGrupos;
    }

    public void setListaSubGrupos(List<SubGrupo> listaSubGrupos) {
        this.listaSubGrupos = listaSubGrupos;
    }

    public SubGrupo getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(SubGrupo subGrupo) {
        this.subGrupo = subGrupo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getOnSeletedGrupo() {
        return onSeletedGrupo;
    }

    public void setOnSeletedGrupo(String onSeletedGrupo) {
        this.onSeletedGrupo = onSeletedGrupo;
    }

}
