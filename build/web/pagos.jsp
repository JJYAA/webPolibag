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
                    <li>Compras</li>
                    <li>Registro de pagos</li>
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
                                                <input type=text" alt="Código Cliente" id="codigo" name="codigo"  onkeypress="return buscarClienteNroDocEnter(event)" Class="form-control input-sm" />
                                                    <span class="input-group-btn">
                                                        <button title="Buscar" type="button" id="btnBuscarProveedor" onclick="buscarCodigoProveedor();" class="btn btn-sm btn-default"><%=Constantes.ICON_SEARCH%></button>
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
                                        </div>                                           
                                    </div>
                                </div>
                                <div id="c_tablaRegistroVenta"></div>   


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

                        <div class="modal fade" id="confirm-pago" >
                            <div class="modal-dialog  modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        Registro de pago
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">Documento</label>

                                                    <div class="col-md-5">
                                                        <input type="text"  id="txtDocumentoProv" name="txtDocumentoProv"  readonly="true" Class="form-control input-sm upper" />
                                                    </div>
                                                </div>
                                            </div>    
                                            <div class="col-md-6">                                                                                      
                                            </div>
                                        </div>   
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">Moneda</label>
                                                    <div class="col-md-3">
                                                        <input type="text"  id="txtMoneda" name="txtMoneda"  readonly="true"  Class="form-control input-sm upper" />
                                                    </div>

                                                </div>                                                                                      
                                            </div>    
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">TC</label>
                                                    <div class="col-md-3">
                                                        <input type="text"  id="txtTCProv" name="txtTCProv"  readonly="true"  Class="form-control input-sm upper" />
                                                    </div>
                                                </div>                                                                                        
                                            </div>
                                        </div>                                         


                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">Forma de pago</label>
                                                    <div class="col-md-5">
                                                        <select class="form-control" name="formapago"  id="formapago">
                                                            <option value="01">Efectivo</option>
                                                            <option value="02">Depositos</option>
                                                        </select>                                                
                                                    </div>
                                                </div>
                                            </div>    
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Moneda</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control"  id="moneda">
                                                            <option value="1">Soles/</option>
                                                            <option value="2">Dolares</option>
                                                        </select>                                                
                                                    </div>
                                                </div>                                                                                        
                                            </div>
                                        </div>    
                                        <div class="row">
                                            <div class="col-md-6">                                        
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">Banco</label>
                                                    <div class="col-md-6">
                                                    <html:select property="banco" styleId="banco"    styleClass="form-control input-sm">
                                                        <html:options collection="listaBancos" labelProperty="descripcion" property="indice" />
                                                    </html:select>                                                           
                                                </div>
                                            </div>                                        
                                        </div>    
                                        <div class="col-md-6">   
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Fecha Op.</label>
                                                <div class="col-md-4">
                                                    <input type="text" alt="fechaOperacion" id="fechaOperacion" name="fechaOperacion" Class="form-control input-sm upper"  readonly="true" />  
                                                </div>
                                            </div>  
                                        </div>    
                                    </div> 
                                    <div class="row">
                                        <div class="col-md-6">      
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">#Deposito</label>
                                                <div class="col-md-5">
                                                    <input type="text" alt="deposito" id="deposito" name="deposito" Class="form-control input-sm upper" />                                           
                                                </div>
                                            </div>                                                  
                                        </div>
                                        <div class="col-md-6">       
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">#Importe</label>
                                                <div class="col-md-3">
                                                    <input type="text" alt="importe" id="importe" name="importe" Class="form-control input-sm decimal-2" />                                           
                                                </div>
                                                <div class="col-md-1"> 
                                                    <button type="button" id="botonaddItem" name="botonaddItem" onclick="AdicionarPago()"  class="btn btn-default btn-ok" > Adicionar</button>
                                                </div>                                                       
                                            </div>                                                  
                                        </div>  
                                    </div>                                                                                 
                                    <div class="row">
                                        <div class="col-md-6">      
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">Total S/.</label>
                                                <div class="col-md-5">
                                                    <input type="text" alt="Total S/" id="total_sol" name="total_sol" readonly="true" Class="form-control input-sm upper" />                                           
                                                </div>                                                   
                                            </div>                                                  
                                        </div>
                                        <div class="col-md-6">   
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Total US$.</label>
                                                <div class="col-md-5">
                                                    <input type="text" alt="Total US$" id="total_dol" name="total_dol" readonly="true" Class="form-control input-sm upper" />                                           
                                                </div>                                                   
                                            </div>                                                 
                                        </div>  
                                    </div>              
                                </div>
                                <div class="row">
                                    <div class="col-md-1">      
                                    </div>    
                                    <div class="col-md-10">      
                                        <div id="c_tablaCobranzaItems"></div>
                                    </div>                                            
                                    <div class="col-md-1">      
                                    </div>    
                                </div>
                                <div class="row">
                                    <div class="col-md-6">      
                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Pagado S/</label>
                                            <div class="col-md-5">
                                                <input type="text" alt="Pagado" id="pagado_sol" name="pagado_sol" readonly="true" Class="form-control input-sm upper" />                                           
                                            </div>
                                        </div>                                                  
                                    </div>
                                    <div class="col-md-6">                                                       
                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Pagado US$/</label>
                                            <div class="col-md-5">
                                                <input type="text" alt="Pagado" id="pagado_dol" name="pagado_dol" readonly="true" Class="form-control input-sm upper" />                                           
                                            </div>
                                        </div>                                                  

                                    </div>   
                                </div>                                   

                                <div class="row">
                                    <div class="col-md-6">      
                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Saldo S/</label>
                                            <div class="col-md-5">
                                                <input type="text" alt="Pagado" id="auxsaldo_sol" name="auxsaldo_sol" readonly="true" Class="form-control input-sm upper" />                                           
                                            </div>
                                        </div>                                                  
                                    </div>
                                    <div class="col-md-6">                                                       
                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Saldo US$/</label>
                                            <div class="col-md-5">
                                                <input type="text" alt="Pagado" id="auxsaldo_dol" name="auxsaldo_dol" readonly="true" Class="form-control input-sm upper" />                                           
                                            </div>
                                        </div>                                                  

                                    </div>   
                                </div>                                      

                                <div class="modal-footer">
                                    <button type="button"  onclick="GrabarPagos()"   class="btn btn-default btn-primary" >Grabar Pagos</button>
                                    <a class="btn btn-danger" data-dismiss="modal">&nbsp;&nbsp;&nbsp;Salir&nbsp;&nbsp;&nbsp;</a>
                                </div>
                            </div>
                        </div>
                    </div>                         


                    <div class="modal fade" id="modalProveedores" data-backdrop="static"  tabindex="-1" role="dialog">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Precios por Producto</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div id="c_tablaPartesProductos"></div>
                                </div>
                            </div>
                        </div>
                    </div>                    

                    <input type="hidden" id="codigoCliente" name="codigoCliente"  />
                    <input type="hidden" id="tipoDocumento" name="tipoDocumento"  />
                    <input type="hidden" id="serieDocumento" name="serieDocumento"  />
                    <input type="hidden" id="numeroDocumento" name="numeroDocumento"  />
                    <input type="hidden" id="item" name="item"  />

                    <input type="hidden" id="auxtotal_sol" name="auxtotal_sol"  />
                    <input type="hidden" id="auxtotal_dol" name="auxtotal_dol"  />

                    <input type="hidden" id="auxpagado_sol" name="auxpagado_sol"  />
                    <input type="hidden" id="auxpagado_dol" name="auxpagado_dol"  />

                    <input type="hidden" id="auxpagadoItem" name="auxpagadoItem"  />






                </html:form>
            </div>
        </div>


        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsPagos.js"></script>
    </body>
</html:html>