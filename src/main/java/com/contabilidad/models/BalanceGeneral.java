package com.contabilidad.models;

public class BalanceGeneral {

    private int parent, id;
    private String nombre, saltoString;
    private double saldo;

    public String getSaltoString() {
        return saltoString;
    }

    public void setSaltoString(String saltoString) {
        this.saltoString = saltoString;
    }

    public BalanceGeneral() {
    }

    public BalanceGeneral(int id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public BalanceGeneral(int parent, int id, String nombre, double saldo) {
        this.parent = parent;
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
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
        return "BalanceGeneral{" + "parent=" + parent + ", id=" + id + ", nombre=" + nombre + ", saldo=" + saldo + '}';
    }

    public String toStringInfo() {
        return "{nombre=" + nombre + ", saldo=" + saldo + '}';
    }

}
