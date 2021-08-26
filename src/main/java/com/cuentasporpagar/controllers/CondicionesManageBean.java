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
import com.cuentasporpagar.daos.CondicionesDAO;
import com.cuentasporpagar.models.Proveedor;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ebert
 */
@ManagedBean(name = "condicionesMB")
@SessionScoped
public class CondicionesManageBean implements Serializable {

     private List<Condiciones> listaCondiciones;
     private Condiciones condiciones;
     private CondicionesDAO condicionesDAO;
     private Proveedor proveedor;
     String msj;
     private boolean check;
     private String value;

     public CondicionesManageBean() {
          value = "HABILITAR";
          check = true;
          setCl("ui-button-danger rounded-button");
          setIc("pi pi-trash");
          listaCondiciones = new ArrayList<>();
          proveedor = new Proveedor();
     }

     public boolean isCheck() {
          return check;
     }

     public void setCheck(boolean check) {
          this.check = check;
     }

     public String getValue() {
          return value;
     }

     public void setValue(String value) {
          this.value = value;
     }

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
               this.condiciones.setProveedor(proveedor);
               this.condicionesDAO.updateCondiciones(condiciones, proveedor.getIdProveedor());
          } catch (SQLException e) {

          }
     }

     public void dhProveedor() throws SQLException {

          if (check) {
               this.condicionesDAO.deshabilitar(proveedor.getNombre(), false);
               FacesContext.getCurrentInstance().addMessage(null, 
                       new FacesMessage("Deshabilitada proveedor: " + proveedor.getNombre()));
               listaCondiciones.clear();
               listaCondiciones = condicionesDAO.llenarP(true);
          } else {
               this.condicionesDAO.deshabilitar(proveedor.getNombre(), true);
               FacesContext.getCurrentInstance().addMessage(null, 
                       new FacesMessage("Deshabilitada proveedor: " + proveedor.getNombre()));
               listaCondiciones.clear();
               listaCondiciones = condicionesDAO.llenarP(false);

          }
          PrimeFaces.current().ajax().update("form:manageProductDialog", "form:messages");
     }

     public List<Condiciones> getListaCondiciones() throws Exception {
          try {
               this.condicionesDAO = new CondicionesDAO();
               habTabla();              
          } catch (Exception e) {
             throw e;
          }
          return listaCondiciones;
     }

     public static void removeSessionScopedBean(String beanName) {
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
     }

     public void habTabla() throws SQLException {
          
          this.listaCondiciones.clear();

          if (check) {
               System.out.println(check);
               this.condicionesDAO = new CondicionesDAO();
               this.listaCondiciones = this.condicionesDAO.llenarP(true);
               setValue("Deshabilitar");
               setCl("ui-button-danger rounded-button");
               setIc("pi pi-trash");

          } else {
               this.listaCondiciones = condicionesDAO.llenarP(false);
               setValue("Habilitar");
               setCl("ui-button-primary rounded-button");
               setIc("pi pi-check");
          }
          PrimeFaces.current().ajax().update("form:dt-products");
     }

     public void cargarDhab(Proveedor p) {
          
          this.proveedor.setNombre(p.getNombre());
         
          PrimeFaces.current().ajax().update(":form:confirmDHab");
          //   System.out.println("com.cuentasporpagar.controllers.CondicionesManageBean.cargarDhab()" + p.getNombre());
          //  this.proveedor.setIdProveedor(condiciones.getProveedor().getIdProveedor());
          //this.proveedor.setNombre(condiciones.getProveedor().getNombre());

     }
     private String cl;
     private String ic;

     public String getCl() {
          return cl;
     }

     public void setCl(String cl) {
          this.cl = cl;
     }

     public String getIc() {
          return ic;
     }

     public void setIc(String ic) {
          this.ic = ic;
     }

     public void resetE() {
          System.out.println("Entrandoa rest");
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
          PrimeFaces.current().resetInputs("form:manage-product-content", "form:dt-products");

     }
}
