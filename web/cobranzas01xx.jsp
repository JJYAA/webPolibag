<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@include file="jspf/browser.jspf" %>
<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html:html>
    <head>
        <%@include file="jspf/css.jspf" %>
    </head>
    <body id="main-wrapper">
        <%@include file="jspf/header.jspf" %>
        <div class="container main-container">
            <div class="widget">
                <div class="widget-content" id="main-content">
                    <ol class="breadcrumb">
                        <li><strong>Transferencias</strong></li>
                        <li><strong>Ventas</strong></li>
                        <li>Cobranzas</li>
                    </ol>
                    <html:form action="/CobranzasNuevoAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-md-1 control-label">Codigo</label>
                                    <div class="col-md-2">
                                        <input type="text" id="codigo" name="codigo" value="20534144602" Class="form-control input-sm upper" />
                                    </div>
                                </div>                                  
                                <div class="form-group">                                                            
                                    <label class="col-md-1 control-label">De</label>
                                    <div class="col-md-2">
                                        <html:text alt="De" property="fechaIni" styleId="fechaIni"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                        </div>
                                        <label class="col-md-1 control-label">Hasta</label>
                                        <div class="col-md-2">
                                        <html:text alt="Hasta" property="fechaFin" styleId="fechaFin"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                        </div>  
                                        <input type="button" onclick="muestraListaCobranzas();" class="btn btn-sm btn-default">
                                    </div>                                                                         
                                </div>                          
                            </div>
                            <div class="panel panel-default">                                                                          
                                <div class="row">
                                    <label class="col-md-1 control-label">Pago</label>
                                    <div class="col-md-2">
                                        <select class="form-control" name="formapago"  id="formapago">
                                            <option value="01">Efectivo</option>
                                            <option value="02">Depositos</option>
                                        </select>                                                
                                    </div>



                                    <label class="col-md-2 control-label">Moneda</label>
                                    <div class="col-md-2">
                                        <select class="form-control"  id="moneda">
                                            <option value="1">Soles/</option>
                                            <option value="2">Dolares</option>
                                        </select>                                                
                                    </div>



                                    <label class="col-md-2 control-label">Fecha Op.</label>
                                    <div class="col-md-2">
                                        <input type="text" alt="fechaOperacion" id="fechaOperacion" name="fechaOperacion" Class="form-control input-sm upper"  readonly="true" >  
                                    </div>


                                </div>    
                                <div class="row">

                                    <label class="col-md-1 control-label">Banco</label>
                                    <div class="col-md-2">
                                    <html:select property="banco" styleId="banco"    styleClass="form-control input-sm">
                                        <html:options collection="listaBancos" labelProperty="descripcion" property="indice" />
                                    </html:select>                                                           
                                </div> 
                                <label class="col-md-2 control-label">#Deposito</label>
                                <div class="col-md-2">
                                    <input type="text" alt="deposito" id="deposito" name="deposito" Class="form-control input-sm upper" />                                           
                                </div>                                                

                            </div> 
                            <div class="row">
                                <label class="col-md-1 control-label">Total</label>
                                <div class="col-md-2">
                                    <input type="text" alt="Total" id="total" name="total" readonly="true" Class="form-control input-sm upper"/>                                           
                                </div>                                        
                                <label class="col-md-2 control-label">#Importe</label>
                                <div class="col-md-2">
                                    <input type="text" alt="importe" id="importe" name="importe" Class="form-control input-sm decimal-2" />                                           
                                </div>
                                <div class="col-md-1"> 
                                    <button type="button" onclick="AdidonarPago()" id="botonaddItem" name="botonaddItem"  class="btn btn-default btn-ok" > Adicionar</button>
                                </div>                                                         
                            </div>    
                        </div>


                        <div id="c_tablaCobranzas"></div>  
                        
                                    <div class="modal-footer">
                                        <button type="button"  onclick="GrabarCobranza()"   class="btn btn-default btn-primary" >Grabar Cobranza</button>
                                        <a class="btn btn-danger" data-dismiss="modal">&nbsp;&nbsp;&nbsp;Salir&nbsp;&nbsp;&nbsp;</a>
                                    </div>                        
                        
                        <%-- Hiddens --%>
                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    </html:form>
                </div>
            </div>
        </div>

        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsCobranzaNuevo.js"></script>
    </body>
</html:html>

