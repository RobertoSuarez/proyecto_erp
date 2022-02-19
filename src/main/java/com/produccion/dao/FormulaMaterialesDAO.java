package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.FormulaMateriales;
import java.sql.SQLException;

public class FormulaMaterialesDAO {

    Conexion conexion;

    public FormulaMaterialesDAO() {
        conexion = new Conexion();
    }

    public int InsertarMateriales(FormulaMateriales materiales) {
        try {
            String consulta = "INSERT INTO public.detalle_formula(\n"
                    + "	codigo_formula, cantidad_unitaria, unidad_medida, precio, codigo_producto,codigo_subproceso)\n"
                    + "	VALUES (" + materiales.getCodigFormula() + ", " + materiales.getCantidad() + ", '" + materiales.getUnidadMedida() + "',"
                    + materiales.getPrecio() + ", " + materiales.getCodigoProducto() + ", " + materiales.getCodigoSuproceso() + ");";
            return conexion.insertar(consulta);
        } finally {
            conexion.desconectar();
        }
    }

}
