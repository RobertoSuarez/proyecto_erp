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
    public void insertarF(FormulaProduccion f) {
        try {
            this.conexion.Conectar();
            
            String cadena = " INSERT INTO public.formula(codigo_formula, codigo_formula, "
                    + "codigo_proceso, nombre_formula, descripcion, rendimiento, estado, codigo_producto)\n"
                    +"VALUES (1,'"+f.getCodigo_formula()+ "','"+f.getDescripcion()+ "','"+f.getEstado()+ "',4)";

            conexion.Ejecutar2(cadena);
            conexion.cerrarConexion();
        } catch (SQLException e) {

        } finally {
            conexion.cerrarConexion();
        }
    }
    
     public void actualizar(FormulaProduccion fp, int codigo) throws SQLException {
        try {
            this.conexion.Conectar();
            String sql = "UPDATE public.formula\n" 
                    +"SET nombre_formula='"+fp.getNombre_formula()+"',"
                    +"descripcion='"+fp.getDescripcion()+"', "
                    +"rendimiento='"+fp.getRendimiento()+"', "
                    + "WHERE codigo_producto="+codigo+"";
            conexion.ejecutar(sql);
            conexion.cerrarConexion();
            
        } catch (SQLException e) {
               throw e;
          } finally {
               this.conexion.cerrarConexion();
          }
    }
    
    
    

}