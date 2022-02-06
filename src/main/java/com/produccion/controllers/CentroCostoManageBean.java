
package com.produccion.controllers;

import com.produccion.dao.CentroCostoDAO;
import com.produccion.models.CentroCosto;
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
@ManagedBean(name = "centroMB")
@ViewScoped
public class CentroCostoManageBean implements Serializable {

    private List<CentroCosto> listaCentro = new ArrayList<>();
    private CentroCostoDAO centroCostoDAO;
    private CentroCosto centroCosto;
    //Variable que rellena el codigo
    private CentroCosto selectCosto;

    /**
     * Constructor Centro Costo Manage Bean inicializamos las variables
     * centroCostoDAO, centroCosto
     */
    public CentroCostoManageBean() {
        centroCosto = new CentroCosto();
        centroCostoDAO = new CentroCostoDAO();
    }

    /**
     * Post Constructor Centro Costo Manage Bean inicializablos la variable
     * listaCentro
     */
    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        listaCentro = centroCostoDAO.getCentroCosots();
    }

    /**
     * Metodo que permitira insertar un Nuevo Centro de Costos
     */
    public void insertar() {
        try {
            /**
             * Validamos campos vacios de los cuadros de texto
             */
            if ("".equals(centroCosto.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(centroCosto.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(centroCosto.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                /**
                 * Si es falso ejecutamos el metodo insertarc del Centro Costo
                 * con sus parametros
                 *
                 * @param centroCosto objeto CentroCosto
                 */
                this.centroCostoDAO.insertarc(centroCosto);
                /**
                 * Mostramos el mensaje
                 */
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos Agregados"));
                /**
                 * Cerramos el dialogo de insertar
                 */
                PrimeFaces.current().executeScript("PF('agregarCentroDeCostos').hide()");
                //PrimeFaces.current().ajax().update("dtCentroCosto");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
    }

    /**
     * Metodo que permitira editar la informacion de un Centro de Costos
     */
    public void editar() {
        try {
            /**
             * Validamos campos vacios de los cuadros de texto asi como sus
             * longitudes
             */
            if ("".equals(centroCosto.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Codigo"));
            } else if ("".equals(centroCosto.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(centroCosto.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                /**
                 * Si es falso ejecutamos el metodo updatec del Centro Costo con
                 * sus parametros
                 *
                 * @param centroCosto objeto CentroCosto
                 */
                this.centroCostoDAO.updatec(centroCosto);
                /**
                 * Llamamos a la lista para actualizar
                 */
                listaCentro = centroCostoDAO.getCentroCosots();
                /**
                 * Mostramos el mensaje
                 */
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Centro Costo Guardado"));
                /**
                 * Cerramos el dialogo de editar
                 */
                PrimeFaces.current().executeScript("PF('centroEditDialog').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }

        PrimeFaces.current().ajax().update(":form-princ:dtProcesoPrin");
    }

    /**
     * Metodo que permitira eliminar la informacion de un Centro de Costos
     */
    public void eliminar() {
        try {
            /**
             * Si es falso ejecutamos el metodo deletec del Centro Costo con sus
             * parametros
             *
             * @param centroCosto objeto CentroCosto
             */
            this.centroCostoDAO.deletec(centroCosto, centroCosto.getIdentificador());
            /**
             * Llamamos a la lista para actualizar
             */
            listaCentro = centroCostoDAO.getCentroCosots();
            /**
             * Mostramos el mensaje
             */
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Eliminado"));
            PrimeFaces.current().executeScript("PF('agregarCentroDeCostos').hide()");
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
        this.centroCosto.setIdentificador("CC-" + uuid + uuid2);
    }
    
    public List<CentroCosto> getListaCentro() {
        return listaCentro;
    }

    public void setListaCentro(List<CentroCosto> listaCentro) {
        this.listaCentro = listaCentro;
    }

    public void openNew() {
        this.selectCosto = new CentroCosto();
        aleatorioIdentiCosto();
    }

    public CentroCostoDAO getCentroCostoDAO() {
        return centroCostoDAO;
    }

    public void setCentroCostoDAO(CentroCostoDAO centroCostoDAO) {
        this.centroCostoDAO = centroCostoDAO;
    }

    public CentroCosto getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CentroCosto centroCosto) {
        this.centroCosto = centroCosto;
    }

    public CentroCosto getSelectCosto() {
        return selectCosto;
    }

    public void setSelectCosto(CentroCosto selectCosto) {
        this.selectCosto = selectCosto;
    }

}
