package com.contabilidad.models;

public class SubCuenta {

    private int id, cuenta, impuesto;
    private String codigo, nombre, tipo;

    public SubCuenta() {

    }

    public SubCuenta(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    

    public SubCuenta(int id, int cuenta, int impuesto, String codigo, String nombre, String tipo) {
        this.id = id;
        this.cuenta = cuenta;
        this.impuesto = impuesto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    @Override
    public String toString() {
        return "SubCuenta{" + "id=" + id + ", cuenta=" + cuenta + ", codigo=" + codigo + ", nombre=" + nombre + ", tipo=" + tipo + '}';
    }
    
    
}
