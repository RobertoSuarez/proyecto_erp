package com.contabilidad.controllers;

import com.contabilidad.dao.GrupoDAO;
import com.contabilidad.models.Grupo;
import com.contabilidad.models.SubGrupo;
import com.primefaces.Messages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

@Named(value = "grupoMB")
@ViewScoped
public class GrupoManagedBean implements Serializable {

    private GrupoDAO grupoDAO;
    
    private List<Grupo> listaGrupos;
    private List<SubGrupo> listSubGrupos;
    
    private Grupo grupo, grupoActual;
    private String action;
    private String selectedGrupo;
    private List<Grupo> filteredGrupoList;

    public GrupoManagedBean() {
        grupoDAO = new GrupoDAO();
        
        listaGrupos = new ArrayList<>();
        listSubGrupos = new ArrayList<>();
        
        filteredGrupoList = new ArrayList<>();
        grupo = new Grupo();
        grupoActual = new Grupo();
    }

    @PostConstruct
    public void init() {
        listaGrupos = grupoDAO.getGrupoCuenta();
        action = "create";
    }

    public void create() {
        System.out.println("Create###########");
        System.out.println("Grupo: " + grupo.toString());
        if (!grupo.getNombre().isEmpty()) {
            if (grupoDAO.insert(grupo)) {
                listaGrupos = grupoDAO.getGrupoCuenta();
                Messages.showInfo("El Grupo " + grupo.getNombre() + " fue registrado");
                PrimeFaces.current().executeScript("PF('dialogFormGrupo').hide();");
            }
        } else {
            Messages.showWarn("Faltan rellenar campos");
        }
    }

    public void edit() {
        System.out.println("Actualizar");
        Grupo grupoDB = grupoDAO.getGrupoById(grupo.getId());
        // validar que no aun no tenga cuentas referenciaas
        
        if (!grupoDAO.isReference(grupo.getId())) {
            
        }
        // valida que no este vacio
        if (!grupo.getNombre().isEmpty()) {
            // se valida que no sea el mismo nombre que ya tiene
            if (!grupo.getNombre().trim().toLowerCase()
                    .equals(grupoDB.getNombre().trim().toLowerCase())) {
                // se valida que no este en la lista - pendiente
                if (grupoDAO.update(grupo)) {
                    PrimeFaces.current().executeScript("PF('dialogFormGrupo').hide();");
                    Messages.showInfo("El grupo se ha atualizado");
                }
            } else {
                Messages.showWarn("No se han detectado cambios");
            }
        } else {
            Messages.showWarn("Campo nombre vacio");
        }
    }

    public void destroy() {
        if (grupoDAO.delete(grupo.getId())) {
            listaGrupos.remove(grupo);
            Messages.showInfo("Grupo " + grupo.getNombre() + " fue eliminado");
        } else {
            Messages.showError("El Grupo tiene cuentas referenciadas, no se puede eliminar");
        }
    }

    public void form(boolean editable) {        
        if (!editable) {
            grupo = new Grupo();
            int codigo = grupoDAO.getUltimoCodigo();
            if (codigo > 0) {
                grupo.setCodigo("" + (codigo + 1));
            }
        } else {
            if (grupoDAO.isReference(grupo.getId())) {
                System.out.println("no se puede modificar");
                Messages.showWarn("No se puede modificar, ya esta referenciado a otras cuentas");
                return;
            }
        }
        PrimeFaces.current().executeScript("PF('dialogFormGrupo').show();");
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Grupo getGrupoActual() {
        return grupoActual;
    }

    public void setGrupoActual(Grupo grupoActual) {
        this.grupoActual = grupoActual;
    }

    public List<Grupo> getFilteredGrupoList() {
        return filteredGrupoList;
    }

    public void setFilteredGrupoList(List<Grupo> filteredGrupoList) {
        this.filteredGrupoList = filteredGrupoList;
    }

    public String getSelectedGrupo() {
        return selectedGrupo;
    }

    public void setSelectedGrupo(String selectedGrupo) {
        this.selectedGrupo = selectedGrupo;
    }
}
