/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.AbonoProveedorDAO;
import com.cuentasporpagar.daos.BuscarProvDAO;
import com.cuentasporpagar.daos.FacturaDAO;
import com.cuentasporpagar.models.AbonoProveedor;
import com.cuentasporpagar.models.TipoPago;
import com.cuentasporpagar.models.TipoBanco;
import com.cuentasporpagar.models.Factura;
import com.cuentasporpagar.models.Proveedor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author PAOLA
 */
@ManagedBean(name = "abonoProveedorMB")
@SessionScoped
public final class AbonoProveedorManagedBean {

    private AbonoProveedor abonoproveedor;
    private TipoPago tipoPago;
    private TipoBanco tipoBanco;
    private Proveedor proveedor;
    private AbonoProveedorDAO abonoDAO;
    private FacturaDAO facturaDAO;
    private List<AbonoProveedor> listaAbonos;
    private List<Factura> listaFactura;
    private List<Factura> detalleFactura;
    private List<Proveedor> listaProveedor;
    private List<Factura> listaDetalleFact;
    private BuscarProvDAO buscarprovDAO;
    private Factura factura;
    private String nfactura;
    private float pago;
    private String nom;
    private String cod;
    private boolean bandera;
    private int dateMofid = 0;
    private LocalDate fecha;
    private String descrPago;
    private String perio;

    public AbonoProveedorManagedBean() {
        abonoproveedor = new AbonoProveedor();
        tipoPago = new TipoPago();
        tipoBanco = new TipoBanco();
        proveedor = new Proveedor();
        listaAbonos = new ArrayList<>();
        listaFactura = new ArrayList<>();
        listaProveedor = new ArrayList<>();
        listaDetalleFact = new ArrayList<>();
        abonoDAO = new AbonoProveedorDAO();
        factura = new Factura();
        buscarprovDAO = new BuscarProvDAO();
        listaProveedor = buscarprovDAO.llenar();
        detalleFactura = new ArrayList<>();
        setFecha(LocalDate.now());
        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        Date date = new Date();
        perio = dateFormat.format(date);
        
    }

    //Metodos 
    public void mostrarAbono() {
        this.listaAbonos = abonoDAO.llenarDatos(abonoproveedor.sentenciaMostrar());
    }

    public void mostrarFactura(String ruc) {
        this.listaFactura.clear();
        this.listaFactura = abonoDAO.llenarFacturas(abonoproveedor.BuscarSentenciaFactura(ruc));
    }

    public void mostraProveedor() {
        this.listaProveedor = abonoDAO.llenarProveedor();
    }

    public void onRowSelectf(SelectEvent<Proveedor> event) {
        String msg2 = event.getObject().getNombre();
        String msg3 = event.getObject().getRuc();
        System.out.print("Nombre: " + msg2);
        System.out.print("Ruc: " + msg3);
        setNom(msg2);
        setCod(msg3);
        this.listaFactura.clear();
        this.listaFactura = abonoDAO.llenarFacturas(abonoproveedor.BuscarSentenciaFactura(msg3));
    }

    public void cargar(AbonoProveedor abonoProveedor) {
        //Buscar idabonoproveedor para tener los datos del abono

        abonoDAO.search_date_payment(abonoProveedor.getPago(), this.abonoproveedor);
        System.out.print("Id Proveedor: " + this.abonoproveedor.getIdAbonoProveedor());
        //Buscar datos de abono proveedor

        abonoDAO.select_date_payment(this.abonoproveedor.getIdAbonoProveedor());
        System.out.print("Cant Lista: " + this.listaAbonos.size());
        //Buscar datos de la facturas

        detalleFactura = abonoDAO.select_date_invoice(this.abonoproveedor.getIdAbonoProveedor());
        System.out.print("Cant Lista-: " + detalleFactura.size());

        //Ingresar los datos a los inputext
        tipoPago.setDescripcion(listaAbonos.get(0).getDetalletipoPago());
        tipoBanco.setDescrpcion(listaAbonos.get(0).getDetalletipoBanco());
        abonoproveedor.setReferencia(listaAbonos.get(0).getReferencia());
        abonoproveedor.setFecha(listaAbonos.get(0).getFecha());
        abonoproveedor.setPeriodo(listaAbonos.get(0).getPeriodo());
        setNom(listaAbonos.get(0).getNombreProveedor());

        //Ingresar los datos a la tabla
    }

    public void enviar(List<Factura> listaFactura) {
        if (this.listaFactura.size() > 0) {
            if (this.listaFactura.size() == dateMofid) {
                abonoproveedor.setDetalletipoPago(tipoPago.getDescripcion());
                abonoproveedor.setDetalletipoBanco(tipoBanco.getDescrpcion());
                descrPago = tipoPago.getDescripcion();
                System.out.println(descrPago);
                if (descrPago == "Caja") {
                    abonoDAO.Insertar(abonoproveedor);
                    bandera = abonoDAO.InsertarDetalle(this.listaFactura, abonoproveedor);
                    if (bandera) {
                        PrimeFaces.current().executeScript("PF('managePagoDialog').hide()");
                        showInfo("Abono proveedor ingresado");
                        dateMofid = 0;
                    } else {
                        showWarn("Error en registrar el abono");
                    }
                } else {
                    if ("".equals(abonoproveedor.getReferencia())) {
                        showWarn("Error: Ingrese referencia");
                    } else if ("".equals(tipoBanco.getDescrpcion())) {
                        showWarn("Error: Ingrese Banco");
                    } else {
                        abonoDAO.Insertar(abonoproveedor);
                        bandera = abonoDAO.InsertarDetalle(this.listaFactura, abonoproveedor);
                    }
                    if (bandera) {
                        PrimeFaces.current().executeScript("PF('managePagoDialog').hide()");
                        showInfo("Abono proveedor ingresado");
                        dateMofid = 0;
                    } else {
                        showWarn("Error en registrar el abono");
                    }
                }

            } else {
                showWarn("Error ingrese datos en Pago");
            }

        } else {

            showWarn("Error el proveedor seleccionado no tiene factura");
        }
    }

    public void deshabilitar(List<Factura> listaFactura) {
        if (this.detalleFactura.size() > 0) {
            abonoproveedor.setDetalletipoPago(tipoPago.getDescripcion());
            abonoproveedor.setDetalletipoBanco(tipoBanco.getDescrpcion());
            abonoDAO.Insertar(abonoproveedor);

            bandera = abonoDAO.InsertarDetalle(this.detalleFactura, abonoproveedor);
            if (bandera) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle de abono revertido"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error en registrar el abono"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error el proveedor seleccionado no tiene factura"));
        }
    }

    public static void removeSessionScopedBean(String beanName) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
    }

    public void onRowEdit(RowEditEvent<Factura> event) {
        float n1 = event.getObject().getPendiente();
        float n2 = pago;
        if (n1 < n2) {
            showWarn("Importe es menor que pagado");
            pago = 0;
        } else if (pago != 0) {
            Factura f = (Factura) event.getObject();
            f.setPagado(pago);
            f.setPor_pagar(pago);
            dateMofid = dateMofid + 1;
            showInfo("Ingreso de pago correctamente");
        } else {
            showWarn("El pago a registrar debe ser mayor a 0");
        }
    }
    
    public void onRowCancel(RowEditEvent<Factura> event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelada"));
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    //Funcion para mostrar mensaje de informacion correcta
    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    //Funcion para mostrar mensaje de informacion incorrecta
    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", message);
    }

    //Limpia los imputext
    public void reset() {
        PrimeFaces.current().resetInputs(":form:pago-content,:form:table-factura, "
                + ":form:pago, :form:pago-content-edit");
        this.setNom("");
        abonoproveedor = new AbonoProveedor();
        tipoBanco = new TipoBanco();
        tipoPago = new TipoPago();
        listaFactura.clear();
    }

    //Elimina una factura que no desea
    public void deleteFactura() {
        this.listaFactura.remove(this.factura);
        this.factura = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        PrimeFaces.current().ajax().update("form:msgs", "form:table-factura");
    }

    public List<AbonoProveedor> getListaAbonos() {
        return listaAbonos;
    }

    public void setListaAbonos(List<AbonoProveedor> listaAbonos) {
        this.listaAbonos = listaAbonos;
    }

    public List<Factura> getListaFactura() {
        return listaFactura;
    }

    public void setListaFactura(List<Factura> listaFactura) {
        this.listaFactura = listaFactura;
    }

    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public Factura getFactura() {
        System.err.println(factura);
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getNfactura() {
        return nfactura;
    }

    public void setNfactura(String nfactura) {
        this.nfactura = nfactura;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public float getPago() {
        return pago;
    }

    public void setPago(float pago) {
        this.pago = pago;
    }

    public AbonoProveedor getAbonoproveedor() {
        return abonoproveedor;
    }

    public void setAbonoproveedor(AbonoProveedor abonoproveedor) {
        this.abonoproveedor = abonoproveedor;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public TipoBanco getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(TipoBanco tipoBanco) {
        this.tipoBanco = tipoBanco;
    }

    public FacturaDAO getFacturaDAO() {
        return facturaDAO;
    }

    public void setFacturaDAO(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public AbonoProveedorDAO getAbonoDAO() {
        return abonoDAO;
    }

    public void setAbonoDAO(AbonoProveedorDAO abonoDAO) {
        this.abonoDAO = abonoDAO;
    }

    public List<Factura> getListaDetalleFact() {
        return listaDetalleFact;
    }

    public void setListaDetalleFact(List<Factura> listaDetalleFact) {
        this.listaDetalleFact = listaDetalleFact;
    }

    public BuscarProvDAO getBuscarprovDAO() {
        return buscarprovDAO;
    }

    public void setBuscarprovDAO(BuscarProvDAO buscarprovDAO) {
        this.buscarprovDAO = buscarprovDAO;
    }

    public List<Factura> getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(List<Factura> detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescrPago() {
        return descrPago;
    }

    public void setDescrPago(String descrPago) {
        this.descrPago = descrPago;
    }

    public String getPerio() {
        return perio;
    }

    public void setPerio(String perio) {
        this.perio = perio;
    }


}
