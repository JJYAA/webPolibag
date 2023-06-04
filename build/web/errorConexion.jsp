<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@include file="jspf/browser.jspf" %>
<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="b" %>

<html:html>
    <head>
        <%@include file="jspf/css.jspf" %>
    </head>
    <body id="main-wrapper">       
        <div class="container main-container">
            <div class="widget">
                <div class="widget-content" id="main-content">
                    <html:form action="/LoginAuxiliarAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">                        
                        <div class="panel panel-default">
                            <div class="panel-heading">ERROR DE CONEXION</div>
                            <div class="panel-body">
                                <div class="form-group">    

                                    <div class="text-center">
                                        <img src="images/conexion01.jpg" class="rounded" alt="...">
                                    </div>                                    
                                    <div class="text-center">
                                        <bean:write name="BeanError" property="error"/>
                                    </div> 
                                    <div class="text-center">                                       
                                        <a href="LoginAuxiliarAction.do?operacion=validaIngreso&proceso=logout"   class="btn btn-primary"> Regresar</a>
                                    </div>                                     
                                </div> 
                            </div>
                        </div>
                        <%-- Hiddens --%>
                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    </html:form>
                </div>
            </div>
        </div>

    </body>
</html:html>