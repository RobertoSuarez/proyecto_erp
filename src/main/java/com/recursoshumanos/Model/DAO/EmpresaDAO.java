/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Dedicacion;
import com.recursoshumanos.Model.Entidad.Empresa;
import com.recursoshumanos.Model.Interfaces.IDAO;
import org.jetbrains.annotations.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Empresa.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class EmpresaDAO implements IDAO<Empresa> {

    /**
     * Declaración de variables
     */
    private final Conexion conexion;
    private final String DEFAUL = "SISTEMA ERPCONTABLE";
    private Empresa empresa;

    public EmpresaDAO() {
        conexion = new Conexion();
        empresa = new Empresa();
    }

    public EmpresaDAO(Empresa empresa) {
        conexion = new Conexion();
        this.empresa = empresa;
    }

    public EmpresaDAO(Conexion conexion) {
        this.conexion = conexion;
        empresa = new Empresa();
    }

    /**
     * Método que usa a la conexion para obtener 
     * los datos de empresa.
     * @param empresa Objeto que administra los datos de la empresa
     * @param conexion Objeto que maneja la conexión hacia la
     * Base de Datos.
     */
    public EmpresaDAO(Empresa empresa, Conexion conexion) {
        this.conexion = conexion;
        this.empresa = empresa;
    }

    /**
     * Método GET de la empresa
     * @return empresa Retorna el dato de la empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Método SET de la empresa
     * @param empresa Envia los datos de la empresa.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Llama a la clase conexión.
     * @return conexion Objeto con los datos para validar la conexión.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de la empresa.
     * @return empresa Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            empresa.setId(conexion.insertar("empresa_matriz",
                    "ruc, tipo, nombre, razon_social, detalle",
                    "'" + empresa.getRuc() + "', '" + empresa.getTipo()
                    + "', '" + empresa.getNombre() + "', '" + 
                    empresa.getRazonSocial() + "', '" + empresa.getDetalle() 
                    + "'", "id_matriz"));
            return empresa.getId();
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de insertar.
     * @param entity Objeto con la información para
     * la validación correspondiente.
     * @return insertar Objeto con la información.
     */
    @Override
    public int insertar(Empresa entity) {
        this.empresa = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar:
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("empresa_matriz",
                    "ruc = '" + empresa.getRuc() + "', tipo = '"
                    + empresa.getTipo() + "', nombre = '" + empresa.getNombre() 
                    + "', razon_social = '" + empresa.getRazonSocial() + 
                    "', detalle = '" + empresa.getDetalle() + "'","id_matriz = " 
                    + empresa.getId());
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de actualizar.
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los
     * elementos en la BD.
     */
    @Override
    public int actualizar(Empresa entity) {
        this.empresa = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda
     */
    @Override
    public Empresa buscarPorId(Object id) {
        List<Empresa> lista = buscar("id_matriz = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<Empresa> Listar() {
        return buscar(null, "nombre ASC");
    }
    
    /**
     * Identifica que no existan parametros o datos nulos,
     * luego valida los datos.
     * @return lista Retorna los datos de la dedicaciòn de la
     * empresa.
     */
    public Empresa cargar() {
        List<Empresa> lista = buscar(null, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }else{
            return new Empresa(-1, new Dedicacion(), "9999999999999", 
            "Comercial", DEFAUL, DEFAUL, DEFAUL);
        }
    }

    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<Empresa> Muestra la lista.
     */
    private List<Empresa> buscar(@Nullable String restricciones, 
    @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<Empresa> empresas;
            try {
                result = conexion.selecionar("empresa_matriz", "id_matriz, ruc, "
                + "tipo, nombre, razon_social, detalle", restricciones, 
                OrdenarAgrupar);
                empresas = new ArrayList<>();
                DedicacionDAO ddao = new DedicacionDAO();
                while (result.next()) {
                    empresas.add(new Empresa(result.getInt("id_matriz"),
                            new Dedicacion(),
                            result.getString("ruc"),
                            result.getString("tipo"),
                            result.getString("nombre"),
                            result.getString("razon_social"),
                            result.getString("detalle")
                    ));
                }
                result.close();
                return empresas;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }
}