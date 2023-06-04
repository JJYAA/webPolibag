/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    //var combos = ["banco"];

    setCalendariosDependientes({
        obj1: "#fechaIni"        
    });
    setCalendariosDependientes({
        obj2:"#fechaFin"
    });    
    
  
    
//    $('#exportarExcelPagar').submit(function(e) {
//        alert(' clicked');
//                
//    });    
    

});

function excelConCortePorPagar(){
        $("#tipo").val($("#auxtipo").val());
        $("#forma").val($("#auxforma").val());
        $("#codigo").val($("#codigo").val());
        if ($("#auxproceso").val() === "T") {
            $("#operacion").val("muestraListaCorte_excelPagar");
        } else {
            $("#operacion").val("muestraListaCorteDetalle_ExcelPagar");
        }
        $("#frm_generico").submit();       
}



function buscasBottonZZ(){
    openModalHelpProveedoresCorte();  
}


function helpProveedores(){
   openModalHelpProveedoresCorte();
   return false;
}



function eligeProducto(cod){
    $("#codigo").val(cod);
     $("#modalProveedor").modal("hide");
}

function xxx(){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagarAction.do?operacion=lista_proveedores",
        cache: false,
        data: {
            txtcodigo: $("#txtbuscar").val()
        },
        success: function (data)
        {
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
                        break;
                    case "exito":                                              
                        $("#c_tablaProveedores").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaProveedores",
                            filasXpagina: 15,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });                       
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

/*
function submitForm(event){
    event.preventDefault();
  
  }*/

function buscarporcodigoprod(e){
 var key = checkKeyCode(e);
    if (key === 13) {
        openModalHelpProveedoresCorte();
    }    
   
}

function openModalHelpProveedoresCorte(){   //help proveedores
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagarAction.do?operacion=lista_proveedores",
        cache: false,
        data: {
            txtcodigo: $("#txtbuscar").val()
        },
        success: function (data)
        {
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
                        break;
                    case "exito":                                              
                        $("#c_tablaProveedores").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaProveedores",
                            filasXpagina: 15,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                         $("#modalProveedor").modal("show");
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



function buscarClienteOrdenVentaEnter(e){
    var key = checkKeyCode(e);
    if (key === 13) {
       muestraListaCortePagar();
       return false;
    }    
}


function muestraListaCortePagar() {
    var tipoReporte = "";
    if ($("#auxproceso").val() === "T")
        tipoReporte = "PagarAction.do?operacion=muestraListaCorte";
    else
        tipoReporte = "PagarAction.do?operacion=muestraListaCorteDetalle";

    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: tipoReporte,
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            fechaFin: $("#fechaFin").val(),
            tipo: $("#auxtipo").val(),
            codigo: $("#codigo").val(),
            forma: '%'
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
                        break;
                    case "exito":
                        $("#c_tablaDepositos").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaDepositos",
                            filasXpagina: 30,
                            cajaBuscar: true,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
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

