/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




//
//
//
//
//
//function muestraListaCortePagar() {
//    var tipoReporte = "";
//    if ($("#auxproceso").val() === "T")
//        tipoReporte = "PagarAction.do?operacion=muestraListaCorte";
//    else
//        tipoReporte = "PagarAction.do?operacion=muestraListaCorteDetalle";
//
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: tipoReporte,
//        cache: false,
//        data: {
//            fechaIni: $("#fechaIni").val(),
//            fechaFin: $("#fechaFin").val(),
//            tipo: $("#auxtipo").val(),
//            codigo: '',
//            forma: '%'
//        },
//        success: function (data) {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjTabla = data.objTabla;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":
//                        $("#c_tablaDepositos").html(jsonObjTabla.tabla);
//                        iniDataTable({
//                            tabla: "#tablaDepositos",
//                            filasXpagina: 30,
//                            cajaBuscar: true,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });
//                        break;
//                }
//            } catch (error) {
//                toastError(error);
//            }
//        },
//        beforeSend: function () {
//            showLoader();
//        },
//        complete: function () {
//            hideLoader();
//        },
//        error: function (xhr, status, error) {
//            toastError(error);
//        }
//    });
//
//}


//
//function excelConCorte() {
//
//    // showLoader();
//    $("#tipo").val($("#auxtipo").val());
//    $("#forma").val($("#auxforma").val());
//    $("#codigo").val("");
//    if ($("#auxproceso").val() === "T") {
//        alert('pipiliiiiiiiiiiiiiiiii');
//        $("#operacion").val("muestraListaCorte_excel");
//    } else {
//        alert('222');
//        $("#operacion").val("muestraListaCorteDetalle_Excel");
//    }
//    $("#frm_generico").submit();
//}


function openModalHelpYYYY(){   //help proveedores
    alert($("#txtbuscar").val());
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
                alert(jsonObjTabla.tabla);
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
                            filasXpagina: 20,
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

function buscaEnterProductoXX(e){
    var key = checkKeyCode(e);
    if (key === 13) {
        $("#txtcodigo").val("aaaaaaaaaaaaaaaaaaa");
    }
}



//
//
//function openModalHelpProveedores() {
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: "genericoAction.do?operacion=lista_proveedores",
//        cache: false,
//        data: {
//            txtcodigo: $("#txtcodigo").val()
//        },
//        success: function (data)
//        {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjTabla = data.objTabla;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":
//                        $("#modalProveedor").modal("show")
//                        $("#c_tablaProveedores").html(jsonObjTabla.tabla);
//                        iniDataTable({
//                            tabla: "#tablaProveedores",
//                            filasXpagina: 20,
//                            cajaBuscar: false,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });
//                        break;
//                }
//            } catch (error) {
//                toastError(error);
//            }
//
//        },
//        beforeSend: function () {
//            showLoader();
//        },
//        complete: function () {
//            hideLoader();
//        },
//        error: function (xhr, status, error) {
//            toastError(error);
//        }
//    });
//}
//
//function buscaEnterProductoXX(e){
//    var key = checkKeyCode(e);
//    if (key === 13) {
//        buscasBottonXXXXX();
//    }    
//}
//function buscasBottonXXXXX(){
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: "genericoAction.do?operacion=lista_proveedores",
//        cache: false,
//        data: {
//            txtcodigo: $("#txtbuscar").val()
//        },
//        success: function (data)
//        {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjTabla = data.objTabla;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":
//                        $("#modalProveedor").modal("show")
//                        $("#c_tablaProveedores").html(jsonObjTabla.tabla);
//                        iniDataTable({
//                            tabla: "#tablaProveedores",
//                            filasXpagina: 20,
//                            cajaBuscar: false,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });
//                        break;
//                }
//            } catch (error) {
//                toastError(error);
//            }
//
//        },
//        beforeSend: function () {
//            showLoader();
//        },
//        complete: function () {
//            hideLoader();
//        },
//        error: function (xhr, status, error) {
//            toastError(error);
//        }
//    });
//}
//
