/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import java.io.Serializable;

/**
 *
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase de tipo ENTIDAD, representa la tabla amonestación de la base de datos
 */
public class DetalleRolPago implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private RolPagos rolPagos;
    private TipoRubro tipoRubro;
    private int rubro;

    /**
     * Constructores para DetalleRolPago
     */
    public DetalleRolPago() {
        this.rolPagos = new RolPagos();
        this.tipoRubro = new TipoRubro();
        this.rubro = 0;
    }

    public DetalleRolPago(RolPagos rolPagos, TipoRubro tipoRubro, int rubro) {
        this.rolPagos = rolPagos;
        this.tipoRubro = tipoRubro;
        this.rubro = rubro;
    }

    /**
     * A continuación continuan los métodos de GET y SET de cada una de las
     * variables declaradas al inicio de la clase.
     *
     * @return lista Los GET tienen un return que nos retornan los datos y los
     * SET una variable que recibe el dato.
     */
    public int getRubro() {
        return rubro;
    }

    public void setRubro(int rubro) {
        this.rubro = rubro;
    }

    public RolPagos getRolPagos() {
        return rolPagos;
    }

    public void setRolPagos(RolPagos rolPagos) {
        this.rolPagos = rolPagos;
    }

    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }

    public void setTipoRubro(TipoRubro tipoRubro) {
        this.tipoRubro = tipoRubro;
    }
}
