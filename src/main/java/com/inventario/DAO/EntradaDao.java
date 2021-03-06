/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.EntradaInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */
public class EntradaDao {

    Conexion conexion = new Conexion();
    private EntradaInventario entradaInventario;
    private ResultSet resultSet;
    private List<EntradaInventario> listaArticulos;
    private List auxlista = new ArrayList<>();

    public EntradaDao() {
        entradaInventario = new EntradaInventario();
        conexion = new Conexion();
        listaArticulos = new ArrayList<>();
    }

    public EntradaDao(EntradaInventario entradaInventario) {
        conexion = new Conexion();
        this.entradaInventario = entradaInventario;
    }

    public List<EntradaInventario> getEntradas(int idEntrada) {
        List<EntradaInventario> ListEntrada = new ArrayList<>();
        String sql = "";
        if (idEntrada > 0) {
            sql = "Select entrada.cod, num_comprobante, fecha, id_proveedor, id_bodega, proveedor.nombre as proveedor, nombre_bodega, observacion\n"
                    + "FROM entrada\n"
                    + "inner join proveedor on id_proveedor = idproveedor \n"
                    + "inner join bodega on id_bodega = bodega.cod"
                    + " WHERE entrada.cod=" + idEntrada;
        } else {
            sql = "Select entrada.cod, num_comprobante, fecha, id_proveedor, id_bodega, proveedor.nombre as proveedor, nombre_bodega, observacion\n"
                    + "FROM entrada\n"
                    + "inner join proveedor on id_proveedor = idproveedor \n"
                    + "inner join bodega on id_bodega = bodega.cod";
        }

        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                EntradaInventario  entradaInventario = new EntradaInventario();
                
                entradaInventario.setCod(resultSet.getInt("cod"));
                entradaInventario.setNumComprobante(resultSet.getString("num_comprobante"));
                entradaInventario.setFecha(resultSet.getDate("fecha"));
                entradaInventario.setIdBodega(resultSet.getInt("id_bodega"));
                
                entradaInventario.setIdProveedor(resultSet.getInt("id_proveedor"));
                entradaInventario.setNombreProveedor(resultSet.getString("proveedor"));
                entradaInventario.setNombreBodega(resultSet.getString("nombre_bodega"));
                entradaInventario.setObservacion(resultSet.getString("observacion"));
                
                ListEntrada.add(entradaInventario);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListEntrada;
    }

    public List<EntradaInventario> getEntradasPermitidas(int idEntrada) {
        List<EntradaInventario> ListEntrada = new ArrayList<>();
        String sql = "";
        if (idEntrada > 0) {
            sql = "Select entrada.cod, entrada.num_comprobante, entrada.fecha, entrada.id_proveedor, entrada.id_bodega, proveedor.nombre as proveedor, nombre_bodega\n"
                    + "FROM entrada\n"
                    + "inner join proveedor on id_proveedor = idproveedor \n"
                    + "inner join bodega on id_bodega = bodega.cod\n"
                    + "left join salida on salida.num_comprobante = entrada.num_comprobante\n"
                    + "where salida.num_comprobante is null and entrada.cod =" + idEntrada;
        } else {
            sql = "Select entrada.cod, entrada.num_comprobante, entrada.fecha, entrada.id_proveedor, entrada.id_bodega, proveedor.nombre as proveedor, nombre_bodega\n"
                    + "FROM entrada\n"
                    + "inner join proveedor on id_proveedor = idproveedor \n"
                    + "inner join bodega on id_bodega = bodega.cod\n"
                    + "left join salida on salida.num_comprobante = entrada.num_comprobante\n"
                    + "where salida.num_comprobante is null";
        }

        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListEntrada.add(new EntradaInventario(resultSet.getInt("cod"),
                        resultSet.getString("num_comprobante"),
                        resultSet.getDate("fecha"),
                        resultSet.getInt("id_bodega"),
                        resultSet.getInt("id_proveedor"),
                        resultSet.getString("proveedor"),
                        resultSet.getString("nombre_bodega")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListEntrada;
    }

    public List<EntradaInventario> getEntradas() {
        return getEntradas(0);
    }

    public List<EntradaInventario> getEntradasPermitidas() {
        return getEntradasPermitidas(0);
    }

    public int GuardarEntrada(EntradaInventario entradaInventario) {
        try {
            ResultSet rs = null;

            this.conexion.conectar();
            rs = this.conexion.ejecutarSql("select cod, num_comprobante from public.entrada order by cod desc limit 1;");
            int codigo = 1;

            //Asignar los valores de la siguiente venta y secuencia.
            while (rs.next()) {
                codigo = rs.getInt(1) + 1;
            }
            entradaInventario.setCod(codigo);
            System.out.println("Entrada: " + entradaInventario.getCod());

            //Insertar nueva venta
            String query = "INSERT INTO public.entrada("
                    + "num_comprobante, fecha, id_proveedor, id_bodega, observacion)"
                    + "VALUES('" + entradaInventario.getNumComprobante() + "-" + codigo + "', '" + entradaInventario.getFecha() + "', " + entradaInventario.getIdProveedor() +
                    ", " + entradaInventario.getIdBodega() + ", '" + entradaInventario.getObservacion() + "')";
            System.out.println(query);
            this.conexion.ejecutarSql(query);

            this.conexion.desconectar();

            System.out.println("Entrada Guardada exitosamente");

            return entradaInventario.getCod();
        } catch (SQLException e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.desconectar();
        }
        return 0;
    }

}
