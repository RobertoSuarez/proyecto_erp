package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.Retencion;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elect
 */
public class RetencionDAO {

    /*
        Query de la consulta
        select r."idRetencion", r."fechaEmision", r."nComprobanteRetencion", r."idFacturaCompra", r."idTipoComprobante"
            from retencion as r;
     */
    // Metodos que se comunica con la db.
    public static List<Retencion> getAll() {
        List<Retencion> retenciones = new ArrayList<>();
        Conexion conn = new Conexion();
        String query = "select r.\"idRetencion\", r.\"fechaEmision\", r.\"nComprobanteRetencion\", r.\"idFacturaCompra\", r.\"idTipoComprobante\""
                + "from retencion as r;";
        try {
            conn.conectar();
            Statement stmt = conn.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Retencion retencion = new Retencion();
                // setiamos los valores
                retencion.setId_retencion(rs.getInt("idRetencion"));
                retencion.setFecha_emision(rs.getObject("fechaEmision", LocalDate.class));
                retencion.setN_comprobante_retencion(rs.getString("nComprobanteRetencion"));
                retencion.setId_factura_compra(rs.getInt("idFacturaCompra"));
                retencion.setId_tipo_comprobante(rs.getInt("idTipoComprobante"));
                retencion.setEstado_retencion("Emitido");

                retenciones.add(retencion);
            }
            conn.connection.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        // Obtenemos las facturas compras de cada retencion.
        retenciones.forEach(ren -> {
            ren.getFacturaCompra();
        });

        return retenciones;

    }
}
