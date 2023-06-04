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
                        <li>Secuencias</li>
                    </ol>
                    <html:form action="/SecuenciaAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <c:choose>
                            <c:when test="${SecuenciaForm.flagMueOcuForm=='muestra'}">       
                                <div class="panel panel-default">
                                    <div class="panel-heading">Actualizar Ruta servidor</div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Año</label>
                                            <div class="col-md-5">
                                                <html:select property="anho" styleId="anho"  styleClass="form-control input-sm">
                                                    <html:option value="2021">2021</html:option>
                                                    <html:option value="2022">2022</html:option>
                                                    <html:option value="2023">2023</html:option>
                                                    <html:option value="2024">2024</html:option>
                                                    <html:option value="2025">2025</html:option>
                                                    <html:option value="2026">2026</html:option>
                                                    <html:option value="2027">2027</html:option>
                                                    <html:option value="2028">2028</html:option>
                                                </html:select>
                                            </div>  
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">Mes</label>
                                        <div class="col-md-5">
                                            <html:select property="mes" styleId="mes"   styleClass="form-control input-sm">
                                                <html:option value="01">Enero</html:option>
                                                <html:option value="02">Febrero</html:option>
                                                <html:option value="03">Marzo</html:option>
                                                <html:option value="04">Abril</html:option>
                                                <html:option value="05">Mayo</html:option>
                                                <html:option value="06">Junio</html:option>
                                                <html:option value="07">Julio</html:option>
                                                <html:option value="08">Agosto</html:option>
                                                <html:option value="09">Setiembre</html:option>
                                                <html:option value="10">Octubre</html:option>
                                                <html:option value="11">Voviembre</html:option>
                                                <html:option value="12">Diciembre</html:option>
                                            </html:select>
                                        </div>
                                        <div class="col-md-1">    
                                            <button onclick="muestraSecuencia();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                Consultar
                                            </button>                                         
                                        </div>                                        

                                    </div>
                                </div>   
                                <div id="c_tablaSecuencia"></div>
                                

                            </div>                                                
                        </div>
                        <div class="accion-line aright">                                      
                            <a href="SecuenciaAction.do?operacion=inicializa" class="btn btn-sm btn-danger" > Cancelar</a>
                            <button title="Confirmar" onclick="GrabarSecuencia();"  id="btnConfirmarSecuencia"   class="btn btn-sm btn-success"   type="button" ><%=Constantes.ICON_CHECK%> Confirmar</button>                                                                                
                        </div>                                                                      
                    </c:when>
                    <c:otherwise> 
                        <div class="accion-line aright">
                        </div>                                
                    </c:otherwise>                                  
                </c:choose>
                <%-- Hiddens --%>
                <html:hidden property="operacion" styleId="operacion"></html:hidden>
                <html:hidden property="selected" styleId="selected"></html:hidden>
                
            </html:form>
        </div>
    </div>
</div>

<%-- Scripts --%>
<%@include file="jspf/js.jspf" %>   
<script type="text/javascript" src="js/funciones/jsGim.js"></script>
</body>
</html:html>