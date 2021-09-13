/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.CiudadDAO;
import com.recursoshumanos.Model.DAO.EmpresaDAO;
import com.recursoshumanos.Model.DAO.ProvinciaDAO;
import com.recursoshumanos.Model.DAO.SucursalDAO;
import com.recursoshumanos.Model.Entidad.Ciudad;
import com.recursoshumanos.Model.Entidad.Empresa;
import com.recursoshumanos.Model.Entidad.Provincia;
import com.recursoshumanos.Model.Entidad.Sucursal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo CONTROLLER que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con la Empresa. Y se encarga de administrar las
 * sentencias de la BD, utilizando las clases de los modelos
 */
@Named(value = "empresaView")
@ViewScoped
public class EmpresaController implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de la parte de empresa.
     */
    private final ProvinciaDAO provinciaDAO;
    private final SucursalDAO sucursalDAO;
    private final EmpresaDAO empresaDAO;
    private final CiudadDAO ciudadDAO;

    private Empresa empresa;
    private Sucursal sucursal;
    private List<Sucursal> sucursales;
    private List<Provincia> provincias;
    private List<Ciudad> ciudades;
    private int idCiudad, idProvincia;

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public EmpresaController() {
        provinciaDAO = new ProvinciaDAO();
        sucursalDAO = new SucursalDAO();
        empresaDAO = new EmpresaDAO();
        ciudadDAO = new CiudadDAO();
        sucursales = new ArrayList<>();
        provincias = new ArrayList<>();
        ciudades = new ArrayList<>();
    }

    /**
     * La notación POSTCONSTRUCT define un método como un método de
     * inicialización de un bean que se ejecuta después de que se complete el
     * ingreso de la dependencia.
     */
    @PostConstruct
    public void constructorEmpresa() {
        empresa = empresaDAO.cargar();
        sucursales = sucursalDAO.Listar();
        provincias = provinciaDAO.Listar();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    /**
     * A continuación continuan los métodos de GET y SET de cada una de las
     * variables declaradas al inicio de la clase.
     *
     * @return lista Los GET tienen un return que nos retornan los datos y los
     * SET una variable que recibe el dato.
     */
    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
        idProvincia = sucursal.getCiudad().getProvincia().getId();
        idCiudad = sucursal.getCiudad().getId();
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    /**
     * postLoad() Verifica si hay valores en empresa, en caso de no hablerlo
     * pide que se ingresen
     */
    public void postLoad() {
        if (empresa.getId() == -1) {
            mostrarMensajePrecaucion("Debe ingresar los datos de la Empresa");
            PrimeFaces.current().executeScript("PF('manageEmpresaDialog').show()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-empresa");
        }
    }

    /**
     * provinciaSeleccionada() Obtiene las ciudades de propias de una provincia
     *
     * @param idProvincia id de la provincia que se se desea
     */
    public void provinciaSeleccionada(int idProvincia) {
        for (Provincia item : provincias) {
            if (item.getId() == idProvincia) {
                ciudades = ciudadDAO.Listar(item);
                break;
            }
        }
        PrimeFaces.current().ajax().update("form:messages", "inputCiudad");
    }

    /**
     * nuevaSucursal() Prepara la nueva sucursal
     */
    public void nuevaSucursal() {
        idCiudad = 0;
        idProvincia = 0;
        sucursal = new Sucursal();
        sucursal.setEmpresa(empresa);
        PrimeFaces.current().ajax().update("form:messages", "form:manage-sucursal-content");
    }

    /**
     * editarSucursal() asigan los nuevos atributos de la suecursal
     *
     * @param idProvincia id de la nueva provincia
     * @param idCiudad id de la nueva ciudad
     */
    public void editarSucursal(int idProvincia, int idCiudad) {
        this.idProvincia = idProvincia;
        this.idCiudad = idCiudad;
        provinciaSeleccionada(idProvincia);
    }

    /**
     * guardarEmpresa() Guarda los datos de la empresa Verifica si se trara de
     * la creación de una empresa o una edición y procede según corresponda
     */
    public void guardarEmpresa() {
        /*if (idDedicacion != 0) {
            empresa.getDedicacion().setId(idDedicacion);*/
        empresaDAO.setEmpresa(empresa);
        if (empresa.getId() < 1) {
            if (empresaDAO.insertar() > 0) {
                empresa.setId(empresaDAO.getEmpresa().getId());
                mostrarMensajeInformacion("Los datos de la empresa se ha guardado con éxito");
            } else {
                mostrarMensajeError("Los datos de la empresa no se pudo guardar");
            }
        } else {
            if (empresaDAO.actualizar() > 0) {
                mostrarMensajeInformacion("Los datos de la empresa se ha editado con éxito");
            } else {
                mostrarMensajeError("Los datos de la empresa no se pudo editar");
            }
        }
        /*} else {
            mostrarMensajeError("Debe indicar la dedicación de la empresa");
        }*/
        PrimeFaces.current().executeScript("PF('manageEmpresaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empresa");
    }

    /**
     * guardarSucursal() Guarda los datos de la sucursal. Verifica si se trara
     * de la creación de una empresa o una edición y procede según corresponda
     */
    public void guardarSucursal() {
        if (idCiudad != 0) {
            for (Ciudad ciudad : ciudades) {
                if (ciudad.getId() == idCiudad) {
                    sucursal.setCiudad(ciudad);
                    break;
                }
            }
            sucursal.setEmpresa(empresa);
            sucursalDAO.setSucursal(sucursal);
            if (sucursal.getId() == 0) {
                if (sucursalDAO.insertar() > 0) {
                    mostrarMensajeInformacion("La sucursal se ha guardado con éxito");
                    sucursales.add(sucursal);
                } else {
                    mostrarMensajeError("La sucursal no se pudo guardar");
                }
            } else {
                if (sucursalDAO.actualizar() > 0) {
                    mostrarMensajeInformacion("La sucursal se ha editado con éxito");
                } else {
                    mostrarMensajeError("La sucursal no se pudo editar");
                }
            }
        } else {
            mostrarMensajePrecaucion("Debe de seleccionar la ciudad en la que esta la sucursal");
        }
        PrimeFaces.current().executeScript("PF('manageSucursalDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-sucursales");
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz de que ha
     * sido con Error el mensaje
     *
     * @param mensaje Objeto que almacena la información ha ser mostrada en la
     * interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //  MENSAJE DE ERROR
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajePrecaucion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
