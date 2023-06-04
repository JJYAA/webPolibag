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
                        <li><strong>Reportes</strong></li>
                        <li>Comprobante</li>
                    </ol>
                    <html:form action="/AuditoriaAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <div class="panel panel-default">
                            <div class="panel-heading"></div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-md-1 control-label">De</label>
                                    <div class="col-md-2">
                                        <html:text alt="De" property="documento" styleId="documento"    styleClass="form-control input-sm upper"></html:text>                                           
                                        </div>                                                                                                                                                                                                                                                 
                                        <div class="col-md-1">    
                                            <button onclick="muestraDocumentos();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                Consultar
                                            </button>                                         
                                        </div>                                          
                                    </div>     
                                     <div id="c_tablaAuditoria"></div>       
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
        <script type="text/javascript" src="js/funciones/jsAuditoria.js"></script>
    </body>
</html:html>