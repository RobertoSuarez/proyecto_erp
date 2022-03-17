package com.produccion.models;

/**
 *
 * @author Alex
 */
public class ProcesoProduccion {

    private int codigo_subProceso;
    private int codigo_proceso;
    private String nombre;
    private String descripcion;
    private String identificador;

    public ProcesoProduccion() {
    }

    public ProcesoProduccion(int codigo_proceso) {
        this.codigo_proceso = codigo_proceso;
    }

    public ProcesoProduccion(int codigo_proceso, String nombre, String descripcion) {
        this.codigo_proceso = codigo_proceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public ProcesoProduccion(int codigo_proceso, String nombre, String descripcion,
            String identificador) {
        this.codigo_proceso = codigo_proceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.identificador = identificador;
    }

    public int getCodigo_proceso() {
        return codigo_proceso;
    }

    public void setCodigo_proceso(int codigo_proceso) {
        this.codigo_proceso = codigo_proceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getCodigo_subProceso() {
        return codigo_subProceso;
    }

    public void setCodigo_subProceso(int codigo_subProceso) {
        this.codigo_subProceso = codigo_subProceso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.codigo_proceso;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcesoProduccion other = (ProcesoProduccion) obj;
        if (this.codigo_proceso != other.codigo_proceso) {
            return false;
        }
        return true;
    }

}
