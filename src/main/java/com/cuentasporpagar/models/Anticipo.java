package com.cuentasporpagar.models;

import com.global.config.Conexion;
import com.google.gson.Gson;
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
 * Modelo para trabajar con los anticipos
 */
public class Anticipo {
    

    
    private String id_anticipo;
    private Double importe;
    private Date fecha;
    private String descripcion;
    private int id_proveedor;
    private Proveedor proveedor;
    private String referencia;
    private boolean habilitado;
    private int id_asiento;
    private boolean revertido;
    private int id_asiento_revertido;

    public Anticipo() {
        this.id_anticipo = "";
        this.importe = 0.0;
        this.fecha = new Date();
        this.descripcion = "";
        this.id_proveedor = 0;
        this.proveedor = new Proveedor();
        this.referencia = "#";
        this.habilitado = true;
    }

    public String getId_anticipo() {
        return id_anticipo;
    }

    public void setId_anticipo(String id_anticipo) {
        this.id_anticipo = id_anticipo;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getId_asiento() {
        return id_asiento;
    }

    public void setId_asiento(int id_asiento) {
        this.id_asiento = id_asiento;
    }

    public boolean isRevertido() {
        return revertido;
    }

    public void setRevertido(boolean revertido) {
        this.revertido = revertido;
    }

    public int getId_asiento_revertido() {
        return id_asiento_revertido;
    }

    public void setId_asiento_revertido(int id_asiento_revertido) {
        this.id_asiento_revertido = id_asiento_revertido;
    }

    
    // Metodo aux para comunicación con db
    public Anticipo GetDBProveedor() {
        if (this.id_proveedor == 0) {
            return null;
        }
        this.proveedor = Proveedor.getOneProveedor(this.id_proveedor);
        return this;
    }

    
    // el metodo InsertDB, inserta el objeto anticipo a la base de datos
    // mediante la funcion en postgres "insert_anticipo()" la cual se le pasa
    // los parametros.
    public void InsertDB() {
        System.out.println("Insertar objecto a la db");
        System.out.println(this.id_anticipo);
        System.out.println(this.fecha);
        System.out.println(this.importe);
        System.out.println(this.id_proveedor);
        
        Asiento asiento = new Asiento();
        asiento.idDiario = "11";
        asiento.total = this.importe.toString();
        asiento.documento = "ANT-" + this.referencia;
        asiento.detalle = this.descripcion;
        asiento.fechaCreacion = new SimpleDateFormat("dd-MM-yyyy").format(this.fecha);
        asiento.fechaCierre = new SimpleDateFormat("dd-MM-yyyy").format(this.fecha);
        
        List<Movimiento> movimientos = new ArrayList<>();
        
        Movimiento movimiento1 = new Movimiento();
        movimiento1.idSubcuenta = "2"; // Subcuenta caja chicha
        movimiento1.debe = this.importe.toString();
        movimiento1.haber = "0";
        movimiento1.tipoMovimiento = "Anticipo de proveedor";
        
        Movimiento movimiento2 = new Movimiento();
        movimiento2.idSubcuenta = "65"; // Subcuenta Anticipo proveedor
        movimiento2.debe = "0";
        movimiento2.haber = this.importe.toString();
        movimiento2.tipoMovimiento = "Anticipo de proveedor";
        
        movimientos.add(movimiento1);
        movimientos.add(movimiento2);
        
        Gson gson = new Gson();
       
       
        
        
        Conexion conn = new Conexion();
        
        try {
            String queryAsiento = String.format("SELECT public.generateasientocotableexternal2('%s', '%s') as id_asiento", gson.toJson(asiento), gson.toJson(movimientos));
            System.out.println(queryAsiento);
            conn.conectar();
            
            Statement statement = conn.conex.createStatement();
            ResultSet data = statement.executeQuery(queryAsiento);
            while (data.next()) {
                this.id_asiento = data.getInt("id_asiento");                
            }
            
            System.out.println("id asiento recuperado de la db " + this.id_asiento);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("No se puedo registrar el asiento del anticipo, se cacelo todo");
            try {
                conn.conex.close();
            } catch (SQLException ex1) {
                Logger.getLogger(Anticipo.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return;
        } 
        
        
        
        String query =  "select insert_anticipo(?, ?, ?, ?, ?, ?, ?);";
        try {
            
            PreparedStatement stmt = conn.conex.prepareStatement(query);
            stmt.setInt(1, this.id_proveedor);
            stmt.setDouble(2, this.importe);
            stmt.setObject(3, new java.sql.Date(this.fecha.getTime()));
            stmt.setString(4, this.descripcion);
            stmt.setString(5, this.referencia);
            stmt.setBoolean(6, true);
            stmt.setInt(7, this.id_asiento);
            
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
    
    // El metodo UpdateDB actualiza el objeto java, en la base de datos
    // mediante el id_anticipo.
    // Consulta
    //update anticipo set "importe"=33, "fechaRegistro"='10/05/2021', descripcion='sdffsdf', "idProveedor"=1
    //where "idAnticipo"=17;
    public void UpdateDB() {
        System.out.println("Update objecto a la db");
        System.out.println(this.id_anticipo);
        System.out.println(this.fecha);
        System.out.println(this.importe);
        System.out.println(this.id_proveedor);
        
        Conexion conn = new Conexion();
        String query =  "update anticipo set \"importe\"=?, \"fecha\"=?, descripcion=?, \"id_proveedor\"=?, habilitado=?, referencia=?\n" +
                        "    where \"id_anticipo\"=?;";
        try {
            conn.conectar();
            
            PreparedStatement stmt = conn.conex.prepareStatement(query);
            stmt.setDouble(1, this.importe);
            //stmt.setDate(2, (java.sql.Date) this.fecha);
            stmt.setObject(2, new java.sql.Date(this.fecha.getTime()));
            stmt.setString(3, this.descripcion);
            stmt.setInt(4, this.id_proveedor);
            stmt.setBoolean(5, this.habilitado);
            stmt.setString(6, this.referencia);
            stmt.setString(7, this.id_anticipo);
            
            stmt.execute();
            //ResultSet rs = stmt.executeQuery(query);
          
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                conn.conex.close();
            } catch (SQLException ex) {
                Logger.getLogger(Anticipo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deleteDB() {
        System.out.println("Delete objecto a la db");
        System.out.println(this.id_anticipo);
        System.out.println(this.fecha.toString());
        System.out.println(this.importe);
        System.out.println(this.id_proveedor);
        
        Conexion conn = new Conexion();
        String query =  "delete from anticipo where id_anticipo=?;";
        try {
            conn.conectar();
            
            PreparedStatement stmt = conn.conex.prepareStatement(query);
            stmt.setString(1, this.getId_anticipo());
            
            stmt.execute();
            
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                conn.conex.close();
            } catch (SQLException ex) {
                Logger.getLogger(Anticipo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public class Asiento {
        private String idDiario;
        private String total;
        private String documento;
        private String detalle;
        private String fechaCreacion;
        private String fechaCierre;

        public Asiento() {
        }

        public String getIdDiario() {
            return idDiario;
        }

        public void setIdDiario(String idDiario) {
            this.idDiario = idDiario;
        }

      

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public String getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(String fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }

        public String getFechaCierre() {
            return fechaCierre;
        }

        public void setFechaCierre(String fechaCierre) {
            this.fechaCierre = fechaCierre;
        }
    }
    
    public class Movimiento {
        private String idSubcuenta;
        private String debe;
        private String haber;
        private String tipoMovimiento;

        public Movimiento() {
        }

        public String getIdSubcuenta() {
            return idSubcuenta;
        }

        public void setIdSubcuenta(String idSubcuenta) {
            this.idSubcuenta = idSubcuenta;
        }

        public String getDebe() {
            return debe;
        }

        public void setDebe(String debe) {
            this.debe = debe;
        }

        public String getHaber() {
            return haber;
        }

        public void setHaber(String haber) {
            this.haber = haber;
        }

        public String getTipoMovimiento() {
            return tipoMovimiento;
        }

        public void setTipoMovimiento(String tipoMovimiento) {
            this.tipoMovimiento = tipoMovimiento;
        }
        
    }
    
}


