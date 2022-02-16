package com.produccion.models;

public class Costo {

    private int codigo_subcuenta;
    private String nombre_subcuenta;
    private String descripcion_subgrupo;
    private String tipo_cuenta;
    private String identificador_subcuenta;
    private float costo;
    public Costo() {
    }

    public Costo(int codigo_costos, String nombre, String descripcion,
            String tipo, String identificador, float costo) {
        this.codigo_subcuenta = codigo_costos;
        this.nombre_subcuenta = nombre;
        this.descripcion_subgrupo = descripcion;
        this.tipo_cuenta = tipo;
        this.identificador_subcuenta = identificador;
        this.costo = costo;
    }

    public Costo(int codigo_costos, String nombre, String descripcion,
            String tipo, String identificador) {
        this.codigo_subcuenta = codigo_costos;
        this.nombre_subcuenta = nombre;
        this.descripcion_subgrupo = descripcion;
        this.tipo_cuenta = tipo;
        this.identificador_subcuenta = identificador;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getCodigo_subcuenta() {
        return codigo_subcuenta;
    }

    public void setCodigo_subcuenta(int codigo_subcuenta) {
        this.codigo_subcuenta = codigo_subcuenta;
    }

    public String getNombre_subcuenta() {
        return nombre_subcuenta;
    }

    public void setNombre_subcuenta(String nombre_subcuenta) {
        this.nombre_subcuenta = nombre_subcuenta;
    }

    public String getDescripcion_subgrupo() {
        return descripcion_subgrupo;
    }

    public void setDescripcion_subgrupo(String descripcion_subgrupo) {
        this.descripcion_subgrupo = descripcion_subgrupo;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public String getIdentificador_subcuenta() {
        return identificador_subcuenta;
    }

    public void setIdentificador_subcuenta(String identificador_subcuenta) {
        this.identificador_subcuenta = identificador_subcuenta;
    }

}
