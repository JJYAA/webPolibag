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

        <div class="widget">
            <div class="widget-content" id="main-content">
                <ol class="breadcrumb">
                    <li><strong>Gestion</strong></li>
                    <li>Utilidad de vendedor</li>
                </ol>
                <html:form action="/UtilidadAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                    <%@include file="jspf/msgs.jspf"%> 
                    <c:choose>
                        <c:when test="${Trans_VentasForm.flagMueOcuForm=='muestra'}">                     
                            <div class="panel panel-default">
                                <div class="panel-heading">Buscar por</div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-md-1 control-label">De</label>
                                        <div class="col-md-1">
                                            <html:text alt="De" property="fechaIni" styleId="fechaIni"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                            </div>                                                                              
                                            <label class="col-md-1 control-label">Hasta</label>
                                            <div class="col-md-1">
                                            <html:text alt="Hasta" property="fechaFin" styleId="fechaFin"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                            </div>   
                                            <div class="col-md-1">    
                                                <button onclick="muestraListaUtilidadVendedor();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                    Consultar
                                                </button>                                         
                                            </div>
                                            <div class="col-md-1">
                                                <button  id="exportButton" disabled="true" class="btn btn-sm btn-danger clearfix"><span class="fa fa-file-excel-o"></span> Exportar Ventas</button>
                                            </div> 
                                            <div class="col-md-1">
                                                <button onclick="excelClientes();" id="exportClientes" disabled="true" class="btn btn-sm btn-danger clearfix"><span class="fa fa-file-excel-o"></span> Exportar Clientes</button>
                                            </div>    
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-1 control-label">Solo</label>
                                            <div class="col-md-1">
                                                <select id="seleccion" name="seleccion" class="form-control input-sm">
                                                    <option value="0"> Todo</option>
                                                    <option value="1"> Ganancias</option>
                                                    <option value="2"> Costo menor igual a CERO </option>
                                                </select>                                            
                                            </div>
                                        </div>        
                                    </div>         
                                </div>
                                <div id="c_table_tree-utilidad"></div>                             
                        </c:when>
                        <c:otherwise> 
                            <div class="accion-line aright">
                            </div>                                
                        </c:otherwise>                                 
                    </c:choose>        
                    <%-- Hiddens --%>


                    <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    <html:hidden property="docSelected" styleId="docSelected"  ></html:hidden>       
                    <html:hidden property="docSelectedDel" styleId="docSelectedDel"  ></html:hidden>
                    <html:hidden property="docSelectedXls" styleId="docSelectedXls"  ></html:hidden>

                </html:form>
            </div>
        </div>


        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsUtilidad.js"></script>
    </body>
</html:html>
