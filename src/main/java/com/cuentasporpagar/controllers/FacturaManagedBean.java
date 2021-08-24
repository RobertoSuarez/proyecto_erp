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
import javax.faces.model.SelectItemGroup;
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
    private boolean check;
    private String value;
    private String cl;
    private String ic;
    private float datoImporte;
    private String datoDetalle;

    //Constructor
    public FacturaManagedBean() {
        factura = new Factura();
        listaFactura = new ArrayList<>();
        listaCuentas = new ArrayList<>();
        detalleFactura = new ArrayList<>();
        check = true;
        value = "habilitar";
        setCl("ui-button-danger rounded-button");
        setIc("pi pi-trash");
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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean cheack) {
        this.check = cheack;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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
    
        //DETALLE FACTURA
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

    //Diana: insertar nueva Factura
    public void insertarfactura() {
        float comp = 0;
        for (int i = 0; i < detalleFactura.size(); i++) {
            comp += detalleFactura.get(i).getImporteD();
            System.out.println("Importe comp: " + comp);
        }
        System.out.println("Importe comp: " + this.factura.getImporte());
        if (factura.getImporte() != comp) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Importe debe ser igual al total del detalle"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Importe= " + factura.getImporte() + " ; Total detalle= " + comp));
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
                        check = true;
                        listaFactura = facturaDAO.llenarP("1");
                        PrimeFaces.current().ajax().update("form:dt-factura", "form:slcbtn");
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

    //Diana Actualizar factura
    public void editarfactura() {
        System.out.println("ESTOY AQUI EN EL MANAGED ACTUALIZAR");
        System.out.println("DETALLE: " + detalleFactura.get(0).getId_detalle());
        System.out.println("ruc: " + factura.getRuc());
        float comp = 0;
        for (int i = 0; i < detalleFactura.size(); i++) {
            comp += detalleFactura.get(i).getImporteD();
            System.out.println("Importe comp: " + comp);
        }
        if (factura.getImporte() != comp) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Importe es menor que el total del detalle"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Importe= " + factura.getImporte() + "Total= " + comp));
        } else {
            if (fechas()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fecha es mayor que vencimiento"));
            } else {
                if (factura.getImporte() < factura.getPagado()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Importe es menor que pagado"));
                } else {
                    try {
                        if ("".equals(factura.getRuc())) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al guardar"));
                        } else {
                            this.facturaDAO.Actualizar(factura);
                            this.facturaDAO.Actdetalle(detalleFactura);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Factura Actualizada"));
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR DAO: " + e);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR AL GUARDAR"));
                    }
                    PrimeFaces.current().executeScript("PF('editFactura').hide()");
                    listaFactura.clear();
                    listaFactura = facturaDAO.llenarP("1");
                    PrimeFaces.current().ajax().update("form:dt-factura", "form:slcbtn");
                }
            }
        }
    }

    //Cardar datos para actualizar
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
        detalleFactura.clear();
        detalleFactura = facturaDAO.llenarDetalle(dato);
        System.out.println("CANTIDAD DETALLE EDITAR2: " + detalleFactura.size());
    }

    //cargar dato para deshabilitar y habilitar
    public void cargarDHab(Factura factura) {
        this.factura.setNfactura(factura.getNfactura());
    }

    //Diana: Habilitar y Deshabilitar
    public void dhFactura() {
        System.out.println("HOLA SI ENTRE DELETE HABILITAR 1");
        if (check) {
            this.facturaDAO.dhabilitar(factura.getNfactura(), 0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Deshabilitada factura: " + factura.getNfactura()));
            listaFactura.clear();
            listaFactura = facturaDAO.llenarP("1");
        } else {
            this.facturaDAO.dhabilitar(factura.getNfactura(), 1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Habilitada factura: " + factura.getNfactura()));
            listaFactura.clear();
            listaFactura = facturaDAO.llenarP("0");
        }
        PrimeFaces.current().ajax().update("form:dt-factura", "form:messages");
    }

    //Mostrar tablas habilitadas y deshabilitadas
    public void habTabla() {
        listaFactura.clear();
        if (check) {
            this.listaFactura = facturaDAO.llenarP("1");
            setValue("deshabilitar");
            setCl("ui-button-danger rounded-button");
            setIc("pi pi-trash");
        } else {
            this.listaFactura = facturaDAO.llenarP("0");
            setValue("habilitar");
            setCl("ui-button-primary rounded-button");
            setIc("pi pi-check");
        }
        PrimeFaces.current().ajax().update("form:dt-factura");
    }

    //Funciones apartes
    public void abrirNuevo() {
        this.factura = new Factura();
    }

    public void reset() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
        PrimeFaces.current().resetInputs("form:outputnuevo, form:dt-detalle");
        removeSessionScopedBean("facturaMB");
        detalleFactura.clear();
    }

    public void resetE() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
        PrimeFaces.current().resetInputs("form:outputedit, form:dt-detalle");
        removeSessionScopedBean("facturaMB");
        detalleFactura.clear();
    }

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

    public void onRowEdit(RowEditEvent<Factura> event) {
        Factura f = (Factura) event.getObject();
        System.out.println("RowEdit: " + listaCuentas.size());
        f.setImporteD(datoImporte);
        f.setDetalle(datoDetalle);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle Editado"));
    }

    public void onRowCancel(RowEditEvent<Factura> event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición Cancelada"));
    }

    public void onAddNew() {
        // Add one new product to the table:
        System.out.println("Cantidad detalle: " + listaCuentas.size());
        Factura newFactura = new Factura(0, "Cuenta contable", "code");
        detalleFactura.add(newFactura);
        listaCuentas.clear();
        llenarCuenta();
        System.out.println("Cantidad detalle 2: " + listaCuentas.size());
        FacesMessage msg = new FacesMessage("New Product added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void llenarCuenta() {
        List<Factura> auxiliar = facturaDAO.llenarCuentas();
        for (int i = 0; i < auxiliar.size(); i++) { 
            SelectItem Cuentas = new SelectItem(auxiliar.get(i).getCuenta(),auxiliar.get(i).getCuenta());
            listaCuentas.add(Cuentas);
        } 
    }

    public void llenar() {
        this.listaFactura.clear();
        this.listaFactura = this.facturaDAO.llenar();
    }
}
