/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.global.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author angul
 */
public class Extensiones {

    public static String ConvertFecha(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("d/MM/yyyy"));
    }

    public static String ConvertFecha(Date fecha) throws Exception {
        DateFormat df = new SimpleDateFormat("d/MM/yyyy");
        return df.format(fecha);
        
    }
}
