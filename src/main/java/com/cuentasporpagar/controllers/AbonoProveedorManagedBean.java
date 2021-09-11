/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.AbonoProveedorDAO;
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
 * @see AbonoProveedor
 * @see TipoPago
 * @see TipoBanco
 * @see AbonoProveedorDAO
 * @see FacturaDAO
 * @see Factura
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
    private float total;
    private float auxTotal = 0;
    
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
        detalleFactura = new ArrayList<>();
        setFecha(LocalDate.now());
        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        Date date = new Date();
        perio = dateFormat.format(date);
        total = 0;
        pago = 0;

    }

    //Metodos 
    /**
     * Consulta para listar todos los datos del abono
     *
     */
    public void show_payment() {
        this.listaAbonos = abonoDAO.llenarDatos(abonoproveedor.sentenciaMostrar());
    }

    /**
     * Consulta para listar todos los proveedores
     */
    public void show_Supplier() {
        this.listaProveedor.clear();
        this.listaProveedor = abonoDAO.llenarProveedor();
    }

    /**
     * Este metodo nos ayuda en selecciona un proveedor y hacer una consulta de
     * todas las facturas que pertenece a dicho proveedor
     *
     * @param event Evento para seleccionar
     */
    public void onRowSelectf(SelectEvent<Proveedor> event) {
        setCod(event.getObject().getRuc());
        setNom(event.getObject().getNombre());
        this.abonoproveedor.setNombreProveedor(getNom());
        this.abonoproveedor.setRuc(getCod());
        this.listaFactura.clear();
        this.listaFactura = abonoDAO.llenarFacturas(abonoproveedor.BuscarSentenciaFactura(getCod()));
    }

    /**
     * Este metodo nos ayuda en cargar todos los datos de dicho pago
     * seleccionado para revertir
     *
     * @param abonoProveedor requerido para saber los datos que ha seleccionado
     * en la vista
     * @
     */
    public void load_Data(AbonoProveedor abonoProveedor) {
        //Buscar idabonoproveedor para tener los datos del abono

        abonoDAO.search_date_payment(abonoProveedor.getPago(), abonoProveedor.getRuc(), this.abonoproveedor);

        //Buscar datos de abono proveedor
        abonoDAO.select_date_payment(this.abonoproveedor.getIdAbonoProveedor());
        //Buscar datos de la facturas

        this.detalleFactura = abonoDAO.select_date_invoice(this.abonoproveedor.getIdAbonoProveedor());
        //Ingresar los datos a los inputext
        this.tipoPago.setDescripcion(listaAbonos.get(0).getDetalletipoPago());
        this.tipoBanco.setDescrpcion(listaAbonos.get(0).getDetalletipoBanco());
        this.abonoproveedor.setDetalletipoPago(listaAbonos.get(0).getDetalletipoPago());
        this.abonoproveedor.setDetalletipoBanco(listaAbonos.get(0).getDetalletipoBanco());
        this.abonoproveedor.setReferencia(listaAbonos.get(0).getReferencia());
        this.abonoproveedor.setFecha(listaAbonos.get(0).getFecha());
        this.abonoproveedor.setPeriodo(listaAbonos.get(0).getPeriodo());
        setNom(listaAbonos.get(0).getNombreProveedor());
        this.abonoproveedor.setNombreProveedor(getNom());
        setCod(listaAbonos.get(0).getRuc());
        this.abonoproveedor.setRuc(getCod());
        for (int i = 0; i < this.detalleFactura.size(); i++) {
            auxTotal = detalleFactura.get(i).getPagado();
        }
        this.abonoproveedor.setImporte(auxTotal);
    }

    /**
     * Envia los datos a verificar y guardar el pago
     */
    public void insert_Payment() {
        //Verificar la seleccion de un porveedor
        if (this.abonoproveedor.getNombreProveedor() == null) {
            showWarn("Seleccione el proveedor");
        } else {
            //Verifica la seleccion de un tipo de pago
            if (this.tipoPago.getDescripcion() == null || this.tipoPago.getDescripcion() == "") {
                showWarn("Ingrese tipo de pago");
            } else {
                //Verifica la lista de facturas que tiene dicho proveedor
                if (this.listaFactura.size() > 0) {
                    if (dateMofid >= this.listaFactura.size()) {
                        abonoproveedor.setDetalletipoPago(tipoPago.getDescripcion());
                        abonoproveedor.setDetalletipoBanco(tipoBanco.getDescrpcion());
                        descrPago = tipoPago.getDescripcion();
                        //Verifica los tipos de pagos 
                        if ("Caja".equals(descrPago)) {
                            //Envio de datos a registrar BD
                            abonoDAO.Insertar(abonoproveedor, 1);
                            bandera = abonoDAO.InsertarDetalle(this.listaFactura, abonoproveedor, 1);
                            abonoDAO.insertasiento(1, abonoproveedor, 1);
                            abonoDAO.update_abono(1, abonoproveedor.getIdAbonoProveedor());
                            if (bandera) {
                                PrimeFaces.current().executeScript("PF('managePagoDialog').hide()");
                                showInfo("Abono proveedor ingresado");
                                dateMofid = 0;
                                reset();
                            } else {
                                showWarn("Error en registrar el abono");
                            }
                        } else {
                            if ("".equals(abonoproveedor.getReferencia())) {
                                showWarn("Ingrese referencia");
                            } else if ("".equals(tipoBanco.getDescrpcion())) {
                                showWarn("Ingrese Banco");
                            } else {
                                abonoDAO.Insertar(abonoproveedor, 1);
                                bandera = abonoDAO.InsertarDetalle(this.listaFactura, abonoproveedor, 1);
                                abonoDAO.insertasiento(3, abonoproveedor, 1);
                                abonoDAO.update_abono(1, abonoproveedor.getIdAbonoProveedor());
                                if (bandera) {
                                    PrimeFaces.current().executeScript("PF('managePagoDialog').hide()");
                                    showInfo("Abono proveedor ingresado");
                                    dateMofid = 0;
                                    reset();
                                } else {
                                    showError("Error en registrar el abono");
                                }
                            }
                        }
                    } else {
                        showWarn("Ingrese datos en Pago");
                    }
                } else {
                    showWarn("El proveedor seleccionado no tiene factura");
                }
            }
        }
    }

    /**
     * Este metodo nos ayuda en deshabilitar un pago registrado
     */
    public void to_Disable() {
        //Actualiza el estado del pago seleccionado
        abonoDAO.update_abono(0, abonoproveedor.getIdAbonoProveedor());
        //Envio de datos a registrar
        abonoDAO.Insertar(this.abonoproveedor, 0);
        bandera = abonoDAO.InsertarDetalle(this.detalleFactura, this.abonoproveedor, 0);
        if (this.abonoproveedor.getDetalletipoPago() == "Caja") {
            abonoDAO.insertasiento(1, abonoproveedor, 0);
        } else {
            abonoDAO.insertasiento(3, abonoproveedor, 0);
        }
        //Actualiza el estado del pago revertido
        abonoDAO.update_abono(0, abonoproveedor.getIdAbonoProveedor());
        //Actualiza los datos de la tabla pago  y muestra dicho datos
        listaAbonos.clear();
        listaAbonos = abonoDAO.llenarDatos(abonoproveedor.sentenciaMostrar());
        PrimeFaces.current().ajax().update("form:proveedortabla");
        //cierra el dialogo de deshabilitar
        PrimeFaces.current().executeScript("PF('managePagoDialog-desh').hide()");
        showInfo("Se revertio el pago correctamente");
        reset();
    }

    /**
     * Evento para editar el dato del pago de las factura del proveedor
     *
     * @param event Evento para ingresar el pago
     */
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
            auxTotal = 0;
            abonoproveedor.setImporte(0);
            dateMofid += 1;
            for (int i = 0; i < this.listaFactura.size(); i++) {
                auxTotal += this.listaFactura.get(i).getPagado();
            }
            abonoproveedor.setImporte(auxTotal);
            setPago(0);
            showInfo("Ingreso de pago correctamente");
        } else {
            showWarn("El pago a registrar debe ser mayor a 0");
        }
    }

    /**
     * Evento para cancelar el dato del pago
     *
     * @param event Evento para cancelar la edición del pago
     */
    public void onRowCancel(RowEditEvent<Factura> event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelada"));
    }

    /**
     * Este metodo muestra los mensajes
     *
     * @param severity la severidad
     * @param summary Texto de mensaje de resumen localizado
     * @param detail Texto de mensaje de detalle localizado
     */
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    /**
     * Funcion para mostrar mensaje de informacion correcta
     *
     * @param message Mensaje detallado a mostrar
     */
    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    /**
     * Funcion para mostrar mensaje de informacion incorrecta
     *
     * @param message Mensaje detallado a mostrar
     */
    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    /**
     * Funcion para mostrar mensaje de error con la BD
     *
     * @param message Mensaje detallado a mostrar
     */
    public void showError(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error", message);
    }

    /**
     * Limpia los imputext
     */
    public void reset() {
        PrimeFaces.current().resetInputs(":form:pago-content,:form:table-factura, "
                + ":form:pago, :form:pago-content-edit");
        this.setNom("");
        abonoproveedor = new AbonoProveedor();
        tipoBanco = new TipoBanco();
        tipoPago = new TipoPago();
        listaFactura.clear();
        this.setPago(0);
        this.setTotal(0);
    }

    /**
     * Elimina una factura que no desea
     */
    public void deleteFactura() {
        this.listaFactura.remove(this.factura);
        this.factura = null;
        auxTotal = 0;
        abonoproveedor.setImporte(0);
        for (int i = 0; i < this.listaFactura.size(); i++) {
            auxTotal += this.listaFactura.get(i).getPagado();
        }
        abonoproveedor.setImporte(auxTotal);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Factura removida"));
        PrimeFaces.current().ajax().update("form:msgs", "form:table-factura", ":form:dtPagos");
        setPago(0);
    }

    /**
     * Getter de lista abono proveedor
     *
     * @return Lista de todos los pago registrados en BD
     */
    public List<AbonoProveedor> getListaAbonos() {
        return listaAbonos;
    }

    /**
     * Setter de lista abono proveedor
     *
     * @param listaAbonos Datos a agregar
     */
    public void setListaAbonos(List<AbonoProveedor> listaAbonos) {
        this.listaAbonos = listaAbonos;
    }

    /**
     * Getter de lista Factura
     *
     * @return lista de todos las facturas registrados en BD
     */
    public List<Factura> getListaFactura() {
        return listaFactura;
    }

    /**
     * Setter de lista Factura
     *
     * @param listaFactura Datos a agregar
     */
    public void setListaFactura(List<Factura> listaFactura) {
        this.listaFactura = listaFactura;
    }

    /**
     * Getter de lista proveedor
     *
     * @return lista de todos las proveedor registrados en BD
     */
    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    /**
     * Setter de lista proveedor
     *
     * @param listaProveedor Datos a agregar
     */
    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public Factura getFactura() {
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getAuxTotal() {
        return auxTotal;
    }

    public void setAuxTotal(float auxTotal) {
        this.auxTotal = auxTotal;
    }

}
