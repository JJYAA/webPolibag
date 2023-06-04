<%@page import="pe.com.gp.connection.ConectaSQL"%>
<%@page import="pe.com.gp.connection.ConectaDb"%>
<%@page import="java.util.List"%>
<%@page import="pe.com.gp.entity.ListaGenerica"%>
<%@page import="pe.com.gp.listas.ListaTiendas"%>
<%@page import="pe.com.gp.util.*"%>
<%@page import="java.util.Calendar" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Leyendo si el servidor es desarrollo o produccion
    ConectaSQL conectaDb = new ConectaSQL();
    String server = conectaDb.getServer();
    String auxiliar = (String) request.getParameter("auxiliar");
%>

<html:html>
    <head> 
        <%@include file="jspf/css.jspf" %>        
    </head>
    <body id="login-wrapper">
        <div id="login-overlay"></div>
        <div class="login-container-v2">
            <div class="container">
                <html:form styleId="frm_login" styleClass="form-signin form-login" action="/LoginAuxiliar" method="POST" >   
                    <div class="c-logo-gp"><img src="images/logo-empresa2-color.png" onerror="this.onerror=null; this.src='images/l ogo-empresa2-color.png'"/></div>                    
                    <p class="login-top text-center">    
                        <span class="block sistema">WEB</span>                        
                    </p>
                    <div class="line line-dashed"></div>
                    <logic:messagesPresent>
                        <div class='alert alert-danger fade in'>
                            <%=Constantes.ICON_CLEAR%>
                            <html:errors />
                        </div>
                    </logic:messagesPresent>
                    <div class="form-group">                                 
                        <html:select property="empresa" styleId="empresa" styleClass="form-control input-sm"  >
                            <html:option value="">--SELECCIONE--</html:option>
                            <html:options collection="listaEmpresas" labelProperty="descripcion" property="indice" />
                        </html:select>                                
                                
                                
                                
                    </div>                  
                    <div class="form-group">   
                        <div class="input-icon izq">
                            <%=Constantes.ICON_USER%>
                            <html:text property="usuario" styleId="usuario" styleClass="form-control upper" alt="Ingrese su usuario" maxlength="10" value="10044449" ></html:text>                            
                            </div> 
                        </div>
                        <div class="form-group">
                            <div class="input-icon izq">
                            <%=Constantes.ICON_LOCK%>
                            <html:password property="contrasena" styleId="clave" styleClass="form-control" alt="Ingrese su clave" maxlength="10" value="070708" ></html:password>                            
                            </div>
                        </div>
                    <%--<div class="form-group">                      
                        <p class="form-control-static text-right">
                            <a href="LoginAuxiliar.do?proceso=iniRecuperarPassword" class="lost-password goloading" title="¿Olvidaste tu contrase&ntilde;a?">¿Olvidaste tu contrase&ntilde;a?</a>
                        </p>
                    </div>--%>
                    <div class="c-boton-login">
                        <button class="btn btn-block btn-default boton-login" type="submit" title="Ingresar">Ingresar</button>
                    </div>
                    <p class="login-footer">
                        <span class="block copy">Copyright &COPY; <%=Calendar.getInstance().get(Calendar.YEAR)%></span>
                        <span class="block copy">Powered by JJ&AA</span>
                        <span class="block version">Versi&oacute;n 1.00 | <%=server%></span>
                    </p>                                
                    <html:hidden property="proceso" value="01" />
                    <html:hidden property="operacion" value="01" />
                    <html:hidden property="auxiliar" value="<%=auxiliar%>" />
                </html:form>
            </div>
        </div> 

        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>
        <script type="text/javascript" src="js/funciones/jsLogin.js"></script> 
    </body>
    <style>
        #login-wrapper #login-overlay{background: none}
    </style>
</html:html>