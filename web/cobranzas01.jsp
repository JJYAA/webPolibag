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
                        <c:choose>
                            <c:when test="${CobranzasForm.flagMueOcuForm=='muestra'}">
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
                                                <button type="button"  id="bttonBuscar" onclick="muestraListaCobranzas();" class="btn btn-primary">Buscar</button>
                                               
                                            </div>                                                                         
                                        </div>                          
                                    </div>
                                    <div class="panel panel-default">                                                                          
                                        <div class="row">
                                            <label class="col-md-1 control-label">Pago</label>
                                            <div class="col-md-2">
                                                <html:select property="formapago" styleId="formapago"    styleClass="form-control input-sm">
                                                    <html:option value="01">Efectivo</html:option>
                                                    <html:option value="02">Depositos</html:option>
                                                    <html:option value="03">Retencion</html:option>
                                                </html:select>                                                  
                                            </div>
                                            <label class="col-md-2 control-label">Moneda</label>
                                            <div class="col-md-2">
                                                <html:select property="moneda" styleId="moneda"    styleClass="form-control input-sm">
                                                    <html:option value="1">Soles</html:option>
                                                    <html:option value="2">Dolares</html:option>
                                                </html:select>                                                  
                                                
                                            </div>
                                            <label class="col-md-2 control-label">Fecha Op.</label>
                                            <div class="col-md-2">
                                                <html:text alt="Fecha " property="fechaOperacion" styleId="fechaOperacion"   styleClass="form-control input-sm upper"></html:text> 
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
                                            <html:text alt="Deposito " property="deposito" styleId="deposito"   styleClass="form-control input-sm upper"></html:text> 
                                        </div>                                                
                                        <label class="col-md-2 control-label">F.Contable</label>
                                        <div class="col-md-2">
                                            <html:text alt="Fecha Contable " property="fechacontable" styleId="fechacontable"   styleClass="form-control input-sm upper"></html:text> 
                                        </div>                                          
                                    </div> 
                                    <div class="row">
                                        <label class="col-md-1 control-label">Total</label>
                                        <div class="col-md-2">
                                            <html:text alt="Total" property="total" styleId="total"   styleClass="form-control input-sm upper"></html:text> 
                                        </div>                                        
                                        <label class="col-md-2 control-label">#Importe</label>
                                        <div class="col-md-2">
                                            <html:text alt="Importe" property="importe" styleId="importe"   styleClass="form-control input-sm decimal-2"></html:text>                                          
                                        </div>
                                    </div>    
                                </div>
                                <div id="c_tablaCobranzas"></div>                          
                                <div class="modal-footer">
                                    <button type="button" id="bttnGrabar"  onclick="GrabarCobranzaNew()"   class="btn btn-default btn-primary" >Grabar Cobranza</button>
                                    <a href="CobranzasNuevoAction.do?operacion=inicializa"  class="btn btn-default btn-danger" > Cancelar</a>
                                </div>                                
                            </c:when> 
                            <c:otherwise> 
                                <div class="accion-line aright">
                                </div>                                
                            </c:otherwise>                              
                        </c:choose>                                                                                                
                        <%-- Hiddens --%>
                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                        <input type="hidden" id="txttotal" name="txttotal">
                        <input type="hidden" id="dseleccion" name="dseleccion">
                    </html:form>
                </div>
            </div>
        </div>

        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsCobranzaNuevo.js"></script>
    </body>
</html:html>

