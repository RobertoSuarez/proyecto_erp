/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.FormulaMateriales;

/**
 *
 * @author HP
 */
public class FormulaMaterialesDAO {
    
    Conexion conexion;

    public FormulaMaterialesDAO() {
        conexion= new Conexion();
    }
    public int InsertarMateriales(FormulaMateriales materiales){
        try {
            conexion.Conectar();
            String consulta="INSERT INTO public.detalle_formula(\n" +
"	codigo_formula, cantidad_unitaria, unidad_medida, precio, codigo_producto)\n" +
"	VALUES ("+materiales.getCodigFormula()+", "+materiales.getCantidad()+", '"+materiales.getUnidadMedida()+"',"+
                    materiales.getPrecio()+", "+materiales.getCodigoProducto()+");";
            return conexion.insertar(consulta);
        } catch (Exception e) {
            return -1;
        }finally{
            conexion.desconectar();
        }
    }
    
    
    
}
