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
                        <li><strong>Archivos</strong></li>
                        <li>Actualiza DATOS</li>
                    </ol>
                    <html:form action="/GimAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <c:choose>
                            <c:when test="${GimForm.flagMueOcuForm=='muestra'}">       
                                <div class="panel panel-default">
                                    <div class="panel-heading">Actualizar Ruta servidor</div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-6 control-label dis">Esta seguro de actualizar los datos</label>                                            
                                        </div>                                        
                                    </div>                                                
                                </div>
                                <div class="accion-line aright">                                      
                                    <a href="GimAction.do?operacion=inicializa" class="btn btn-sm btn-danger" > Cancelar</a>
                                    <button title="Confirmar" id="btnConfirmarDatos"   class="btn btn-sm btn-success"   type="button" ><%=Constantes.ICON_CHECK%> Procesar Actualizacion</button>                                                                                
                                </div>                                                                      
                            </c:when>
                            <c:otherwise> 
                                <div class="accion-line aright">
                                </div>                                
                            </c:otherwise>                                  
                        </c:choose>
                        <%-- Hiddens --%>
                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    </html:form>
                </div>
            </div>
        </div>

        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsGim.js"></script>
    </body>
</html:html>