<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%--<%@include file="jspf/browser.jspf" %>--%>

<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
    <head>
        <%@include file="jspf/css.jspf" %>
    </head>
    <body id="main-wrapper">
        <%@include file="jspf/header.jspf" %>   
       
            <div class="widget">                   
                <div class="widget-content" id="main-content">
                    <h3>Empresa&nbsp;&nbsp;:<bean:write name="Usuario" property="nomTienda" /></H3>
                    <h5 class="nmt">Hola <strong><bean:write name="Usuario" property="nombre" /></strong> !</h5>
                    <p>Bienvenido al Sistema. A continuaci&oacute;n algunos puntos importantes:</p>                    
                    <ul class="iconic nmb">
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">En la parte superior puedes encontrar todas las opciones a las que tienes acceso.</span></li>
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Para realizar tus operaciones, selecciona (con un clic) cualquiera de ellas y espera un momento.</span></li>
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Si est&aacute;s utilizando el sistema desde una red wifi, verifica que tu conexi&oacute;n sea buena.</span></li>
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Antes de procesar cualquier transacci&oacute;n, revisa que los datos ingresados sean correctos.</span></li>
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Si no utilizar&aacute;s el sistema por m&aacute;s de <strong>30 minutos</strong>, debes cerrarlo para evitar errores.</span></li>
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Cuando termines de utilizar el sistema, por tu seguridad, no olvides <strong>cerrar tu sesi&oacute;n.</strong></span></li>
                        <li><span class="green"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Cualquier duda o inconveniente con el sistema, comun&iacute;cate con el Departamento de Sistemas.</span></li>
                        <li><span class="orange"><%=Constantes.ICON_CHECK%></span><span class="icono-izq">Tipo Cambio D&oacute;lares : <bean:write name="Global" property="tipoCambioDolar" /></span></li>
                    </ul>                    
                    
                </div>
            </div>
       
        <%@include file="jspf/js.jspf" %>
    </body>
</html:html>