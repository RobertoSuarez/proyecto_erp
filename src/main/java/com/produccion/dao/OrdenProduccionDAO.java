package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.CentroCosto;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.OrdenProduccion;
import com.produccion.models.OrdenTrabajo;
import com.produccion.models.SolicitudOrden;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrdenProduccionDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public OrdenProduccionDAO() {
        conexion = new Conexion();
    }

    public List<OrdenProduccion> getListaOrden() {
        List<OrdenProduccion> ordenProduccion = new ArrayList<>();
        sentenciaSql = String.format("select * from orden_produccion;");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProduccion.add(new OrdenProduccion(resultSet.getInt("codigo_orden"), resultSet.getDate("fecha_orden"),
                        resultSet.getDate("fecha_fin"), resultSet.getString("descripcion"), resultSet.getString("estado")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProduccion;
    }

    public List<OrdenTrabajo> getListaProducto(int codigo_orden) {
        List<OrdenTrabajo> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select a.id,rop.codigo_registro,a.nombre,rop.cantidad from orden_produccion as op\n"
                + "	inner join registro_orden_produccion as rop on op.codigo_orden=rop.codigo_orden\n"
                + "	inner join articulos as a on rop.\"Codigo_producto\"=a.id\n"
                + "	where op.codigo_orden=" + codigo_orden + "and rop.estado='P';");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProducto.add(new OrdenTrabajo(resultSet.getInt("id"), resultSet.getInt("codigo_registro"),
                        resultSet.getString("nombre"), resultSet.getFloat("cantidad")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProducto;
    }

    public List<OrdenTrabajo> getListaFormula(int codigo_producto) {
        List<OrdenTrabajo> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select f.codigo_formula,f.nombre_formula from formula as f \n"
                + "	inner join articulos as a on f.codigo_producto=a.id\n"
                + "	where f.codigo_producto=" + codigo_producto + ";");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProducto.add(new OrdenTrabajo(resultSet.getInt("codigo_formula"),
                        resultSet.getString("nombre_formula")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProducto;
    }

    public String getProceso(int codigo_formula) {
        String proceso = "";
        sentenciaSql = String.format("select pp.codigo_proceso,pp.nombre from formula as f\n"
                + "	inner join proceso_produccion as pp\n"
                + "	on f.codigo_proceso=pp.codigo_proceso\n"
                + "	where f.codigo_formula=" + codigo_formula + ";");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                proceso = resultSet.getString("nombre");
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return proceso;
    }

    public List<CentroCosto> getListaCentro() {
        List<CentroCosto> centro = new ArrayList<>();
        sentenciaSql = String.format("select codigo_centroc,nombre from centro_costo;");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                centro.add(new CentroCosto(resultSet.getInt("codigo_centroc"),
                        resultSet.getString("nombre")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return centro;
    }

    public List<ArticuloFormula> getListaConsumoMateriales(int codigo, float cantidad) {
        List<ArticuloFormula> articulosFormula = new ArrayList<>();
        sentenciaSql = String.format("select sc.idsubcuenta,sc.codigo,a.id,a.nombre,a.descripcion, df.\"cantidadUnidad\"*" + cantidad + " as cantidadUnidad,t.tipo ,df.unidad_medida "
                + ",a.costo from detalle_formula as df \n"
                + "	inner join articulos as a on df.codigo_producto=a.id\n"
                + "     inner join subcuenta as sc on sc.idsubcuenta=a.id_subcuenta\n"
                + "	inner join tipo as t on t.cod=a.id_tipo\n"
                + "	where df.codigo_formula=" + codigo + ";");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                articulosFormula.add(new ArticuloFormula(resultSet.getInt("id"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("tipo"),
                        resultSet.getFloat("cantidadUnidad"), resultSet.getString("unidad_medida"), resultSet.getFloat("costo"), resultSet.getInt("idsubcuenta"), resultSet.getString("codigo")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return articulosFormula;
    }

    public boolean renderProduccionDetallada(int id_formula) {
        boolean verifica = false;
        sentenciaSql = String.format("select estado from formula \n"
                + "	where codigo_formula=" + id_formula + ";");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                verifica = resultSet.getBoolean("estado");
            }
            return verifica;
        } catch (SQLException e) {
            return verifica;
        } finally {
            conexion.desconectar();
        }
    }

    public List<FormulaProduccion> getListaCosto(int codigo_formula, float unidad) {
        List<FormulaProduccion> costos = new ArrayList<>();
        sentenciaSql = String.format("select nombre_formula, tiempo_unidad*" + unidad + " as tiempo,((tiempo_unidad*" + unidad + ")*modunidad) as MO,\n"
                + "	((tiempo_unidad*" + unidad + ")*cifunidad) as CIF from formula\n"
                + "	where codigo_formula=" + codigo_formula + ";");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costos.add(new FormulaProduccion(resultSet.getString("nombre_formula"), resultSet.getFloat("tiempo"),
                        resultSet.getFloat("mo"), resultSet.getFloat("cif")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return costos;
    }

    public List<FormulaProduccion> getListaCostos(int codigo_formula, int cod_registro, float unidad, String costo) {
        List<FormulaProduccion> costos = new ArrayList<>();
        sentenciaSql = String.format("select sc.idsubcuenta,sc.codigo,sc.nombre as nombrecuenta ,pp.nombre as nombreproceso,sum(dsp." + costo + "*" + unidad + ") as costo from orden_produccion as op \n"
                + "	inner join registro_orden_produccion as rop \n"
                + "	on op.codigo_orden=rop.codigo_orden\n"
                + "	inner join articulos as a on a.id=rop.\"Codigo_producto\"\n"
                + "	inner join formula as f on f.codigo_producto=a.id\n"
                + "	inner join proceso_produccion as pp on f.codigo_proceso=pp.codigo_proceso\n"
                + "	inner join detalle_proceso_p as dp on pp.codigo_proceso=dp.codigo_proceso\n"
                + "	inner join subproceso as sp on sp.codigo_subproceso=dp.codigo_subproceso\n"
                + "	inner join detalle_subproceso as dsp on dsp.codigo_subproceso=sp.codigo_subproceso\n"
                + "	inner join subcuenta as sc on sc.idsubcuenta=dsp.idsubcuenta\n"
                + "	where f.codigo_formula=" + codigo_formula + " and rop.codigo_registro=" + cod_registro + "" + " and (dsp." + costo + "*" + unidad + ")>0\n"
                + "	group by sc.idsubcuenta,sc.codigo,sc.nombre,pp.nombre\n"
                + "	order by sc.nombre asc");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costos.add(new FormulaProduccion(resultSet.getString("nombreproceso"), resultSet.getFloat("costo"), resultSet.getInt("idsubcuenta"), resultSet.getString("codigo"), resultSet.getString("nombrecuenta")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return costos;
    }

    public int registrarProduccion(OrdenTrabajo ordenTrabajo) {
        sentenciaSql = String.format("INSERT INTO public.detalleproceso(\n"
                + "	codigo_formula, codigo_registro, codigo_centroc, tiempo_proceso, costos_generado, fecha_inicio, fecha_fin, descripcion, cantidad, costomateriaprima, costodirecto, costoindirecto, costounitario)\n"
                + "	VALUES (" + ordenTrabajo.getCodigo_formula() + ", " + ordenTrabajo.getCodigo_registro()
                + ", " + ordenTrabajo.getCodigo_centro_trabajo() + ", " + ordenTrabajo.getTiempo() + ", " + ordenTrabajo.getCostoTotal()
                + ",'" + ordenTrabajo.getFecha_inicio() + "', '" + ordenTrabajo.getFecha_fin() + "', '" + ordenTrabajo.getDescripcion() + "', "
                + ordenTrabajo.getCantidad() + ", " + ordenTrabajo.getTotalMateria() + ", " + ordenTrabajo.getTotalMOD() + ", "
                + ordenTrabajo.getTotalCIF() + ", " + ordenTrabajo.getCostoUnitario() + ");");
        try {

            if (conexion.insertar(sentenciaSql) > 0) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }

    }

    public int actualizarOrden(int codigoOrden) {
        sentenciaSql = String.format("UPDATE public.registro_orden_produccion\n"
                + "	SET estado='T'\n"
                + "	WHERE codigo_registro=" + codigoOrden + ";");
        try {
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }

    }

    public boolean verificaOrden(int orden) {
        boolean estado = true;
        List<OrdenTrabajo> state = new ArrayList<>();
        sentenciaSql = String.format("select estado from registro_orden_produccion\n"
                + "	where codigo_orden=" + orden + "");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                state.add(new OrdenTrabajo(resultSet.getString("estado").trim()));
            }
            for (OrdenTrabajo o : state) {
                if (!"T".equals(o.getEstado())) {
                    estado = false;
                }
            }
            return estado;
        } catch (SQLException e) {
            return false;
        } finally {
            conexion.desconectar();
        }
    }

    public int actualizaEstado(int orden) {
        try {
            sentenciaSql = String.format("UPDATE public.orden_produccion\n"
                    + "	SET estado='T'\n"
                    + "	WHERE codigo_orden=" + orden + ";");
            return conexion.insertar(sentenciaSql);

        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public SolicitudOrden calculaProduccion(int orden) {
        try {
            SolicitudOrden nueva = new SolicitudOrden();
            sentenciaSql = String.format("select op.codigo_orden,op.codigo_secundario,op.descripcion,op.fecha_orden,op.fecha_fin,\n"
                    + "	sum(costos_generado)as total from orden_produccion as op\n"
                    + "	inner join registro_orden_produccion as rop \n"
                    + "	on op.codigo_orden=rop.codigo_orden\n"
                    + "	inner join detalleproceso as dp on dp.codigo_registro=rop.codigo_registro\n"
                    + "	where op.codigo_orden=" + orden + "\n"
                    + "	group by  op.codigo_orden,op.codigo_secundario,op.descripcion,op.fecha_orden,op.fecha_fin");
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                nueva.setCodigo_orden(resultSet.getInt("codigo_orden"));
                nueva.setCodigoSecundario(resultSet.getString("codigo_secundario").trim());
                nueva.setDescripcion(resultSet.getString("descripcion").trim());
                nueva.setFecha_orden(resultSet.getDate("fecha_orden"));
                nueva.setFecha_fin(resultSet.getDate("fecha_fin"));
                nueva.setTotal(resultSet.getFloat("total"));
            }
            return nueva;
        } catch (SQLException e) {
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    public SolicitudOrden movimientosProduccion(int orden) {
        try {
            SolicitudOrden nueva = new SolicitudOrden();
            sentenciaSql = String.format("select op.codigo_orden,op.codigo_secundario,op.descripcion,op.fecha_orden,op.fecha_fin,\n"
                    + "	sum(costos_generado)as total from orden_produccion as op\n"
                    + "	inner join registro_orden_produccion as rop \n"
                    + "	on op.codigo_orden=rop.codigo_orden\n"
                    + "	inner join detalleproceso as dp on dp.codigo_registro=rop.codigo_registro\n"
                    + "	where op.codigo_orden=" + orden + "\n"
                    + "	group by  op.codigo_orden,op.codigo_secundario,op.descripcion,op.fecha_orden,op.fecha_fin");
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                nueva.setCodigo_orden(resultSet.getInt("codigo_orden"));
                nueva.setCodigoSecundario(resultSet.getString("codigo_secundario").trim());
                nueva.setDescripcion(resultSet.getString("descripcion").trim());
                nueva.setFecha_orden(resultSet.getDate("fecha_orden"));
                nueva.setFecha_fin(resultSet.getDate("fecha_fin"));
                nueva.setTotal(resultSet.getFloat("total"));
            }
            return nueva;
        } catch (SQLException e) {
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    public List<OrdenTrabajo> listaCostoProduccion(int orden) {
        List<OrdenTrabajo> costosMovimiento = new ArrayList<>();
        sentenciaSql = String.format("select dp.codigo_formula,rop.codigo_registro,rop.cantidad from detalleproceso as dp \n"
                + "	inner join registro_orden_produccion as rop on dp.codigo_registro=rop.codigo_registro\n"
                + "	where rop.codigo_orden=" + orden + "");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                costosMovimiento.add(new OrdenTrabajo(resultSet.getInt("codigo_registro"), resultSet.getInt("codigo_formula"), resultSet.getFloat("cantidad")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return costosMovimiento;
    }

    public void insertAsiento(SolicitudOrden orden, List<FormulaProduccion> listaMovimientos,float directo,float indirect,float materiales ) {
        try {
            int iddiario = 0;
            String cadena = " select iddiario from diariocontable \n"
                    + "	  where descripcion = 'Modulo Producción'";
            resultSet = conexion.ejecutarSql(cadena);
            while (resultSet.next()) {
                iddiario = resultSet.getInt("iddiario");
            }
            String sentencia1, sentencia;

            sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + orden.getTotal()
                    + ",\"documento\": \"" + orden.getCodigoSecundario() + " R\",\"detalle\": \""
                    + orden.getDescripcion() + "\",\"fechaCreacion\": \""
                    + new SimpleDateFormat("dd-MM-yyyy").format(orden.getFecha_orden()) + "\",\"fechaCierre\":\""
                    + new SimpleDateFormat("dd-MM-yyyy").format(orden.getFecha_fin()) + "\"}";
            sentencia1 = "[";
            for (FormulaProduccion movimiento : listaMovimientos) {
                sentencia1 += "{\"idSubcuenta\":\"" + movimiento.getSubcuenta() + "\",\"debe\":\""
                        + movimiento.getCosto() + "\",\"haber\":\"0\",\"tipoMovimiento\":\""
                        + movimiento.getNombre() + "\"},";
            }
            sentencia1 += "{\"idSubcuenta\":\"104\",\"debe\":\"0\",\"haber\":\""
                    + directo + "\",\"tipoMovimiento\":\"Produccion en proceso\"},{\"idSubcuenta\":\"17\",\"debe\":\"0\",\"haber\":\""
                    + materiales + "\",\"tipoMovimiento\":\"Produccion en proceso\"},{\"idSubcuenta\":\"143\",\"debe\":\"0\",\"haber\":\""
                    + indirect + "\",\"tipoMovimiento\":\"Produccion en proceso\"}]";
            intJson(sentencia, sentencia1);
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
    }

    public void intJson(String a, String b) {

        try {
            String cadena = "SELECT public.generateasientocotableexternal('"
                    + a + "','" + b + "')";
            conexion.ejecutarSql(cadena);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " error en conectarse");
        } finally {
            conexion.desconectar();
        }

    }

    public List<ArticuloFormula> listaMaterialesFormula(int orden) {
        List<ArticuloFormula> materiales = new ArrayList<>();
        sentenciaSql = String.format("	select dp.codigo_formula,rop.cantidad from orden_produccion as op \n"
                + "	inner join registro_orden_produccion as rop\n"
                + "	on op.codigo_orden=rop.codigo_orden\n"
                + "	inner join detalleproceso as dp on dp.codigo_registro=rop.codigo_registro\n"
                + "	where op.codigo_orden=" + orden + "");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                materiales.add(new ArticuloFormula(resultSet.getInt("codigo_formula"), resultSet.getFloat("cantidad")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return materiales;
    }

}
