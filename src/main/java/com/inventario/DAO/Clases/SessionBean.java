/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO.Clases;

/**
 *
 * @author angul
 */
import java.io.IOException;
import java.security.Principal;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class SessionBean {

    private RolesDAO roles; // The JPA entity.

    public RolesDAO getRoles() {
        if (roles == null) {
            RolesDAO principal = (RolesDAO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roles");
            if (principal != null) {
                roles = principal;
            }
        }
        return roles;
    }
}
