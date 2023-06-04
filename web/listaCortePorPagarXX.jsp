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
                    <li><strong>Reportes</strong></li>
                    <li>Listado de documentos por Pagar</li>
                </ol>
                <html:form action="/PagarAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                    <%@include file="jspf/msgs.jspf"%>
                    <div class="panel panel-default">
                        <div class="panel-heading">Buscar por</div>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="form-group">
                                    <label class="col-md-1 control-label">C&oacute;digo Cliente</label>											
                                    <div class="col-md-2">
                                        <div class="input-group">
                                            <input type="text"  id="txtcodigo" name="txtcodigo"  
                                                       onkeypress="return buscaEnterProveedor(event)"   
                                                       Class="form-control input-sm"   >
                                                <span class="input-group-btn">
                                                    <button title="Buscar" type="button" id="btnBuscarCliNroDoc" onclick="buscarClienteNroDocBoton();" class="btn btn-sm btn-default"><%=Constantes.ICON_SEARCH%></button>
                                                <button class="btn btn-sm btn-default"	id="btnAbreModalHelpCli" onclick="openModalHelpYYYY();" type="button" title="Help de Cliente"><%=Constantes.ICON_MENU%></button>                                                             
                                            </span>                                                                                        
                                        </div>                                                     
                                    </div>                                  
                                </div>
                                <div class="form-group">
                                    <label class="col-md-1 control-label">Desde</label>
                                    <div class="col-md-1">
                                        <html:text alt="De" property="fechaIni" styleId="fechaIni"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                        </div>                                                                              
                                        <label class="col-md-1 control-label">Hasta</label>
                                        <div class="col-md-1">
                                        <html:text alt="Hasta" property="fechaFin" styleId="fechaFin"   onkeypress="return buscarProductoEnter(event)" styleClass="form-control input-sm upper"></html:text>                                           
                                        </div>   
                                        <div class="col-md-1">
                                            <select class="form-control"  id="auxtipo" name="auxtipo">
                                                <option value="2">Pendientes</option>
                                                <option value="1">Cancelados</option>
                                                <option value="3">Todos</option>
                                            </select>                                         
                                        </div>
                                        <div class="col-md-1">
                                            <select class="form-control"  id="auxproceso" name="auxproceso">
                                                <option value="T">Totalizado</option>
                                                <option value="D">Detallado</option>
                                            </select>                                         
                                        </div>

                                        <div class="col-md-1">    
                                            <button onclick="muestraListaCortePagar();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                Consultar
                                            </button>                                         
                                        </div>
                                        <div class="col-md-1">
                                            <button onclick="excelConCorte();" id="exportButton"  class="btn btn-sm btn-danger clearfix"><span class="fa fa-file-excel-o"></span> Exportar</button>
                                        </div>   
                                    </div>                                           
                                </div>
                            </div>
                            <div id="c_tablaDepositos"></div>                           
                        <%-- Hiddens --%>

                        <div class="modal fade" id="modalProveedor" data-backdrop="static"  tabindex="-1" role="dialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Proveedores</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Busca por</label> 
                                            <div class="col-md-6">
                                                <input type="text" id="txtbuscar" name="txtbuscar" onkeypress="buscaEnterProductoXX(event)"  Class="form-control input-sm upper"> 
                                            </div>
                                            <div class="col-md-1">
                                                <button type="button" onclick="buscasBottonXXXXX()"  class="btn btn-sm btn-primary" >Buscar</button>    
                                            </div>                                              
                                        </div>                                         
                                    </div>
                                    <div class="modal-body">
                                        <div id="c_tablaProveedores"></div>
                                    </div>                                                                            
                                </div>
                            </div>
                        </div>             


                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                        <html:hidden property="tipo" styleId="tipo"></html:hidden>
                        <html:hidden property="forma" styleId="forma"></html:hidden> 
                    </html:form>
                </div>
            </div>


            <%-- Scripts --%>
            <%@include file="jspf/js.jspf" %>   
            <script type="text/javascript" src="js/funciones/jsCortePagosXX.js"></script>
    </body>
</html:html>