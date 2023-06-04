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
                        <li><strong>Transferencias</strong></li>
                        <li>Compras</li>
                        <li>Provision documentos</li>
                    </ol>
                    <html:form action="/ProvisionAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                        <%@include file="jspf/msgs.jspf"%>
                        <c:choose>
                            <c:when test="${ProvisionForm.flagMueOcuForm=='muestra'}">      

                                <div class="panel panel-default">
                                    <div class="panel-heading">Titulo</div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">Proveedor</label>

                                                    <div class="col-md-5">
                                                        <input type="text"  alt="RUC" id="rucProveedor" name="rucProveedor"   Class="form-control input-sm upper" />
                                                    </div>                                            
                                                </div>
                                            </div>    
                                            <div class="col-md-7">                                         
                                                <label class="col-md-4 control-label">Razon social</label>
                                                <div class="col-md-7">
                                                    <input type="text"  alt="Razon social" id="txtRazonSocial" name="txtRazonSocial"  readonly="true" Class="form-control input-sm upper" />
                                                </div>                                                                                    
                                            </div>
                                        </div>  
                                        <div class="row">
                                            <div class="col-md-5">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">#Tipo Documento</label>
                                                    <div class="col-md-5">
                                                        <html:select property="tipodocumento" styleId="tipodocumento"    styleClass="form-control input-sm">
                                                            <html:options collection="listaTipoDocumento" labelProperty="descripcion" property="indice" />
                                                        </html:select>    
                                                    </div>                                            
                                                </div>                                                
                                            </div>  
                                            <div class="col-md-7">                                         
                                                <label class="col-md-4 control-label">Asiento</label>
                                                <div class="col-md-4">
                                                    <input type="text"  alt="Asiento" id="txtasiento" name="txtasiento"  readonly="true" Class="form-control input-sm" />
                                                </div>                                                                                    
                                            </div>                                            
                                        </div>                                 
                                        <div class="row">
                                            <div class="col-md-5">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">#Documento</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  alt="#Documento" id="numeroDocumento" name="numeroDocumento"   Class="form-control input-sm upper" />
                                                    </div>                                            
                                                </div>
                                            </div>  
                                            <div class="col-md-7">     
                                                <button type="button"  id="botonbuscar" name="botonbuscar"  onclick="BuscarDocumento()" class="btn btn-primary"> Buscar</button>
                                            </div>

                                        </div>                                 
                                    </div>                          
                                </div>

                                <div class="panel panel-default">
                                    <br>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">Fecha Documento</label>

                                                <div class="col-md-5">
                                                    <input type="text"  alt="Fecha Documento" id="fechaDocumento" name="fechaDocumento"  readonly="true" Class="form-control input-sm upper" />
                                                </div>                                            
                                            </div>
                                        </div>    
                                        <div class="col-md-7">                                         
                                            <label class="col-md-4 control-label">Fecha Vencimiento</label>
                                            <div class="col-md-4">
                                                <input type="text"  alt="Fecha Vencimiento" id="fechaVencimiento" name="fechaVencimiento"  readonly="true" Class="form-control input-sm upper" />
                                            </div>                                                                                    
                                        </div>
                                    </div>   
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">Gravado</label>
                                                <div class="col-md-6">
                                                    <input type="checkbox"   name="gravado" id="gravado"  Class="input-sm"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">Moneda</label>

                                                <div class="col-md-4">
                                                    <select id="moneda" name="moneda" Class="form-control input-sm" >
                                                        <option value="1">Soles</option>
                                                        <option value="2">Dolares</option>
                                                    </select>
                                                </div>                                            
                                            </div>
                                        </div>    
                                    </div> 
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">Base Imponible</label>
                                                <div class="col-md-8">
                                                    <div class="col-md-5">
                                                        <input type="text"  alt=" Base Imponible" id="baseImponible" name="baseImponible"   Class="form-control input-sm decimal-2" />
                                                    </div>                                                
                                                </div>                                            
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">IGV</label>
                                                <div class="col-md-8">
                                                    <div class="col-md-5">
                                                        <input type="text"  alt=" IGV" id="igv" name="igv"  Class="form-control input-sm decimal-2" />
                                                    </div>                                                                   
                                                </div>                                            
                                            </div>                                    
                                            <div class="form-group">
                                                <label class="col-md-4 control-label">Total</label>
                                                <div class="col-md-6">
                                                    <div class="col-md-7">
                                                        <input type="text"  alt=" 0" id="total" name="total"   Class="form-control input-sm decimal-2"/>
                                                    </div>                                                                   
                                                </div>   
                                                <div class="col-md-1">     
                                                    <button type="button"  id="addCuentas" name="addCuentas" disabled="true"  onclick="Adicionar()" class="btn btn-primary">+</button>
                                                </div>                                                                                
                                            </div>                                    
                                        </div>                                                                    
                                    </div>                             
                                </div>  
                                <div class="accion-line aright">      
                                    <button title="Eliminar" disabled="true" onclick="EliminarAsientos()" name="btnEliminarProv" id="btnEliminarProv" class="btn btn-sm btn-danger"   type="button" ><%=Constantes.ICON_CLEAR %> Eliminar provision</button> 
                                    <button title="Confirmar" id="btnConAsientoGrabar" class="btn btn-sm btn-success"   type="button" ><%=Constantes.ICON_CHECK%> Genera provision</button> 
                                    <a href="ProvisionAction.do?operacion=inicializa" class="btn btn-sm btn-success"> <%=Constantes.ICON_CHECK%> Cancelar</a>
                                </div>                        
                            </c:when>
                            <c:otherwise> 
                                <div class="accion-line aright">
                                </div>                                
                            </c:otherwise>      
                        </c:choose>                           



                        <%-- Hiddens --%>

                        <html:hidden property="operacion" styleId="operacion"></html:hidden>
                         <html:hidden property="asiento" styleId="asiento"></html:hidden>

                            <div class="modal fade" id="Items_provision" >
                                <div class="modal-dialog  modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            Registro de pago
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Cuenta Contable</label>
                                                <div class="col-md-5">
                                                    <div class="input-group">
                                                    <html:select property="cuenta" styleId="cuenta"    styleClass="form-control input-sm">
                                                        <html:options collection="listaPlanCuenta" labelProperty="descripcion" property="indice" />
                                                    </html:select>                                                                                                        
                                                </div>  
                                            </div>           
                                            <label class="col-md-2 control-label">Base Imponible</label>
                                            <div class="col-md-2">
                                                <input type=text" alt="Glosa" id="txtbaseImp" name="txtbaseImp" readonly="true" Class="form-control input-sm upper" />    
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Glosa</label>
                                            <div class="col-md-6">
                                                <input type=text" alt="Glosa" id="txtglosa" name="txtglosa" Class="form-control input-sm upper" />                                                                                   
                                            </div>                                                                            
                                        </div>                                                                                           
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Debe</label>
                                            <div class="col-md-2">
                                                <input type=text" alt="Debe" id="txtdebe" name="txtdebe" Class="form-control input-sm  decimal-2" />                                                                                   
                                            </div>                                                                            
                                            <label class="col-md-2 control-label">Haber</label>
                                            <div class="col-md-2">
                                                <input type=text" alt="Haber" id="txthaber" name="txthaber" Class="form-control input-sm decimal-2" />                                                                                   
                                            </div> 
                                            <button type="button"  onclick="AdicionarItemProv()"  class="btn btn-primary" >+</button>
                                        </div>  
                                        <div id="c_tablaAddItem"></div>   

                                    </div>                                        


                                    <div class="modal-footer">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Total</label>
                                            <div class="col-md-2">
                                                <input type=text" alt="Total" id="txtTotalCuenta" name="txtTotalCuenta" Class="form-control input-sm  decimal-2" />                                                                                   
                                            </div>                                              
                                        </div>                                        
                                        <button type="button"  onclick="GrabarPagos()"   class="btn btn-default btn-primary" >Aceptar</button>
                                        <a class="btn btn-danger" data-dismiss="modal">&nbsp;&nbsp;&nbsp;Salir&nbsp;&nbsp;&nbsp;</a>
                                    </div>
                                </div>
                            </div>
                        </div>                         

                        <div class="modal fade" id="confirm-delete" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Eliminar asientos</h4>
                                    </div>
                                    <div class="modal-body">
                                        <label>¿Estás seguro de eliminar el documento?</label>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default btn-ok" data-dismiss="modal">Sí</button>
                                        <a class="btn btn-danger" data-dismiss="modal">No
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>  

                    </html:form>
                </div>
            </div>
        </div>

        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsProvision.js"></script>
    </body>
</html:html>