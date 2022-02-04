package com.produccion.controllers;

import com.produccion.dao.SubProcesoDAO;
import com.produccion.dao.dSubprocesoDAO;
import com.produccion.models.Costo;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import com.produccion.models.dSubproceso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "subprocesoMB")
@ViewScoped
public class SubProcesoManageBean implements Serializable {

    private List<SubProceso> listaSubProceso;
    private List<ProcesoProduccion> listaProceso;
    private SubProcesoDAO subProcesoDAO;
    private SubProceso subProceso;
    private ProcesoProduccion procesoProduccion;
    private Costo costoProduccion;
    private List<Costo> listaCostoDirecto;
    private List<Costo> listaCostoIndirecto;
    private int id_subproceso;
    private int idCostoIndirecto;
    private int idCostoDirecto;
    private dSubproceso dsubproceso;
    private dSubprocesoDAO subproDAO;

    public dSubprocesoDAO getSubproDAO() {
        return subproDAO;
    }

    public void setSubproDAO(dSubprocesoDAO subproDAO) {
        this.subproDAO = subproDAO;
    }

    private List<Costo> costos = new ArrayList<>();
    int indiceI = 0;

    public dSubproceso getDsubproceso() {
        return dsubproceso;
    }

    public void setDsubproceso(dSubproceso dsubproceso) {
        this.dsubproceso = dsubproceso;
    }

    public List<Costo> getCostos() {
        return costos;
    }

    public void setCostos(List<Costo> costos) {
        this.costos = costos;
    }

    public int getIdCostoDirecto() {
        return idCostoDirecto;
    }

    public void setIdCostoDirecto(int idCostoDirecto) {
        this.idCostoDirecto = idCostoDirecto;
    }

    public int getIdCostoIndirecto() {
        return idCostoIndirecto;
    }

    public void setIdCostoIndirecto(int idCostoIndirecto) {
        this.idCostoIndirecto = idCostoIndirecto;
    }

    public int getId_subproceso() {
        return id_subproceso;
    }

    public void setId_subproceso(int id_subproceso) {
        this.id_subproceso = id_subproceso;
    }

    public List<Costo> getListaCostoDirecto() {
        return listaCostoDirecto;
    }

    public void setListaCostoDirecto(List<Costo> listaCostoDirecto) {
        this.listaCostoDirecto = listaCostoDirecto;
    }

    public List<Costo> getListaCostoIndirecto() {
        return listaCostoIndirecto;
    }

    public void setListaCostoIndirecto(List<Costo> listaCostoIndirecto) {
        this.listaCostoIndirecto = listaCostoIndirecto;
    }

    public Costo getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(Costo costoProduccion) {
        this.costoProduccion = costoProduccion;
    }
    int idSubproceso;

    public int getIdSubproceso() {
        return idSubproceso;
    }

    public void setIdSubproceso(int idSubproceso) {
        this.idSubproceso = idSubproceso;
    }

    public ProcesoProduccion getProcesoProduccion() {
        return procesoProduccion;
    }

    public void setProcesoProduccion(ProcesoProduccion procesoProduccion) {
        this.procesoProduccion = procesoProduccion;
    }

    public List<ProcesoProduccion> getListaProceso() {
        return listaProceso;
    }

    public void setListaProceso(List<ProcesoProduccion> listaProceso) {
        this.listaProceso = listaProceso;
    }

    public SubProcesoManageBean() {
        subProcesoDAO = new SubProcesoDAO();
        listaSubProceso = new ArrayList<>();
        listaProceso = new ArrayList<>();
        procesoProduccion = new ProcesoProduccion();
        idSubproceso = subProcesoDAO.idSubproceso();
        costoProduccion = new Costo();
        listaCostoDirecto = new ArrayList<>();
        listaCostoIndirecto = new ArrayList<>();
        subproDAO = new dSubprocesoDAO();
        dsubproceso = new dSubproceso();
    }

    @PostConstruct
    public void init() {
        subProceso = new SubProceso();
        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = subproDAO.getCosto("Directo");
        listaCostoIndirecto = subproDAO.getCosto("Indirecto");

    }

    public void listarDirectos() {
        System.out.println(this.idSubproceso);

    }

    public void aggCostoIndirecto() {

        Costo costoI = new Costo();
        costoI.setCodigo_costos(costoIndirecto().getCodigo_costos());
        costoI.setNombre(costoIndirecto().getNombre());
        costoI.setDescripcion(costoIndirecto().getDescripcion());
        costoI.setTipo(costoIndirecto().getTipo());
        costoI.setIdentificador(costoIndirecto().getIdentificador());
        costos.add(indiceI, costoI);
        indiceI++;
        System.out.println(indiceI);
    }

    public Costo costoIndirecto() {
        costoProduccion = new Costo();
        for (Costo costoI : listaCostoIndirecto) {
            if (costoI.getCodigo_costos() == idCostoIndirecto) {
                costoProduccion = new Costo(costoI.getCodigo_costos(), costoI.getNombre(),
                        costoI.getDescripcion(), costoI.getTipo(), costoI.getIdentificador());
            }
        }
        return costoProduccion;
    }

    

    public void insertSubproceso() {
        subProceso.setCodigo_subproceso(idSubproceso);
        System.out.println(" " + subProceso.getNombre() + subProceso.getDescripcion());
        subProcesoDAO.insertarSubproceso(subProceso);

    }

    public List<SubProceso> getListaSubProceso() {
        return listaSubProceso;
    }

    public void setListaSubProceso(List<SubProceso> listaSubProceso) {
        this.listaSubProceso = listaSubProceso;
    }

    public SubProcesoDAO getSubProcesoDAO() {
        return subProcesoDAO;
    }

    public void setSubProcesoDAO(SubProcesoDAO subProcesoDAO) {
        this.subProcesoDAO = subProcesoDAO;
    }

    public SubProceso getSubProceso() {
        return subProceso;
    }

    public void setSubProceso(SubProceso subProceso) {
        this.subProceso = subProceso;
    }

}
