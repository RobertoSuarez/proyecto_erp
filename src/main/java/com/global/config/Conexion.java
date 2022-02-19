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

    public Connection connection;
    private Statement statement;
    private ResultSet result;
    private boolean estado;
    private String mensaje;
    private boolean transaccionIniciada;
    private FacesMessage.Severity tipoMensaje;

    //Nuevos parametros estandarizados

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
        return connection;
    }

    public void setCnx(Connection conex) {
        this.connection = conex;

    }

    public boolean isTransaccionIniciada() {
        return transaccionIniciada;
    }

    public void setTransaccionIniciada(boolean transaccionIniciada) {
        this.transaccionIniciada = transaccionIniciada;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Connection getConexion() {
        return connection;
    }

    public ResultSet getResult() {
        return result;
    }

    public FacesMessage.Severity getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(FacesMessage.Severity tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    //  MÉTODO CONECTAR PARA INICIAR UNA CONEXIÓN A LA BASE DE DATOS
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


   //  MÉTODO DESCONECTAR PARA CERRAR UNA CONEXIÓN A LA BASE DE DATOS
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

    //  EJECUTAR CONSULTAS SQL
    public ResultSet ejecutarSql(String sql) {
        try {
            conectar();
            result = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error: No se ejecuto la consulta: " + ex.getMessage());
        } 
        return result;
    }

    // MÉTODO PARA CUENTAS POR COBRAR
    public int ejecutarProcedimiento(String sql) {
        int retorno = -1;
        try {
            if (conectar()) {
                statement.executeQuery(sql);
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
            desconectar();
        }
        return retorno;
    }

    // MÉTODO PARA CONTABILIDAD
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

    /* MÉTODO PARA PRODUCCIÓN
    public int insertar(String sql) {
        int retorno = -1;
        try {
            if (conectar()) {
                retorno = statement.executeUpdate(sql);
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
        }
        desconectar();
        return retorno;
    }*/

    //  OTRO MÉTODO PARA PRODUCCIÓN
    public int insertar(String sql) {
        int retorno = -1;
        try {
            if (conectar()) {
                retorno = statement.executeUpdate(sql);
                mensaje = "Se guardó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
        } finally {
            desconectar();
        }
        return retorno;
    }

    //Para modulo activos fijos
    public String obtenerValor(String consulta, int indx) {
        String valor = "";
        try {
            if (conectar()) {
                statement = connection.createStatement();
                result = statement.executeQuery(consulta);
                if (result.next()) {
                    valor = result.getString(indx);
                }
                desconectar();
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(consulta);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);

        } finally {
            desconectar();
        }
        return valor;
    }

    //Diana -- Lo uso
    public void Ejecutar2(String sql) {
        try {
            if (conectar()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException exc) {
            System.out.print(exc);
        } finally {
            desconectar();
        }
    }

    //  RRHH
    public boolean iniciarTransaccion() throws SQLException, ClassNotFoundException {
        if (connection == null || !(connection.isClosed())) {
            Class.forName(classForName);
            connection = DriverManager.getConnection(url, usuario, clave);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            estado = true;
            transaccionIniciada = true;
            return estado;
        } else {
            return false;
        }
    }

    public void ejecutarInsertarToTrnasaccion(String tabla, String campos, String valores) throws SQLException {
        if (transaccionIniciada) {
            statement.executeUpdate("INSERT INTO public." + tabla + "(" + campos + ")" + "VALUES(" + valores + ")");
        } else {
            throw new SQLException("Debe ejecutar primero la funcion iniciarTransaccion()");
        }
    }

    public void finalizarTransaccion(boolean conmit) throws SQLException {
        if (conmit) {
            connection.commit();
        } else {
            connection.rollback();
        }
        desconectar();
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
            if (conectar()) {
                result = statement.executeQuery(sql);
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
            desconectar();
        }
        return result;
    }

    //  Insertar con tres parámetros
    public int insertar(String tabla, String campos, String valores) {
        int retorno = -1;
        String sql = "INSERT INTO public." + tabla + " (" + campos + ")" + " VALUES(" + valores + ")";
        try {
            if (conectar()) {
                retorno = statement.executeUpdate(sql);
                mensaje = "Se insertó correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            desconectar();
        }
        return retorno;
    }

    //  Insertar con cuatro parámetros
    public int insertar(String tabla, String campos, String valores, String id) {
        int retorno = -1;
        String sql = "INSERT INTO public." + tabla + " (" + campos + ")" + " VALUES(" + valores + ");";
        try {
            if (conectar()) {
                retorno = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                result = statement.executeQuery("SELECT MAX(" + id + ") AS ID FROM public." + tabla + ";");
                if (result.next()) {
                    retorno = result.getInt("ID");
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
            desconectar();
        }
        return retorno;
    }

    public int ejecutarProcedure(String procedure, String parametros) {
        int retorno = -1;
        String sql = "SELECT public." + procedure + "(" + parametros + ");";
        try {
            if (conectar()) {
                statement.execute(sql);
                desconectar();
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            desconectar();
        }
        return retorno;
    }

    public int modificar(String tabla, String camposModificados, String restrinciones) {
        int retorno = -1;
        String sql = "UPDATE " + tabla + " SET " + camposModificados + " WHERE " + restrinciones;
        try {
            if (conectar()) {
                retorno = statement.executeUpdate(sql);
                desconectar();
                mensaje = "Se modifico correctamente : ";
                tipoMensaje = FacesMessage.SEVERITY_INFO;
            }
        } catch (SQLException exc) {
            System.out.println(sql);
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        } finally {
            desconectar();
        }
        return retorno;
    }


    /**
    public boolean conectar() throws SQLException {
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

    public void desconectar() {
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

    public ResultSet ejecutarConsulta(String sql) {
        try {
            if (conectar()) {
                lector = st.executeQuery(sql);
            }
        } catch (SQLException exc) {
            mensaje = exc.getMessage();
            tipoMensaje = FacesMessage.SEVERITY_FATAL;
            System.out.println(mensaje);
        }
        return lector;
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
    **/
}
