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
                        <li><strong>Archivo</strong></li>
                        <li>Datos factura del proveedor</li>
                    </ol>
                    <html:form action="/GimAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <div class="panel panel-default">
                            <div class="panel-heading">Orden de compra</div>
                            <div class="panel-body">

                                <div class="form-group">
                                    <label class="col-md-2 control-label">Orden de compra</label>
                                    <div class="col-md-2">
                                        <input type="text" alt="O/C" id="oc" name="oc" onkeypress="EnterCodigo(event);" Class="form-control input-sm numeros"  />
                                    </div>                                                                                                                          
                                    <div class="col-md-2">    
                                        <button onclick="buscaOrdenCompra();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                            Buscar
                                        </button>                                         
                                    </div>  
                                </div>                                           
                            </div>                                
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">DATOS DE LA FACTURA</div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-md-2 control-label">PROVEEDOR</label>
                                    <div class="col-md-2">
                                        <input type="text" alt="RUC" id="proveedor" name="proveedor"   readonly="true"   Class="form-control input-sm upper"/>
                                    </div>                                                                                                                          
                                </div> 
                                <div class="form-group">
                                    <label class="col-md-2 control-label">RAZON SOCIAL</label>
                                    <div class="col-md-4">
                                        <input type="text" alt="Razon social" id="razonsocial" name="razonsocial"   readonly="true"   Class="form-control input-sm "/>
                                    </div>                                                                                                                          
                                </div>                                 
                                <div class="form-group">
                                    <label class="col-md-2 control-label">FECHA</label>
                                    <div class="col-md-2">
                                        <input type="text" alt="Fecha" id="fecha" name="fecha" readonly="true"   Class="form-control input-sm  "/>
                                    </div>                                                                                                                          
                                </div>                                           

                                <div class="form-group">
                                    <label class="col-md-2 control-label"># DE FACTURA</label>
                                    <div class="col-md-2">
                                        <input type="text" alt="# Factura" id="factura" name="factura"  readonly="true"   Class="form-control input-sm upper "/>
                                    </div>                                                                                                                          
                                </div>                                           
                                <div class="form-group">
                                    <label class="col-md-2 control-label"># REFERENCIA</label>
                                    <div class="col-md-2">
                                        <input type="text" alt="OC Referencia" id="referencia" name="referencia"  readonly="true"   Class="form-control input-sm upper "/>
                                    </div>                                                                                                                          
                                </div>                                 
                            </div>                                
                        </div> 
                        <div class="modal-footer">
                            <a href="GimAction.do?operacion=init_datos_proveedor" class="btn btn-sm btn-danger" > Cancelar</a>
                            <button type="button"  onclick="actualizaOC()" class="btn btn-default btn-primary" >Grabar</button>                                        
                        </div>                      
                    </div>
                    <%-- Hiddens --%>
                    <html:hidden property="operacion" styleId="operacion"></html:hidden>
                </html:form>
            </div>
        </div>
    </div>

    <%-- Scripts --%>
    <%@include file="jspf/js.jspf" %>   
    <script type="text/javascript" src="js/funciones/jsGimDatos.js"></script>
</body>
</html:html>