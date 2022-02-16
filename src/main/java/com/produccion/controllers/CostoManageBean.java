package com.produccion.controllers;

import com.produccion.dao.CostoDAO;
import com.produccion.models.Costo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "costoMB")
@ViewScoped
public class CostoManageBean implements Serializable {

    private List<Costo> listaCosto = new ArrayList<>();
    private CostoDAO costoDAO;
    private Costo costo;
    //Variable que rellena el codigo
    private Costo selectCosto;

    /**
     * Constructor Costo Manage Bean inicializamos las variables 
     * costoDAO, costo
     */
    public CostoManageBean() {
        costoDAO = new CostoDAO();
        costo = new Costo();
    }

    /**
     * Post Constructor Costo Manage Bean inicializablos la variable listaCosto
     */
    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        listaCosto = costoDAO.getCosto();
    }

    /**
     * Metodo que permitira insertar un Nuevo Costo
     */
    public void insertar() {
        try {
            /**
             * Validamos campos vacios de los cuadros de texto
             */
            if ("".equals(costo.getIdentificador_subcuenta())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(costo.getNombre_subcuenta())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(costo.getDescripcion_subgrupo())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                /**
                 * Si es falso ejecutamos el metodo insertarCosto del Costo con
                 * sus parametros
                 *
                 * @param costo objeto Costo
                 */
                this.costoDAO.insertarCosto(costo);
                /**
                 * Mostramos el mensaje
                 */
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos Agregados"));
                /**
                 * Cerramos el dialogo de insertar
                 */
                PrimeFaces.current().executeScript("PF('agregarCostos').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
    }

    /**
     * Metodo que permitira editar la informacion de un Costo
     */
    public void editar() {
        try {
            /**
             * Validamos campos vacios de los cuadros de texto asi como sus
             * longitudes
             */
            if ("".equals(costo.getIdentificador_subcuenta())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Codigo"));
            } else if ("".equals(costo.getNombre_subcuenta())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(costo.getDescripcion_subgrupo())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                /**
                 * Si es falso ejecutamos el metodo updateCosto del Costo con
                 * sus parametros
                 *
                 * @param costo objeto Costo
                 */
                this.costoDAO.updateCosto(costo);
                /**
                 * Llamamos a la lista para actualizar
                 */
                listaCosto = costoDAO.getCosto();
                /**
                 * Mostramos el mensaje
                 */
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos Guardados"));
                /**
                 * Cerramos el dialogo de editar
                 */
                PrimeFaces.current().executeScript("PF('costoEditDialog').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }

        PrimeFaces.current().ajax().update(":form-princ:dtCosto");
    }

    /**
     * Metodo que permitira Eliminar la informacion de un Costo
     */
    public void eliminar() {
        try {
            /**
             * Si es falso ejecutamos el metodo deletec del Centro Costo con sus
             * parametros
             *
             * @param costo objeto Costo
             */
            this.costoDAO.deletecosto(costo, costo.getIdentificador_subcuenta());
            /**
             * Llamamos a la lista para actualizar
             */
            listaCosto = costoDAO.getCosto();
            /**
             * Mostramos el mensaje
             */
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Eliminado"));
            PrimeFaces.current().executeScript("PF('agregarCostos').hide()");
        } catch (Exception e) {
            /**
             * Ocurre un error se muestra un msj de error
             */
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al eliminar"));
        }
    }

    /**
     * Metodo que generara una palabra aleatoria para el codigo
     */
    public void aleatorioIdentiCosto() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.costo.setIdentificador_subcuenta("CT-" + uuid + uuid2);
    }

    public void openNew() {
        this.selectCosto = new Costo();
        aleatorioIdentiCosto();
    }

    public CostoDAO getCostoDAO() {
        return costoDAO;
    }

    public void setCostoDAO(CostoDAO costoDAO) {
        this.costoDAO = costoDAO;
    }

    public Costo getCosto() {
        return costo;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
    }

    public Costo getSelectCosto() {
        return selectCosto;
    }

    public void setSelectCosto(Costo selectCosto) {
        this.selectCosto = selectCosto;
    }

    public List<Costo> getListaCosto() {
        return listaCosto;
    }

    public void setListaCosto(List<Costo> listaCosto) {
        this.listaCosto = listaCosto;
    }

}
