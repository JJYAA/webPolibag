package pe.com.gp.entity;

import java.io.Serializable;

public class Tmp_Terceros implements Serializable {

    private static final long serialVersionUID = 1L;
    private long coneccion = 0;
    private int item = 0;
    private String cod_mobra = "";
    private String descripcion1 = "";
    private String descripcion2 = "";
    private String descripcion3 = "";
    private String descripcion4 = "";
    private String descri = null;
    private double vvp = 0.0;
    private double costo = 0.0;
    private int cantidad = 0;
    private double descuento = 0.0;
    private int wwn_reg = 0;
    private String ord_terceros = "";
    private String ot = "";
    private String ruc = "";
    private String placa = "";
    private String direccion = "";
    private String razon_social = "";
    private String fecha = "";
    private String proceso = null;
    private String moneda = "S";
    private String t_moneda = "";
    private String realizado = "";
    private String anulado = "";
    private long ord_compra;
    private String cod_usr = null;
    private String cod_mec = null;

    public Tmp_Terceros() {
    }

    public long getConeccion() {
        return coneccion;
    }

    public void setConeccion(long coneccion) {
        this.coneccion = coneccion;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getCod_mobra() {
        return cod_mobra;
    }

    public void setCod_mobra(String cod_mobra) {
        this.cod_mobra = cod_mobra;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public String getDescripcion4() {
        return descripcion4;
    }

    public void setDescripcion4(String descripcion4) {
        this.descripcion4 = descripcion4;
    }

    public double getVvp() {
        return vvp;
    }

    public void setVvp(double vvp) {
        this.vvp = vvp;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getWwn_reg() {
        return wwn_reg;
    }

    public void setWwn_reg(int wwn_reg) {
        this.wwn_reg = wwn_reg;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getOrd_terceros() {
        return ord_terceros;
    }

    public void setOrd_terceros(String ord_terceros) {
        this.ord_terceros = ord_terceros;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public long getOrd_compra() {
        return ord_compra;
    }

    public void setOrd_compra(long ord_compra) {
        this.ord_compra = ord_compra;
    }

    public String getT_moneda() {
        return t_moneda;
    }

    public void setT_moneda(String t_moneda) {
        this.t_moneda = t_moneda;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
    }

    public String getCod_usr() {
        return cod_usr;
    }

    public void setCod_usr(String cod_usr) {
        this.cod_usr = cod_usr;
    }

    public String getCod_mec() {
        return cod_mec;
    }

    public void setCod_mec(String cod_mec) {
        this.cod_mec = cod_mec;
    }
}
