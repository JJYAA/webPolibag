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
                    <li><strong>Transferencias</strong></li>
                    <li><strong>Compras</strong></li>
                    <li>Transferencias de pagos</li>
                </ol>
                <html:form action="/PagosAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                    <%@include file="jspf/msgs.jspf"%>
                    <c:choose>
                        <c:when test="${CobranzasForm.flagMueOcuForm=='muestra'}">                     
                            <div class="panel panel-default">
                                <div class="panel-heading">Buscar por</div>
                                <div class="panel-body">
                                    <%--
                                    <div class="form-group">
                                        <label class="col-md-1 control-label">Codigo</label>
                                        <div class="col-md-2">
                                            
                                            <div class="input-group">
                                                <input type=text" alt="Código Cliente" id="codigo" name="codigo"   onkeypress="return buscarClienteNroDocEnter(event)" Class="form-control input-sm" />
                                                    <span class="input-group-btn">
                                                        <!-- <button title="Buscar" type="button" id="btnBuscarProveedor" onclick="buscarCodigoProveedor();" class="btn btn-sm btn-default"><%=Constantes.ICON_SEARCH%></button>-->
                                                    <button class="btn btn-sm btn-default" id="btnAbreModalHelpProveedor" onclick="openModalHelpProveedores();" type="button" title="Help de Cliente"><%=Constantes.ICON_MENU%></button>                                                            
                                                </span>
                                            </div>                                                    
                                        </div>                                                                            
                                    </div>                                      
                                    --%>
                                    
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
                                                <button onclick="muestraListaPagos();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                    Consultar
                                                </button>                                         
                                            </div>
                                            <div class="col-md-1">
                                                <button onclick="excelPagos();" id="exportButton" disabled="true" class="btn btn-sm btn-danger clearfix"><span class="fa fa-file-excel-o"></span> Exportar Pagos</button>
                                            </div>   
                                        </div>                                           
                                    </div>
                            </div>
                                <div id="c_tablaRegistroVenta"></div>   
                                <div class="accion-line aright">      
                                    <button title="Confirmar" id="btnConAsientoPagos" class="btn btn-sm btn-success"   type="button" ><%=Constantes.ICON_CHECK%> Genera Asientos Pagos</button> 
                                    <button title="Eliminar" id="btnEliminarPagos" onclick="EliminarAsientos();" class="btn btn-sm btn-success"   type="button" ><%=Constantes.ICON_CLEAR%> Eliminar Asientos Pagos</button>                                                                                
                                </div>
                        </c:when>
                        <c:otherwise> 
                            <div class="accion-line aright">
                            </div>                                
                        </c:otherwise>                                 
                    </c:choose>        
                    <%-- Hiddens --%>
                        <div class="modal fade" id="confirm-delete" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Eliminar asientos</h4>
                                    </div>
                                    <div class="modal-body">
                                        <label>¿Estás seguro de eliminar los asientos?</label>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default btn-ok" data-dismiss="modal">Sí</button>
                                        <a class="btn btn-danger" data-dismiss="modal">No
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>                    
                    
                    <html:hidden property="operacion" styleId="operacion"></html:hidden>
                    <html:hidden property="docSelected" styleId="docSelected"  ></html:hidden>       
                    <html:hidden property="docSelectedDel" styleId="docSelectedDel"  ></html:hidden>
                    <html:hidden property="docSelectedXls" styleId="docSelectedXls"  ></html:hidden>
                </html:form>
            </div>
        </div>


        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsPagosPagos.js"></script>
    </body>
</html:html>