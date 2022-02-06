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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HP
 */
@Named(value = "procesosMBean")
@ViewScoped
public class ProcesosMBean implements Serializable {
//Eliminar urgente este BEAN


}
