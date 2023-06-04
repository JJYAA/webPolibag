$(function () 
{
      $('#chkvalor').prop('checked', true);
});

$(function () {
    setCalendariosDependientes({
        obj1: "#fechaIni",
        obj2: "#fechaFin"
    });
});

function muestraListaStock() {
    var valor="1";
    var c = $("#chkvalor");
    if (c.is(":checked")) {
         valor="1";
    } else {
        valor="0";
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "StockAction.do?operacion=muestraStockProductos",
        cache: false,
        data: {
            codigoProducto: $("#producto").val(),
            chkvalor:valor
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
                        $("#c_tablaStock").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaPartesProductos",
                            filasXpagina: 100,
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




function buscarProductoEnter(e) {
    var key = checkKeyCode(e);
    if (key === 13) {
        muestraListaStock();
    }
}

function buscarProductoBoton() {
    muestraListaStock();
}


function buscarKardexBoton() {
    muestraListaKardex();
}


function muestraListaKardex() {

    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "KardexAction.do?operacion=muestraKardex",
        cache: false,
        data: {
            codigoProducto: $("#producto").val(),
            fechaInicio : $("#fechaIni").val(),
            fechaFinal : $("#fechaFin").val(),  
            actividad : "%",
            almacen : $("#almacen").val()
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
                        $("#c_tablaStock").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaPartesProductos",
                            filasXpagina: 100,
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



function consultaListaBoletas() {

    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "BoletaAlmacenAction.do?operacion=consultaListaBoletas",
        cache: false,
        data: {
            tipoBoleta: $("#tipoBoleta").val(),
            fechaInicio : $("#fechaIni").val(),
            fechaFinal : $("#fechaFin").val(),              
            almacen : $("#almacen").val(),
            actividad : "%"
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
                        $("#c_tablaStock").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaPartesProductos",
                            filasXpagina: -1,
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