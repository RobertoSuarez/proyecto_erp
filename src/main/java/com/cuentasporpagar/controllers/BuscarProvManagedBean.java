/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.BuscarProvDAO;
import com.cuentasporpagar.daos.AbonoProveedorDAO;
import com.cuentasporpagar.daos.AnticipoDAO;
import com.cuentasporpagar.models.AbonoProveedor;
import com.cuentasporpagar.models.Anticipo;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import com.cuentasporpagar.models.Proveedor;
import com.cuentasporpagar.models.Factura;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ninat
 */
@ManagedBean(name = "BuscarMB")
@ViewScoped
public class BuscarProvManagedBean implements Serializable {

    private Proveedor proveedor;
    private BuscarProvDAO buscarprovDAO;
    private Factura factura;
    private List<Proveedor> listaProveedor;
    private List<Factura> listafactura;
    private String nom;
    private String cod;
    private int nvenc;
    private LocalDate fec;
    private LocalDate vence;
    private String Nfactura;
    private float Pago;
    private AbonoProveedorManagedBean abonoMB;
    private AbonoProveedor abonoproveedor;
    private AbonoProveedorDAO abonoDAO;
    private List<Anticipo> anticiposFavorables;
    private int totalAnticipo;

    public BuscarProvManagedBean() {
        proveedor = new Proveedor();
        listaProveedor = new ArrayList<>();
        listafactura = new ArrayList<>();
        buscarprovDAO = new BuscarProvDAO();
        listaProveedor = buscarprovDAO.llenar();
        abonoMB = new AbonoProveedorManagedBean();
        factura = new Factura();
        abonoDAO = new AbonoProveedorDAO();
        abonoproveedor = new AbonoProveedor();
        anticiposFavorables = new ArrayList<>();
        totalAnticipo = 0;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public BuscarProvDAO getBuscarProvDAO() {
        return buscarprovDAO;
    }

    public void setBuscarProvDAO(BuscarProvDAO buscarprovDAO) {
        this.buscarprovDAO = buscarprovDAO;
    }

    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public void onRowSelect(SelectEvent<Proveedor> event) {
        String msg2 = event.getObject().getNombre();
        String msg3 = event.getObject().getRuc();
        int msg4 = event.getObject().getVence();
        int idProv = event.getObject().getIdProveedor();
        AnticipoDAO antiDao = new AnticipoDAO();
        List<Anticipo> anticipos = antiDao.getAncitipoByProveedor(idProv);
        for(int i = 0; i < anticipos.size(); i++){
            this.totalAnticipo += anticipos.get(i).getImporte();
        }
        System.out.println("Nombre: " + msg2);
        System.out.println("Ruc: " + msg3);
        System.out.println("Vence: " + msg4);
        setNom(msg2);
        setCod(msg3);
        setNvenc(msg4);
        setFec(LocalDate.now());
        setVence(getFec().plusDays(msg4));
        System.out.println(getFec() + "----" + getVence());
    }

    public int getTotalAnticipo() {
        return totalAnticipo;
    }

    public void setTotalAnticipo(int totalAnticipo) {
        this.totalAnticipo = totalAnticipo;
    }
    
    
    
    public void onRowSelect2(SelectEvent<Proveedor> event) {
        LocalDate fecha = (LocalDate) event.getComponent().getAttributes().get("fech");
        String msg2 = event.getObject().getNombre();
        String msg3 = event.getObject().getRuc();
        int msg4 = event.getObject().getVence();
        System.out.println("Nombre: " + msg2);
        System.out.println("Ruc: " + msg3);
        System.out.println("Vence: " + fecha);
        setNom(msg2);
        setCod(msg3);
        setNvenc(msg4);
        setVence(fecha.plusDays(msg4));
        System.out.println(fecha + "----" + getVence());
        PrimeFaces.current().ajax().update("form:editdven","form:editvencimiento");
    }

    public void sumfechas(int d1, LocalDate d2) {

        if (d1 != 0) {
            System.out.println("DIAS" + d1);
            System.out.println("FECHA" + d2);
            setVence(d2.plusDays(d1));
            System.out.println("VENCIMIENTO" + getVence());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Seleccione un proveedor"));
        }
    }
    //Paola: Llenar Factura

    public void onRowSelectf(SelectEvent<Proveedor> event) {
        String msg2 = event.getObject().getNombre();
        String msg3 = event.getObject().getRuc();
        System.out.println("Nombre: " + msg2);
        System.out.println("Ruc: " + msg3);
        setNom(msg2);
        setCod(msg3);
        this.listafactura = abonoDAO.llenarFacturas(abonoproveedor.BuscarSentenciaFactura(msg3));
    }

    public void onRowDoubleClick(final SelectEvent event) {
        Object obj = (Object) event.getObject();
        // rest of your logic
        if (obj != null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "New:" + obj);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

//    public void Envio() {
//        abonoMB.enviar(listaFactura);
//    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCod() {
        return cod;
    }

    public int getNvenc() {
        return nvenc;
    }

    public void setNvenc(int nvenc) {
        this.nvenc = nvenc;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public LocalDate getFec() {
        return fec;
    }

    public void setFec(LocalDate fec) {
        this.fec = fec;
    }

    public LocalDate getVence() {
        return vence;
    }

    public void setVence(LocalDate vence) {
        this.vence = vence;
    }

    public AbonoProveedorManagedBean getAbonoMB() {
        return abonoMB;
    }

    public void setAbonoMB(AbonoProveedorManagedBean abonoMB) {
        this.abonoMB = abonoMB;
    }

    public String getNfactura() {
        return Nfactura;
    }

    public void setNfactura(String Nfactura) {
        this.Nfactura = Nfactura;
    }

    public float getPago() {
        return Pago;
    }

    public void setPago(float Pago) {
        this.Pago = Pago;
    }

    public Factura getFactura() {
        System.out.println(factura.getNfactura() + listafactura.size());
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public AbonoProveedor getAbonoproveedor() {
        return abonoproveedor;
    }

    public void setAbonoproveedor(AbonoProveedor abonoproveedor) {
        this.abonoproveedor = abonoproveedor;
    }

    public AbonoProveedorDAO getAbonoDAO() {
        return abonoDAO;
    }

    public void setAbonoDAO(AbonoProveedorDAO abonoDAO) {
        this.abonoDAO = abonoDAO;
    }

    public List<Factura> getListafactura() {

        return listafactura;
    }

    public void setListafactura(List<Factura> listafactura) {
        this.listafactura = listafactura;
    }

    public BuscarProvDAO getBuscarprovDAO() {
        return buscarprovDAO;
    }

    public void setBuscarprovDAO(BuscarProvDAO buscarprovDAO) {
        this.buscarprovDAO = buscarprovDAO;
    }

    public void leer(Factura factSelection) {
        factura = factSelection;
        System.out.println("Controller.BuscarProvManagedBean.leer()");

    }

}
