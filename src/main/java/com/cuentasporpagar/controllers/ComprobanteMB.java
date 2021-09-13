/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.RetencionDAO;
import com.cuentasporpagar.models.Retencion;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author elect
 */
@ManagedBean(name = "comprobanteMB")
@RequestScoped
public class ComprobanteMB {

    private List<Retencion> retenciones;
    
    public ComprobanteMB() {
        this.retenciones = new ArrayList<>();
        this.retenciones = RetencionDAO.getAll();
        
    }

    public List<Retencion> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<Retencion> retenciones) {
        this.retenciones = retenciones;
    }
    
    /**
      * Metodos auxiliares
      * @return this.retenciones.stream().filter(retencion -> estado.equals(retencion.getEstado_retencion())).count() a devolver
      */
    public long getCountRegister(String estado) {
        return this.retenciones.stream().filter(retencion -> estado.equals(retencion.getEstado_retencion())).count();
    }
    
}
