/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.HistoricoPrecios;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    public int GuardarHistorico(HistoricoPrecios historico) {
        try {
            ResultSet rs = null;

            this.conexion.conectar();
            rs = this.conexion.ejecutarSql("select id from public.historico_precios order by id desc limit 1;");
            int codigo = 1;

            //Asignar los valores al nuevo historico.
            while (rs.next()) {
                codigo = rs.getInt(1) + 1;
            }
            historico.setId(codigo);
            System.out.println("Historico: " + historico.getId());
            String fechafin = "'12-31-" + LocalDateTime.now().getYear()+"'";
            //Insertar en la base de datos
            String query = "INSERT INTO public.historico_precios("
                    + "fecha_inicio, fecha_fin, id_articulo, costo, precioventa)"
                    + "VALUES( '" + historico.getFechaInicio()+ "', " + fechafin  + ", " + historico.getId_articulo() + ", " + historico.getCosto()+ ", " + historico.getPrecioVenta()+ ")";
            System.out.println(query);
            this.conexion.ejecutarSql(query);

            this.conexion.desconectar();

            System.out.println("Se ha registrado el historico");

            return historico.getId();
        } catch (SQLException e) {
            if (conexion.isEstado()) {
                conexion.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.conexion.desconectar();
        }
        return 0;
    }
    

}
