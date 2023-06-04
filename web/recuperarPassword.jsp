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
        <div class="container main-container" style="max-width: 800px !important">
            <div class="widget">
                <div class="widget-content" id="main-content">                    
                    <html:form action="/LoginAuxiliar" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <div class="panel panel-default">                          
                            <div class="panel-body">
                                <h3 style="margin-top: 13px">Recuperar mi contrase&ntilde;a</h3>
                                <p>Podemos ayudarte a recuperar tu contrase&ntilde;a usando una de las direcciones de correo electr&oacute;nico asociada a tu cuenta.</p>
                                <div class="form-group">                                    
                                    <label class="control-label col-md-2">Tienda</label>
                                    <div class="col-md-4">                                       
                                        <html:select property="tienda" styleId="tienda" styleClass="form-control input-sm">
                                            <html:options collection="listaTiendas" labelProperty="descripcion" property="indice" />
                                        </html:select>                                   
                                    </div>
                                </div>                 
                                <div class="form-group">
                                    <label class="control-label col-md-2">DNI o CE</label>
                                    <div class="col-md-4">
                                        <div class="input-group">
                                            <html:text property="documento" styleId="documento" alt="DNI o CE" maxlength="20" styleClass="form-control input-sm upper"></html:text>
                                                <span class="input-group-btn">
                                                    <button title="Buscar" type="button" onclick="correosRecuperarPassword();" class="btn btn-sm btn-default"><%=Constantes.ICON_SEARCH%><span class="icono-izq">Buscar</span></button>
                                                </span>
                                            </div>                                        
                                        </div>
                                        <div class="he-7 visible-xs visible-sm hidden-md hidden-lg"></div>
                                        <div class="col-md-2">
                                            <a href="LoginAuxiliar.do?proceso=Teclado" class="btn btn-default btn-sm goloading" title="Cancelar"><%=Constantes.ICON_REPLY%><span class="icono-izq">Regresar</span></a>
                                        </div>
                                    </div>
                                    <br/>
                                <c:choose>
                                    <c:when test="${flagHizoBusqueda=='S'}">                                        
                                        <logic:empty name="listaCorreos">
                                            <div class='alert alert-info fade in alert-dismissable'>                                       
                                                <%=Constantes.ICON_INFO%>
                                                No se encontraron correos registrados
                                            </div>
                                        </logic:empty>
                                        <logic:notEmpty name="listaCorreos">                                    
                                            <table class="table table-condensed table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Correo</th>
                                                        <th class="text-center" style="width: 150px;">Enviar contrase&ntilde;a</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <logic:iterate name="listaCorreos" id="listaCorreosId">
                                                        <tr>
                                                            <td><bean:write name="listaCorreosId"/></td>                                                  
                                                            <td class="text-center">
                                                                <button title="Enviar contrase&ntilde;a a este correo" class="btn btn-xs btn-default" onclick="recuperarPassword('${listaCorreosId}');" type="button"><%=Constantes.ICON_CHECK%> Enviar</button>
                                                            </td>
                                                        </tr>
                                                    </logic:iterate>
                                                </tbody>                                        
                                            </table>                                                                                     
                                        </logic:notEmpty>
                                    </c:when>
                                </c:choose>
                                <%@include file="jspf/msgs.jspf"%>                                
                            </div>
                        </div>
                        <%-- Hiddens --%>
                        <html:hidden property="proceso" styleId="proceso"></html:hidden>
                        <html:hidden property="correo" styleId="correo"></html:hidden>
                    </html:form>
                </div>
            </div>
        </div>
        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsRecuperarPassword.js"></script>
    </body>
</html:html>