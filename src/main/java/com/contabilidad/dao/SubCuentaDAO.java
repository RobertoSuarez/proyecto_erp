/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.dao;

import com.contabilidad.models.SubCuenta;
import com.global.config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubCuentaDAO {
    private Conexion conexion;
    private ResultSet result;
    private List<SubCuenta> subcuentas;

    public SubCuentaDAO() {
        this.conexion = new Conexion();
    }
    
    public List<SubCuenta> getSubcuentas(){
        subcuentas = new ArrayList<>();
        try{
            conexion.conectar();
            String query = "select * from public.subcuenta;";
            result = conexion.ejecutarSql(query);
            while(result.next()){
                subcuentas.add(new SubCuenta(result.getInt("idsubcuenta"), result.getInt("idcuenta"), result.getInt("idimpuesto"), result.getString("codigo"),
                            result.getString("nombre"), result.getString("tiposaldo")));
            }
            return subcuentas;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }finally{
            conexion.desconectar();
        }
    }
    
    public List<SubCuenta> getSubCuentas(String nombreCuenta){
        subcuentas = new ArrayList<>();
        try{
            conexion.conectar();
            String query = "select * from public.subcuenta as sc inner join public.cuenta as c on c.idcuenta = sc.idcuenta where c.nombre = '" + nombreCuenta + "';";
            result = conexion.ejecutarSql(query);
            while(result.next()){
                subcuentas.add(new SubCuenta(result.getInt("idsubcuenta"), result.getInt("idcuenta"), result.getInt("idimpuesto"), result.getString("codigo"),
                            result.getString("nombre"), result.getString("tiposaldo")));
            }
            return subcuentas;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }finally{
            conexion.desconectar();
        }
    }
    
    public SubCuenta getSubCuenta(Integer idSubCuenta){
        SubCuenta subCuenta = new SubCuenta();
        try{
            conexion.conectar();
            String query = "select * from public.subcuenta where idsubcuenta = " + idSubCuenta + ";";
            result = conexion.ejecutarSql(query);
            while(result.next()){
                subCuenta = new SubCuenta(result.getInt("idsubcuenta"), result.getInt("idcuenta"), result.getInt("idimpuesto"), result.getString("codigo"),
                            result.getString("nombre"), result.getString("tiposaldo"));
                if(subcuentas != null){ 
                    subcuentas.add(subCuenta);
                }
            }
            return subCuenta;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }finally{
            conexion.desconectar();
        }
    }
}
