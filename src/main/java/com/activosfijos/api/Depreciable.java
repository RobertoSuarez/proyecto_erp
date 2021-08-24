/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.api;

import com.activosfijos.dao.TangibleDAO;
import com.activosfijos.model.ActivoDepreciable;
import com.activosfijos.model.ActivosFijos;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author tonyp
 */
@Path("depreciable")
public class Depreciable {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokemonClass
     */
    public Depreciable() {
    }

    @POST
    @Path("/insertdepreciable")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertdepreciable(String data) {
        String message = "";
        System.out.println("insertdepreciable");
        System.out.println(data);

        JsonParser parser = new JsonParser();
        JsonObject Jso = parser.parse(data).getAsJsonObject();

        String detalle_de_activo = Jso.get("detalle_de_activo").getAsString();
        String valor_adquisicion = Jso.get("valor_adquisicion").getAsString();
        String fecha_adquisicion = Jso.get("fecha_adquisicion").getAsString();
        String idproveedor = Jso.get("idproveedor").getAsString();
        String numero_factura = Jso.get("numero_factura").getAsString();
        String depreciacion_meses = Jso.get("depreciacion_meses").getAsString();
        String porcentaje_depreciacion = Jso.get("porcentaje_depreciacion").getAsString();

        ActivosFijos activosFijos = new ActivosFijos();
        ActivoDepreciable activodepreciable = new ActivoDepreciable();

        activosFijos.setDetalle_de_activo(detalle_de_activo);
        activosFijos.setValor_adquisicion(Integer.parseInt(valor_adquisicion));
        activosFijos.setFecha_adquisicion(LocalDate.parse(fecha_adquisicion));
        activosFijos.setIdproveedor(Integer.parseInt(idproveedor));
        activosFijos.setNumero_factura(numero_factura);
        activodepreciable.setDepreciacion_meses(Integer.parseInt(depreciacion_meses));
        activodepreciable.setPorcentaje_depreciacion(Double.parseDouble(porcentaje_depreciacion));

        TangibleDAO tangibledao = new TangibleDAO();
        try {
            tangibledao.guardar(activosFijos, activodepreciable);
            System.out.println("Registrado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

        //insert
        //message = String.format("{\"name\":\"%s\",\"level\":\"%s\"}", nombre, level);
        message = String.format("{\"status\":\"%s\"}", "Activo ingresado correctamente");

        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @POST
    @Path("/aggpokemonjson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aggpokemonJson(JsonObject data) {
        String message = "";
        System.out.println("aggpokemon");

//        JsonParser parser = new JsonParser();
//        JsonObject Jso = parser.parse(data).getAsJsonObject();
        String nombre = data.get("nombre").getAsString();
        String level = data.get("level").getAsString();

        //insert
        message = String.format("{\"nombre\":\"%s\",\"nivel\":\"%s\"}", nombre, level);

        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
}
