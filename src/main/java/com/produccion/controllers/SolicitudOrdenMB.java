/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.solicitudOrdenDAO;
import com.produccion.models.SolicitudOrden;
import com.produccion.models.productosOrden;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
@Named(value = "solicitudOrdenMB")
@ViewScoped
public class SolicitudOrdenMB implements Serializable {

    private SolicitudOrden solicitudOrden;
    solicitudOrdenDAO solicitudDAO;
    private productosOrden ordenSolicitud;
    private List<productosOrden> listaProductOrden;
    private List<productosOrden> listaOrden;
    private List<productosOrden> listaOrdenConfirmados;

    public SolicitudOrdenMB() {
        solicitudOrden = new SolicitudOrden();
        listaProductOrden = new ArrayList<>();
        listaOrden = new ArrayList<>();
        listaOrdenConfirmados = new ArrayList<>();
        solicitudDAO = new solicitudOrdenDAO();
        ordenSolicitud = new productosOrden();
    }

    @PostConstruct
    public void init() {
        listaProductOrden = solicitudDAO.getAticulosOrden();
        aleatorioIdenti();
        solicitudOrden.setFecha_orden(new Date());
    }

    public SolicitudOrden getSolicitudOrden() {
        return solicitudOrden;
    }

    public void setSolicitudOrden(SolicitudOrden solicitudOrden) {
        this.solicitudOrden = solicitudOrden;
    }

    public List<productosOrden> getListaProductOrden() {
        return listaProductOrden;
    }

    public void setListaProductOrden(List<productosOrden> listaProductOrden) {
        this.listaProductOrden = listaProductOrden;
    }

    public List<productosOrden> getListaOrden() {
        return listaOrden;
    }

    public void setListaOrden(List<productosOrden> listaOrden) {
        this.listaOrden = listaOrden;
    }

    public productosOrden getOrdenSolicitud() {
        return ordenSolicitud;
    }

    public void setOrdenSolicitud(productosOrden ordenSolicitud) {
        this.ordenSolicitud = ordenSolicitud;
    }

    public List<productosOrden> getListaOrdenConfirmados() {
        return listaOrdenConfirmados;
    }

    public void setListaOrdenConfirmados(List<productosOrden> listaOrdenConfirmados) {
        this.listaOrdenConfirmados = listaOrdenConfirmados;
    }

    public void insertarSolicitud() {
        try {
            if (solicitudOrden.getFecha_orden() == null) {
                showWarn("Ingrese una fecha de inicio");
            } else if (solicitudOrden.getFecha_fin() == null) {
                showWarn("Ingrese una fecha de finalización");
            } else if ("".equals(solicitudOrden.getDescripcion())) {
                showWarn("Ingrese una descripción");
            } else if (!verificaLista()) {
                showWarn("Ingrese un producto a la solicitud");
            } else if (!verificaCampos()) {
                showWarn("Ingrese valores en cantidad y unidad de medida");
            } else if (solicitudOrden.getFecha_fin().before(solicitudOrden.getFecha_orden())) {
                showWarn("La fecha no puede ser anterior a la fecha de inicio de la Solicitud");
            } else {
                solicitudOrden.setEstado('P');
                if (solicitudDAO.insertarSolicitud(solicitudOrden) > 0) {
                    int codigo = solicitudDAO.idSolicitud();
                    for (productosOrden solicitudProducto : listaOrdenConfirmados) {
                        solicitudProducto.setCodigoOrden(codigo);
                        solicitudDAO.insertarDetalleSolicitud(solicitudProducto);
                    }
                    showInfo("Solicitud registrada con exito");
                    limpiarSolicitud();
                } else {
                    showWarn("No se pudo generar la solicitud");
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo generar la solicitud" + e);
        }
    }

    public void addMateriales(productosOrden producto) {
        if (producto.isVerifica() == true) {
            listaOrden.add(new productosOrden(producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getDescripcion(), producto.getTipoProducto(),producto.getUnidadMedida()));
            
        } else {
            for (productosOrden lista : listaOrden) {
                if (lista.getCodigoProducto() == producto.getCodigoProducto()) {
                    listaOrden.remove(lista);
                }
            }
        }
    }

    public boolean verificaCampos() {
        boolean verifica = true;
        for (productosOrden solicitudProducto : listaOrdenConfirmados) {
            if (solicitudProducto.getCantidad() < 1 || "".equals(solicitudProducto.getUnidadMedida())) {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

    public boolean verificaLista() {
        if (listaOrdenConfirmados.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void llenaProductoConfirmado() {
        for (productosOrden lista : listaOrden) {
            if (duplicidadDatos(lista)) {
                showWarn("El producto ya se encuentra agregado");
            } else {
                lista.setEstado('P');
                listaOrdenConfirmados.add(lista);
            }

        }
    }

    public void aleatorioIdenti() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.solicitudOrden.setCodigoSecundario("ODPR-" + uuid + uuid2);
    }

    public void deleteFila(productosOrden producto) {
        listaOrdenConfirmados.remove(producto);
    }

    public boolean duplicidadDatos(productosOrden producto) {
        boolean confirmacion = false;
        for (productosOrden lista : listaOrdenConfirmados) {
            if (lista.getCodigoProducto() == producto.getCodigoProducto()) {
                confirmacion = true;
            }
        }
        return confirmacion;
    }

    public void limpiarSolicitud() {
        solicitudOrden = new SolicitudOrden();
        listaProductOrden = new ArrayList<>();
        listaOrden = new ArrayList<>();
        listaOrdenConfirmados = new ArrayList<>();
        solicitudDAO = new solicitudOrdenDAO();
        ordenSolicitud = new productosOrden();
        listaProductOrden = solicitudDAO.getAticulosOrden();
        aleatorioIdenti();
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

    public void showError(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Error", message);
    }

}
