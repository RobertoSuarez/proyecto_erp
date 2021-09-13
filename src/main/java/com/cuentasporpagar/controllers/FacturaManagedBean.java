/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.FacturaDAO;
import com.cuentasporpagar.models.Factura;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author ninat
 */
@ManagedBean(name = "facturaMB")
@SessionScoped
public class FacturaManagedBean {

     private Factura factura;
     private FacturaDAO facturaDAO = new FacturaDAO();
     private List<Factura> listaFactura;
     private List<Factura> detalleFactura;
     private List<SelectItem> listaCuentas;
     private float datoImporte;
     private String datoDetalle;
     private String datoCuenta;

     //Constructor
     public FacturaManagedBean() {
          factura = new Factura();
          listaFactura = new ArrayList<>();
          listaCuentas = new ArrayList<>();
          detalleFactura = new ArrayList<>();
          this.listaFactura.clear();
          this.listaFactura = this.facturaDAO.llenarP("1");
     }

     // Getter and Setter
     public Factura getFactura() {
          return factura;
     }

     public void setFactura(Factura factura) {
          this.factura = factura;
     }

     public FacturaDAO getFacturaDAO() {
          return facturaDAO;
     }

     public void setFacturaDAO(FacturaDAO facturaDAO) {
          this.facturaDAO = facturaDAO;
     }

     public List<Factura> getListaFactura() {
          return listaFactura;
     }

     public void setListaFactura(List<Factura> listaFactura) {
          this.listaFactura = listaFactura;
     }

     public List<Factura> getDetalleFactura() {
          return detalleFactura;
     }

     public void setDetalleFactura(List<Factura> detalleFactura) {
          this.detalleFactura = detalleFactura;
     }

     public List<SelectItem> getListaCuentas() {
          return listaCuentas;
     }

     public void setListaCuentas(List<SelectItem> listaCuentas) {
          this.listaCuentas = listaCuentas;
     }

     //GETTER AND SETTER DETALLE FACTURA
     public float getDatoImporte() {
          return datoImporte;
     }

     public void setDatoImporte(float datoImporte) {
          this.datoImporte = datoImporte;
     }

     public String getDatoDetalle() {
          return datoDetalle;
     }

     public void setDatoDetalle(String datoDetalle) {
          this.datoDetalle = datoDetalle;
     }

     public String getDatoCuenta() {
          return datoCuenta;
     }

     public void setDatoCuenta(String datoCuenta) {
          this.datoCuenta = datoCuenta;
     }

     //DIANA: INSERTAR FACTURA
     public void insertarfactura() {
          System.out.println("factura.getNfactura().length()");
          float comp = 0;
          for (int i = 0; i < detalleFactura.size(); i++) {
               comp += detalleFactura.get(i).getImporteD();
          }
          if (factura.getImporte() != comp) {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Importe debe ser igual al total del detalle"));
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Importe= " + factura.getImporte() + " ; Total detalle= " + comp));
          } else {
               if (factura.getNfactura().length() < 15) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El número de factura debe tener 15 digitos"));
                    PrimeFaces.current().ajax().update("form:messages");
               } else {
                    try {
                         if ("".equals(factura.getRuc())) {
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar"));
                         } else {
                              if (facturaDAO.Insertar(factura) == 0) {
                                   System.out.println("YA INSERTE, AHORA EL DETALLE");
                                   facturaDAO.insertdetalle(detalleFactura, factura);
                                   facturaDAO.insertasiento(detalleFactura, factura);
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Factura Guardada"));
                                   PrimeFaces.current().executeScript("PF('newFactura').hide()");
                                   listaFactura.clear();
                                   detalleFactura.clear();
                                   listaFactura = facturaDAO.llenarP("1");
                                   PrimeFaces.current().ajax().update("form:dt-factura", "form:messages");
                              } else {
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Factura ya existe"));
                              }
                         }
                    } catch (Exception e) {
                         System.out.println("ERROR DAO: " + e);
                         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ERROR AL GUARDAR"));
                    }
               }
          }
     }

     public void revertirfactura() {
          try {
               facturaDAO.revasiento(detalleFactura, factura);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Factura Revertida"));
               PrimeFaces.current().executeScript("PF('revFactura').hide()");
               listaFactura.clear();
               listaFactura = facturaDAO.llenarP("1");
               PrimeFaces.current().ajax().update("form:dt-factura");
          } catch (Exception e) {
               System.out.println("ERROR DAO: " + e);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ERROR AL GUARDAR"));
          }
     }

     //Diana Actualizar factura
     public void editarfactura() {
          float comp = 0;
          for (int i = 0; i < detalleFactura.size(); i++) {
               comp += detalleFactura.get(i).getImporteD();
          }
          if (factura.getImporte() != comp) {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Importe debe ser igual al total del detalle"));
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Importe= " + factura.getImporte() + " ; Total detalle= " + comp));
          } else {
               if (fechas()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fecha es mayor que vencimiento"));
               } else {
                    if (factura.getImporte() < factura.getPagado()) {
                         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Importe es menor que pagado"));
                    } else {
                         try {
                              if ("".equals(factura.getRuc())) {
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar"));
                              } else {
                                   this.facturaDAO.Actualizar(factura);
                                   this.facturaDAO.Actdetalle(detalleFactura);
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Factura Actualizada"));
                              }
                         } catch (Exception e) {
                              System.out.println("ERROR DAO: " + e);
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ERROR AL GUARDAR"));
                         }
                         PrimeFaces.current().executeScript("PF('editFactura').hide()");
                         listaFactura.clear();
                         listaFactura = facturaDAO.llenarP("1");
                         PrimeFaces.current().ajax().update("form:dt-factura", "form:slcbtn");
                    }
               }
          }
     }

     /**
      * Cardar datos para actualizar
      *
      * @param factura parametro objetp
      */
     //
     public void cargarEditar(Factura factura) {
          String dato = factura.getNfactura();
          this.factura.setNombre(factura.getNombre());
          this.factura.setNfactura(factura.getNfactura());
          this.factura.setDescripcion(factura.getDescripcion());
          this.factura.setImporte(factura.getImporte());
          this.factura.setFecha(factura.getFecha());
          this.factura.setVencimiento(factura.getVencimiento());
          this.factura.setRuc(facturaDAO.Buscar(dato));
          this.factura.setPagado(facturaDAO.buscarPagado(dato));
          this.factura.setAux(sumfechas());
          listaCuentas.clear();
          llenarCuenta();
          detalleFactura.clear();
          detalleFactura = facturaDAO.llenarDetalle(dato);
     }

     /**
      * cargar dato para deshabilitar y habilitar
      * @param factura objeto
      */
     //
     public void cargarDHab(Factura factura) {
          this.factura.setNfactura(factura.getNfactura());
     }

     /**
      * Funciones apartes
      */
     public void abrirNuevo() {
          this.factura = new Factura();
          ejemplofac();
     }

     public void reset(String d) {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
          PrimeFaces.current().resetInputs("form:" + d + ", form:dt-detalle");
          removeSessionScopedBean("facturaMB");
          detalleFactura.clear();
     }

//    public void resetE() {
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
//        PrimeFaces.current().resetInputs("form:outputedit, form:dt-detalle");
//        removeSessionScopedBean("facturaMB");
//        detalleFactura.clear();
//    }
     public static void removeSessionScopedBean(String beanName) {
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
     }

     //Comparación de fechas
     public Boolean fechas() {
          int year1 = Integer.parseInt(((factura.getFecha()).toString()).substring(0, 4));
          int mes1 = Integer.parseInt(((factura.getFecha()).toString()).substring(5, 7));
          int dia1 = Integer.parseInt(((factura.getFecha()).toString()).substring(8, 10));
          int year2 = Integer.parseInt(((factura.getVencimiento()).toString()).substring(0, 4));
          int mes2 = Integer.parseInt(((factura.getVencimiento()).toString()).substring(5, 7));
          int dia2 = Integer.parseInt(((factura.getVencimiento()).toString()).substring(8, 10));

          if (year1 > year2) {
               return true;
          } else {
               if (year1 == year2) {
                    if (mes1 > mes2) {
                         return true;
                    } else {
                         if (mes1 == mes2) {
                              if (dia1 > dia2) {
                                   return true;
                              } else {
                                   return false;
                              }
                         } else {
                              return false;
                         }
                    }
               } else {
                    return false;
               }
          }
     }

     public int sumfechas() {
          Duration diff = Duration.between(factura.getFecha().atStartOfDay(), factura.getVencimiento().atStartOfDay());
          long diffDays = diff.toDays();
          System.out.println("Diffrence between dates is : " + diffDays + "days");
          int dia = (int) diffDays;
          return dia;
     }

     public void ejemplofac() {
          this.factura.setNfactura("000-000-000");
     }

     public void onRowEdit(RowEditEvent<Factura> event) {
          Factura f = (Factura) event.getObject();
          f.setImporteD(datoImporte);
          f.setDetalle(datoDetalle);
          f.setCuenta(datoCuenta);
          datoImporte = 0;
          datoDetalle = "";
          datoCuenta = "";
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle Editado"));
     }

     public void onRowEdit2(RowEditEvent<Factura> event) {
          System.out.println("com.cuentasporpagar.controllers.FacturaManagedBean.onRowEdit2()");
          Factura f = (Factura) event.getObject();
          datoImporte = f.getImporte();
          f.setImporteD(datoImporte);
          f.setDetalle(datoDetalle);
          f.setCuenta(datoCuenta);
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle Editado"));
     }

     public void onRowCancel(RowEditEvent<Factura> event) {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición Cancelada"));
     }

     public void onAddNew() {
          // Add one new product to the table:
          System.out.println("Cantidad detalle: " + listaCuentas.size());
          Factura newFactura = new Factura(0, "Detalle", "Cuenta contable", "code");
          detalleFactura.add(newFactura);
          listaCuentas.clear();
          llenarCuenta();
          System.out.println("Cantidad detalle 2: " + listaCuentas.size());
     }

     public void llenarCuenta() {
          List<Factura> auxiliar = facturaDAO.llenarCuentas();
          for (int i = 0; i < auxiliar.size(); i++) {
               SelectItem Cuentas = new SelectItem(auxiliar.get(i).getCuentadetalle(), auxiliar.get(i).getCuentadetalle());
               listaCuentas.add(Cuentas);
          }
     }

     public void llenar() {
          this.listaFactura.clear();
          this.listaFactura = this.facturaDAO.llenar();
     }
}
