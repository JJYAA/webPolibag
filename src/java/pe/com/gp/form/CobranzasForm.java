/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.form;

import java.io.Serializable;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Administrador
 */
public class CobranzasForm extends ActionForm implements Serializable {
    String fechaIni;
    String fechaFin;
    String operacion;
    String asiento;
    String docSelected;
    String docSelectedDel;
    String docSelectedXls;
    String flagMueOcuForm;    
    String banco;
    String tipo;
    String codigo;
    String forma;
    String documento;
    String formapago;
    String importe;
    String total;
    String deposito;
    String moneda;
    String fechaOperacion;
    String fechacontable;
    String num_ope;
    String proceso;

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getNum_ope() {
        return num_ope;
    }

    public void setNum_ope(String num_ope) {
        this.num_ope = num_ope;
    }
    
    
    
    
    public String getFechacontable() {
        return fechacontable;
    }

    public void setFechacontable(String fechacontable) {
        this.fechacontable = fechacontable;
    }
    
    
    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }
    
    
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    

    public String getDocSelectedXls() {
        return docSelectedXls;
    }

    public void setDocSelectedXls(String docSelectedXls) {
        this.docSelectedXls = docSelectedXls;
    }
        
    
    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getDocSelected() {
        return docSelected;
    }

    public void setDocSelected(String docSelected) {
        this.docSelected = docSelected;
    }

    public String getDocSelectedDel() {
        return docSelectedDel;
    }

    public void setDocSelectedDel(String docSelectedDel) {
        this.docSelectedDel = docSelectedDel;
    }

    public String getFlagMueOcuForm() {
        return flagMueOcuForm;
    }

    public void setFlagMueOcuForm(String flagMueOcuForm) {
        this.flagMueOcuForm = flagMueOcuForm;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
    
    
    
            
            
}
