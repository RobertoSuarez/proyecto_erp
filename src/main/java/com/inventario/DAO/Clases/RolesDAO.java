/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO.Clases;

import com.seguridad.models.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */

/*
  Clase encargada para administrar los roles que se manejaran
*/
public class RolesDAO implements Serializable {

    private List<Rol> roles;

    public RolesDAO() {
        this.roles = new ArrayList<Rol>();
    }

    public RolesDAO(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Rol> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /*
    Método para agregar un rol a una lista
    */
    public void addRol(Rol rol) {
        ArrayList<Rol> lista = new ArrayList<Rol>();
        if (roles == null) {
            roles = lista;
        }
        this.roles.add(rol);
    }
    /*
    Método para verificar que un rol  exista mediante el nombre que recibe
    */
    public boolean rolExist(String rolName) {
        if (roles != null) {
            if (!roles.isEmpty()) {
                for (Rol namerol : roles) {
                    if (namerol.getNombre().equals(rolName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    Método para verificar que varios roles existan mediante el arreglo
    que recibe
    */
    public boolean rolExist(String[] rolNames) {
        for (String rolname : rolNames) {
            if (rolExist(rolname)) {
                return true;
            }
        }
        return (false);
    }
    /*
    Método para verificar que varios roles exista mediante el arreglo
    que recibe
    */
    public boolean rolExist(int[] ids) {
        for (int rolId : ids) {
            if (rolExist(rolId)) {
                return true;
            }
        }
        return (false);
    }
    
     /*
    Método para verificar que varios roles existan mediante una lista
    que recibe
    */
    public boolean rolExist(List<String> rolNames){
        return this.rolExist((String[])rolNames.toArray());
    }
    public boolean rolExist(int rolId) {
        if (roles != null) {
            if (!roles.isEmpty()) {
                for (Rol namerol : roles) {
                    if (namerol.getId() == rolId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
