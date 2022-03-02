package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dSubprocesoDAO {

    private Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public dSubprocesoDAO() {
        conexion = new Conexion();
    }

    public List<Costo> getCostoDirecto() {
        List<Costo> costo = new ArrayList<>();
        sentenciaSql = String.format("SELECT sc.idsubcuenta as codigo_subcuenta, sc.nombre subcuenta,  \n"
                + "   sg.nombre subgrupo, c.nombre cuenta,sc.codigo as identificador\n"
                + "  FROM subcuenta as sc inner join cuenta as c on sc.idcuenta = c.idcuenta \n"
                + "  inner join subgrupo as sg on c.idsubgrupo = sg.idsubgrupo \n"
                + "  inner join grupocuenta as g on sg.idgrupo = g.idgrupo \n"
                + "  where g.idgrupo=5 and g.codigo='5' and sg.codigo='5.2' and c.codigo='5.2.1'\n"
                + " order by sc.idsubcuenta");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_subcuenta"), resultSet.getString("subcuenta"),
                        resultSet.getString("subgrupo"), resultSet.getString("cuenta"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return costo;
    }

    public List<Costo> getCostoIndirecto() {
        List<Costo> costo = new ArrayList<>();
        sentenciaSql = String.format("SELECT sc.idsubcuenta as codigo_subcuenta, sc.nombre subcuenta,\n"
                + "	sg.nombre subgrupo, c.nombre cuenta,sc.codigo as identificador\n"
                + "	FROM subcuenta as sc inner join cuenta as c on sc.idcuenta = c.idcuenta \n"
                + "	inner join subgrupo as sg on c.idsubgrupo = sg.idsubgrupo\n"
                + "	inner join grupocuenta as g on sg.idgrupo = g.idgrupo \n"
                + "	where (g.idgrupo=5 and g.codigo='5') and (sg.codigo='5.2' or sg.codigo='5.3'or sg.codigo='5.4')\n"
                + "	and (c.codigo='5.2.2' or c.codigo='5.3.1' or c.codigo='5.3.2')\n"
                + "	order by sc.idsubcuenta");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_subcuenta"), resultSet.getString("subcuenta"),
                        resultSet.getString("subgrupo"), resultSet.getString("cuenta"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return costo;
    }

    public List<PersonalSubproceso> getListaEmpleado() {
        List<PersonalSubproceso> personal = new ArrayList<>();
        sentenciaSql = String.format("select e.id_empleado,e.nombre1,s.valor from empleado_bck_rrhh as e \n"
                + "inner join sueldo as s on e.id_empleado=s.id_empleado");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                personal.add(new PersonalSubproceso(resultSet.getInt("id_empleado"), resultSet.getString("nombre1"),
                         resultSet.getFloat("valor")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return personal;
    }
}
