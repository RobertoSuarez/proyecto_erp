package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.FormulaMateriales;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.SubProceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormulaProduccionDAO {

    Conexion conexion = new Conexion();
    FormulaProduccion formulaProduccion;
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
            //enviamos la sentencia
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

        } catch (SQLException e) {

        } finally {
            conexion.desconectar();
        }
        return formula;
    }

    public int InsertarMateriales(FormulaMateriales materiales, int id) {
        try {
            String consulta = "INSERT INTO public.detalle_formula(\n"
                    + "	codigo_formula, cantidad_unitaria, unidad_medida, precio, codigo_producto,codigo_subproceso,\"cantidadUnidad\")\n"
                    + "	VALUES (" + id + ", " + materiales.getCantidad() + ", '" + materiales.getUnidadMedida() + "',"
                    + materiales.getPrecio() + ", " + materiales.getCodigoProducto() + ", " + materiales.getCodigoSuproceso() + ", " + materiales.getCantidadUnidad() + ");";
            return conexion.insertar(consulta);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }

    }

    public int actualizarMateriales(int idFormula, int idproducto, float cantidad) {
        try {
            String consulta = "UPDATE public.detalle_formula\n"
                    + "	SET cantidad_unitaria=" + cantidad + "\n"
                    + "	WHERE codigo_formula=" + idFormula + " and codigo_producto=" + idproducto + ";";
            return conexion.insertar(consulta);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }

    }

    public List<SubProceso> getSubProceso(int id) {
        List<SubProceso> subProceso = new ArrayList<>();
        String sql = String.format("select SP.codigo_subproceso,SP.nombre from subproceso as SP \n"
                + "	inner join detalle_proceso_p as DP on SP.codigo_subproceso=DP.codigo_subproceso\n"
                + "	inner join proceso_produccion as P on DP.codigo_proceso=P.codigo_proceso\n"
                + "	where P.codigo_proceso=" + id);
        try {

            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                subProceso.add(new SubProceso(resultSet.getInt("codigo_subproceso"),
                        resultSet.getString("nombre")
                ));

            }

        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return subProceso;
    }

    public int IdFormula() {
        try {
            int idFormula = 0;
            String sentencia = "select max(codigo_formula)+1 as Id from formula";
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                idFormula = resultSet.getInt("Id");
            }
            return idFormula;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public List<FormulaProduccion> getArticulosT(String nombre) {
        List<FormulaProduccion> Materiales = new ArrayList<>();
        String sqlSentencia = "select a.id,a.nombre,c.nom_categoria,a.descripcion,t.tipo,a.costo\n"
                + ",ab.cant,a.max_stock,um.unidad_medida\n"
                + "from articulo_bodega as ab \n"
                + "inner join articulos as a on ab.id_articulo=a.id\n"
                + "inner join categoria as c on a.id_categoria=c.cod\n"
                + "inner join tipo as t on t.cod=a.id_tipo\n"
                + "inner join unidades_medidas as um on a.id_unidadmedida=um.id\n"
                + "where c.nom_categoria!='" + nombre + "' and c.nom_categoria!='Servicio'";

        try {

            resultSet = conexion.ejecutarSql(sqlSentencia);
            //Llena la lista de los datos
            while (resultSet.next()) {
                Materiales.add(new FormulaProduccion(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("nom_categoria"),
                        resultSet.getString("descripcion"), resultSet.getString("tipo"), resultSet.getFloat("costo"), resultSet.getFloat("cant"), resultSet.getFloat("max_stock"), resultSet.getString("unidad_medida")));

            }

        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return Materiales;
    }

    public List<FormulaProduccion> getArticulosM() {
        List<FormulaProduccion> Materiales = new ArrayList<>();
        String sqlSentencia = "select a.id,a.nombre, c.nom_categoria,a.descripcion,t.tipo,\n"
                + "	a.costo,a.cantidad,a.max_stock,a.unidadmedida\n"
                + "	from articulos as a	inner join categoria as c on a.id_categoria=c.cod\n"
                + "	inner join tipo as t on t.cod=a.id_tipo\n"
                + "	where c.nom_categoria!='Producto Terminado'";

        try {

            resultSet = conexion.ejecutarSql(sqlSentencia);
            //Llena la lista de los datos
            while (resultSet.next()) {
                Materiales.add(new FormulaProduccion(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("nom_categoria"),
                        resultSet.getString("descripcion"), resultSet.getString("tipo"), resultSet.getFloat("costo"), resultSet.getFloat("cantidad"), resultSet.getFloat("max_stock"), resultSet.getString("unidadmedida")));

            }

        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return Materiales;
    }

    public int insertarFormula(FormulaProduccion f) {
        try {
            String cadena = "INSERT INTO public.formula(\n"
                    + " nombre_formula, descripcion, rendimiento, estado,codigo_producto,\"MOD\", \"CIF\",tiempo_formula, tiempo_unidad, modunidad, cifunidad)\n"
                    + "	VALUES ('" + f.getNombre_formula() + "', '" + f.getDescripcion() + "', " + f.getRendimiento()
                    + ", '" + f.getEstado() + "', " + f.getCodigo_producto() + ", " + f.getMOD() + ", " + f.getCIF() + ", " + f.getTiempoFormula() + ", " + f.getTiempoUnidad() + ", " + f.getMODUnidad() + ", " + f.getCIFUnidad() + ");";
            return conexion.insertar(cadena);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public int idFormula() {
        int id = 0;
        String sqlSentencia = "select max(codigo_formula)as id from formula";

        try {

            resultSet = conexion.ejecutarSql(sqlSentencia);
            //Llena la lista de los datos
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public void update(FormulaProduccion formula) throws SQLException {
        String sql = "UPDATE public.formula\n"
                + "	SET  nombre_formula='" + formula.getNombre_formula() + "', descripcion='" + formula.getDescripcion() + "'\n"
                + "	WHERE codigo_formula=" + formula.getCodigo_formula() + ";";
        conexion.insertar(sql);
        conexion.desconectar();
    }

    public void eliminarF(FormulaProduccion formulaProduccion, String aux) throws SQLException {
        try {

            String sql = ("DELETE FROM public.formula WHERE nombre_formula = '" + aux + "'");
            conexion.ejecutarSql(sql);

        } finally {
            conexion.desconectar();
        }

    }

    public float MOD(int codigo_proceso, float pieza) {
        try {
            float cif = 0;
            String sql = "select ((pp.minutos_pieza*" + pieza + ")*pp.minuto_directo)as MDO \n"
                    + "	from proceso_produccion as pp \n"
                    + "	where pp.codigo_proceso=" + codigo_proceso + ";";
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                cif = resultSet.getFloat("MDO");
            }
            return cif;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public float CIF(int codigo, float pieza) {
        try {
            float cif = 0;
            String sql = "select ((pp.minutos_pieza*" + pieza + ")*pp.minuto_indirecto)as CIF \n"
                    + "	from proceso_produccion as pp \n"
                    + "	where pp.codigo_proceso=" + codigo + ";";
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                cif = resultSet.getFloat("CIF");
            }
            return cif;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public float tiempoFormula(int codigo, float pieza) {
        try {
            float cif = 0;
            String sql = "select (pp.minutos_pieza*" + pieza + ")as total \n"
                    + "	from proceso_produccion as pp \n"
                    + "	where pp.codigo_proceso=" + codigo + ";";
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                cif = resultSet.getFloat("total");
            }
            return cif;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public List<FormulaProduccion> traemeFormula(int id) {
        List<FormulaProduccion> editFormula = new ArrayList<>();
        String sqlSentencia = "select f.codigo_formula, a.nombre,f.nombre_formula,f.descripcion,\n"
                + "f.rendimiento,t.tipo,c.nom_categoria  from formula as f \n"
                + "	inner join proceso_produccion as pp on pp.codigo_proceso=f.codigo_proceso\n"
                + "	inner join articulos as a \n"
                + "	inner join tipo as t  on t.cod=a.id_tipo\n"
                + "	inner join categoria as c on c.cod=a.id_categoria\n"
                + "	on f.codigo_producto=a.id\n"
                + "	where f.codigo_formula=" + id + "";
        try {

            resultSet = conexion.ejecutarSql(sqlSentencia);
            //Llena la lista de los datos
            while (resultSet.next()) {
                editFormula.add(new FormulaProduccion(resultSet.getInt("codigo_formula"), resultSet.getString("nombre_formula"), resultSet.getString("nombre"), resultSet.getString("descripcion"), resultSet.getInt("rendimiento"),
                        resultSet.getString("nom_categoria"), resultSet.getString("tipo")));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return editFormula;
    }

    public List<FormulaProduccion> listaMateriales(int id) {
        List<FormulaProduccion> editFormula = new ArrayList<>();
        String sqlSentencia = "select df.codigo_formula,a.id, a.nombre as nombre_producto,a.descripcion,um.unidad_medida,df.cantidad_unitaria,\n"
                + "		 a.costo,sp.codigo_subproceso,sp.nombre \n"
                + "		 from detalle_formula as df \n"
                + "		 inner join articulos as a on df.codigo_producto=a.id\n"
                + "		 inner join unidades_medidas as um on a.id_unidadmedida=um.id\n"
                + "		 inner join subproceso as sp on sp.codigo_subproceso=df.codigo_subproceso\n"
                + "		 where df.codigo_formula="+id+"";
        try {

            resultSet = conexion.ejecutarSql(sqlSentencia);
            //Llena la lista de los datos
            while (resultSet.next()) {
                editFormula.add(new FormulaProduccion(resultSet.getInt("codigo_formula"),
                        resultSet.getInt("codigo_subproceso"), resultSet.getInt("id"), resultSet.getString("nombre_producto"),
                        resultSet.getString("descripcion"), resultSet.getString("nombre"), resultSet.getFloat("costo"),
                        resultSet.getFloat("cantidad_unitaria"), resultSet.getString("unidad_medida")));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return editFormula;
    }

    public int rendimiento(int idProceso) {
        try {
            int rendimiento = 0;
            String sentencia = "select cantidad_estimada from proceso_produccion\n"
                    + "where codigo_proceso=" + idProceso + "";
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                rendimiento = resultSet.getInt("cantidad_estimada");
            }
            return rendimiento;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public List<SubProceso> getListaSubProceso(int id) {
        List<SubProceso> subProceso = new ArrayList<>();
        String sql = String.format("select sp.codigo_subproceso,sp.nombre,sp.costo_directo,sp.costo_indirecto \n"
                + "		from proceso_produccion as pp \n"
                + "		inner join detalle_proceso_p as dpp on pp.codigo_proceso=dpp.codigo_proceso\n"
                + "		inner join subproceso as sp on sp.codigo_subproceso=dpp.codigo_subproceso\n"
                + "		where pp.codigo_proceso=" + id);
        try {

            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                subProceso.add(new SubProceso(resultSet.getInt("codigo_subproceso"),
                        resultSet.getString("nombre"), resultSet.getFloat("costo_directo"), resultSet.getFloat("costo_indirecto")
                ));

            }

        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return subProceso;
    }

    public List<SubProceso> getListaSubProcesoAdicional() {
        List<SubProceso> subProceso = new ArrayList<>();
        String sql = String.format("select * from subproceso");
        try {

            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                subProceso.add(new SubProceso(resultSet.getInt("codigo_subproceso"),
                        resultSet.getString("nombre"), resultSet.getFloat("costo_directo"), resultSet.getFloat("costo_indirecto")
                ));

            }

        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return subProceso;
    }

    public int InsertarDetalleFormula(SubProceso detalle, int idFormula) {
        try {
            String consulta = "INSERT INTO public.detalle_formula_subproceso(\n"
                    + "	codigo_formula, codigo_subproceso, hora_trabajo, minuto_trabajo, importe_directo, importe_indirecto, minutos_pieza, costo_minuto_directo, costo_minuto_indirecto)\n"
                    + "	VALUES (" + idFormula + ", " + detalle.getCodigo_subproceso() + ",'" + detalle.getHora() + "'," + convertMinutos(detalle.getHora())
                    + " , " + detalle.getImporte_directo() + ", " + detalle.getImporte_indirecto() + ", " + detalle.getPieza_minuto() + ", " + detalle.getCosto_minuto_directo() + ", " + detalle.getCosto_minuto_indirecto() + ");";
            return conexion.insertar(consulta);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }

    }

    public float convertMinutos(String hora) {
        float minutos = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(hora);
        } catch (ParseException e) {
            System.out.println("" + e);
        }
        minutos += date.getSeconds() / 60;
        minutos += date.getHours() * 60;
        minutos += date.getMinutes();
        return minutos;
    }

}
