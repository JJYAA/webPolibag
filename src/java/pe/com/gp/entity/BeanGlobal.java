/*
 * Para almacenar variables globales que se usan en todo el sistema.
 */
package pe.com.gp.entity;

import java.io.Serializable;

public class BeanGlobal implements Serializable {

    static final long serialVersionUID = 1L;
    private double tipoCambioVenta;
    private double tipoCambioCompra;
    private double porcIGV;
    private double porcISC;
    private String fechaSistema;
    private String empresa;
    private String ruta;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    
    

    public BeanGlobal() {
    }

    public double getTipoCambioVenta() {
        return tipoCambioVenta;
    }

    public void setTipoCambioVenta(double tipoCambioVenta) {
        this.tipoCambioVenta = tipoCambioVenta;
    }

    public double getTipoCambioCompra() {
        return tipoCambioCompra;
    }

    public void setTipoCambioCompra(double tipoCambioCompra) {
        this.tipoCambioCompra = tipoCambioCompra;
    }

    public double getPorcIGV() {
        return porcIGV;
    }

    public void setPorcIGV(double porcIGV) {
        this.porcIGV = porcIGV;
    }

    public double getPorcISC() {
        return porcISC;
    }

    public void setPorcISC(double porcISC) {
        this.porcISC = porcISC;
    }

    public String getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(String fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

}
