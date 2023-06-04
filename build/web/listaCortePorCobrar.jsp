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
                    <li>Listado de documentos por corte</li>
                </ol>
                <html:form action="/CobranzasAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                    <%@include file="jspf/msgs.jspf"%>
                    <div class="panel panel-default">
                        <div class="panel-heading">Buscar por</div>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-md-1 control-label">C&oacute;digo Cliente</label>
                                <div class="col-md-2">
                                    <div class="input-group">
                                        <html:text alt="Código Cliente"
                                                   property="codigo"
                                                   styleId="codigo"                                                  
                                                   styleClass="form-control input-sm"></html:text>
                                            <span class="input-group-btn">
                                            <a href="#" class="btn btn-sm btn-default" id="btnAbreModalHelpCli" onclick="helpClientesCliente();" title="Help de Cliente" ><%=Constantes.ICON_MENU%></a>
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
                                    <%--
                                    <div class="col-md-1">
                                        <select class="form-control"  id="auxForma" name="auxForma">
                                            <option value="%">TODOS</option>
                                            <option value="CO">CONTADO</option>
                                            <option value="CR">CREDITO</option>
                                        </select>                                         
                                    </div>
                                    --%>
                                    <div class="col-md-1">    
                                        <A href="#" onclick="muestraListaCorte();" class="btn btn-sm btn-primary" title="Consultar"> Consultar</A>
                                    </div>
                                    <div class="col-md-1">
                                        <A href="#" onclick="excelConCorte();" class="btn btn-sm btn-success" title="Consultar">Exportar Excel</A>
                                    </div>   
                                </div>                                           
                            </div>
                        </div>
                        <div id="c_tablaDepositos"></div>                           
                    <%-- Hiddens --%>
                    
                    <div class="modal fade" id="modalClientes" data-backdrop="static"  tabindex="-1" role="dialog">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Clientes</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>                                    
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">Busca por</label> 
                                        <div class="col-md-6">
                                            <input type="text" id="txtbuscar" name="txtbuscar" 
                                                     onkeypress="buscarporcodigoCliente(event);" 
                                                   Class="form-control input-sm upper"> 
                                        </div>
                                        <div class="col-md-1">                                            
                                            <a href="#" onclick="buscasBottonClientes()"  class="btn btn-sm btn-success" title=" Buscar "> Buscar cliente</a>
                                        </div>                                              
                                    </div>                                         
                                </div>
                                <div class="modal-body">
                                    <div id="c_tablaClientes"></div>
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
        <script type="text/javascript" src="js/funciones/jsCobranzaPagos.js?v2023052600002"></script>
    </body>
</html:html>