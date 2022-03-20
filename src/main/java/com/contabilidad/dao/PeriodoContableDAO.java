/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.dao;

import com.contabilidad.models.Periodo;
import com.global.config.Conexion;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author User
 */
public class PeriodoContableDAO {

    private ejercicioContableDAO ejercicioContableDAO;

    public void insertarPeriodo(List<Periodo> pr) {
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Conexion cox = new Conexion();
        try {
            cox.conectar();
            for (int i = 0; i < pr.size(); i++) {
                String cadena = "INSERT INTO public.periodo(\n"
                        + "	 nombreperiodo, fechainicio, fechafin, estado, foran_periodo_fiscal)\n"
                        + "	VALUES ( '" + pr.get(i).getNombrePeriodo() + "',"
                        + "'" + pr.get(i).getFechaInicio() + "', "
                        + "'" + pr.get(i).getFechaFin() + "', "
                        + "'" + pr.get(i).isEstado() + "', "
                        + "'5');";
                cox.Ejecutar2(cadena);
                System.out.println(cadena);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            cox.desconectar();
        }

    }

}
