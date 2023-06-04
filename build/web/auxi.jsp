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
    
        <%@include file="jspf/header.jspf" %>

        
            
                <ol class="breadcrumb">
                    <li><strong>Transferencias</strong></li>
                    <li><strong>Ventas</strong></li>
                    <li>Cobranzas</li>
                </ol>
                <html:form action="/CobranzasNuevoAction" styleId="frm_generico" method="POST" styleClass="form-horizontal">
                    <%@include file="jspf/msgs.jspf"%>
                    <c:choose>
                        <c:when test="${CobranzasForm.flagMueOcuForm=='muestra'}">                     
                            <div class="panel panel-default">
                                <div class="panel-heading">Buscar por</div>
                                <div class="panel-body">                                   
                                    <div class="form-group">
                                        <label class="col-md-1 control-label">Codigo</label>
                                        <div class="col-md-1">
                                            <input type="text" id="codigo" name="codigo" value="20534144602" Class="form-control input-sm upper" />
                                        </div>
                                    </div>                                   
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
                                                <button onclick="muestraListaCobranzas();" class="btn btn-sm btn-default" type="button" title="Consultar">                                                    
                                                    Consultar
                                                </button>                                         
                                            </div>  
                                        </div>                                           
                                    </div>
                                </div>
                                <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                                    <li class="nav-item">
                                      <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-home" role="tab" aria-controls="pills-home" aria-selected="true">Home</a>
                                    </li>
                                    <li class="nav-item">
                                      <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-profile" role="tab" aria-controls="pills-profile" aria-selected="false">Profile</a>
                                    </li>
                                    <li class="nav-item">
                                      <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-contact" role="tab" aria-controls="pills-contact" aria-selected="false">Contact</a>
                                    </li>
                                </ul>
                                
                                    <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">...</div>
                                    <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">...</div>
                                    <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">...</div>
                                
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

                        <input type="hidden" id="codigoCliente" name="codigoCliente"  />
                        <input type="hidden" id="tipoDocumento" name="tipoDocumento"  />
                        <input type="hidden" id="serieDocumento" name="serieDocumento"  />
                        <input type="hidden" id="numeroDocumento" name="numeroDocumento"  />
                        <input type="hidden" id="item" name="item"  />


                        <input type="hidden" id="auxtotal" name="auxtotal"  />
                        <input type="hidden" id="auxpagado" name="auxpagado"  />

                        <input type="hidden" id="auxpagadoItem" name="auxpagadoItem"  />



                        <input type="text" id="dseleccion" name="dseleccion"  />

                        <input type="text" id="txtCodCli" name="txtCodCli"  />
                        <input type="text" id="txtPagarMulti" name="txtPagarMulti"  />


                </html:form>
            
        


        <%-- Scripts --%>
        <%@include file="jspf/js.jspf" %>   
        <script type="text/javascript" src="js/funciones/jsCobranzaNuevo.js"></script>
    
</html:html>