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
                        <li><strong>Consulta</strong></li>
                        <li>Kardex</li>
                    </ol>
                    <html:form action="/KardexAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <div class="panel panel-default">
                            <div class="panel-heading">Buscar por</div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Almacen</label>
                                    <div class="col-md-3">
                                        <html:select property="almacen" styleId="almacen"    styleClass="form-control input-sm">
                                            <html:options collection="listatIiendAlmacen" labelProperty="descripcion" property="indice" />
                                        </html:select>                                                       
                                    </div>  
                                </div>                                

                                <div class="form-group">
                                    <label class="col-md-2 control-label">De</label>
                                    <div class="col-md-2">
                                        <div class="input-group">
                                            <html:text alt="De" property="fechaIni" styleId="fechaIni"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                            </div>                                                     
                                        </div>  
                                        <label class="col-md-1 control-label">De</label>
                                        <div class="col-md-2">
                                            <div class="input-group">
                                            <html:text alt="Hasta" property="fechaFin" styleId="fechaFin"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                            </div>                                                     
                                        </div>                                                     
                                    </div>                                
                                    <div class="form-group">                                    
                                        <label class="col-md-2 control-label">Hasta</label>
                                        <div class="col-md-3">
                                            <div class="input-group">
                                            <html:text alt="Numero documento" property="producto" styleId="producto"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>
                                                <span class="input-group-btn">
                                                    <button title="Buscar" type="button" id="btnBuscarCliNroDoc" onclick="buscarKardexBoton();" class="btn btn-sm btn-default"><%=Constantes.ICON_SEARCH%></button>
                                            </span>                                            
                                            </div>                                                                                       
                                        </div>
                                        <div class="col-md-2">    
                                            <button onclick="buscarKardexBoton();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                    Consultar
                                            </button>
                                        </div>                                                                                                     
                                    </div>
                            </div>
                        </div>
                        <div id="c_tablaStock"></div>                      

                        <%-- Hiddens --%>
                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    </html:form>
                </div>
            </div>
        </div>

        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsStock.js"></script>
    </body>
</html:html>