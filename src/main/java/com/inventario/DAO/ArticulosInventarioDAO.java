/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.contabilidad.models.SubCuenta;
import com.global.config.Conexion;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Categoria;
import com.inventario.report.ProductoReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticulosInventarioDAO {

    Conexion conexion = new Conexion();
    private ArticulosInventario articulosInventario;
    private SubCuenta subCuenta;
    private ResultSet resultSet;
    private String sentenciaSql;
    private List<ArticulosInventario> listaArticulos;
    private List<SubCuenta> listasubCuentas;
    private List auxlista = new ArrayList<>();

    public ArticulosInventarioDAO() {
        articulosInventario = new ArticulosInventario();
        conexion = new Conexion();
        listaArticulos = new ArrayList<>();
        listasubCuentas = new ArrayList<>();
    }

    public ArticulosInventarioDAO(ArticulosInventario articulosInventario) {
        conexion = new Conexion();
        this.articulosInventario = articulosInventario;
    }

    public ArticulosInventarioDAO(SubCuenta subCuenta) {
        conexion = new Conexion();
        this.subCuenta = subCuenta;
    }

    public ArticulosInventarioDAO(SubCuenta subCuenta, List<SubCuenta> listasubCuentas) {
        this.subCuenta = subCuenta;
        this.listasubCuentas = listasubCuentas;
    }

        public List<ArticulosInventario> getArticulosEntradas() {
        List<ArticulosInventario> ListaInv = new ArrayList<>();
        String sql = String.format("Select * FROM articulos");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaInv.add(new ArticulosInventario(resultSet.getInt("id"),
                        resultSet.getInt("cat_cod"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_tipo"),
                        resultSet.getInt("cod"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("id_bodega"),
                        resultSet.getInt("min_stock"),
                        resultSet.getInt("max_stock"),
                        resultSet.getInt("cantidad"),
                        resultSet.getInt("costo"),
                        resultSet.getInt("iva"),
                        resultSet.getFloat("ice") ));
             }
            
             
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }finally{
            conexion.desconectar();
        }
        return ListaInv;
         }
    
    public List<ArticulosInventario> getArticulos(int idProveedor,String numeroComprobante) {
        List<ArticulosInventario> ListaInv = new ArrayList<>();
        String sql = String.format("select distinct "
                + "articulos.id,articulos.id_categoria,articulos.nombre,articulos.id_tipo,articulos.descripcion,articulos.id_bodega, cant ,articulos.costo,articulos.iva,articulos.ice,articulos.max_stock, cantidad "
                + "from articulos\n" +
"inner join entrada_detalle on cod_articulo = articulos.id\n" +
"inner join entrada on entrada.cod = entrada_detalle.id_entrada\n" +
"where entrada.num_comprobante = '"+numeroComprobante+"' and entrada.id_proveedor = "+idProveedor+"");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ArticulosInventario articulo = new ArticulosInventario();
                articulo.setId(resultSet.getInt("id"));
                articulo.setId_categoria(resultSet.getInt("id_categoria"));
                articulo.setNombre(resultSet.getString("nombre"));
                articulo.setId_tipo(resultSet.getInt("id_tipo"));
                articulo.setDescripcion(resultSet.getString("descripcion"));
                articulo.setId_bodega(resultSet.getInt("id_bodega"));
                articulo.setCantidad(resultSet.getInt("cant"));
                articulo.setCosto(resultSet.getInt("costo"));
                articulo.setIva(resultSet.getInt("iva"));
                articulo.setIceproducto(resultSet.getFloat("ice"));
                articulo.setMax_stock(resultSet.getInt("cantidad"));
                
                ListaInv.add(articulo);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaInv;
    }
    
    public List<ArticulosInventario> getArticulos() {
        List<ArticulosInventario> ListaInv = new ArrayList<>();
        String sql = String.format("select a.id, a.nombre,a.descripcion,b.nombre_bodega,a.unidadmedida, a.cantidad, a.costo\n"
                + "from articulos a inner join bodega b on a.id_bodega=b.cod order by a.id");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ArticulosInventario articulo = new ArticulosInventario();
                articulo.setId(resultSet.getInt("id"));
                articulo.setNombre(resultSet.getString("nombre"));
                articulo.setDescripcion(resultSet.getString("descripcion"));
                articulo.setNomBodega(resultSet.getString("nombre_bodega"));
                articulo.setUnidadMedida(resultSet.getString("unidadmedida"));
                articulo.setCantidad(resultSet.getInt("cantidad"));
                articulo.setCoast(resultSet.getFloat("costo"));
                ListaInv.add(articulo);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaInv;
    }

    //listar subcuenta
    public List<SubCuenta> LlenarSubcuentas() {
        if (conexion.isEstado()) {
            try {
                String consulta = "select idsubcuenta, nombre from subcuenta order by idsubcuenta desc";
                resultSet = conexion.ejecutarSql(consulta);
                while (resultSet.next()) {
                    listasubCuentas.add(new SubCuenta(resultSet.getInt("idsubcuenta"),
                            resultSet.getString("nombre")));
                }
                System.out.println(consulta);
                resultSet.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.desconectar();
            }
        }
        return listasubCuentas;
    }

    public Categoria getCategoria(int id) {
        ResultSet rs;
        Categoria temp = new Categoria();

        try {
            String code = String.valueOf(id);
            conexion.conectar();
            //rs = conexion.ejecutarSql("select * from public.buscarproductocodigo(" + code.trim() + ")");
            rs = conexion.ejecutarSql("select cod,nom_categoria,descripcion from public.categoria where cod=" + code.trim());

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");

                while (rs.next()) {
                    System.out.print("Producto " + rs.getInt(1));
                    temp.setIdCat(rs.getInt(1));
                    temp.setNom_categoria(rs.getString(2));
                    temp.setDescripcion_categoria(rs.getString(3));

                }
            }
            conexion.desconectar();

            return temp;
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
        } finally {
            conexion.desconectar();
        }

        return null;
    }

    public ArticulosInventario ObtenerProducto(int id) {
        ResultSet rs;
        ArticulosInventario temp = new ArticulosInventario();

        try {
            String code = String.valueOf(id);
            conexion.conectar();
            //rs = conexion.ejecutarSql("select * from public.buscarproductocodigo(" + code.trim() + ")");
            rs = conexion.ejecutarSql("select codigo,codigoaux,stock,description,price,subsi,t_ice,t_iva,t_descuento,max_stock from public.buscarproductocodigo(" + code.trim()
                    + ")inner join articulos on public.articulos.id = codigo ");
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");

                while (rs.next()) {
                    System.out.print("Producto " + rs.getInt(1));
                    temp.setCod(rs.getInt(1));
                    temp.setCat_cod(rs.getInt(2));
                    temp.setNombre(rs.getString(3));
                    temp.setDescripcion(rs.getString(4));
                    temp.setId_bodega(rs.getInt(5));
                    temp.setCosto(rs.getInt(6));
                    temp.setIce(rs.getInt(7));
                    temp.setIva(rs.getInt(8));
                    temp.setMax_stock(rs.getInt(9));
                }
            }
            conexion.desconectar();

            return temp;
        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
        } finally {
            conexion.desconectar();
        }

        return null;
    }

    public List<Categoria> getCategorias() {
        List<Categoria> ListaCategoria = new ArrayList<>();
        String sql = String.format("Select * FROM categoria");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaCategoria.add(new Categoria(resultSet.getInt("cod"),
                        resultSet.getString("nom_categoria"),
                        resultSet.getString("descripcion")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaCategoria;
    }

    //insertar nuevo articulo
    public void insertarAriculo(ArticulosInventario articulo) {
        try {
            ResultSet rs = null;
            this.conexion.conectar();
            rs = this.conexion.ejecutarSql("select id from articulos order by id desc limit 1;");
            int cod = 1;
            int tipo = 1;
            int bodegaid = 1;
            int categoriaid = 1;
            while (rs.next()) {
                cod = rs.getInt(1) + 1;
            }
            articulo.setId(cod);
            System.out.println("Articulo: " + articulo.getId());

            sentenciaSql = "INSERT INTO public.articulos(id,nombre, id_categoria,id_tipo,descripcion,id_bodega,cantidad,costo,iva,ice)\n"
                    + "	VALUES (" + articulo.getId() + "'," + articulo.getNombre() + "', " + categoriaid + ", " + tipo + ",'" + articulo.getDescripcion()
                    + "'," + bodegaid + "," + articulo.getCantidad() + "," + articulo.getCosto() + ",12,0)";
            //enviamos la sentencia
            conexion.ejecutarSql(sentenciaSql);
            this.conexion.desconectar();

            System.out.println("Entrada Guardada exitosamente");

        } catch (Exception e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            conexion.desconectar();
        }
    }

    
    
    public List<ProductoReport> getArticulosReport() {
        List<ProductoReport> ListaInv = new ArrayList<>();
        String sql = String.format("select entradas.cod, ar.nombre, cantidad, ar.costo, (cantidad * ar.costo) as total from(\n"
                + "select * from entrada_detalle ed inner join entrada e on ed.id_entrada = e.cod) as entradas\n"
                + "inner join proveedor p on p.idproveedor = entradas.id_proveedor inner join articulos ar\n"
                + "on entradas.cod_articulo = ar.id");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ProductoReport articulo = new ProductoReport();
                articulo.setCodigo(resultSet.getString("cod"));
                articulo.setProducto(resultSet.getString("nombre"));
                articulo.setCosto(resultSet.getInt("cantidad"));
                articulo.setCantidad(resultSet.getInt("costo"));
                articulo.setTotal(resultSet.getInt("total"));

                ListaInv.add(articulo);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return ListaInv;
    }

    public void insertArticulosconIva(ArticulosInventario a, boolean iva) {
        try {

            this.conexion.conectar();
            String sentencia = "insert into articulos(nombre, id_categoria,id_tipo,descripcion,id_bodega,cantidad, costo,iva,\n"
                    + "ice,max_stock,id_subcuenta,unidadmedida,precio_venta)\n"
                    + "values\n"
                    + "('" + a.getNombre() + "'," + a.getCat_cod() + "," + a.getId_tipo() + " ,'" + a.getDescripcion() + "'," + a.getId_bodega() + ""
                    + "," + a.getCantidad() + ",'" + a.getCoast()+ "',12,'" + a.getIce() + "',500," + a.getIdSubCuenta() + ",'" + a.getUnidadMedida() + "','" + a.getCosto() + "')";
           conexion.ejecutarSql(sentencia);

        } catch (Exception e) {
        } finally {
            this.conexion.desconectar();
        }

    }
    public void insertArticulosSinIva(ArticulosInventario a) {
        try {

            this.conexion.conectar();
            String sentencia = "insert into articulos(nombre, id_categoria,id_tipo,descripcion,id_bodega,cantidad, costo,iva,\n"
                    + "ice,max_stock,id_subcuenta,unidadmedida,precio_venta)\n"
                    + "values\n"
                    + "('" + a.getNombre() + "'," + a.getCat_cod() + "," + a.getId_tipo() + " ,'" + a.getDescripcion() + "'," + a.getId_bodega() + ""
                    + "," + a.getCantidad() + ",'" + a.getCoast()+ "',0,'" + a.getIce() + "',500," + a.getIdSubCuenta() + ",'" + a.getUnidadMedida() + "','" + a.getCosto() + "')";
           conexion.ejecutarSql(sentencia);

        } catch (Exception e) {
        } finally {
            this.conexion.desconectar();
        }

    }

}
