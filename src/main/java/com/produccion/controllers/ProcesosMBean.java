/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.ProcesoProduccionDAO;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HP
 */
@Named(value = "procesosMBean")
@RequestScoped
public class ProcesosMBean implements Serializable {
//Eliminar urgente este BEAN
    private int idSubproceso;
    private TreeNode singleSelectedTreeNode;
    private List<ProcesoProduccion> listaProcesos;
    private List<SubProceso> listaSubProcesos;
    private TreeNode root = new DefaultTreeNode("Root Node", null);
    TreeNode documents;
    ProcesoProduccionDAO procesoDao;
    SubProceso sProceso;
    TreeNode document1;

    public ProcesoProduccionDAO getProcesoDao() {
        return procesoDao;
    }

    public void setProcesoDao(ProcesoProduccionDAO procesoDao) {
        this.procesoDao = procesoDao;
    }
    
    public SubProceso getsProceso() {
        return sProceso;
    }

    public void setsProceso(SubProceso sProceso) {
        this.sProceso = sProceso;
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

    public TreeNode getDocuments() {
        return documents;
    }

    public void setDocuments(TreeNode documents) {
        this.documents = documents;
    }

    public ProcesosMBean() {
        procesoDao = new ProcesoProduccionDAO();
        listaProcesos = procesoDao.getProcesosProduccion();
        for (ProcesoProduccion listaProceso : listaProcesos) {
            documents = new DefaultTreeNode(new ProcesoProduccion(listaProceso.getCodigo_proceso(),
                    listaProceso.getNombre(), listaProceso.getDescripcion(), listaProceso.getIdentificador()), this.root);
            listaSubProcesos = procesoDao.getsubProcesosProduccion(listaProceso.getCodigo_proceso());
            for (SubProceso sub : listaSubProcesos) {
                document1 = new DefaultTreeNode(new ProcesoProduccion(sub.getCodigo_subproceso(), sub.getNombre(), sub.getDescripcion()), documents);
            }
        }
    }

    public void onRowSelect(NodeSelectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", String.valueOf(((ProcesoProduccion) event.getTreeNode().getData()).getCodigo_proceso()));
        FacesContext.getCurrentInstance().addMessage(null, message);
        sProceso= new SubProceso(((ProcesoProduccion) event.getTreeNode().getData()).getCodigo_proceso());
        sProceso.setCodigo_subproceso(((ProcesoProduccion) event.getTreeNode().getData()).getCodigo_proceso());
        idSubproceso=((ProcesoProduccion) event.getTreeNode().getData()).getCodigo_proceso();
    }

    public List<ProcesoProduccion> getListaProcesos() {
        return listaProcesos;
    }

    public void setListaProcesos(List<ProcesoProduccion> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

}
