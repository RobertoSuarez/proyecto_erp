package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import com.produccion.models.dSubproceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubProcesoDAO {

    private Conexion conexion;
    private ResultSet resultSet;

    public SubProcesoDAO() {
        conexion = new Conexion();
    }

    public List<ProcesoProduccion> getProcesosProduccion() {
        List<ProcesoProduccion> procesos = new ArrayList<>();
        String sentenciaSql = String.format("select * from proceso_produccion;");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new ProcesoProduccion(resultSet.getInt("codigo_proceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return procesos;
    }

    public int insertardSubproceso(SubProceso proceso) {
        try {
            //llamamos a la conexion
            float minutos = 0;
            this.conexion.Conectar();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date = null;
            System.out.println("" + proceso.getHora());
            try {
                date = sdf.parse(proceso.getHora());
            } catch (ParseException e) {
            }
            minutos += date.getSeconds() / 60;
            minutos += date.getHours() * 60;
            minutos += date.getMinutes();

            String sentenciaSql = "INSERT INTO public.detalle_proceso_p(\n"
                    + "	codigo_proceso, codigo_subproceso, horas, cantidad_estimada)\n"
                    + "	VALUES (" + proceso.getId_codigo_proceso() + "," + proceso.getCodigo_subproceso()
                    + "," + minutos + "," + proceso.getRendimiento() + ");";
            //enviamos la sentencia
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public int insertarDetalleSubproceso(dSubproceso subproceso) {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            String sentenciaSql = "INSERT INTO public.detalle_subproceso(\n"
                    + "	codigo_subproceso, codigo_costos, costo_mano_obra, costo_indirecto, hora_costo)\n"
                    + "	VALUES (" + subproceso.getCodigo_subproceso() + ", " + subproceso.getCodigo_costos() + ", " + subproceso.getCosto_mano_obra() + ", " + subproceso.getCosto_indirecto() + ", "
                    + subproceso.getHora_costo() + ");";
            //enviamos la sentencia
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public int insertarSubproceso(SubProceso proceso) {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            String sentenciaSql = "INSERT INTO public.subproceso(\n"
                    + " nombre, descripcion)\n"
                    + "	VALUES ('"
                    + proceso.getNombre() + "', '" + proceso.getDescripcion() + "');";
            //enviamos la sentencia
            return conexion.insertar(sentenciaSql);
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public int actualizaProceso(SubProceso proceso) {
        try {
            float minutos = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date = null;
            System.out.println("" + proceso.getHora());
            try {
                date = sdf.parse(proceso.getHora());
            } catch (ParseException e) {
            }
            minutos += date.getSeconds() / 60;
            minutos += date.getHours() * 60;
            minutos += date.getMinutes();
            proceso.setPieza(1);
            proceso.setMinuto_pieza(minutos / proceso.getRendimiento());
            proceso.setMinuto_directo((MOD(proceso.getId_codigo_proceso()))/minutos);
            proceso.setMinuto_intirecto((CIF(proceso.getId_codigo_proceso()))/minutos);
            String sentenciaSql = "UPDATE public.proceso_produccion\n"
                    + "	SET pieza=" + proceso.getPieza() + ", minutos_pieza=" + proceso.getMinuto_pieza() + ", minuto_directo="+proceso.getMinuto_directo()
                    +", minuto_indirecto="+proceso.getMinuto_intirecto()+", cantidad_estimada="+proceso.getRendimiento()+"\n"
                    + "	WHERE codigo_proceso="+proceso.getId_codigo_proceso()+";";
            
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        }
    }

    public float CIF(int codProceso) {
        try {
            conexion.Conectar();
            float costoIndirecto = 0;
            String sentenciaSql = "select Sum(dsp.costo_indirecto) as CIF from proceso_produccion as pp \n"
                    + "	inner join detalle_proceso_p as dp on pp.codigo_proceso=dp.codigo_proceso\n"
                    + "	inner join subproceso as sp on sp.codigo_subproceso=dp.codigo_subproceso\n"
                    + "	inner join detalle_subproceso as dsp on sp.codigo_subproceso=dsp.codigo_subproceso\n"
                    + "	where pp.codigo_proceso=" + codProceso;
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                costoIndirecto = resultSet.getFloat("CIF");
            }
            return costoIndirecto;

        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public float MOD(int codProceso) {
        try {
            conexion.Conectar();
            float costoIndirecto = 0;
            String sentenciaSql = "select Sum(dsp.costo_mano_obra) as CMO from proceso_produccion as pp \n"
                    + "	inner join detalle_proceso_p as dp on pp.codigo_proceso=dp.codigo_proceso\n"
                    + "	inner join subproceso as sp on sp.codigo_subproceso=dp.codigo_subproceso\n"
                    + "	inner join detalle_subproceso as dsp on sp.codigo_subproceso=dsp.codigo_subproceso\n"
                    + "	where pp.codigo_proceso=" + codProceso;
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                costoIndirecto = resultSet.getFloat("CMO");
            }
            return costoIndirecto;

        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }

    }

    public int idSubproceso() {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            int id = -1;
            String sentenciaSql = "select max(codigo_subproceso)as ultimo from subproceso;";
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                id = resultSet.getInt("ultimo");
            }
            return id;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }
}
