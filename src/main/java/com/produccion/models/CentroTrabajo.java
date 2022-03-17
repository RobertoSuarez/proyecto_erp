/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

/**
 *
 * @author HP
 */
public class CentroTrabajo {

    private int codigoOrden;
    private int codigoFormula;
    private int codigoSubproceso;
    private String nombreSubproceso;
    private float costoDirecto;
    private float costoIndirecto;
    private String horaInicio;
    private String horaFin;
    private String tiempoMinutos;
    private float totalDirecto;
    private float totalIndirecto;

    public CentroTrabajo() {
    }

    public CentroTrabajo(int codigoSubproceso, String nombreSubproceso, float costoDirecto, float costoIndirecto) {
        this.codigoSubproceso = codigoSubproceso;
        this.nombreSubproceso = nombreSubproceso;
        this.costoDirecto = costoDirecto;
        this.costoIndirecto = costoIndirecto;
    }

    public CentroTrabajo(int codigoOrden, int codigoFormula, int codigoSubproceso, String nombreSubproceso, float costoDirecto, float costoIndirecto, String horaInicio, String horaFin, String tiempoMinutos, float totalDirecto, float totalIndirecto) {
        this.codigoOrden = codigoOrden;
        this.codigoFormula = codigoFormula;
        this.codigoSubproceso = codigoSubproceso;
        this.nombreSubproceso = nombreSubproceso;
        this.costoDirecto = costoDirecto;
        this.costoIndirecto = costoIndirecto;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tiempoMinutos = tiempoMinutos;
        this.totalDirecto = totalDirecto;
        this.totalIndirecto = totalIndirecto;
    }

    public int getCodigoOrden() {
        return codigoOrden;
    }

    public void setCodigoOrden(int codigoOrden) {
        this.codigoOrden = codigoOrden;
    }

    public int getCodigoFormula() {
        return codigoFormula;
    }

    public void setCodigoFormula(int codigoFormula) {
        this.codigoFormula = codigoFormula;
    }

    public int getCodigoSubproceso() {
        return codigoSubproceso;
    }

    public void setCodigoSubproceso(int codigoSubproceso) {
        this.codigoSubproceso = codigoSubproceso;
    }

    public String getNombreSubproceso() {
        return nombreSubproceso;
    }

    public void setNombreSubproceso(String nombreSubproceso) {
        this.nombreSubproceso = nombreSubproceso;
    }

    public float getCostoDirecto() {
        return costoDirecto;
    }

    public void setCostoDirecto(float costoDirecto) {
        this.costoDirecto = costoDirecto;
    }

    public float getCostoIndirecto() {
        return costoIndirecto;
    }

    public void setCostoIndirecto(float costoIndirecto) {
        this.costoIndirecto = costoIndirecto;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void setTiempoMinutos(String tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public float getTotalDirecto() {
        return totalDirecto;
    }

    public void setTotalDirecto(float totalDirecto) {
        this.totalDirecto = totalDirecto;
    }

    public float getTotalIndirecto() {
        return totalIndirecto;
    }

    public void setTotalIndirecto(float totalIndirecto) {
        this.totalIndirecto = totalIndirecto;
    }

}
