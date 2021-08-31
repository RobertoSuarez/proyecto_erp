package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.Anticipo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elect
 */
public class AnticipoDAO {

    // getAll trae todos los registro de la base de datos, de los anticipos
    // Consulta:
    // select "idAnticipo", importe, "fechaRegistro", descripcion, "idProveedor"
    //      from anticipo;
    public static List<Anticipo> getAll() {
        List<Anticipo> anticipos = new ArrayList<>();
        Conexion conn = new Conexion();
        String query = "select \"id_anticipo\", importe, \"fecha\", descripcion, \"id_proveedor\"\n"
                + "    from anticipo;";
        try {
            conn.abrirConexion();
            Statement stmt = conn.conex.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Anticipo anticipo = new Anticipo();
                anticipo.setId_anticipo(rs.getString("id_anticipo"));
                anticipo.setImporte(rs.getDouble("importe"));
                anticipo.setFecha(rs.getObject("fecha", Date.class));
                anticipo.setDescripcion(rs.getString("descripcion"));
                anticipo.setId_proveedor(rs.getInt("id_proveedor"));

                anticipos.add(anticipo);
            }
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                conn.conex.close();
            } catch (SQLException ex) {
                Logger.getLogger(AnticipoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return anticipos;
    }

    // GetAllDBProveedor va iterando por cada anticipo y va a traer el proveedor
    // de cada anticipo.
    public static List<Anticipo> GetAllDBProveedor(List<Anticipo> anticipos) {
        anticipos.forEach((Anticipo ant) -> {
            ant.GetDBProveedor();
        });
        return anticipos;
    }

    // El metodo getAllJson(), trae todos los anticipos con su proveedor,
    // toda esta información es traida en formato json, la cual se deserializa
    // con la biblioteca Gson de google.
    public static List<Anticipo> getAllJson(boolean revertido) throws SQLException {
        String datos = null;
        Conexion conn = new Conexion();
        String query = "select select_all_anticipo_width_proveedor('"+ revertido +"') as _anticipo;";
        try {
            conn.abrirConexion();

            Statement statement = conn.conex.createStatement();
            //PreparedStatement stmt = conn.conex.prepareStatement(query);

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                datos = rs.getString("_anticipo");
            }

            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.conex.close();
        }

        // Contenedor de los datos.
        List<Anticipo> anticiposDB = null;

        try {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Anticipo>>() {
            }.getType();
            // Deserializamos los datos de json a java objeto.
            anticiposDB = gson.fromJson(datos, collectionType);

            System.out.println("Llegaron los datos correctamente.");
            System.out.println(datos);

        } catch (JsonSyntaxException ex) {
            System.out.println("Error gson: " + ex.getMessage());
        }

        return anticiposDB;
    }
    
    public static void Revertir(Anticipo anticipo) {
        int id_asiento = 0;
        
        
        // creamos los asientos y los movimientos.
        Anticipo.Asiento asiento = new Anticipo().new Asiento();
        asiento.setIdDiario("11");
        asiento.setTotal(anticipo.getImporte().toString());
        asiento.setDocumento("ANT-REV-" + anticipo.getReferencia());
        asiento.setDetalle(anticipo.getDescripcion());
        asiento.setFechaCreacion(new SimpleDateFormat("dd-MM-yyyy").format(anticipo.getFecha()));
        asiento.setFechaCierre(new SimpleDateFormat("dd-MM-yyyy").format(anticipo.getFecha()));
        
        List<Anticipo.Movimiento> movimientos = new ArrayList<>();
        
        Anticipo.Movimiento movimiento1 = new Anticipo().new Movimiento();
        movimiento1.setIdSubcuenta("2"); // Subcuenta caja chica
        movimiento1.setDebe("0");
        movimiento1.setHaber(anticipo.getImporte().toString()); // lo que entra en la caja chica
        movimiento1.setTipoMovimiento("Anticipo de proveedor Revertido");
        
        Anticipo.Movimiento movimiento2 = new Anticipo().new Movimiento();
        movimiento2.setIdSubcuenta("65"); // Subcuenta Anticipo proveedor
        movimiento2.setDebe(anticipo.getImporte().toString()); // lo que sale de anticipo
        movimiento2.setHaber("0"); // lo que entra en anticipo
        movimiento2.setTipoMovimiento("Anticipo de proveedor Revertido");
        
        movimientos.add(movimiento1);
        movimientos.add(movimiento2);
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(asiento));
        System.out.println(gson.toJson(movimientos));
        
        Conexion conn = new Conexion();
        
        // Registramos el asiento con los movimeintos, y obtenemos el id del asiento.
        try {
            String queryAsiento = String.format("SELECT public.generateasientocotableexternal2('%s', '%s') as id_asiento", gson.toJson(asiento), gson.toJson(movimientos));
            System.out.println("desde reversión: " + queryAsiento);
            conn.abrirConexion();
            
            Statement statement = conn.conex.createStatement();
            ResultSet data = statement.executeQuery(queryAsiento);
            while (data.next()) {
                id_asiento = data.getInt("id_asiento");                
            }
            
            System.out.println("id asiento recuperado de la db " + id_asiento);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("No se puedo registrar el asiento del anticipo, se cacelo todo en el ambito de revertir el anticipo");
            try {
                conn.conex.close();
            } catch (SQLException ex1) {
                Logger.getLogger(Anticipo.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return;
        }
        
        
        // Actualizamos el anticipo, para revertirlo con el asiento registrado anteriormente.
        String query =  "update anticipo set revertido='true', id_asiento_revertido=? where id_anticipo=?;";
        try {
            
            PreparedStatement stmt = conn.conex.prepareStatement(query);
            stmt.setInt(1, id_asiento);
            stmt.setString(2, anticipo.getId_anticipo());
            
            stmt.execute();
            //ResultSet rs = stmt.executeQuery(query);
          
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
            
        } finally {
            try {
                conn.conex.close();
            } catch (SQLException ex1) {
                Logger.getLogger(Anticipo.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    
    }
}
