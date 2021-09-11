/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.CondicionesDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import com.cuentasporpagar.models.Proveedor;
import com.cuentasporpagar.daos.ProveedorDAO;
import com.cuentasporpagar.models.Condiciones;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * Creamos el Manage Beans Proveedor
 *
 * @author Ebert Guaranga
 * @see Condiciones
 * @see CondicionesDAO
 */
@ManagedBean(name = "proveedorDAO")
@ViewScoped
public class ProveedorManageBean implements Serializable {

     private Proveedor proveedor;
     private Condiciones condiciones;
     private ProveedorDAO proveedorDAO;
     private CondicionesDAO condicionesDAO;
     private List<Proveedor> listaProveedor;
     private List<Proveedor> Proveedores;
     private Proveedor selectedProveedor;

     private String nom;
     private String cod;

     /**
      * Constructor Proveedor Manage Bean inicializablos las variables condiciones,
      * condicionesDAO,listaProveedor, proveedorDao, proveedor
      */
     public ProveedorManageBean() {
          condiciones = new Condiciones();
          condicionesDAO = new CondicionesDAO();
          listaProveedor = new ArrayList<>();
          proveedorDAO = new ProveedorDAO();
          proveedor = new Proveedor();

     }

     public Condiciones getCondiciones() {
          return condiciones;
     }

     public void setCondiciones(Condiciones condiciones) {
          this.condiciones = condiciones;
     }

     public Proveedor getProveedor() {
          return proveedor;
     }

     public void setProveedor(Proveedor proveedor) {
          this.proveedor = proveedor;
     }

     public ProveedorDAO getProveedorDAO() {
          return proveedorDAO;
     }

     public void setProveedorDAO(ProveedorDAO proveedorDAO) {
          this.proveedorDAO = proveedorDAO;
     }

     public String getNom() {
          return nom;
     }

     public void setNom(String nom) {
          this.nom = nom;
     }

     public String getCod() {
          return cod;
     }

     public void setCod(String cod) {
          this.cod = cod;
     }

     public CondicionesDAO getCondicionesDAO() {
          return condicionesDAO;
     }

     public void setCondicionesDAO(CondicionesDAO condicionesDAO) {
          this.condicionesDAO = condicionesDAO;
     }

     public List<Proveedor> getProveedores() {
          return Proveedores;
     }

     public void setProveedores(List<Proveedor> Proveedores) {
          this.Proveedores = Proveedores;
     }

     public Proveedor getSelectedProveedor() {
          return selectedProveedor;
     }

     public void cargarProveedor(Condiciones condiciones) {
          this.proveedor.setIdProveedor(condiciones.getProveedor().getIdProveedor());
     }

     public void setSelectedProveedor(Proveedor selectedProveedor) {
          this.selectedProveedor = selectedProveedor;
     }

     /**
      * CargarEditar metodo que permite cargar la infomracion del proveedor
      *
      * @param p objeto Proveedor
      * @param c objeto Condiciones
      */
     public void cargarEditar(Proveedor p, Condiciones c) {
          /**
           * Proveedores datos
           */
          this.proveedor.setIdProveedor(p.getIdProveedor());
          this.proveedor.setCodigo(p.getCodigo());
          this.proveedor.setNombre(p.getNombre());
          this.proveedor.setDireccion(p.getDireccion());
          this.proveedor.setContacto(p.getContacto());
          this.proveedor.setWebPage(p.getWebPage());
          this.proveedor.setRuc(p.getRuc());
          this.proveedor.setRazonSocial(p.getRazonSocial());
          this.proveedor.setTelefono(p.getTelefono());
          this.proveedor.setEmail(p.getEmail());
          this.proveedor.setEstado(p.isEstado());
          /**
           * Condiciones datos
           */
          this.condiciones.setCantDiasVencidos(c.getCantDiasVencidos());
          this.condiciones.setDescuento(c.getDescuento());
          this.condiciones.setDiasNeto(c.getDiasNeto());
          this.condiciones.setDiasDescuento(c.getDiasDescuento());
          this.condiciones.setDescripcion(c.getDescripcion());
     }

     /**
      * Editar metodo que permite editar la infomracion del proveedor
      */
     public void editar() {
          /**
           * Validacion email
           */
          Pattern pattern = Pattern
                  .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

          Matcher mather = pattern.matcher(proveedor.getEmail());
          try {
               /**
                * Validamos campos vacios del cuadro de texto asi como sus longitudes
                */
               if ("".equals(proveedor.getNombre())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un nombre"));
               } else if ("".equals(proveedor.getRazonSocial())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una razón social"));

               } else if ("".equals(proveedor.getTelefono())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un teléfono"));

               } else if ("".equals(proveedor.getEmail())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un email"));

               } else if (mather.find() == false) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un email válido"));
               } else if ("".equals(proveedor.getRuc())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Ruc"));
               } else if (proveedor.getRuc().length() < 13) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ruc incompleto"));
                    PrimeFaces.current().ajax().update("form:messages");
               } else if (proveedor.getTelefono().length() < 10) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Teléfono incompleto"));
                    PrimeFaces.current().ajax().update("form:messages");
               } else if ("".equals(condiciones.getDiasNeto())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un día neto"));

               } else if ("".equals(condiciones.getDescuento())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un descuento"));

               } else if ("".equals(condiciones.getDiasDescuento())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un día descuento"));

               } else if ("".equals(condiciones.getCantDiasVencidos())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una cantidad de días vencidos"));

               } else if ("".equals(condiciones.getDescripcion())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una descripción"));

               } else {
                    /**
                     * Si es falso ejecutamos el metodo update del proveedor con sus paramtros
                     *
                     * @param proveedor objeto Proveedor
                     * @param proveedor.getIdProveedor() objeto Condiciones
                     */
                    this.proveedorDAO.update(proveedor, proveedor.getIdProveedor());
                    this.condiciones.setProveedor(this.proveedor);
                    /**
                     * Ejecutamos el metodo update de las condiciones con sus paramtros
                     *
                     * @param proveedor objeto Condiciones
                     * @param proveedor.getIdProveedor() objeto Condiciones
                     */
                    this.condicionesDAO.updateCondiciones(condiciones, proveedor.getIdProveedor());
                    /**
                     * Mostramos el mensaje
                     */
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proveedor Guardado"));
               }
          } catch (SQLException e) {
               /**
                * Ocurre un erro se muestra un msj de error
                */

               FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                               "Error al guardar"));

          }
          PrimeFaces.current().executeScript("PF('manageProductDialogEdit').hide()");
          PrimeFaces.current().ajax().update("form:dt-products", "form:messages");
     }

     /**
      * Metodo que permitira insertar un proveedor
      */
     public void insertar() {
          //pattern valida correo
          Pattern pattern = Pattern
                  .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

          Matcher mather = pattern.matcher(proveedor.getEmail());

          try {
               //validaciones de msj
               if ("".equals(proveedor.getNombre())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un nombre"));
               } else if ("".equals(proveedor.getRazonSocial())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una razón social"));

               } else if ("".equals(proveedor.getTelefono())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un teléfono"));

               } else if ("".equals(proveedor.getEmail())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un email"));

               } else if (mather.find() == false) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un email válido"));
               } else if ("".equals(proveedor.getRuc())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Ruc"));
               } else if (proveedor.getRuc().length() < 13) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ruc incompleto"));
                    PrimeFaces.current().ajax().update("form:messages");
               } else if (proveedor.getTelefono().length() < 10) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Teléfono incompleto"));
                    PrimeFaces.current().ajax().update("form:messages");
               } else if ("".equals(condiciones.getDiasNeto())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un día neto"));

               } else if ("".equals(condiciones.getDescuento())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un descuento"));

               } else if ("".equals(condiciones.getDiasDescuento())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un día descuento"));

               } else if ("".equals(condiciones.getCantDiasVencidos())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una cantidad de días vencidos"));

               } else if ("".equals(condiciones.getDescripcion())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una descripción"));

               } else {
                    //mandamos el metodo insertar un proveedor
                    this.proveedorDAO.insertarp(proveedor);
                    //mandamos a insertar las condiciones
                    condicionesDAO.insertarCondiciones(condiciones);
                    //msj
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proveedor Agregado"));
               }
          } catch (Exception e) {
               //msj
               FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                               "Error al guardar"));

          }
          PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
     }

     /**
      * Función getListaProveedor retorna una lista de proveedores para su visualización
      * @return listaProveedor retorna un List
      */
     public List<Proveedor> getListaProveedor() {
          try {
               this.proveedorDAO = new ProveedorDAO();
          } catch (Exception e) {
               throw e;
          }
          return listaProveedor;
     }

     /**
      * Obtine una lista de proveedores
      *
      * @param listaProveedor variable tipo List
      */

     public void setListaProveedor(List<Proveedor> listaProveedor) {
          this.listaProveedor = listaProveedor;
     }

     /**
      * Selecciona un proveedor de la fila
      *
      * @param event recibe un parametro de evento para su ejecución
      */
     public void onRowSelect(SelectEvent<Proveedor> event) {
          //obtencion de datos segun el proveedor
          String msg2 = event.getObject().getNombre();
          String msg3 = event.getObject().getCodigo();
          //envio de los nuevos datos 
          setNom(msg2);
          setCod(msg3);
     }

     public void aleatorioCod() {
          String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
          String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
          this.proveedor.setCodigo("PR-" + uuid + uuid2);
     }

     /**
      * Metodo openNew me permite crear un nuevo proveedor contiene el metodo aleatorioCod() para la
      * asignacion de un código aleatorio
      */
     public void openNew() {
          this.selectedProveedor = new Proveedor();
          aleatorioCod();
     }

}
