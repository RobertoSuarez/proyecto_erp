/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.Bodega;
import com.inventario.models.HistoricoPrecios;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sun.jvm.hotspot.utilities.soql.SOQLException;

/**
 *
 * @author angul
 */
public class HistoricoPreciosDAO {
    Conexion conexion;
    private HistoricoPrecios historicoPrecios;
    private ResultSet resultSet;
    private List<HistoricoPrecios> listaHistorico;
    private List auxlista = new ArrayList<>();

    public HistoricoPreciosDAO() {
        historicoPrecios = new HistoricoPrecios();
        conexion = new Conexion();
        listaHistorico = new ArrayList<>();
    }

    public HistoricoPreciosDAO(HistoricoPrecios b) {
        conexion = new Conexion();
        this.historicoPrecios = b;
    }

    public HistoricoPrecios getHistoricoPrecios() {
        return historicoPrecios;
    }

    public void setHistoricoPrecios(HistoricoPrecios historicoPrecios) {
        this.historicoPrecios = historicoPrecios;
    }

    public List<HistoricoPrecios> getHistorico() {
        List <HistoricoPrecios> ListaHistorico = new ArrayList<>();
        
        String sql = String.format("select * from historico_precios");
        try{
            resultSet = conexion.ejecutarSql(sql);
            while(resultSet.next()){
            ListaHistorico.add(new HistoricoPrecios (resultSet.getInt("id"),  
                                    resultSet.getDate("fecha_inicio"),
                                    resultSet.getDate("fecha_fin"),
                                    resultSet.getInt("id_articulo"),
                                    resultSet.getDouble("costo"),
                                    resultSet.getDouble("preciovetna"),
                                    resultSet.getBoolean("estado")));
            }
        
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return ListaHistorico;
    }

}
