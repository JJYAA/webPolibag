/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    setCalendariosDependientes({
        obj1: "#fechaIni"
    });            
});


function muestraAuditoria() {
    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "AuditoriaAction.do?operacion=muestraAuditoria",
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            aux4212: $("#aux4212").val(),
            auxtipo: $("#auxtipo").val()            
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
                  var jsonObjTotales = data.objTotales;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#c_tablaAuditoria").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaAuditoria",
                            filasXpagina: 30,
                            cajaBuscar: true,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                        $("#neto").val(jsonObjTotales.neto);
                        $("#igv").val(jsonObjTotales.igv);
                        $("#total").val(jsonObjTotales.total);
                        $("#exportButton").prop("disabled", false);
                        $("#exportClientes").prop("disabled", false);
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


function muestraDocumentos() {
    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "AuditoriaAction.do?operacion=muestraDocumentos",
        cache: false,
        data: {
            documento: $("#documento").val()        
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
                  var jsonObjTotales = data.objTotales;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#c_tablaAuditoria").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaAuditoria",
                            filasXpagina: 30,
                            cajaBuscar: true,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                        $("#neto").val(jsonObjTotales.neto);
                        $("#igv").val(jsonObjTotales.igv);
                        $("#total").val(jsonObjTotales.total);
                        $("#exportButton").prop("disabled", false);
                        $("#exportClientes").prop("disabled", false);
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
