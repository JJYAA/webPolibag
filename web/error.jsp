<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@include file="jspf/browser.jspf" %>
<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
    <head><%@include file="jspf/css.jspf" %></head>
    <body id="main-wrapper">
        <%@include file="jspf/header.jspf" %>
        <div class="container main-container">
            <div class="widget">
                <div class="widget-content" id="main-content">                 
                    <div class="row">
                        <div class="col-md-12">
                            <h2 class="text-danger">Ha ocurrido un error</h2>
                            <p class="text-muted">Por favor, <b>capture esta pantalla</b> y comun&iacute;quese con el Departamento de Sistemas.</p>
                            <%@include file="jspf/msgs.jspf" %>
                            <br/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="jspf/js.jspf" %>   
    </body>
</html:html>