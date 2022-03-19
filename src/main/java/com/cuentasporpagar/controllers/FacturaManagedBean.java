/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.FacturaDAO;
import com.cuentasporpagar.models.Anticipo;
import com.cuentasporpagar.models.Factura;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
    private List<SelectItem> listaProductos;
    private List<SelectItem> listaRenta;
    private List<SelectItem> listaIva;
    private List<Factura> retenciones;
    private float datoImporte;
    private String datoDetalle;
    private String datoCuenta;
    private double datoIva;
    private double datoCantidad;
    private double impuestoR;
    private double impuestoI;
    private String tipoDocumento;
    private int opciones;
    private boolean ret;
    private List<Anticipo> anriciposVigentes;

    //Constructor
    public FacturaManagedBean() {
        factura = new Factura();
        listaFactura = new ArrayList<>();
        listaCuentas = new ArrayList<>();
        detalleFactura = new ArrayList<>();
        listaProductos = new ArrayList<>();
        retenciones = new ArrayList<>();
        listaRenta = new ArrayList<>();
        listaIva = new ArrayList<>();
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

    public List<SelectItem> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<SelectItem> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<SelectItem> getListaRenta() {
        return listaRenta;
    }

    public void setListaRenta(List<SelectItem> listaRenta) {
        this.listaRenta = listaRenta;
    }

    public List<SelectItem> getListaIva() {
        return listaIva;
    }

    public void setListaIva(List<SelectItem> listaIva) {
        this.listaIva = listaIva;
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

    public double getDatoIva() {
        return datoIva;
    }

    public void setDatoIva(double datoIva) {
        this.datoIva = datoIva;
    }

    public double getDatoCantidad() {
        return datoCantidad;
    }

    public void setDatoCantidad(double datoCantidad) {
        this.datoCantidad = datoCantidad;
    }

    public String getDatoCuenta() {
        return datoCuenta;
    }

    public void setDatoCuenta(String datoCuenta) {
        this.datoCuenta = datoCuenta;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getOpciones() {
        return opciones;
    }

    public void setOpciones(int opciones) {
        this.opciones = opciones;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public double getImpuestoR() {
        return impuestoR;
    }

    public void setImpuestoR(double impuestoR) {
        this.impuestoR = impuestoR;
    }

    public double getImpuestoI() {
        return impuestoI;
    }

    public void setImpuestoI(double impuestoI) {
        this.impuestoI = impuestoI;
    }

    /**
     * Método para insertar una factura
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     */
    public void insertarfactura() throws IOException, JRException {
        if (tipoDocumento == "0" || detalleFactura.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
        } else {
            if (factura.getNfactura().length() < 9) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El número de factura debe tener 9 digitos"));
                PrimeFaces.current().ajax().update("form:messages");
            } else {
                if ("".equals(factura.getRuc())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar"));
                } else {
                    if (facturaDAO.Insertar(factura) == 0) {
                        System.out.println("YA INSERTE, AHORA EL DETALLE");
                        facturaDAO.insertdetalle(detalleFactura, factura, opciones);
                        if (ret) {
                            facturaDAO.InsertarRetencion(factura, opciones);
                            facturaDAO.asientoRetención(detalleFactura, factura, opciones, tipoDocumento);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Factura Guardada"));
                            PrimeFaces.current().executeScript("PF('newFactura').hide()");
                            listaFactura.clear();
                            detalleFactura.clear();
                            listaFactura = facturaDAO.llenarP("1");
                            PrimeFaces.current().ajax().update("form:dt-factura", "form:messages");
                        } else {
                            facturaDAO.insertasiento(detalleFactura, factura, opciones, tipoDocumento);
                        }
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
            }
        }
    }

    /**
     * Método para revertir una factura
     *
     */
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

    /**
     * Actualiza la factura
     *
     */
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
     *
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
        this.factura.setSerie("001-001");
        this.factura.setNfactura("000000000");
        ret = true;
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

    //
    /**
     * Comparación de fechas
     *
     * @return false Retorar boleano para compara fechas
     */
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

    /**
     * Suma de fechas
     *
     * @return dia retorna un dia dado a la fecha
     */
    public int sumfechas() {
        Duration diff = Duration.between(factura.getFecha().atStartOfDay(), factura.getVencimiento().atStartOfDay());
        long diffDays = diff.toDays();
        System.out.println("Diffrence between dates is : " + diffDays + "days");
        int dia = (int) diffDays;
        return dia;
    }

    /**
     * Editar una fila
     *
     * @param event seleciona un evento
     */
    public void onRowEdit(RowEditEvent<Factura> event) {
        Factura f = (Factura) event.getObject();
        f.setImporteD(datoImporte);
        f.setDetalle(datoDetalle);
        f.setIvaDetalle(datoIva);
        f.setCantidad(datoCantidad);

        float importe = 0;
        float iva = 0;
        for (int i = 0; i < detalleFactura.size(); i++) {
            importe += detalleFactura.get(i).getImporteD() * detalleFactura.get(i).getCantidad();
            if (detalleFactura.get(i).getIvaDetalle() == 0.12) {
                iva += (detalleFactura.get(i).getImporteD() * detalleFactura.get(i).getCantidad()) * detalleFactura.get(i).getIvaDetalle();
            }
        }
        this.factura.setSubtotal(importe);
        this.factura.setIva(Math.round(iva));
        this.factura.setImporte(importe + iva);
        PrimeFaces.current().ajax().update("form:importe");
        PrimeFaces.current().ajax().update("form:iva");
        PrimeFaces.current().ajax().update("form:sub");
        PrimeFaces.current().ajax().update("form:subRet");
        PrimeFaces.current().ajax().update("form:ivaRet");

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle Editado"));
    }

    /**
     * Editar una fila 2
     *
     * @param event seleciona un evento
     */
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

    public void deleteRow(Factura f) {
        float importe = f.getImporteD();
        this.detalleFactura.remove(f);

        this.factura.setImporte(factura.getImporte() - importe);
        PrimeFaces.current().ajax().update("form:dt-detalle");
    }

    /**
     * Metodo onAddNew Añade un producto a la tabla
     */
    public void onAddNew() {
        // Add one new product to the table:
        Factura newFactura = new Factura(0, "Detalle", 0, "code", 1);
        listaCuentas.clear();
        switch (opciones) {
            case 1:
                llenarProductos();
                detalleFactura.add(newFactura);
                break;
            case 2:
                llenarCuenta();
                detalleFactura.add(newFactura);
                break;
            default:
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Por favor seleccione un tipo"));
                PrimeFaces.current().ajax().update("form:messages");
                break;
        }
        datoImporte = 0;
        datoDetalle = "";
    }

    public void llenarCuenta() {
        List<Factura> auxiliar = facturaDAO.llenarCuentas();
        for (int i = 0; i < auxiliar.size(); i++) {
            SelectItem Cuentas = new SelectItem(auxiliar.get(i).getCuentadetalle(), auxiliar.get(i).getCuentadetalle());
            listaCuentas.add(Cuentas);
        }
    }

    public void llenarProductos() {
        List<Factura> auxiliar = facturaDAO.llenarProductos();

        for (int i = 0; i < auxiliar.size(); i++) {
            SelectItem product = new SelectItem(auxiliar.get(i).getCuentadetalle(), auxiliar.get(i).getCuentadetalle());
            listaCuentas.add(product);
        }
    }

    public void llenarRentenciones() {
        List<Factura> auxiliar = facturaDAO.llenarRetenciones("Renta");
        for (int i = 0; i < auxiliar.size(); i++) {
            SelectItem Cuentas = new SelectItem(auxiliar.get(i).getId_impuesto(), auxiliar.get(i).getDes_impuesto());
            listaRenta.add(Cuentas);
        }

        auxiliar.clear();
        auxiliar = facturaDAO.llenarRetenciones("IVA");
        for (int i = 0; i < auxiliar.size(); i++) {
            SelectItem Cuentas = new SelectItem(auxiliar.get(i).getId_impuesto(), auxiliar.get(i).getDes_impuesto());
            listaIva.add(Cuentas);
        }
    }

    /**
     * Metodo para llenar una factura
     *
     */
    public void llenar() {
        this.listaFactura.clear();
        this.listaFactura = this.facturaDAO.llenar();
    }

    public void retenciones() {
        System.out.println("com.cuentasporpagar.controllers.FacturaManagedBean.retenciones()");
        if (detalleFactura.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Por favor Ingrese datos a la tabla"));
        } else {
            llenarRentenciones();
            this.factura.setNserie("001-001");
            this.factura.setNcomprobante("000000020");
            PrimeFaces.current().executeScript("PF('dlgRet').show()");
            PrimeFaces.current().ajax().update("form:ncompr");
            PrimeFaces.current().ajax().update("form:serieRet");
            PrimeFaces.current().ajax().update("form:outRet");
        }
    }

    public void hola() {
        String hol;
        if (ret) {
            hol = "true";
        } else {
            hol = "false";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(hol));

    }

    public void renta() {
        double porcentaje;
        double sub = this.factura.getSubtotal();
        porcentaje = facturaDAO.porcentaje(this.factura.getId_impuestoR());
        setImpuestoR(porcentaje);
        this.factura.setValorRenta(Math.round( this.factura.getSubtotal() * porcentaje));
        this.factura.setImporte((float) Math.round(((sub - this.factura.getValorRenta()) + (this.factura.getIva() - this.factura.getValorIva()))));
        PrimeFaces.current().ajax().update("form:importe");
        PrimeFaces.current().ajax().update("form:valRet");
    }

    public void iva() {
        double porcentaje;
        double importe = this.factura.getSubtotal() + this.factura.getIva();
        porcentaje = facturaDAO.porcentaje(this.factura.getId_impuestoI());
        setImpuestoI(porcentaje);
        this.factura.setValorIva(Math.round((this.factura.getIva() * porcentaje)));
        this.factura.setImporte((float) Math.round(((importe - this.factura.getValorIva()) - this.factura.getValorRenta())));
        PrimeFaces.current().ajax().update("form:importe");
        PrimeFaces.current().ajax().update("form:valRetIva");
    }

    public void exportpdf() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        System.out.println("LISTA RETENCIONES");
        this.retenciones.clear();
        Factura f = new Factura();
        if (this.factura.getValorRenta() > 0) {
            f.setEjercicio("2022");
            f.setSubtotal(this.factura.getSubtotal());
            f.setTipo("Renta");
            f.setPorcentajeR(getImpuestoR());
            f.setValorRenta(this.factura.getValorRenta());
            this.retenciones.add(f);
        }
        Factura g = new Factura();
        if (this.factura.getValorIva() > 0) {
            g.setEjercicio("2022");
            g.setSubtotal(this.factura.getIva());
            g.setTipo("IVA");
            g.setPorcentajeR(getImpuestoI());
            g.setValorRenta(this.factura.getValorIva());
            this.retenciones.add(g);
        }
        System.out.println("LISTA RETENCIONES " + retenciones.size() + "hola ");

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", String.format("attachment; filename=Retencion_compra.pdf"));

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ncomprobante", this.factura.getNserie()+"-"+this.factura.getNcomprobante());
            parametros.put("proveedor", factura.getNombre());
            parametros.put("fecha", (this.factura.getFecha()).toString());
            parametros.put("ruc", this.factura.getRuc());
            parametros.put("direccion", "Quevedo");
            parametros.put("tipocomp", tipoDocumento);
            parametros.put("nfactura", this.factura.getSerie()+"-"+this.factura.getNfactura());
            parametros.put("totalvalor", String.valueOf(this.factura.getValorRenta() + this.factura.getValorIva()));
            System.out.println(String.valueOf(this.factura.getValorRenta() + this.factura.getValorIva()));

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/Retencion_compras.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.retenciones));
            // exportamos a pdf.
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

            stream.flush();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            // enviamos la respuesta.
            fc.responseComplete();
        }

    }
}
