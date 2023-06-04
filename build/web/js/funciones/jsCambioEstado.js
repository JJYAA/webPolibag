$(function () 
{
      $('#chkvalor').prop('checked', true);
      muestraListaStock();
});

function verImagen(baseStr64){
    $("#imagen").attr('src', baseStr64);   
    $('#imagen').height('220px');
    $('#imagen').width('220px');
    $("#myModalImagen").modal("show");
}

function GrabarCambioEstado(){  
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CambioEstadoAction.do?operacion=GrabarCambioEstado",
        cache: false,
        data: {
            numeroParte: $("#txtcodigoparte").val(),
            costoPromedio: $("#costoPromedio").val(),
            ultimoCosto: $("#ultimoCosto").val(),
            total: $("#total").val(),
            disponible: $("#disponible").val(),
            seguridad: $("#seguridad").val(),
            almacenes: $("#almacenes").val(),
            temporal :$("#temporal").val()
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        toastAlerta("Errorrrrrrrrrrrr");
                        break;
                    case "exito":
                         toastAlerta("Proceso termino con exito");

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
function xxxxx(){
    $("#numeroparteAux").val(""); 
    $("#descripcion").val("");     
    $("#costoPromedio").val("");     
    $("#ultimoCosto").val("");     
    $("#total").val("");  
    $("#disponible").val("");
    $("#seguridad").val("");
    $("#almacenes").val("");
    $("#temporal").val("");
    $("#ultimoIngreso").val("");
    $("#ultimaSalida").val(""); 
    $("#btnConFacRep").prop("disabled", true);
    $("#btnCanFacRep").prop("disabled", true);       
}

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
        url: "CambioEstadoAction.do?operacion=muestraStockProductos",
        cache: false,
        data: {
            numeroParte: $("#numeroParte").val(),
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
                        $("#c_tablaPartesProductos").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaPartesProductos",
                            filasXpagina: -1,
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

function VerProducto(parm){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CambioEstadoAction.do?operacion=VerProducto",
        cache: false,
        data: {
            numeroParte: parm
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var jsonObjParte = data.objParte;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#txtcodigoparte").val(jsonObjParte.producto); 
                        $("#numeroparteAux").val(jsonObjParte.producto);                         
                        $("#descripcion").val(jsonObjParte.descripcion);     
                        $("#costoPromedio").val(jsonObjParte.costoPromedio);     
                        $("#ultimoCosto").val(jsonObjParte.ultimoCosto);     
                        $("#total").val(jsonObjParte.total);  
                        $("#disponible").val(jsonObjParte.disponible);
                        $("#seguridad").val(jsonObjParte.seguridad);
                        $("#almacenes").val(jsonObjParte.almacenes);
                        $("#temporal").val(jsonObjParte.temporal);
                        $("#ultimoIngreso").val(jsonObjParte.ultimoIngreso);
                        $("#ultimaSalida").val(jsonObjParte.ultimaSalida); 
                        $("#btnConFacRep").prop("disabled", false);
                        $("#btnCanFacRep").prop("disabled", false);
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


