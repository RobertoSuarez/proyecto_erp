/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.SalidaDetalleInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */
public class SalidaDetalleDao {
    
    Conexion conexion = new Conexion();
    private SalidaDetalleInventario salidaDetalleInventario;
    private ResultSet resultSet;
    private List<SalidaDetalleInventario> listaArticulos;
    private List auxlista = new ArrayList<>();

    public SalidaDetalleDao() {
        salidaDetalleInventario = new SalidaDetalleInventario();
        conexion = new Conexion();
        listaArticulos = new ArrayList<>();
    }

    public SalidaDetalleDao(SalidaDetalleInventario entradaDetalleInventario) {
        conexion = new Conexion();
        this.salidaDetalleInventario = entradaDetalleInventario;
    }

    public List<SalidaDetalleInventario> getSalidasDetalle() {
        return getSalidasDetalle(0);
    }

    public List<SalidaDetalleInventario> getSalidasDetalle(int idEntrada) {
        List<SalidaDetalleInventario> ListSalida = new ArrayList<>();

        String sql = "";
        if (idEntrada > 0) {
            sql =     "SELECT cod_articulo,id_entrada,cant,entrada_detalle.costo as costo,entrada_detalle.ice as ice,nombre,entrada_detalle.iva as iva,nombre,descripcion FROM entrada_detalle "
                    + "INNER JOIN articulos ON cod_articulo = id "
                    + "WHERE id_entrada=" + idEntrada;
        } else {
            sql = "SELECT cod_articulo,id_entrada,cant,entrada_detalle.costo as costo,entrada_detalle.ice as ice,nombre,entrada_detalle.iva as iva,descripcion FROM entrada_detalle "
                    + "INNER JOIN articulos ON cod_articulo = id ";
        }
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                SalidaDetalleInventario detalle = new SalidaDetalleInventario();
                detalle.setIdArticulo(resultSet.getInt("cod_articulo"));
                detalle.setIdEntrada(resultSet.getInt("id_entrada"));
                detalle.setCant(resultSet.getInt("cant"));
                detalle.setCosto(resultSet.getDouble("costo"));
                detalle.setIva(resultSet.getDouble("iva"));
                detalle.setIce(resultSet.getDouble("ice"));
                detalle.setNombreCategoria(resultSet.getString("descripcion"));
                detalle.setNombreProducto(resultSet.getString("nombre"));
                ListSalida.add(detalle);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListSalida;
    }

    public int GuardarEntrada(SalidaDetalleInventario entradaDetalleInventario) {
        try {
            ResultSet rs = null;

            this.conexion.conectar();
            rs = this.conexion.ejecutarSql("select cod from public.entrada_Detalle order by cod desc limit 1;");
            int codigo = 1;

            //Asignar los valores de la siguiente venta y secuencia.
            while (rs.next()) {
                codigo = rs.getInt(1) + 1;
            }
            entradaDetalleInventario.setIdEntradaDetalle(codigo);
            System.out.println("Entrada: " + entradaDetalleInventario.getIdEntradaDetalle());

            //Insertar nueva venta
            String query = "INSERT INTO public.entrada("
                    + "cod_articulo, id_entrada_detalle, id_entrada, cant, costo, iva, ice)"
                    + "VALUES(" + entradaDetalleInventario.getIdEntradaDetalle() + ",'" + entradaDetalleInventario.getIdEntradaDetalle() + "', " + entradaDetalleInventario.getCant() + ", " + entradaDetalleInventario.getCosto() + ", " + entradaDetalleInventario.getIva() + ", " + entradaDetalleInventario.getIce() + ")";
            System.out.println(query);
            this.conexion.ejecutarSql(query);

            this.conexion.desconectar();

            System.out.println("Salida Guardada exitosamente");

            return entradaDetalleInventario.getIdEntradaDetalle();
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.desconectar();
        }
        return 0;
    }

    public void RegistrarProductos(int idVenta, int idProducto, double cantidad, double precio, double iva, double ice) {
        try {
            int idDetalle = 1;
            ResultSet rs = null;
            this.conexion.conectar();

            //Recibir siguiente código de detalle venta
            String query = "select id_entrada_detalle from public.entrada_detalle order by id_entrada_detalle desc limit 1;";
            rs = this.conexion.ejecutarSql(query);

            while (rs.next()) {
                idDetalle = rs.getInt(1) + 1;
            }

            //insertar detalle venta
            query = "insert into public.entrada_detalle(id_salida_detalle, id_entrada, cod_articulo, cant, costo, iva, ice) values(" + idDetalle + "," + idVenta + ","
                    + idProducto + "," + cantidad + "," + precio +", " + iva + ", " + ice + ")";
            System.out.println(query);
            this.conexion.ejecutarSql(query);

            //Reducir stock
            int cantidadActual = 0;
            query = "select cantidad from public.articulos where id =" + idProducto + ";";
            rs = this.conexion.ejecutarSql(query);
            while (rs.next()) {
                cantidadActual = rs.getInt(1);
            }
            query = "update public.articulos set cantidad = " + (cantidadActual + (int) cantidad) + " where id = " + idProducto + ";";
            this.conexion.ejecutarSql(query);

            this.conexion.desconectar();
        } catch (SQLException e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.desconectar();
        }
    }
}
