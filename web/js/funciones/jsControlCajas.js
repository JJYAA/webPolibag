/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#boletaIngreso").focus();
});

function grabarControl(){
        showLoader();        
        $("#operacion").val("btnConControl");
        $("#frm_generico").submit();    
}

function buscarBoletaEnter(e) {
    var key = checkKeyCode(e);
    if (key === 13) {
        buscarBoletaFactura();
    }
}

function buscaParteEnter(e){
    var key = checkKeyCode(e);
    if (key === 13) {
        VerProducto($("#numeroParte").val());
    }    
}

function cajasEnter(e){
    var key = checkKeyCode(e);
    if (key === 13) {
        validaCantidad($("#numeroParte").val());
    }      
}

function validaCantidad(){
    if ($.nullNum($("#cajas").val()) === 0) 
    {
         return false;
    }
    agregarCajas($("#cajas").val());
}

function limpiar(){
    $("#numeroParte").val("");
    $("#descripcion").val("");
    $("#cajas").val("");
}

function VerProducto(parm){
    var c = $("#cbox2");
    if (c.is(":checked")) {
         valor="1";
    } else {
        valor="0";
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProductosAction.do?operacion=VerProducto",
        cache: false,
        data: {
            numeroParte: parm
        },
        success: function (data) 
        {
            try 
            {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var jsonObjParte = data.objParte;
                switch (tipoMsg) 
                {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        limpiar();
                        break;
                    case "exito":
                        $("#descripcion").val(jsonObjParte.descripcion);  
                       
                        if (valor==="1" ) {
                             agregarCajas("1");   
                             limpiar();
                             $("#numeroParte").focus();
                         }     
                        else
                            $( "#cajas" ).focus();
                        break;
                }
            } catch (error) {
                toastError(error);
            }
        },
        beforeSend: function () {
            showLoader();
        },
        complete: function () {
            hideLoader();
        },
        error: function (xhr, status, error) {
            toastError(error);
        }
    }); 
}


function buscarBoletaFactura(){    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ControlCajaAction.do?operacion=buscarBoletaFactura",
        cache: false,
        data: {
            
            Ntoboleta:$("#boletaIngreso").val()
            
        },
        success: function (data) {            
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        toastError("No Existe informaci&oacute;n que procesar");
                        break;
                    case "exito":
                        
                        $("#c_tablasBoletasFactura").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablasBoletasFactura",
                            filasXpagina: -1,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });                        
                        if (jsonObjTabla.ctosRegs>0){
                            $("#boletaIngreso").prop("disabled", true);
                            $("#btnBuscar").prop("disabled", true);
                            $("#numeroParte").prop("disabled", false);
                            $("#cajas").prop("disabled", false);
                            $("#BtnAgregar").prop("disabled", false);
                            $( "#numeroParte" ).focus();                            
                        }                            
                        break;
                }
            } catch (error) {
                toastError(error);
            }
        },
        beforeSend: function () {
            showLoader();
        },
        complete: function () {
            hideLoader();
        },
        error: function (xhr, status, error) {
            toastError(error);
        }
    });    
}



function agregarCajas(items){
    var c = $("#cbox2");
    if (c.is(":checked")) {
         valor="1";
    } else {
        valor="0";
    }    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ControlCajaAction.do?operacion=buscarProducto",
        cache: false,
        data: {            
            cantidad : items,
            codigoProducto : $("#numeroParte").val(),
            control : valor
            
        },
        success: function (data) {            
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
             var cajas = jsonObjTabla.ctosCajas;
                          
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        toastError("No Existe informaci&oacute;n que procesar");
                        break;
                    case "exito":                        
                        $("#c_tablasBoletasFactura").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablasBoletasFactura",
                            filasXpagina: -1,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                        
                        if (jsonObjTabla.ctosRegs>0){
                            $("#boletaIngreso").prop("disabled", true);
                            $("#btnBuscar").prop("disabled", true);
                            $("#numeroParte").prop("disabled", false);
                            $("#cajas").prop("disabled", false);
                            $("#BtnAgregar").prop("disabled", false);
                            $( "#numeroParte" ).focus();                            
                        }
                        if (cajas>0)
                         $("#btnConControl").prop("disabled", false);   
                         else
                          $("#btnConControl").prop("disabled", true);      
                         limpiar();
                         $("#numeroParte").focus();                       
                        break;
                }
            } catch (error) {
                toastError(error);
            }
        },
        beforeSend: function () {
            showLoader();
        },
        complete: function () {
            hideLoader();
        },
        error: function (xhr, status, error) {
            toastError(error);
        }
    });
    
    
}



function EliminiarItem(Producto){   
   
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ControlCajaAction.do?operacion=eliminarItems",
        cache: false,
        data: {            
            codigoProducto:Producto
            
        },
        success: function (data) {            
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
                var cajas = jsonObjTabla.ctosCajas;
                
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        toastError("No Existe informaci&oacute;n que procesar");
                        break;
                    case "exito":
                        
                        $("#c_tablasBoletasFactura").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablasBoletasFactura",
                            filasXpagina: -1,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });                        
                        if (cajas>0)
                         $("#btnConControl").prop("disabled", false);   
                         else
                          $("#btnConControl").prop("disabled", true);                                
                        break;
                }
            } catch (error) {
                toastError(error);
            }
        },
        beforeSend: function () {
            showLoader();
        },
        complete: function () {
            hideLoader();
        },
        error: function (xhr, status, error) {
            toastError(error);
        }
    });    
}
