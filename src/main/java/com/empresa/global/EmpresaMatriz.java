package com.empresa.global;

public class EmpresaMatriz {
    private int idmatriz;
    private String ruc;
    private String nombre;
    private String razonsocial;
    private String detalle;

    public EmpresaMatriz(int idmatriz, String ruc, String nombre, String razonsocial, String detalle) {
        this.idmatriz = idmatriz;
        this.ruc = ruc;
        this.nombre = nombre;
        this.razonsocial = razonsocial;
        this.detalle = detalle;
    }

    public EmpresaMatriz() {
    }

    public int getIdmatriz() {
        return idmatriz;
    }

    public void setIdmatriz(int idmatriz) {
        this.idmatriz = idmatriz;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
}
