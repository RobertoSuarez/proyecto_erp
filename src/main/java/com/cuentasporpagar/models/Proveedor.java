/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.models;

import com.global.config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Proveedor {

    int idProveedor;
    String codigo;
    String razonSocial;
    String ruc;
    String nombre;
    String direccion;
    String email;
    String webPage;
    String contacto;
    String telefono;
    boolean estado = true;
    int vence;

  
  
    public Proveedor(String codigo, String nombre, String email,String direccion, String telefono, String contacto, boolean estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.contacto = contacto;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Proveedor(int idProveedor, String codigo, String razonSocial, String ruc, String nombre, String direccion, String email, String webPage, String contacto, String telefono, boolean estado) {
        this.idProveedor = idProveedor;
        this.codigo = codigo;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.webPage = webPage;
        this.contacto = contacto;
        this.telefono = telefono;
        this.estado = estado;
    }

    //DIANA: YO USO ESTO
    public Proveedor(int idProveedor, String codigo, String ruc, String nombre, int vence) {
        this.idProveedor = idProveedor;
        this.codigo = codigo;
        this.ruc = ruc;
        this.nombre = nombre;
        this.vence = vence;
    }
    
    //PAOLA LO USA
    public Proveedor(String codigo, String ruc, String nombre) {
        this.codigo = codigo;
        this.ruc = ruc;
        this.nombre = nombre;
    }

    public Proveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor() {
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){
         this.nombre = nombre;
    }

    public String set_Nombre(String nombre) {
        this.nombre = nombre;
        if (nombre == null) {
            return "";
        }
        return this.nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getVence() {
        return vence;
    }

    public void setVence(int vence) {
        this.vence = vence;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.idProveedor;
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
        final Proveedor other = (Proveedor) obj;
        return this.idProveedor == other.idProveedor;
    }
    
    
    // Metodos aux para comunicación con db.
    public static Proveedor getOneProveedor(int idproveedor) {
        Proveedor pro = new Proveedor();
        
        Conexion conn = new Conexion();
        String query = "select idproveedor, codigo, razonsocial, ruc, nombre, direccion, email, webpage, contacto, telefono, estado\n" +
                            "from proveedor\n" +
                            "where idproveedor=?;";
        try {
            conn.conectar();
            
            //Statement stmt = conn.conex.createStatement();
            PreparedStatement stmt = conn.conex.prepareStatement(query);
            stmt.setInt(1, idproveedor);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pro.setIdProveedor(rs.getInt("idproveedor"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setRazonSocial(rs.getString("razonsocial"));
                pro.setRuc(rs.getString("ruc"));
                pro.setNombre(rs.getString("nombre"));
                pro.setDireccion(rs.getString("direccion"));
                pro.setEmail(rs.getString("email"));
                pro.setWebPage(rs.getString("webpage"));
                pro.setContacto(rs.getString("contacto"));
                pro.setTelefono(rs.getString("estado"));
            }
            conn.conex.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return pro;
    }
}
