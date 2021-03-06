/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.models;

/**
 *
 * @author User
 */
public class EstadoResultado {

    private int parent, id;
    private String nombre, saldoCadena;
    private double saldo;

    public EstadoResultado(int parent, int id, String nombre, String saldoCadena, double saldo) {
        this.parent = parent;
        this.id = id;
        this.nombre = nombre;
        this.saldoCadena = saldoCadena;
        this.saldo = saldo;
    }

    public EstadoResultado() {
    }

    public String getSaldoCadena() {
        return saldoCadena;
    }

    public void setSaldoCadena(String saldoCadena) {
        this.saldoCadena = saldoCadena;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "EstadoResultado{" + "parent=" + parent + ", id="
                + id + ", nombre=" + nombre + ", saldo=" + saldo + '}';
    }
}
