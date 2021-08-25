/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.models;

import java.time.LocalDate;

/**
 *
 * @author PAOLA
 */
public class AbonoProveedor {

    private int idAbonoProveedor;
    private String referencia;
    private int idAsiento;
    private int idTipoPago;
    private int idTipoBanco;
    private int idProveedor;
    private LocalDate fecha;
    private float Pago;
    private String periodo;
    private String detalletipoPago;
    private String nombreProveedor;
    private String detalletipoBanco;
    private String ruc;
    private float Importe;

    public AbonoProveedor() {
    }

    public AbonoProveedor(String referencia, int idProveedor, LocalDate fecha,
            float Pago, String periodo, String detalletipoPago,
            String nombreProveedor) {
        this.referencia = referencia;
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.Pago = Pago;
        this.periodo = periodo;
        this.detalletipoPago = detalletipoPago;
        this.nombreProveedor = nombreProveedor;
    }

    public AbonoProveedor(int idAbonoProveedor, String referencia,
            int idAsiento, int idTipoPago, int idTipoBanco,
            int idProveedor, LocalDate fecha) {
        this.idAbonoProveedor = idAbonoProveedor;
        this.referencia = referencia;
        this.idAsiento = idAsiento;
        this.idTipoPago = idTipoPago;
        this.idTipoBanco = idTipoBanco;
        this.idProveedor = idProveedor;
        this.fecha = fecha;
    }

    public AbonoProveedor(int idAbonoProveedor, String referencia, LocalDate fecha, String periodo, String detalletipoPago, String nombreProveedor, String detalletipoBanco, String ruc) {
        this.idAbonoProveedor = idAbonoProveedor;
        this.referencia = referencia;
        this.fecha = fecha;
        this.periodo = periodo;
        this.detalletipoPago = detalletipoPago;
        this.nombreProveedor = nombreProveedor;
        this.detalletipoBanco = detalletipoBanco;
        this.ruc = ruc;
    }

    public int getIdAbonoProveedor() {
        return idAbonoProveedor;
    }

    public void setIdAbonoProveedor(int idAbonoProveedor) {
        this.idAbonoProveedor = idAbonoProveedor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(int idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public int getIdTipoBanco() {
        return idTipoBanco;
    }

    public void setIdTipoBanco(int idTipoBanco) {
        this.idTipoBanco = idTipoBanco;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getPago() {
        return Pago;
    }

    public void setPago(float Pago) {
        this.Pago = Pago;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDetalletipoPago() {
        return detalletipoPago;
    }

    public void setDetalletipoPago(String detalletipoPago) {
        this.detalletipoPago = detalletipoPago;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDetalletipoBanco() {
        return detalletipoBanco;
    }

    public void setDetalletipoBanco(String detalletipoBanco) {
        this.detalletipoBanco = detalletipoBanco;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public float getImporte() {
        return Importe;
    }

    public void setImporte(float Importe) {
        this.Importe = Importe;
    }

    public String getSentencia(String descripcionPago, String descripcionBanco, String proveedor, LocalDate fecha) {

        String sentencia = String.format("INSERT INTO abononproveedor( referencia, idasiento, idtipopago, idtipobanco, idproveedor, fecha)\n"
                + "VALUES ('%1$s',%2$d,(select t.idtipopago FROM public.tipopago t where t.descripcion='%3$s'),(select t.idtipobanco FROM tipobanco t where t.descripcion='%4$s'),"
                + "(select idproveedor from proveedor pro where pro.codigo ='%5$s'),'%6$s');",
                getReferencia(), 1, descripcionPago, descripcionBanco, proveedor, fecha);
        System.out.print(sentencia);
        return sentencia;
    }

    public String BuscarSentenciaFactura(String proveedor) {
        String sentencia = String.format("select f.nfactura,f.importe,f.pagado,"
                + "f.fecha,f.vencimiento,(f.importe-f.pagado)as pendiente   from factura f\n"
                + "where f.idproveedor=(Select p.idproveedor from proveedor p where p.ruc='%1$s') \n"
                + "and f.habilitar=1 and f.estado=1 and f.pagado<f.importe;", proveedor);
        System.out.println(sentencia);
        return sentencia;
    }

    public String sentenciaMostrar() {
        String sentencia = "SELECT a.fecha,pag.descripcion,a.referencia,sum(d.pago) as Pago,a.idproveedor,p.nombre,a.periodo\n"
                + "FROM abonoproveedor a INNER JOIN detalleabono d ON ( a.idabonoproveedor = d.idabonoproveedor  ) \n"
                + "INNER JOIN tipopago t ON ( a.idtipopago= t.idtipopago  ) \n"
                + "INNER JOIN proveedor p ON ( a.idproveedor = p.idproveedor)  \n"
                + "INNER JOIN tipopago pag ON ( a.idtipopago = pag.idtipopago) \n"
                + "group by a.periodo,a.fecha,pag.descripcion,a.referencia,a.idproveedor,p.nombre";
        return sentencia;
    }

}
