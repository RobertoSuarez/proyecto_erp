/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.models;

/**
 *
 * @author Andres Mora
 */
public class Modulo {
    private int idModule;
    private String nameModule;
    private String descriptionModule;
    private boolean enabled;

    public Modulo() {
        
    }

    public Modulo(String nameModule, String descriptionModule) {
        this.nameModule = nameModule;
        this.descriptionModule = descriptionModule;
    }
    
    

    public Modulo(int idModule, String nameModule, String descriptionModule, boolean enabled) {
        this.idModule = idModule;
        this.nameModule = nameModule;
        this.descriptionModule = descriptionModule;
        this.enabled = enabled;
    }

    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }

    public String getNameModule() {
        return nameModule;
    }

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public String getDescriptionModule() {
        return descriptionModule;
    }

    public void setDescriptionModule(String descriptionModule) {
        this.descriptionModule = descriptionModule;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
