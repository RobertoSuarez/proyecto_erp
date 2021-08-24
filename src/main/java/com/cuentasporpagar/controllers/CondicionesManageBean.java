/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import java.io.Serializable;
import java.util.List;
import com.cuentasporpagar.models.Condiciones;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import com.cuentasporpagar.daos.CondicionesDAO;
import com.cuentasporpagar.models.Proveedor;
import javax.annotation.PostConstruct;

/**
 *
 * @author ebert
 */
@ManagedBean(name = "condicionesMB")
@ViewScoped
public class CondicionesManageBean implements Serializable {

     private List<Condiciones> listaCondiciones;
     private Condiciones condiciones;
     private CondicionesDAO condicionesDAO;
     private Proveedor proveedor;
     String msj;

     @PostConstruct
     public void init() {
          this.condiciones = new Condiciones();
          this.proveedor = new Proveedor();
     }

     public void setListaCondiciones(List<Condiciones> listaCondiciones) {
          this.listaCondiciones = listaCondiciones;
     }

     public Condiciones getCondiciones() {
          return condiciones;
     }

     public void setCondiciones(Condiciones condiciones) {
          this.condiciones = condiciones;
     }

     public CondicionesDAO getCondicionesDAO() {
          return condicionesDAO;
     }

     public void setCondicionesDAO(CondicionesDAO condicionesDAO) {
          this.condicionesDAO = condicionesDAO;
     }

     public Proveedor getProveedor() {
          return proveedor;
     }

     public void setProveedor(Proveedor proveedor) {
          this.proveedor = proveedor;
     }

     public void editaCondiciones() {
          try {
               System.out.println("ENTRANDO A  EDITAR condiciones: ");
               this.condiciones.setProveedor(proveedor);
               this.condicionesDAO.updateCondiciones(condiciones, proveedor.getIdProveedor());
          } catch (Exception e) {

          }
     }

     public List<Condiciones> getListaCondiciones() {
          try {
               this.condicionesDAO = new CondicionesDAO();
               this.listaCondiciones = this.condicionesDAO.llenarCondiciones();
               System.out.println("SE LLENO CORRECTAMENTE");
          } catch (Exception e) {
               System.out.println("--ERROR AL LLENAR");
          }
          return listaCondiciones;
     }
}
