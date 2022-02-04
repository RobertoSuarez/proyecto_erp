/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.models;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author cturriagos
 */
public class PeriodoFiscal {
    int idPeriodoFiscal;
    Date FechaInicio;
    Date FechaFin;
    int Preriodo;
    boolean activo;

    public PeriodoFiscal() { }

    public PeriodoFiscal(Date FechaInicio, Date FechaFin, int Preriodo, boolean activo) {
        this.FechaInicio = FechaInicio;
        this.FechaFin = FechaFin;
        this.Preriodo = Preriodo;
        this.activo = activo;
    }

    public PeriodoFiscal(int idPeriodoFiscal, Date FechaInicio, Date FechaFin, int Preriodo, boolean activo) {
        this.idPeriodoFiscal = idPeriodoFiscal;
        this.FechaInicio = FechaInicio;
        this.FechaFin = FechaFin;
        this.Preriodo = Preriodo;
        this.activo = activo;
    }

    public int getIdPeriodoFiscal() {
        return idPeriodoFiscal;
    }

    public void setIdPeriodoFiscal(int idPeriodoFiscal) {
        this.idPeriodoFiscal = idPeriodoFiscal;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Date getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
    }

    public int getPreriodo() {
        return Preriodo;
    }

    public void setPreriodo(int Preriodo) {
        this.Preriodo = Preriodo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 89 + this.idPeriodoFiscal;
        hash += 89 * Objects.hashCode(this.FechaInicio);
        hash += 89 * Objects.hashCode(this.FechaFin);
        hash += 89 * this.Preriodo;
        hash += 89 * Objects.hashCode(this.activo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PeriodoFiscal other = (PeriodoFiscal) obj;
        if (this == obj) {
            return true;
        }
        return (this.idPeriodoFiscal == other.idPeriodoFiscal) && (this.Preriodo == other.Preriodo) && (Objects.equals(this.FechaInicio, other.FechaInicio)) && Objects.equals(this.FechaFin, other.FechaFin) && (this.activo == other.activo);
    }
}
