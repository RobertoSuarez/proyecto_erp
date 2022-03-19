/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Cheque;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChequeDAO implements Serializable{
    List<Cheque> lista_cheques;
    Conexion conex;
    Cheque cheque;
    ResultSet result;
    
    //Constructor sin parámetros, para iniciar una conexion.

    public ChequeDAO() {
        conex = new Conexion();
    }
   
    //Constructor que recibe el objeto Cheque e inicia una nueva conexion.
    public ChequeDAO(Cheque cheque) {
        conex = new Conexion();
        this.cheque = cheque;
    }

    public ChequeDAO(List<Cheque> lista_cheques, Conexion conex, Cheque cheque, ResultSet result) {
        this.lista_cheques = lista_cheques;
        this.conex = conex;
        this.cheque = cheque;
        this.result = result;
    }
   
    /*Procedimiento para insertar un nuevo cheque. Caso falle retornará -1*/
    public int insertarNuevoCheque(int idVenta) {
        /*Se ubica en el siguiente orden: 
        (codigo del banco, id de la factura/venta, la fecha de cobro, 
            numero de cuenta, y el estado del cheque (Cobrado, Pendiente,
            protestado, Rechazado y revocado))*/
        String sentenciaSQL = "Select Ingresar_Cheque("+cheque.getCodBanco()+","
                +idVenta+","+cheque.getFecha()+","+cheque.getNumeroDeCuenta()+","+cheque.getEstadoCheque()+")";

        //Verificamos la conexion
        if (conex.isEstado()) {

            /*Una vez se asegura que la conexion este correcta.
            Se ejecuta la sentencia ingresada.*/
            return conex.ejecutarProcedimiento(sentenciaSQL);

        }

        return -1;
    }
    
    //Modificar/Actualizar un Cheque, retorn 1 o -1 dependiendo si la función 
    //ejecuta correctamente
    public int actualizarCheque(int idCheque){
        String sentenciaSQL="select actualizar_cheque("+idCheque+","
                +cheque.getCodBanco()+","+cheque.getIdVenta()+","
                +cheque.getFecha()+","+cheque.getNumeroDeCuenta()+","
                +cheque.getEstadoCheque()+")";
        
        //Verificamos la conexion
        if (conex.isEstado()) {
            
            //Una vez se asegura que la conexion este correcta.
            //Se ejecuta la sentencia ingresada.
            return conex.ejecutarProcedimiento(sentenciaSQL);
            
        }
        //Caso contrario: Se retorna -1 indicando que la conexión está
        //en estado Falso
        return -1;
        
    }   
    
    //Funcion para enlistar todos los cheques un cliente.
    public List<Cheque> obtenerChequesDeCliente(int idCliente){
        lista_cheques=new ArrayList<>();
        
        //verificamos la conexion
         if(conex.isEstado()){
             try{
                 /* Se obtiene una TABLA con todos los cheques que se receptaron
                 de un cliente a una determinada factura.*/
               String sentencia="Select*from obtener_cheques_de_clientes("+idCliente+")";
               result = conex.ejecutarSql(sentencia);
               
                //Recorremos la TABLA retornada y la almacenamos en la lista.
               while(result.next()){
                   
                   lista_cheques.add(new Cheque(result.getInt("idcheque_r"),
                           result.getInt("idventa_r"),
                           result.getString("nombrecliente_r"),
                           result.getString("codbanco_r"),
                           result.getString("nombrebanco_r"),
                           result.getString("numerodecuenta_r"),
                           result.getString("estadocheque_r"),
                           result.getObject("estadocheque_r", LocalDate.class )));
                   
               }
               
             } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_cheques.add(new Cheque(-1,
                           -1,
                           "",
                           "",
                           "",
                           "",
                           "",
                           null));

                
            } finally {
                
                conex.desconectar();

            }
         }
        
        return lista_cheques;
    }
    
    public List<Cheque> ObtenerTodosLosCheques(){
        List<Cheque> lstCheq = new ArrayList<>();
        String sentenciaSql ="SELECT * FROM public.cheques ch Inner join public.bancos b on b.codbanco=ch.codbanco ";
        ResultSet rs;
        Cheque cheq;
        try{
            conex.conectar();
            rs=conex.ejecutarSql(sentenciaSql);
            while(rs.next()){
                cheq= new Cheque();
                cheq.setIdCheque(rs.getInt(1));
                cheq.setCodBanco(rs.getString(2));
                cheq.setIdVenta(3);
                cheq.setFecha(rs.getDate(4).toLocalDate());
                cheq.setNumeroDeCuenta(rs.getString(5));
                cheq.setEstadoCheque(rs.getString(6));
                cheq.setValor_Cheque(rs.getDouble(7));
                lstCheq.add(cheq);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return lstCheq;
    }
    
    public int ObtenerIdCliente(int idVenta){
        int idcliente=0;
        String sentenciaSql ="SELECT v.idcliente FROM public.cheques ch inner join public.venta v on ch.idventa=v.idventa where v.idventa="+ String.valueOf(idVenta)+" group by v.idcliente";
        ResultSet rs;
        try{
            conex.conectar();
            rs=conex.ejecutarSql(sentenciaSql);
            while(rs.next()){
                idcliente = rs.getInt(1);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return idcliente;
    }
    
    public void ModificarCheque(Cheque cheq){
        String Sql="UPDATE public.cheques SET estadocheque='"+String.valueOf(cheq.getEstadoCheque())+"'WHERE idcheques="+String.valueOf(cheq.getIdCheque())+";";
        try{
            conex.conectar();
            conex.ejecutarSql(Sql);
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            conex.desconectar();
        }
    }
}

