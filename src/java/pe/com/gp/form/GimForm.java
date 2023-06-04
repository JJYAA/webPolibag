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
public class GimForm extends ActionForm implements Serializable {

    String pathServidor;
    String flagMueOcuForm;
    String operacion;
    Boolean chkCliPro;
    Boolean chkAsiento;

    public Boolean getChkCliPro() {
        return chkCliPro;
    }

    public void setChkCliPro(Boolean chkCliPro) {
        this.chkCliPro = chkCliPro;
    }

    public Boolean getChkAsiento() {
        return chkAsiento;
    }

    public void setChkAsiento(Boolean chkAsiento) {
        this.chkAsiento = chkAsiento;
    }

    
    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getFlagMueOcuForm() {
        return flagMueOcuForm;
    }

    public void setFlagMueOcuForm(String flagMueOcuForm) {
        this.flagMueOcuForm = flagMueOcuForm;
    }

    public String getPathServidor() {
        return pathServidor;
    }

    public void setPathServidor(String pathServidor) {
        this.pathServidor = pathServidor;
    }

}
