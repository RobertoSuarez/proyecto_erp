/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.ProcesoProduccionDAO;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "procesoMB")
@ViewScoped

public class ProcesosManagedBean implements Serializable {

    int idSubproceso = 12;
    private List<ProcesoProduccion> listaProcesos;
    private ProcesoProduccionDAO procesoProduccionDAO;
    private ProcesoProduccion procesoProduccion;
    private List<SubProceso> listaSubProcesos;
    private ProcesoProduccion selectProceso;
    private TreeNode singleSelectedTreeNode;
    private TreeNode root;
    TreeNode documents;
    SubProceso sProceso;
    TreeNode document1;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public ProcesosManagedBean() {
        procesoProduccionDAO = new ProcesoProduccionDAO();
        procesoProduccion = new ProcesoProduccion();
        listaProcesos = new ArrayList<>();
        listaSubProcesos = new ArrayList<>();
        root = new DefaultTreeNode("Root Node", null);
    }

    public void cargarLista() {
        listaProcesos = procesoProduccionDAO.getProcesosProduccion();
        for (ProcesoProduccion listaProceso : listaProcesos) {
            documents = new DefaultTreeNode(new ProcesoProduccion(listaProceso.getCodigo_proceso(),
                    listaProceso.getNombre(), listaProceso.getDescripcion(), listaProceso.getIdentificador()), this.root);
            listaSubProcesos = procesoProduccionDAO.getsubProcesosProduccion(listaProceso.getCodigo_proceso());
            for (SubProceso sub : listaSubProcesos) {
                document1 = new DefaultTreeNode(new ProcesoProduccion(sub.getCodigo_subproceso(), sub.getNombre(), sub.getDescripcion()), documents);
            }
        }
    }

    @PostConstruct
    public void init() {
        cargarLista();
        this.selectProceso = new ProcesoProduccion();
        aleatorioIdenti();
    }

    public void aleatorioIdenti() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.procesoProduccion.setIdentificador("PR-" + uuid + uuid2);
    }

    public void insertar() {
        try {
            if ("".equals(procesoProduccion.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(procesoProduccion.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(procesoProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.procesoProduccionDAO.insertarp(procesoProduccion);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));

                PrimeFaces.current().executeScript("PF('nuevoProcesoPrincDialog').hide()");
                externalContext.redirect("../produccion/procesoProduccion.xhtml");
            }

        } catch (IOException e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
    }

    public void edit2(ProcesoProduccion proceso) throws SQLException {
        if (procesoProduccionDAO.actualizarProceso(proceso) > 0) {
            FacesMessage msg = new FacesMessage("Proceso Editado", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Proceso Editado", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public ProcesoProduccion getProcesoProduccion() {
        return procesoProduccion;
    }

    public void setProcesoProduccion(ProcesoProduccion procesoProduccion) {
        this.procesoProduccion = procesoProduccion;
    }

    public List<ProcesoProduccion> getListaProcesos() {
        return listaProcesos;
    }

    public void setListaProcesos(List<ProcesoProduccion> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public SubProceso getsProceso() {
        return sProceso;
    }

    public void setsProceso(SubProceso sProceso) {
        this.sProceso = sProceso;
    }

    public ProcesoProduccionDAO getProcesoProduccionDAO() {
        return procesoProduccionDAO;
    }

    public void setProcesoProduccionDAO(ProcesoProduccionDAO procesoProduccionDAO) {
        this.procesoProduccionDAO = procesoProduccionDAO;
    }

    public ProcesoProduccion getSelectProceso() {
        return selectProceso;
    }

    public void setSelectProceso(ProcesoProduccion selectProceso) {
        this.selectProceso = selectProceso;
    }

    public int getIdSubproceso() {
        return idSubproceso;
    }

    public void setIdSubproceso(int idSubproceso) {
        this.idSubproceso = idSubproceso;
    }

    public TreeNode getSingleSelectedTreeNode() {
        return singleSelectedTreeNode;
    }

    public void setSingleSelectedTreeNode(TreeNode singleSelectedTreeNode) {
        this.singleSelectedTreeNode = singleSelectedTreeNode;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getDocuments() {
        return documents;
    }

    public void setDocuments(TreeNode documents) {
        this.documents = documents;
    }

}
