/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.global.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 *
 * @author angul
 */
public class JsonManager {

    public static String serialize(Object obj) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
         JsonParser jp = new JsonParser();
        JsonElement je = jp.parse((gson.toJson(obj)));
        return gson.toJson(je);

    }

    public static <T> T deserialize(String json, Class<T> T) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.fromJson(json, T);
    }
    public static String serializeWithEscaping(Object obj) {
        Gson gson = new GsonBuilder().create();
        return (gson.toJson(obj));

    }

    public static <T> T deserializeWithEscaping(String json, Class<T> T) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, T);
    }
}
