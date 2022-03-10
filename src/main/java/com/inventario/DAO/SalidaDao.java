/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.SalidaInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */
public class SalidaDao {
    Conexion conexion = new Conexion();
    private SalidaInventario entradaInventario;
    private ResultSet resultSet;
    private List<SalidaInventario> listaArticulos;
    private List auxlista = new ArrayList<>();

    public SalidaDao() {
        entradaInventario = new SalidaInventario();
        conexion = new Conexion();
        listaArticulos = new ArrayList<>();
    }

    public SalidaDao(SalidaInventario salidaInventario) {
        conexion = new Conexion();
        this.entradaInventario = entradaInventario;
    }

    public List<SalidaInventario> getSalidas(int idSalida) {
        List<SalidaInventario> ListEntrada = new ArrayList<>();
        String sql = "";
        if (idSalida > 0) {
            sql = "Select salida.cod, num_comprobante, fecha, id_proveedor, id_bodega, proveedor.nombre as proveedor, nombre_bodega\n"
                    + "FROM salida\n"
                    + "inner join proveedor on id_proveedor = idproveedor \n"
                    + "inner join bodega on id_bodega = bodega.cod"
                    + " WHERE salida.cod=" + idSalida;
        } else {
            sql = "Select salida.cod, num_comprobante, fecha, id_proveedor, id_bodega, proveedor.nombre as proveedor, nombre_bodega\n"
                    + "FROM salida\n"
                    + "inner join proveedor on id_proveedor = idproveedor \n"
                    + "inner join bodega on id_bodega = bodega.cod";
        }

        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListEntrada.add(new SalidaInventario(resultSet.getInt("cod"),
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

    public List<SalidaInventario> getSalidas() {
        return getSalidas(0);
    }

    public int GuardarSalida(SalidaInventario entradaInventario) {
        try {
            ResultSet rs = null;

            this.conexion.conectar();
            rs = this.conexion.ejecutarSql("select cod, num_comprobante from public.salida order by cod desc limit 1;");
            int codigo = 1;

            //Asignar los valores de la siguiente venta y secuencia.
            while (rs.next()) {
                codigo = rs.getInt(1) + 1;
            }
            entradaInventario.setCod(codigo);
            System.out.println("Entrada: " + entradaInventario.getCod());

            //Insertar nueva venta
            String query = "INSERT INTO public.salida("
                    + "num_comprobante, fecha, id_proveedor, id_bodega)"
                    + "VALUES('" +  entradaInventario.getNumComprobante() + "', '" + entradaInventario.getFecha() + "', " + entradaInventario.getIdProveedor() + ", " + entradaInventario.getIdBodega() + ")";
            System.out.println(query);
            this.conexion.ejecutarSql(query);

            this.conexion.desconectar();

            System.out.println("Salida Guardada exitosamente");

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
