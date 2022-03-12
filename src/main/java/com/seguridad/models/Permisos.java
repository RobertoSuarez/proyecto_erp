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
public class Permisos {
    private int id_vista;
    private String nameViews;
    private boolean can_erased;
    private boolean can_insert;
    private boolean can_edit;
    private boolean can_views;

    public Permisos() {
    }

    public Permisos(int id_vista, String nameViews, boolean can_erased, boolean can_insert, boolean can_edit, boolean can_views) {
        this.id_vista = id_vista;
        this.nameViews = nameViews;
        this.can_erased = can_erased;
        this.can_insert = can_insert;
        this.can_edit = can_edit;
        this.can_views = can_views;
    }

    public int getId_vista() {
        return id_vista;
    }

    public void setId_vista(int id_vista) {
        this.id_vista = id_vista;
    }

    public String getNameViews() {
        return nameViews;
    }

    public void setNameViews(String nameViews) {
        this.nameViews = nameViews;
    }

    public boolean isCan_erased() {
        return can_erased;
    }

    public void setCan_erased(boolean can_erased) {
        this.can_erased = can_erased;
    }

    public boolean isCan_insert() {
        return can_insert;
    }

    public void setCan_insert(boolean can_insert) {
        this.can_insert = can_insert;
    }

    public boolean isCan_edit() {
        return can_edit;
    }

    public void setCan_edit(boolean can_edit) {
        this.can_edit = can_edit;
    }

    public boolean isCan_views() {
        return can_views;
    }

    public void setCan_views(boolean can_views) {
        this.can_views = can_views;
    }
    
    
}
