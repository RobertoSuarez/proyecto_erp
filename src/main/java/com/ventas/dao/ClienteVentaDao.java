/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.ClienteVenta;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClienteVentaDao implements Serializable {

    Conexion con;
    ClienteVenta clienteVenta;

    public ClienteVentaDao() {
        this.con = new Conexion();
    }

    public ClienteVenta BuscarClientePorId(int id) throws SQLException {
        ResultSet rs = null;
        ClienteVenta temp = new ClienteVenta();
        String query = "select cl.idcliente, T.* from "
                + "(select pn.idpersonanatural as IdNatural, null as IdJuridico, "
                + "pr.id_persona, pn.nombre1||' '||pn.nombre2||' '||pn.apellido1||' '||pn.apellido2 as Nombre, pr.identificacion "
                + "from public.persona_natural pn inner join public.persona pr on pn.id_persona = pr.id_persona UNION "
                + "select null as IdNatural, pj.id_persona_juridica as IdJuridico, pr.id_persona, pj.razon_social as Nombre, pr.identificacion "
                + "from public.persona_juridica pj inner join public.persona pr on pj.id_persona = pr.id_persona) as T "
                + "inner join public.clientes cl on (cl.idpersonanatural = T.idnatural or cl.id_persona_juridica = T.idjuridico) where cl.idcliente = " + id + ";";
        try {
            con.abrirConexion();
            rs = this.con.consultar(query);
            con.conex.close();
            while (rs.next()) {
                temp.setIdCliente(rs.getInt(1));
                temp.setNombre(rs.getString(5));
                temp.setIdentificacion(rs.getString(6));
                con.conex.close();
            }
            con.cerrarConexion();
            return temp;
        } catch (Exception e) {
            if (con.isEstado()) {
                con.cerrarConexion();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            con.conex.close();
        }
        return temp;
    }
    
    public List<ClienteVenta> ListarClientes(){
        List<ClienteVenta> clientes = new ArrayList<>();
        try{
            this.con.abrirConexion();
            String query = "select cl.idcliente, T.* from "
                + "(select pn.idpersonanatural as IdNatural, null as IdJuridico, "
                + "pr.id_persona, pn.nombre1||' '||pn.nombre2||' '||pn.apellido1||' '||pn.apellido2 as Nombre, pr.identificacion "
                + "from public.persona_natural pn inner join public.persona pr on pn.id_persona = pr.id_persona UNION "
                + "select null as IdNatural, pj.id_persona_juridica as IdJuridico, pr.id_persona, pj.razon_social as Nombre, pr.identificacion "
                + "from public.persona_juridica pj inner join public.persona pr on pj.id_persona = pr.id_persona) as T "
                + "inner join public.clientes cl on (cl.idpersonanatural = T.idnatural or cl.id_persona_juridica = T.idjuridico);";
            ResultSet rs = this.con.consultar(query);
            con.conex.close();
            while(rs.next()){
                this.clienteVenta = new ClienteVenta();
                this.clienteVenta.setIdCliente(rs.getInt(1));
                this.clienteVenta.setNombre(rs.getString(5));
                this.clienteVenta.setIdentificacion(rs.getString(6));
                clientes.add(this.clienteVenta);
            }
            this.con.cerrarConexion();
        }catch(Exception e){
            if (con.isEstado()) {
                con.cerrarConexion();
            }
            System.out.println(e.getMessage().toString());
        }finally{
            this.con.cerrarConexion();
        }
        return clientes;
    } 

    public ClienteVenta BuscarCliente(String id) {
        ResultSet rs = null;
        ClienteVenta temp = new ClienteVenta();

        System.out.println("Consultando " + id);

        try {
            con.abrirConexion();
            if (id.length() <= 10) {
                rs = con.ejecutarConsulta("select * from public.buscarclientenatural('" + id.trim() + "')");
            } else if (id.length() > 10) {
                rs = con.ejecutarConsulta("select * from public.buscarclientejuridico('" + id.trim() + "')");
            } else {
                rs = null;
            }

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    temp.setIdCliente(rs.getInt(1));
                    temp.setIdentificacion(rs.getString(2));
                    temp.setNombre(rs.getString(3));
                    temp.setIdTipoCliente(rs.getInt(4));
                    temp.setTipoCliente(rs.getString(5));
                    temp.setDireccion(rs.getString(6));
                    temp.setContacto(rs.getString(7));
                }
            }
            con.cerrarConexion();

            return temp;
        } catch (Exception e) {
            System.out.println(e.toString());
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        } finally {
            con.cerrarConexion();
        }
        return null;
    }
}
