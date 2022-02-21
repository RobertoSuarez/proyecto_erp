/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

public class Categoria {
    
    private int idCat;
    private String nom_categoria;
    private String descripcion_categoria;

    public Categoria() {
    }

    public Categoria(int idCat, String nom_categoria, String descripcion_categoria) {
        this.idCat = idCat;
        this.nom_categoria = nom_categoria;
        this.descripcion_categoria = descripcion_categoria;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }
    
    
}
