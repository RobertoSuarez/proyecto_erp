package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.AbonoDAO;
import com.cuentasporcobrar.daos.PersonaDAO;
import com.cuentasporcobrar.daos.RetencionDAO;
import com.cuentasporcobrar.models.Abono;
import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Retencion;
import com.cuentasporcobrar.models.Facturas_Pendientes;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.PrimeFaces;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import org.primefaces.event.SelectEvent;

@Named(value = "abonoController")
@ViewScoped
/**
 * Clase de tipo Controlador para los Abonos. Se encarga de llevar toda la
 * lógica del negocio con respecto a los Abonos.
 */
public class AbonoController implements Serializable {

    //Declaramos variables para identificar un plan de pago, factura y cliente.
    int idPlanDePago = 0;
    int idFactura = 0;
    int idCliente = 0;

    //Objeto para traer funciones de primefaces
    PrimeFaces current = PrimeFaces.current();

    //Se Declaran las clases Abono y AbonoDAO
    Abono abono;
    AbonoDAO abonoDAO;

    //Declaramos las clases para tener acceso a los metodos y los atributos.
    Retencion retencion;
    RetencionDAO retencionDAO;
    Persona persona;
    PersonaDAO personaDAO;

    //Declaro mi lista de los abonos de una determinada factura
    List<Abono> list_Abonos;
    List<Facturas_Pendientes> lstFacturasPendientes;
    List<Facturas_Pendientes> lstFacturasAPagar;

    //Variables para cargar el total de abonos y total pendiente
    double totalAbonos = 0;
    double totalPendiente = 0;
    double dblValorPago = 0;
    double dblValorPendiente = 0;

    //Variables para cargar la fecha de credito y fecha de vencimiento:
    LocalDate[] fechasPlan = {null, null};

    //Variable con la identificacion;
    String identificacion = "";

    //Variable de almacenamiento de tipo de pago y fechas
    int intTipoPago;
    LocalDate ldtFechaPago = LocalDate.now();

    //Declaramos una lista que tendra la lista de facturas de un cliente.
    List<SelectItem> listaVenta;

    //Para mostrar un salida de datos determinado
    DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Constructor que inicializa algunas variables declaradas.
     */
    public AbonoController() {
        abono = new Abono();
        abonoDAO = new AbonoDAO();
        lstFacturasPendientes = new ArrayList<>();
        lstFacturasAPagar = new ArrayList<>();
    }

    //Getter y Setter de las variables, clases y listas declaradas.
    public int getIdPlanDePago() {
        return idPlanDePago;
    }

    public void setIdPlanDePago(int idPlanDePago) {
        this.idPlanDePago = idPlanDePago;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public List<Abono> getList_Abonos() {
        return list_Abonos;
    }

    public void setList_Abonos(List<Abono> list_Abonos) {
        this.list_Abonos = list_Abonos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public List<SelectItem> getListaVenta() {
        return listaVenta;
    }

    public void setListaVenta(List<SelectItem> listaVenta) {
        this.listaVenta = listaVenta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public double getTotalAbonos() {
        return totalAbonos;
    }

    public void setTotalAbonos(double totalAbonos) {
        this.totalAbonos = totalAbonos;
    }

    public double getTotalPendiente() {
        return totalPendiente;
    }

    public void setTotalPendiente(double totalPendiente) {
        this.totalPendiente = totalPendiente;
    }

    public LocalDate[] getFechasPlan() {
        return fechasPlan;
    }

    public void setFechasPlan(LocalDate[] fechasPlan) {
        this.fechasPlan = fechasPlan;
    }

    public Abono getAbono() {
        return abono;
    }

    public void setAbono(Abono abono) {
        this.abono = abono;
    }

    public List<Facturas_Pendientes> getLstFacturasPendientes() {
        return lstFacturasPendientes;
    }

    public void setLstFacturasPendientes(List<Facturas_Pendientes> lstFacturasPendientes) {
        this.lstFacturasPendientes = lstFacturasPendientes;
    }

    public List<Facturas_Pendientes> getLstFacturasAPagar() {
        return lstFacturasAPagar;
    }

    public void setLstFacturasAPagar(List<Facturas_Pendientes> lstFacturasAPagar) {
        this.lstFacturasAPagar = lstFacturasAPagar;
    }

    public double getDblValorPago() {
        return dblValorPago;
    }

    public void setDblValorPago(double dblValorPago) {
        this.dblValorPago = dblValorPago;
    }

    public double getDblValorPendiente() {
        return dblValorPendiente;
    }

    public void setDblValorPendiente(double dblValorPendiente) {
        this.dblValorPendiente = dblValorPendiente;
    }

    public int getIntTipoPago() {
        return intTipoPago;
    }

    public void setIntTipoPago(int intTipoPago) {
        this.intTipoPago = intTipoPago;
    }

    public LocalDate getLdtFechaPago() {
        return ldtFechaPago;
    }

    public void setLdtFechaPago(LocalDate ldtFechaPago) {
        this.ldtFechaPago = ldtFechaPago;
    }

    //Fin
    /**
     * Método para cargar las facturas de un determinado cliente en un SelectOne
     */
    public void cargarFacturas() {
        try {
            personaDAO = new PersonaDAO();
            persona = new Persona();
            //Cargamos el nombre del cliente en el input
            persona = personaDAO.obtenerNombreClienteXIdentificacion(identificacion);

            //Este if nos permite verificar si existe o no un cliente.
            if (persona.getIdCliente() == 0) {
                mostrarMensajeAdvertencia("El Cliente No Existe o esta Inactivo");

            } else {
                // En caso de que exista cargamos sus ventas
                listaVenta = new ArrayList<>();
                this.retencionDAO = new RetencionDAO();
                idCliente = persona.getIdCliente();

                //Cargamos las ventas en el select one
                List<Retencion> r = retencionDAO.obtenerVentas(persona.getIdCliente());
                for (Retencion lret : r) {

                    String numFactura = abonoDAO.obtenerConcatenacionFactura(
                            lret.getIdSucursal(), lret.getPuntoEmision(),
                            lret.getSecuencia());

                    //Funcion que devuelve un string con la concatenacion de la factura.
                    SelectItem ventasItem = new SelectItem(lret.getIdVenta(), numFactura);
                    this.listaVenta.add(ventasItem);

                }

                current.ajax().update(":form:tblRegistroCobro");
                list_Abonos = new ArrayList();
                LocalDate[] fechas = {null, null};
                this.fechasPlan = fechas;
                totalAbonos = 0;
                totalPendiente = 0;

                //Este if valida si el cliente tiene o no cobros.
                if (listaVenta.isEmpty()) {
                    mostrarMensajeAdvertencia("Ese cliente no tiene facturas");
                } else {
                    mostrarMensajeInformacion("Se Cargaron las Facturas de " + persona.getRazonNombre());

                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Método que se ejecuta cuando se da clic en "Cargar Deuda" Su principal
     * objetivo es cargar los datos del plan de pago de dicha factura
     */
    @SuppressWarnings("empty-statement")
    public void cargarDeuda() {
        try {
            // En esta lista cargaremos los abonos de esa venta
            list_Abonos = new ArrayList<>();
            abonoDAO = new AbonoDAO();

            idPlanDePago = abonoDAO.obtenerIdPlanPago(idFactura);
            if (idPlanDePago == 0) {
                mostrarMensajeAdvertencia("Esa factura no pertenece a un plan de pago.");
            }
            if (idFactura == -1) {
                list_Abonos = new ArrayList();
                LocalDate[] fechas = {null, null};
                this.fechasPlan = fechas;
                totalAbonos = 0;
                totalPendiente = 0;
            } else {

                //Cargamos los abonos de un determinado Cliente.
                list_Abonos = abonoDAO.obtenerAbonos(idFactura);

                //Cargamos las fechas
                fechasPlan = abonoDAO.obtenerFechaCreditoVencimiento(idFactura);

                //Cargamos el total de los abonos y el total pendiente de una factura
                totalAbonos = abonoDAO.obtenerSumAbonos(idFactura);
                totalPendiente = abonoDAO.obtenerValorPendiente(idFactura);

                if (list_Abonos.isEmpty()) {
                    mostrarMensajeAdvertencia("Esa factura no tiene ningun abono.");
                } else {
                    mostrarMensajeInformacion("Se Cargaron los abonos.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    /**
     * Método que valida e inicia la interfaz para guardar un nuevo abono
     */
    public void nuevoAbono() {
        this.abono = new Abono();

        try {

            //@return diasDeCredito Retorna los dias de credito
            double diasDeCredito = (double) ChronoUnit.DAYS.between(fechasPlan[0], fechasPlan[1]);

            //@return valor Retorna el valor mensual a pagar del plan de pago.
            double valorMensual = ((totalPendiente + totalAbonos) / (diasDeCredito / 30));
            valorMensual = Double.valueOf(df.format(valorMensual).replace(',', '.'));

            //Si el totalPendiente es menor al valor mensual
            //Retorno el valor pendiente, caso contrario se devuelve el valor Mensual
            if (totalPendiente <= valorMensual) {
                abono.setValorAbonado(totalPendiente);
            } else {
                abono.setValorAbonado(Double.valueOf(df.format(valorMensual).replace(',', '.')));
            }

            PrimeFaces current = PrimeFaces.current();
            if (idFactura == 0) {
                mostrarMensajeAdvertencia("Antes de agregar un Abono. Elija una Factura.");
            } else if (idPlanDePago == 0) {
                mostrarMensajeAdvertencia("No se puede ingresar un abono a una Factura que"
                        + "no corresponda a un crédito.");
            } else {

                current.executeScript("PF('nuevoCobro').show();");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Método para almacenar un nuevo abono.
     */
    public void guardarAbono() {
        try {
            abonoDAO = new AbonoDAO(abono);

            if (abono.getIdFormaDePago() == -1) {

                mostrarMensajeAdvertencia("Por Favor. Elija una forma de Pago..");

            } else if (abono.getValorAbonado() <= 0) {

                mostrarMensajeAdvertencia("El abono no puede ser menor o igual a 0.");

            }
            if (abono.getValorAbonado() > totalPendiente) {

                mostrarMensajeAdvertencia("El valor a Abonar no debe exceder al"
                        + " valor pendiente.");

            } else if (abonoDAO.insertarNuevoAbono(idFactura, idPlanDePago) > 0) {

                mostrarMensajeInformacion("Se Registró Correctamente");
                abonoDAO.insertasiento(idFactura);
                PrimeFaces.current().executeScript("PF('nuevoCobro').hide()");
                this.list_Abonos = abonoDAO.obtenerAbonos(idCliente);

            } else {
                mostrarMensajeError("No se Registró Correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error2: " + ex.getMessage());
        }
    }

    /**
     * Método para exportar un PDF de las facturas pendientes.
     *
     * @throws IOException Excepción que no controla un programador.
     * @throws JRException Expeción del JasperReport.
     */
    public void exportarPDF() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition", "attachment; "
                + "filename=ReciboDePago.pdf");

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
//            Map<String, Object> parametros = new HashMap<String, Object>();
//            parametros.put("titulo", "Reporte desde java");
//            parametros.put("fecha", LocalDate.now().toString());
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("nomCliente", persona.getRazonNombre());
            parametros.put("monto", abono.getValorAbonado());
            parametros.put("tipoPago", "EFECTIVO");
            parametros.put("fechaPago", abono.getFechaAbono());

            System.out.println(persona.getRazonNombre());
            System.out.println(abono.getValorAbonado());
            System.out.println(abono.getDescrFormaPago());
            System.out.println(abono.getFechaAbono());

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/ReciboCobro.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros
            );

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

            System.out.println("fin proccess");
        }
    }

    /**
     * Se selecciona la fila de un determinado cliente.
     *
     * @param event Un evento de selección de primefaces.
     */
    public void onRowSelect(SelectEvent<Persona> event) {
        try {
            this.identificacion = event.getObject().getIdentificacion();
            System.out.println(this.identificacion);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Metodos para mostrar mensajes de Información y Error
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exitó", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeAdvertencia(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Advertencia", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void cargarFacturasPendientesPorCliente() {
        lstFacturasPendientes = new ArrayList<>();
        personaDAO = new PersonaDAO();
        persona = new Persona();
        //Cargamos el nombre del cliente en el input
        persona = personaDAO.obtenerNombreClienteXIdentificacion(identificacion);
        lstFacturasPendientes = abonoDAO.getPendingInvoices(identificacion);
    }

    public void AgregarFacturaPagar(Facturas_Pendientes fact) {
        lstFacturasAPagar.add(fact);
        dblValorPendiente = dblValorPendiente + fact.getValorPendiente();
        lstFacturasPendientes.remove(fact);
        PagarTodo();
    }

    public void QuitarFacturaPagar(Facturas_Pendientes fact) {
        lstFacturasAPagar.remove(fact);
        lstFacturasPendientes.add(fact);
        dblValorPendiente = dblValorPendiente - fact.getValorPendiente();
        PagarTodo();
    }

    public void PagarTodo() {
        dblValorPago = dblValorPendiente;
    }

    /**
     * Metodo donde se ingresan los valores descritos en el table
     */
    public void IngresarPago() {
        Abono abon;
        int revision;
        if (dblValorPago == dblValorPendiente) {
            for (Facturas_Pendientes fact:lstFacturasAPagar) {
                abon = new Abono();
                revision=0;
                abon.setIdVenta(fact.getIdFactura());
                abon.setIdPlanDePago(abonoDAO.obtenerIdPlanPago(fact.getIdFactura()));
                abon.setIdFormaDePago(intTipoPago);
                abon.setValorAbonado(fact.getValorPendiente());
                abon.setFechaAbono(ldtFechaPago);
                revision = abonoDAO.insertarNuevoCobro(abon, persona.getIdCliente(), abon.getIdPlanDePago());
                if (revision < 0) {
                    mostrarMensajeInformacion("El pago completo se realizo satisfactoriamente");
                    abonoDAO.insertAccountingSeat(fact.getIdFactura(), abon);
                }
            }
        } else {
            for (Facturas_Pendientes fact:lstFacturasAPagar){
                abon = new Abono();
                revision=0;
                abon.setIdVenta(fact.getIdFactura());
                abon.setIdPlanDePago(abonoDAO.obtenerIdPlanPago(fact.getIdFactura()));
                abon.setIdFormaDePago(intTipoPago);
                if (fact.getValorPendiente() > dblValorPago) {
                    abon.setValorAbonado(dblValorPago);
                    dblValorPago=0;
                } else {
                    abon.setValorAbonado(fact.getValorPendiente());
                    dblValorPago = dblValorPago - fact.getValorPendiente();
                }
                abon.setFechaAbono(ldtFechaPago);
                revision = abonoDAO.insertarNuevoCobro(abon, persona.getIdCliente(), abon.getIdPlanDePago());
                if (revision < 0) {
                    mostrarMensajeInformacion("El pago completo se realizo satisfactoriamente");
                    abonoDAO.insertAccountingSeat(fact.getIdFactura(), abon);
                }
            }
        }
        lstFacturasAPagar.clear();
        lstFacturasPendientes = abonoDAO.getPendingInvoices(identificacion);
        dblValorPendiente = 0;
        dblValorPago = 0;
        PrimeFaces.current().executeScript("PF('nuevoCobro').hide()");
    }
}
