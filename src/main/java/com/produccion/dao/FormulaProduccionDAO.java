/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.FormulaProduccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimmy
 */
public class FormulaProduccionDAO {

    Conexion conexion = new Conexion();
    private FormulaProduccion formulaProduccion;
    private ResultSet resultSet;
    private List<FormulaProduccion> listaFormula;
    private List auxlista = new ArrayList<>();

    public FormulaProduccionDAO() {
        formulaProduccion = new FormulaProduccion();
        conexion = new Conexion();
        listaFormula = new ArrayList<>();
    }

    public FormulaProduccionDAO(FormulaProduccion formulaProduccion) {
        conexion = new Conexion();
        this.formulaProduccion = formulaProduccion;
    }

    public List<FormulaProduccion> getFormula() {
        List<FormulaProduccion> formula = new ArrayList<>();
        String sql = String.format("Select * from formula");
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                formula.add(new FormulaProduccion(resultSet.getInt("codigo_formula"),
                        resultSet.getInt("codigo_proceso"),
                        resultSet.getString("nombre_formula"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("rendimiento"),
                        resultSet.getString("estado"),
                        resultSet.getInt("codigo_producto")));

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
           conexion.desconectar();
        }
        return formula;
    }

    /**
     * Método para insertar un proveedor recibiendo un parámetro Dicha clase
     * implementa try and catch
     *
     * @param f objeto formula
     */
    public void insertarFormula(FormulaProduccion f) {
        try {
            this.conexion.Conectar();
            String cadena = "INSERT INTO public.FormulaProduccion(\n"
                    + " codigo_formula, nombre_formula,descripcion,rendimiento,estado)\n"
                    + " VALUES ('" + f.getCodigo_formula() + "','" + f.getNombre_formula() + "'"
                    + ",'" + f.getDescripcion() + "','" + f.getRendimiento() + "','" + f.getEstado() + "');";
            conexion.Ejecutar2(cadena);
            conexion.cerrarConexion();
        } catch (SQLException e) {

        } finally {
            conexion.cerrarConexion();
        }
    }

    
}