package com.global.config;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 *
 * Coexion a la db por line command $ psql -U appweb -h 190.15.134.7 -p 8080
 * erpcontableappweb password: @Aplicaciones@Web@2021
 */
public class Conexion implements Serializable {

    public Connection conex;
    private java.sql.Statement st;
    private ResultSet lector;
    private boolean estado;
    private String mensaje;
    private boolean transaccionIniciada;
    private FacesMessage.Severity tipoMensaje;

    //Nuevos parametros estandarizados
    private Connection connection;
    private Statement statement;
    private ResultSet result;

    //Credenciales para la conexion
    private String url = "jdbc:postgresql://190.15.134.7:8080/erpcontableappweb";
    private String usuario = "appweb";
    private String clave = "@Aplicaciones@Web@2021";
    private String classForName = "org.postgresql.Driver";

    public Conexion() {
        estado = true;
    }

    public Conexion(String user, String pass, String url) {
        usuario = user;
        clave = pass;
        this.url = url;
        estado = true;
    }

    public boolean abrirConexion() throws SQLException {
        try {
            if (conex == null || !(conex.isClosed())) {
                //System.out.println(mensaje+ " si abre la conexion");
                Class.forName(classForName);
                conex = DriverManager.getConnection(url, usuario, clave);
                st = conex.createStatement();
                estado = true;
            }
        } catch (ClassNotFoundException | SQLException exSQL) {
            mensaje = exSQL.getMessage();
            System.out.println(mensaje + " no abre la conexion");
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            return false;
        }
        return true;
    }

    //Conexion estandar, pendiente de revision para implementar en todos los modulos
    public boolean conectar() {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, usuario, clave);
                statement = connection.createStatement();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("No hay conexion a la base de datos: " + e.getMessage());
        }
        return false;
    }

    public void cerrarConexion() {
        try {
            if (conex != null && !conex.isClosed()) {
                conex.close();
                conex = null;
            }
            if (st != null && !st.isClosed()) {
                st.close();
                st = null;
            }
            if (lector != null && !lector.isClosed()) {
                lector.close();
                lector = null;
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println("ERROR: " + mensaje);
        }
    }

    public boolean desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                statement.close();
                return true;
            } else {
                System.out.println("No hay una conexion para desconectar");
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al desconectar la conexion");
        }
        return false;
    }

    public ResultSet ejecutarConsulta(String sql) {
        try {
            if (abrirConexion()) {
                lector = st.executeQuery(sql);
            }
        } catch (SQLException exc) {
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        }
        return lector;
    }

    public int ejecutarProcedimiento(String sql) {
        int retorno = -1;
        try {
            if (abrirConexion()) {
                st.executeQuery(sql);
                mensaje = "El procedimiento se ejecutó correctamente";
                retorno = 1;
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            cerrarConexion();
        }
        return retorno;
    }

    public int ejecutar(String sql) {
        int retorno = -1;
        try {
            if (abrirConexion()) {
                retorno = st.executeUpdate(sql);
                mensaje = "Se guardó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            cerrarConexion();
        }
        return retorno;
    }

    public int eliminar(String sql) {
        int result = -1;
        try {
            conectar();
            result = statement.executeUpdate(sql);
            System.out.println("Se ha eliminado el registro");
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar el registro " + e.getMessage());
        } finally {
            desconectar();
        }
        return result;
    }

    public ResultSet ejecutarSql(String sql) {
        try {
            conectar();
            result = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error: No se ejecuto la consulta: " + ex.getMessage());
        }finally{
            cerrarConexion();
        }
        return result;
    }

    public ResultSet consultar(String sql) {
        try {
            conectar();
            result = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error: No se ejecuto la consulta: " + ex.getMessage());
        }
        return result;
    }

    public int insertar(String sql) {
        int retorno = -1;
        try {
            if (abrirConexion()) {
                System.out.println(retorno = st.executeUpdate(sql));
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
                System.out.println(retorno + "HOLIS");
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje + " AQUI");
        }
        cerrarConexion();
        return retorno;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isEstado() {
        return estado;
    }

    //EBERT-- LO USO
    public Connection getCnx() {
        return conex;
    }

    public void setCnx(Connection conex) {
        this.conex = conex;

    }

    public boolean isTransaccionIniciada() {
        return transaccionIniciada;
    }

    public void setTransaccionIniciada(boolean transaccionIniciada) {
        this.transaccionIniciada = transaccionIniciada;
    }

    public void Conectar() throws SQLException {
        try {
            if (conex == null || !(conex.isClosed())) {
                Class.forName(classForName);
                conex = DriverManager.getConnection(url, usuario, clave);
                st = conex.createStatement();
                estado = true;
            }
        } catch (ClassNotFoundException | SQLException exSQL) {
            mensaje = exSQL.getMessage();
            System.out.println(mensaje);
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
        }

    }
//Diana -- Lo uso

    public void Ejecutar2(String sql) {
        try {
            if (abrirConexion()) {
                st.executeUpdate(sql);
            }
        } catch (SQLException exc) {
            System.out.print(exc);
        } finally {
            cerrarConexion();
        }
    }
    //para modulo activos fijos

    public String obtenerValor(String consulta, int indx) {
        String valor = "";
        try {
            if (abrirConexion()) {
                st = conex.createStatement();
                lector = st.executeQuery(consulta);
                if (lector.next()) {
                    valor = lector.getString(indx);
                }
                cerrarConexion();
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(consulta);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);

        } finally {
            cerrarConexion();
        }
        return valor;
    }

    //  RRHH
    public boolean iniciarTransaccion() throws SQLException, ClassNotFoundException {
        if (conex == null || !(conex.isClosed())) {
            Class.forName(classForName);
            conex = DriverManager.getConnection(url, usuario, clave);
            conex.setAutoCommit(false);
            st = conex.createStatement();
            estado = true;
            transaccionIniciada = true;
            return estado;
        } else {
            return false;
        }
    }

    public void ejecutarInsertarToTrnasaccion(String tabla, String campos, String valores) throws SQLException {
        if (transaccionIniciada) {
            st.executeUpdate("INSERT INTO public." + tabla + "(" + campos + ")" + "VALUES(" + valores + ")");
        } else {
            throw new SQLException("Debe ejecutar primero la funcion iniciarTransaccion()");
        }
    }

    public void finalizarTransaccion(boolean conmit) throws SQLException {
        if (conmit) {
            conex.commit();
        } else {
            conex.rollback();
        }
        cerrarConexion();
    }

    public ResultSet selecionar(String tabla, String campos, @Nullable String restrinciones, @Nullable String ordenar) {
        String sql = "SELECT " + campos + " FROM public." + tabla;
        if (restrinciones != null) {
            sql = sql + " WHERE " + restrinciones;
        }
        if (ordenar != null) {
            sql = sql + " ORDER BY " + ordenar;
        }
        try {
            if (abrirConexion()) {
                lector = st.executeQuery(sql);
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
            cerrarConexion();
        }
        return lector;
    }

    //  Insertar con tres parámetros
    public int insertar(String tabla, String campos, String valores) {
        int retorno = -1;
        String sql = "INSERT INTO public." + tabla + " (" + campos + ")" + " VALUES(" + valores + ")";
        try {
            if (abrirConexion()) {
                retorno = st.executeUpdate(sql);
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            cerrarConexion();
        }
        return retorno;
    }

    //  Insertar con cuatro parámetros
    public int insertar(String tabla, String campos, String valores, String id) {
        int retorno = -1;
        String sql = "INSERT INTO public." + tabla + " (" + campos + ")" + " VALUES(" + valores + ");";
        try {
            if (abrirConexion()) {
                retorno = st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                lector = st.executeQuery("SELECT MAX(" + id + ") AS ID FROM public." + tabla + ";");
                if (lector.next()) {
                    retorno = lector.getInt("ID");
                }
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            cerrarConexion();
        }
        return retorno;
    }

    public int ejecutarProcedure(String procedure, String parametros) {
        int retorno = -1;
        String sql = "SELECT public." + procedure + "(" + parametros + ");";
        try {
            if (abrirConexion()) {
                st.execute(sql);
                cerrarConexion();
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            cerrarConexion();
        }
        return retorno;
    }

    public int modificar(String tabla, String camposModificados, String restrinciones) {
        int retorno = -1;
        String sql = "UPDATE " + tabla + " SET " + camposModificados + " WHERE " + restrinciones;
        try {
            if (abrirConexion()) {
                retorno = st.executeUpdate(sql);
                cerrarConexion();
                mensaje = "Se modifico correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            cerrarConexion();
        }
        return retorno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Connection getConexion() {
        return conex;
    }

    public ResultSet getLector() {
        return lector;
    }

    public FacesMessage.Severity getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(FacesMessage.Severity tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
}
