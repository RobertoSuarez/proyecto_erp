package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.AbonoDAO;
import com.cuentasporcobrar.models.Retencion;
import com.cuentasporcobrar.daos.RetencionDAO;
import com.cuentasporcobrar.daos.PersonaDAO;
import com.cuentasporcobrar.models.Persona;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named(value = "retencionController")
@ViewScoped
/**
 * Clase de tipo Controlador para las retenciones. Se encarga de llevar toda la
 * lógica del negocio con respecto a las retenciones.
 */
public class RetencionController implements Serializable {

    //Declaramos variables para identificar una retencion, factura y cliente.
    int idRetencion = 0;
    int idFactura = 0;
    int idCliente = 0;
    boolean statusFact=true;

    //Objeto para traer funciones de primefaces
    PrimeFaces current = PrimeFaces.current();

    //Declaramos las clases para tener acceso a los metodos y los atributos.
    Retencion retencion;
    RetencionDAO retencionDAO;
    Persona persona;
    PersonaDAO personaDAO;

    //Declaramos dos lista que tendra las retenciones y las ventas.
    List<Retencion> listaRetenciones;
    List<SelectItem> listaVenta;

    String identificacion = "";

    /**
     * Constructor para inicializar los objetos retencion y retencionDAO
     */
    public RetencionController() {
        retencion = new Retencion();
        retencionDAO = new RetencionDAO();
    }

    //Getter y Setter de las variables, clases y listas declaradas
    public Retencion getRetencion() {
        return retencion;
    }

    public void setRetencion(Retencion retencion) {
        this.retencion = retencion;
    }

    public List<Retencion> getListaRetenciones() {
        return listaRetenciones;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public void setListaRetenciones(List<Retencion> listaRetenciones) {
        this.listaRetenciones = listaRetenciones;
    }

    public List<SelectItem> getListaVentas() {
        return listaVenta;
    }

    public boolean isStatusFact() {
        return statusFact;
    }

    public void setStatusFact(boolean statusFact) {
        this.statusFact = statusFact;
    }
    
    //Fin

    /**
     * Método para cargar las facturas de un determinado cliente en un select
     * one
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

                //Instanciamos la clase AbonoDAO para usar un metodo
                AbonoDAO abonoDAO = new AbonoDAO();

                //Cargamos las ventas en el select one
                List<Retencion> r = retencionDAO.obtenerVentas(persona.getIdCliente());
                for (Retencion lret : r) {

                    //Usamos la funcion de Abono Dao para concatenar la factura
                    String numFactura = abonoDAO.obtenerConcatenacionFactura(
                            lret.getIdSucursal(), lret.getPuntoEmision(),
                            lret.getSecuencia());

                    SelectItem ventasItem = new SelectItem(lret.getIdVenta(), numFactura);
                    this.listaVenta.add(ventasItem);

                }

                current.ajax().update(":frmprincipal:tblRetenciones");
                this.listaRetenciones = new ArrayList();

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

    /**
     * Método para cargar los datos que seran editables en el Dialog.
     *
     * @param ret Objeto con una retención.
     */
    public void CargarDatos(Retencion ret) {
        this.retencion = ret;
    }
    
    public void renderizarBoton(){
        if(this.idFactura>0){
            this.statusFact=false;
        }
        else{
            this.statusFact=true;
            mostrarMensajeAdvertencia("Por favor elija una opcion valida");
        }
    }

    /**
     * Método para cargar las retenciones en una Tabla.
     */
    public void cargarRetenciones() {
        try {
            retencionDAO = new RetencionDAO();
            listaRetenciones = new ArrayList<>();
            System.out.println(this.idFactura);

            //cargamos la lista con las retenciones
            listaRetenciones = retencionDAO.obtenerRetenciones(this.idFactura);

            //Código para validar si existen retenciones y si no ha seleccionado
            //una factura.
            if (this.idFactura > 0) {
                if (!listaRetenciones.isEmpty()) {
                    mostrarMensajeInformacion("Se Cargaron las Retenciones.");
                } else {
                    mostrarMensajeAdvertencia("No existen Retenciones");
                }
            } else {
                mostrarMensajeAdvertencia("Por favor seleccione una factura.");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Método para registrar una nueva retención, lo cual toma los parámetros de
     * el dialog Nueva Retención.
     */
    public void registrarRetencion() {
        try {
            retencionDAO = new RetencionDAO(retencion);

            //If para verificar si se insertó correctamente una retención.
            if (retencionDAO.insertarRetencion(this.idCliente, this.idFactura) > 0) {

                mostrarMensajeInformacion("Se Registró Correctamente");
                //Aqui se ubica codigo para cargar nuevamente la tabla de retenciones
                listaRetenciones = retencionDAO.obtenerRetenciones(this.idFactura);

            } else {
                mostrarMensajeError("No se Registró Correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        PrimeFaces.current().executeScript("PF('RetencionNew').hide()");
    }

    /**
     * Método para modificar una retención, lo cual toma los parámetros de el
     * dialog modificar Retención.
     */
    public void actualizarRetencion() {
        try {
            retencionDAO = new RetencionDAO(retencion);

            //If para verificar si se modificó correctamente una retención.
            if (retencionDAO.actualizarRetencion(retencion, this.idCliente) > 0) {

                mostrarMensajeInformacion("Se Editó Correctamente");
                //Aqui se ubica codigo para cargar nuevamente la tabla de retenciones
                listaRetenciones = retencionDAO.obtenerRetenciones(this.idFactura);

            } else {
                mostrarMensajeError("No se Editó Correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void cargarBaseImponible() {
        if (idFactura > 0) {
            retencion.setBaseImponible(retencionDAO.obtainTaxBase(idFactura));
        }
        else{
            mostrarMensajeAdvertencia("Seleccione una factura");
        }
    }

    //Metodos para mostrar mensajes de Información, Advertencia y Error
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exito", mensaje);
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
}
