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

        <div class="widget">
            <div class="widget-content" id="main-content">
                <ol class="breadcrumb">
                    <li><strong>Gestion</strong></li>
                    <li>Utilidad por documento</li>
                </ol>
                <html:form action="/UtilidadAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                    <%@include file="jspf/msgs.jspf"%>
                    <c:choose>
                        <c:when test="${Trans_VentasForm.flagMueOcuForm=='muestra'}">                     
                            <div class="panel panel-default">
                                <div class="panel-heading">Buscar por</div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-md-1 control-label">De</label>
                                        <div class="col-md-1">
                                            <html:text alt="De" property="fechaIni" styleId="fechaIni"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                            </div>                                                                              
                                            <label class="col-md-1 control-label">Hasta</label>
                                            <div class="col-md-1">
                                            <html:text alt="Hasta" property="fechaFin" styleId="fechaFin"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                            </div>   
                                            <div class="col-md-1">    
                                                <button onclick="muestraListaUtilidad();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                    Consultar
                                                </button>                                         
                                            </div>  
                                        </div>       
                                    </div>         
                                </div>
                                <div id="c_tableUtilidad"></div>                             
                        </c:when>
                        <c:otherwise> 
                            <div class="accion-line aright">
                            </div>                                
                        </c:otherwise>                                 
                    </c:choose>        
                    <%-- Hiddens --%>
                    <div class="modal fade" id="detalle" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Detalle del documento</h4>
                                </div>
                                <div class="modal-footer">
                                    <div id="c_tableDetalleDocumento"></div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-sm btn-default" title="Cerrar" onclick="cerrar();"><span class="icono-izq"> Cerrar</span></button>                                
                                </div>                                 
                            </div>                           
                        </div>
                    </div>                    

                    <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    <html:hidden property="docSelected" styleId="docSelected"  ></html:hidden>       
                    <html:hidden property="docSelectedDel" styleId="docSelectedDel"  ></html:hidden>
                    <html:hidden property="docSelectedXls" styleId="docSelectedXls"  ></html:hidden>

                </html:form>
            </div>
        </div>


        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsUtilidad.js"></script>
    </body>
</html:html>
