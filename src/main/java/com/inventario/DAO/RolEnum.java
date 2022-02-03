/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

/**
 *
 * @author angul
 */
public enum RolEnum {

    VENDEDOR("Vendedor"),
    GERENTE("Gerente")
    ;

    private final String text;

    /**
     * @param text
     */
    RolEnum(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
    
    
}
