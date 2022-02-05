/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.SubProceso;
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
    
    public void getProcess(int cod){
        
    }

    public List<FormulaProduccion> getFormula() {
        List<FormulaProduccion> formula = new ArrayList<>();
        String sql = String.format("Select * from formula");
        try {

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
        } finally{
            conexion.desconectar();
        }
        return formula;
    }
    
    public List<SubProceso> getSubProceso(int id) {
        List<SubProceso> subProceso = new ArrayList<>();
        String sql = String.format("select SP.codigo_subproceso,SP.nombre from subproceso as SP \n" +
"	inner join detalle_proceso_p as DP on SP.codigo_subproceso=DP.codigo_subproceso\n" +
"	inner join proceso_produccion as P on DP.codigo_proceso=P.codigo_proceso\n" +
"	where P.codigo_proceso="+id);
        try {

            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                subProceso.add(new SubProceso(resultSet.getInt("codigo_subproceso"),
                        resultSet.getString("nombre")
                        ));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally{
            conexion.desconectar();
        }
        return subProceso;
    }
    
    public List<FormulaProduccion> getArticulos(){
        List<FormulaProduccion> Materiales = new ArrayList<>();
        String sqlSentencia="select a.id,a.nombre, c.nom_categoria,a.descripcion,t.tipo,a.costo,a.cantidad,a.max_stock from articulos as a\n" +
"	inner join categoria as c on a.cat_cod=c.cod\n" +
"	inner join tipo as t on t.cod=a.id_tipo";
        try {

            resultSet = conexion.ejecutarSql(sqlSentencia);
            //Llena la lista de los datos
            while (resultSet.next()) {
                Materiales.add(new FormulaProduccion(resultSet.getInt("id"),resultSet.getString("nombre"),resultSet.getString("nom_categoria"),
                resultSet.getString("descripcion"),resultSet.getString("tipo"),resultSet.getFloat("costo"),resultSet.getFloat("cantidad"),resultSet.getFloat("max_stock")));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally{
            conexion.desconectar();
        }
        return Materiales;
    }

    /**
     * Método para insertar un proveedor recibiendo un parámetro Dicha clase
     * implementa try and catch
     *
     * @param f objeto formula
     */
    public int insertarFormula(FormulaProduccion f) {
        try {
            String cadena = "INSERT INTO public.formula(\n"
                    + "	 codigo_proceso, nombre_formula, descripcion, rendimiento, estado,codigo_producto)\n"
                    + "	VALUES ( "+f.getCodigo_proceso()+", '" + f.getNombre_formula() + "', '" + f.getDescripcion() + "', " + f.getRendimiento() + ", 'T', "+f.getCodigo_producto()+");";
            return conexion.insertar(cadena);

        } catch (Exception e) {
            return -1;
        }finally {
            conexion.cerrarConexion();
        }
    }

    public void update(FormulaProduccion formula) throws SQLException {
        try {
            this.conexion.Conectar();
            String sql = "UPDATE public.formula\n"
                    + " SET codigo_proceso='" + formula.getCodigo_proceso() + "',"
                    + " nombre_formula='" + formula.getNombre_formula() + "', "
                    + " descripcion='" + formula.getDescripcion() + "', "
                    + " rendimiento='" + formula.getRendimiento() + "', "
                    + " estado='" + formula.getEstado() + "', "
                    + " codigo_producto='" + formula.getCodigo_producto() + "', "
                    + "Where codigo_formula= ";

            conexion.ejecutar(sql);

        } catch (SQLException e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }
    
    // Parte de Elimar formula 
   public void eliminarF(FormulaProduccion formulaProduccion, String aux) throws SQLException{
       try {
           
           this.conexion.Conectar();
           String sql=("DELETE FROM public.formula WHERE nombre_formula = '"+aux+"'");
           conexion.ejecutar(sql);
           conexion.cerrarConexion();
           
       } catch (Exception e) {
           throw e;
       } finally{
       this.conexion.cerrarConexion();
       
       }
   
        
   }
    
    

}
