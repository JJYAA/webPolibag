/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    //var combos = ["banco"];

    setCalendariosDependientes({
        obj1: "#fechaVencimiento"
    });
    setCalendariosDependientes({
        obj2: "#fechaDocumento"
    });
    $("#tipodocumento").val("");
    

    $('#gravado').prop('checked', true);

    $('#btnConAsientoGrabar').click(function () {        
        
        if ($("#rucProveedor").val() === "")
        {
            toastError("Ingrese RUC del proveedor");
            return false;
        }
        if ($("#tipodocumento").val() === null) {
            toastError("Seleccione el tipo de documento");
            return false;
        }
        if ($("#numeroDocumento").val() === "")
        {
            toastError("Ingrese el DOCUMENTO");
            return false;
        }
        if ($("#fechaDocumento").val() === "")
        {
            toastError("Fecha del documento");
            return false;
        }
        if ($("#fechaVencimiento").val() === "")
        {
            toastError("Fecha de vencimiento");
            return false;
        }        
        var baseImp = $.nullFlo($("#baseImponible").val());
        var igv = $.nullFlo($("#igv").val());
        var total = $.nullFlo($("#total").val());
        if (total === 0) {
            toastError("No existe Total ingresado, verifique");
            return false;
        }
        var c = $("#gravado");
        if (c.is(":checked")) {
            valor = "1";
        } else {
            valor = "0";
        }
        if ((valor === "1") && (igv === 0)) {
            toastError("Error en el IGV, verifique");
            return false;
        }
        var sum_total = baseImp + igv;
        sum_total =  $.nullFlo(sum_total.toFixed(2));

        if (sum_total !== total) {
            toastError("Existe un descuadre, verifique");
            return false;
        }
        showLoader();
        $("#asiento").val($("#txtasiento").val());
        $("#operacion").val("conAsientoProvision");
        $("#frm_generico").submit();

    });

    $('#confirm-delete').on('show.bs.modal',function(){
                    $('.btn-ok').click(function(){
            showLoader();
            $("#operacion").val("conEliminarAsiento");
            $("#frm_generico").submit();
            });
        });   


});


function Adicionar() {
    $("#cuenta").val("");
    $("#txtdebe").val("");
    $("#txthaber").val("");
    $("#txtglosa").val("");
    var baseImp = $.nullFlo($("#baseImponible").val());
    var igv = $.nullFlo($("#igv").val());
    var total = $.nullFlo($("#total").val());
    if (total === 0) {
        toastError("No existe Total ingresado, verifique");
        return false;
    }
    var c = $("#gravado");
    if (c.is(":checked")) {
        valor = "1";
    } else {
        valor = "0";
    }
    if ((valor === "1") && (igv === 0)) {
        toastError("Error en el IGV, verifique");
        return false;
    }

    var sum_total = baseImp + igv;
    sum_total =  $.nullFlo(sum_total.toFixed(2));
    if (sum_total !== total) {
        toastError("Existe un descuadre, verifique");
        return false;
    }
    $("#txtbaseImp").val(baseImp);


    $("#Items_provision").modal("show");

}

function GrabarPagos() {
    var totalCta = $.nullFlo($("#txtTotalCuenta").val());
    var baseImp = $.nullFlo($("#txtbaseImp").val());
    if (totalCta > baseImp) {
        toastError("Error en DESCUADRE, Verifique");
        return false;
    }
    $("#Items_provision").modal("hide");

}

function AdicionarItemProv() {
    var baseDebe = $.nullFlo($("#txtdebe").val());
    var baseHaber = $.nullFlo($("#txthaber").val());
    var totalCta = $.nullFlo($("#txtTotalCuenta").val());
    var baseImp = $.nullFlo($("#txtbaseImp").val());
    if ($("#cuenta").val() === "")
    {
        toastError("Seleccione la Cuenta");
        return false;
    }
    if ($("#txtglosa").val().length === 0) {
        toastError("Agrege la Glosa");
        return false;
    }
    if ((baseDebe + baseHaber) === 0) {
        toastError("Verifica los totales");
        return false;
    }
    if ((baseDebe > 0) && (baseHaber > 0)) {
        toastError("Verifica los totales");
        return false;
    }
    if ((totalCta + (baseDebe + baseHaber)) > baseImp) {
        toastError("Error supera la base imponible");
        return false;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProvisionAction.do?operacion=AdicionarItemProv",
        cache: false,
        data: {
            cuenta: $("#cuenta").val(),
            glosa: $("#txtglosa").val(),
            debe: $("#txtdebe").val(),
            haber: $("#txthaber").val()
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

                        ListaItemProv();
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

function ListaItemProv() {
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProvisionAction.do?operacion=listaDocumentoProveedor_ProvisionTmp",
        cache: false,
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
                        $("#txtTotalCuenta").val(jsonObjMsg.total);
                        $("#c_tablaAddItem").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaAddItem",
                            filasXpagina: 30,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                        /*$("#neto").val(jsonObjTotales.neto);
                         $("#igv").val(jsonObjTotales.igv);
                         $("#total").val(jsonObjTotales.total);
                         $("#exportButton").prop("disabled", false);
                         $("#exportClientes").prop("disabled", false);*/
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



function BuscarDocumento() {
    if ($("#rucProveedor").val() === "")
    {
        toastError("Ingrese RUC del proveedor");
        return false;
    }
    if ($("#tipodocumento").val() === null) {
        toastError("Seleccione el tipo de documento");
        return false;
    }
    if ($("#numeroDocumento").val() === "")
    {
        toastError("Ingrese el DOCUMENTO");
        return false;
    }

    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProvisionAction.do?operacion=BuscarDocumento",
        cache: false,
        data: {
            ruc: $("#rucProveedor").val(),
            tipodocumento: $("#tipodocumento").val(),
            documento: $("#numeroDocumento").val()

        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjDatos = data.objDatos;
                var jsonObjTotales = data.objTotales;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        alert('error');
                        break;
                    case "exito":

                        $("#fechaDocumento").val(jsonObjDatos.fechadocumento);
                        $("#fechaVencimiento").val(jsonObjDatos.vencimiento);
                        if (jsonObjDatos.gravado === "1")
                            $('#gravado').prop("checked", true);
                        else
                            $('#gravado').prop("checked", false);
                        if (jsonObjDatos.moneda === '1')
                            $("#moneda > option[value=1]").attr("selected", true);
                        else
                            $("#moneda > option[value=2]").attr("selected", true);
                        $("#baseImponible").val(jsonObjDatos.baseImponible);
                        $("#txtasiento").val(jsonObjDatos.asiento);
                        $("#igv").val(jsonObjDatos.igv);
                        $("#total").val(jsonObjDatos.total);
                        $('#rucProveedor').prop('readonly', true);

                        var combos = ["tipodocumento"];
                        bloqueaCombos(combos);

                        $('#numeroDocumento').prop('readonly', true);
                        $('#addCuentas').prop('disabled', false);
                        $('#botonbuscar').prop('disabled', true);
                        
                        $("#btnEliminarProv").prop("disabled", false);
                        detalle_provision();
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

function detalle_provision(){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProvisionAction.do?operacion=detalle_provision",
        cache: false,
        data: {
            ruc: $("#rucProveedor").val(),
            tipodocumento: $("#tipodocumento").val(),
            documento: $("#numeroDocumento").val()

        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjDatos = data.objDatos;
                var jsonObjTotales = data.objTotales;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        alert('error');
                        break;
                    case "exito":
                        ListaItemProv();
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


function EliminaItem(indice){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProvisionAction.do?operacion=EliminaItem",
        cache: false,
        data: {
            item: indice
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjDatos = data.objDatos;
                var jsonObjTotales = data.objTotales;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        alert('error');
                        break;
                    case "exito":
                        ListaItemProv();
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



function EliminarAsientos(){
    if ($("#docSelectedDel").val()===""){
                 toastError("Debe seleccionar algun Item");
                return false;
            }
    $('#confirm-delete').modal('show');   
}