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
public class Trans_VentasForm extends ActionForm implements Serializable {
    String fechaIni;
    String fechaFin;
    String operacion;
    String asiento;
    String docSelected;
    String docSelectedDel;
    String docSelectedXls;
    String flagMueOcuForm;

    public String getDocSelectedXls() {
        return docSelectedXls;
    }

    public void setDocSelectedXls(String docSelectedXls) {
        this.docSelectedXls = docSelectedXls;
    }

    
    
    public String getFlagMueOcuForm() {
        return flagMueOcuForm;
    }

    public void setFlagMueOcuForm(String flagMueOcuForm) {
        this.flagMueOcuForm = flagMueOcuForm;
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
    
    
    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }
    
    
    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
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
    
    
    
}
