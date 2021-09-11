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
 * @see Proveedor
 * @see CondicionesDAO
 * @see Condiciones
 */
@ManagedBean(name = "condicionesMB")
@SessionScoped
public final class CondicionesManageBean implements Serializable {

     private List<Condiciones> listaCondiciones;
     private Condiciones condiciones;
     private CondicionesDAO condicionesDAO;
     private Proveedor proveedor;
     String msj;
     private boolean check;
     private String value;
     private String cl;
     private String ic;

     /**
      * Constructor de la clase
      */
     public CondicionesManageBean() {
          value = "habilitar";
          check = true;
          setCl("ui-button-danger  private boolean check;\n"
                  + "   rounded-button");
          setIc("pi pi-trash");
          listaCondiciones = new ArrayList<>();
          proveedor = new Proveedor();
     }

     /**
      * PostConstruct de la clase
      */
     @PostConstruct
     public void init() {
          this.condiciones = new Condiciones();
          this.proveedor = new Proveedor();
     }

     public boolean isCheck() {
          return check;
     }

     /**
      * Método setCheck
      *
      * @param check
      */
     public void setCheck(boolean check) {
          this.check = check;
     }

     /**
      * Función getValue
      *
      * @return value
      */
     public String getValue() {
          return value;
     }

     /**
      * Método setValue
      *
      * @param value
      */
     public void setValue(String value) {
          this.value = value;
     }

     /**
      * Método setListaCondiciones
      *
      * @param listaCondiciones
      */
     public void setListaCondiciones(List<Condiciones> listaCondiciones) {
          this.listaCondiciones = listaCondiciones;
     }

     /**
      * Función getCondiciones
      *
      * @return condiciones
      */
     public Condiciones getCondiciones() {
          return condiciones;
     }

     /**
      * Método setCondiciones
      *
      * @param condiciones
      */
     public void setCondiciones(Condiciones condiciones) {
          this.condiciones = condiciones;
     }

     /**
      * Función getCondicionesDAO
      *
      * @return condicionesDAO
      */
     public CondicionesDAO getCondicionesDAO() {
          return condicionesDAO;
     }

     /**
      * Método setCondicionesDAO
      *
      * @param condicionesDAO
      */
     public void setCondicionesDAO(CondicionesDAO condicionesDAO) {
          this.condicionesDAO = condicionesDAO;
     }

     /**
      * Función getProveedor
      *
      * @return proveedor
      */
     public Proveedor getProveedor() {
          return proveedor;
     }

     /**
      * Método setProveedor
      *
      * @param proveedor
      */
     public void setProveedor(Proveedor proveedor) {
          this.proveedor = proveedor;
     }

     /**
      * Función getCl
      *
      * @return cl
      */
     public String getCl() {
          return cl;
     }

     /**
      * Método setCl
      *
      * @param cl
      */
     public void setCl(String cl) {
          this.cl = cl;
     }

     /**
      * Función getIc
      *
      * @return ic
      */
     public String getIc() {
          return ic;
     }

     /**
      * Método setIc
      *
      * @param ic
      */
     public void setIc(String ic) {
          this.ic = ic;
     }

     /**
      * Método para los proveedores en la tabla se : deshabilitados o habilitados Con ina sentencia
      * if la cual chequeará si el proveedor está habilitado o no
      *
      * @throws SQLException
      */
     public void dhProveedor() throws SQLException {

          if (check) {
               //si nuestro check es vrd, entonces mandamos los parametros
               //nombre del proveedor junto con un parametro false, 
               //dichos parámetros son necesarios para la connsulta sql

               this.condicionesDAO.deshabilitar(proveedor.getNombre(), false);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito:", "Deshabilitado proveedor: " + proveedor.getNombre()));

               //limpiamos la lista
               listaCondiciones.clear();
               //llenamos la lista con los provedeores habilitados
               listaCondiciones = condicionesDAO.llenarP(true);

          } else {
               //si nuestro check es falso, entonces mandamos los parametros
               //nombre del proveedor junto con un parametro false, 
               //dichos parámetros son necesarios para la connsulta sql
               this.condicionesDAO.deshabilitar(proveedor.getNombre(), true);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito:", "Habilitado proveedor: " + proveedor.getNombre()));

               //se limpia  la lista
               listaCondiciones.clear();
               //se llena la lista con los provedeores deshabilitados
               listaCondiciones = condicionesDAO.llenarP(false);

          }
          //actualizamos el dialog y enviamos un msj
          PrimeFaces.current().ajax().update("form:manageProductDialog", "form:messages");
     }

     /**
      * Función para listar condiciones
      *
      * @return listaCondiciones
      * @throws Exception
      */
     public List<Condiciones> getListaCondiciones() throws Exception {
          try {
               habTabla();
          } catch (SQLException e) {
               throw e;
          }
          return listaCondiciones;
     }

     public static void removeSessionScopedBean(String beanName) {
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
     }

     /**
      * Método para listar segun sean habilitados o deshabilitados implementa throws
      *
      * @throws SQLException
      */
     public void habTabla() throws SQLException {

          this.listaCondiciones.clear();
          if (check) {

               //si el check es verdadero...
               this.condicionesDAO = new CondicionesDAO();
               //llenamos la tabla segun nuestra consulta en este caso los habilitados
               this.listaCondiciones = this.condicionesDAO.llenarP(true);
               //asignamos el nombre al btn
               setValue("deshabilitar");
               //asignamos el color al btn
               setCl("ui-button-danger rounded-button");
               //asignamos el icono al btn
               setIc("pi pi-trash");

          } else {

               //si el check es falso...
               //llenamos la tabla segun nuestra consulta en este caso los deshabilitados
               this.listaCondiciones = condicionesDAO.llenarP(false);
               //asignamos el nombre al btn
               setValue("habilitar");
               //asignamos el color al btn
               setCl("ui-button-primary rounded-button");
               //asignamos el icono al btn
               setIc("pi pi-check");

          }
          PrimeFaces.current().ajax().update("form:dt-products");
     }

     /**
      * Método para obtener el nombre del proveedor para cargarlo en la ventana Recive como
      * parámetro un objeto tipo proveedor
      *
      * @param p
      */
     public void cargarDhab(Proveedor p) {
          this.proveedor.setNombre(p.getNombre());
     }

     public void resetE() {
          System.out.println("Entrandoa rest");
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
          PrimeFaces.current().resetInputs("form:manage-product-content", "form:dt-products");
          removeSessionScopedBean("condicionesMB");
          listaCondiciones.clear();
     }
}
