/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.models;

/**
 *
 * @author Crisito
 */
public class Vista {
    private int id_Vista;
    private String name_Vista;
    private String description_Vista;
    private int id_Modulo;
    private String name_Modulo;

    public Vista() {
    }

    public Vista(int id_Vista, String name_Vista, String description_Vista, int id_Modulo, String name_modulo) {
        this.id_Vista = id_Vista;
        this.name_Vista = name_Vista;
        this.description_Vista = description_Vista;
        this.id_Modulo = id_Modulo;
        this.name_Modulo = name_modulo;
    }

    public int getId_Vista() {
        return id_Vista;
    }

    public void setId_Vista(int id_Vista) {
        this.id_Vista = id_Vista;
    }

    public String getName_Vista() {
        return name_Vista;
    }

    public void setName_Vista(String name_Vista) {
        this.name_Vista = name_Vista;
    }

    public String getDescription_Vista() {
        return description_Vista;
    }

    public void setDescription_Vista(String description_Vista) {
        this.description_Vista = description_Vista;
    }

    public int getId_Modulo() {
        return id_Modulo;
    }

    public void setId_Modulo(int id_Modulo) {
        this.id_Modulo = id_Modulo;
    }

    public String getName_Modulo() {
        return name_Modulo;
    }

    public void setName_Modulo(String name_Modulo) {
        this.name_Modulo = name_Modulo;
    }
    
}
