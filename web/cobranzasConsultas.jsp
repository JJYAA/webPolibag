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
                            <div class="panel-heading"></div>
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
            <script type="text/javascript" src="js/funciones/jsCobranzaNuevo.js"></script>
    </body>
</html:html>