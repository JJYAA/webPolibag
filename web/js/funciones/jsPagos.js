/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 
$(function () {
    //var combos = ["banco"];
    $("#item").val("0");
    setCalendariosDependientes({
        obj1: "#fechaIni"        
    });
    setCalendariosDependientes({
        obj2:"#fechaFin"
    });    
    
    setCalendariosDependientes({
        obj2:"#fechaOperacion"
    });     
    
    
    
   $("#banco").val("");
   $("#deposito").val("");
  // bloqueaCombos(combos);     
 //  $("#deposito").prop("readonly", true);
   
   $('#formapago').change(function() {
        var combos = ["banco"]; // solo libera la moneda
        if ($(this).val() === "01") {
            $("#banco").val("");
            $("#deposito").val("");            
            //bloqueaCombos(combos);         
            //$("#deposito").prop("readonly", true);
            $("#banco").val("");
            $("#deposito").val("");            
        } else {
           //$("#deposito").prop("readonly", false);      
           //desbloqueaCombos(combos);     
        }   
    });   
   
});

function muestraListaPagos(){
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin) {
         toastError("Debe estar en el mismo PERIODO");
        return;
    }
        
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=muestraListaPagos",
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            fechaFin: $("#fechaFin").val(),
            codigo : ''
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
                        $("#c_tablaRegistroVenta").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaRegistroVenta",
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


function Pago(tipoDocumento,SerieDocumento,NumeroDocumento,CodCliente,tipocambio,total_sol,total_dol, moneda){
    $("#importe").val("");
    $("#tipoDocumento").val(tipoDocumento);
    $("#serieDocumento").val(SerieDocumento);
    $("#numeroDocumento").val(NumeroDocumento);
    $("#codigoCliente").val(CodCliente); 
    
    $("#total_sol").val(total_sol); 
    $("#total_dol").val(total_dol); 
    $("#txtMoneda").val(moneda); 
    $("#txtDocumentoProv").val(tipoDocumento + ' / ' + SerieDocumento + '-' + NumeroDocumento);
    $("#txtTCProv").val(tipocambio);

    $("#auxtotal_sol").val(total_sol); 
    $("#auxtotal_dol").val(total_dol); 
    $("#formapago").val("01");
    $("#banco").val("");
    $("#deposito").val("");
    $("#importe").val("");
    $("#fechaOperacion").val("");
    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=cargaItemPagos",
        cache: false,
        data: {
            codigoCliente:$("#codigoCliente").val(),
            tipoDocumento:$("#tipoDocumento").val(),
            serieDocumento:$("#serieDocumento").val(),
            numeroDocumento:$("#numeroDocumento").val()
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
                        
                        $("#confirm-pago").modal("show");
                        muestraItemsDepositos();
                        var combos = ["moneda"];
                        if (moneda==='MN'){
                            
                            bloqueaCombos(combos);                            
                        }
                        else {
                            desbloqueaCombos(combos);
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

function muestraItemsDepositos(){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=listaDocumentoProveedor_Pagos",
        cache: false,
        data: {
            codigoCliente: $("#codigoCliente").val(),
            tipoDocumento: $("#tipoDocumento").val(),
            serieDocumento :$("#serieDocumento").val(),
            numeroDocumento :$("#numeroDocumento").val()
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

                        $("#c_tablaCobranzaItems").html(jsonObjTabla.tabla);
                        $("#pagado_sol").val(jsonObjMsg.pagado_sol);
                        $("#pagado_dol").val(jsonObjMsg.pagado_dol);
                        $("#auxpagado_sol").val(jsonObjMsg.pagado_sol); 
                        $("#auxpagado_dol").val(jsonObjMsg.pagado_dol); 
                        
                        
                        var vSaldoSol = $.nullFlo($("#total_sol").val()) - $.nullFlo(jsonObjMsg.pagado_sol);
                        var vSaldoDol = $.nullFlo($("#total_dol").val()) - $.nullFlo(jsonObjMsg.pagado_dol);
                        $("#auxsaldo_sol").val(vSaldoSol.toFixed(2));
                        $("#auxsaldo_dol").val(vSaldoDol.toFixed(2));
                        iniDataTable({
                            tabla: "#tablaCobranzaItems",
                            filasXpagina: 12,
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


function AdicionarPago(){
    var ls_banco = $("#banco").val();
    if (ls_banco===null) ls_banco="";    
    var importe = $.nullFlo($("#importe").val());
    if  ($("#formapago").val()==="02") {
        if (ls_banco==="") {
            toastError("Debe seleccionar el BANCO");
            return;            
        }
        if ($("#deposito").val()==="") {
            toastError("Debe seleccionar el Deposito");
            return;            
        }                       
    }
    if ($("#fechaOperacion").val().length===0) 
    {
            toastError("Debe seleccionar la Fecha de OPERACION");
            return;            
    }      
    if (importe===0) {
        toastError("Debe ingresar el importe");
        return;
    }
    
    var pagadoItem = $.nullFlo($("#auxpagadoItem").val());
    
    var total_sol = $.nullFlo($("#auxtotal_sol").val());
    var total_dol = $.nullFlo($("#auxtotal_dol").val());
    
    var pagado_sol = $.nullFlo($("#auxpagado_sol").val());
    var pagado_dol = $.nullFlo($("#auxpagado_dol").val());
    
    if ($("item").val()==="0")
    {
        pagadoItem = 0;
    }
    
    if ($("#txtMoneda").val()==='MN'){
        if ((pagado_sol - pagadoItem) ===total_sol) {
            toastError("El documento esta cuadrado en MONEDA ORIGEN");
            return;
        }   
        else {
            if ((pagado_sol + importe - pagadoItem)>total_sol) {
                toastError("El total supera al saldo, verifique");
                return;
            }              
        }
    }
    else
    {
        if ((pagado_dol - pagadoItem)===total_dol) {
            toastError("El documento esta cuadrado en MONEDA ORIGEN");
            return;
        }          
        else 
        {
            if ($("#moneda").val()==='1'){
                if ((pagado_sol + importe )>total_sol) 
                {
                    toastError("El total supera al saldo Soles, verifique");
                    return;
                }
            }
            else
            {
                var sumatoria = pagado_dol + importe -  pagadoItem;
                sumatoria = sumatoria.toFixed(2);
                if (sumatoria>total_dol) {
                    toastError("El total supera al saldo Dolares, verifique");
                    return;
                }
            }
        }
    }    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=adicionarItem",
        cache: false,
        data: {
            banco: $("#banco").val(),
            formapago: $("#formapago").val(),
            importe :$("#importe").val(),
            deposito :$("#deposito").val(),
            moneda :$("#moneda").val(),
            fechaOperacion:$("#fechaOperacion").val(),
            codigoCliente:$("#codigoCliente").val(),
            tipoDocumento:$("#tipoDocumento").val(),
            serieDocumento:$("#serieDocumento").val(),
            numeroDocumento:$("#numeroDocumento").val(),
            item: $("#item").val(),
            monedaProv: $("#txtMoneda").val(),
            tcProv : $("#txtTCProv").val()
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
                        toastError(msgError);
                        break;
                    case "exito":
                        muestraItemsDepositos();
                        $("#importe").val("");
                        $("#banco").val("");
                        $("#deposito").val("");
                        $("#fechaOperacion").val("");
                        $("#botonaddItem").html('Adicionar');
                        $("#botonaddItem").css('background-color', 'White');   
                        $("#item").val("0");
                        $("#auxpagadoItem").val("0");
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

function Eliminar(pIndice){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=EliminarItem",
        cache: false,
        data: {
            codigoCliente:$("#codigoCliente").val(),
            tipoDocumento:$("#tipoDocumento").val(),
            serieDocumento:$("#serieDocumento").val(),
            numeroDocumento:$("#numeroDocumento").val(),
            indice:pIndice
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
                        muestraItemsDepositos();
                        $("#importe").val("");                        
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



function GrabarPagos(){    
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=GrabarPagos",
        cache: false,
        data: {
            codigoCliente: $("#codigoCliente").val(),
            tipoDocumento: $("#tipoDocumento").val(),
            serieDocumento :$("#serieDocumento").val(),
            numeroDocumento :$("#numeroDocumento").val(),
            monedaDoc:$("#txtMoneda").val(),
            totalDoc:$("#auxtotal_dol").val(),
            totalPagado:$("#auxpagado_dol").val()
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#confirm-pago").modal("hide"); 
                        muestraListaPagos();

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

function openModalHelpProveedores(){
    
}

function Editar(parm,parm2){
    $("#auxpagadoItem").val(parm2);
    $("#item").val(parm);
    $("#formapago").val("");
    $("#banco").val("");  
    $("#fechaOperacion").val("");    
    $("#deposito").val("");  
    $("#moneda").val("");      
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=EditarItem",
        cache: false,
        data: {
            indice:parm
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjDatos = data.objDatos;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":   
                        $("#formapago").val(jsonObjDatos.formapago);
                        $("#moneda").val(jsonObjDatos.moneda);
                        $("#banco").val(jsonObjDatos.banco);
                        $("#fechaOperacion").val(jsonObjDatos.fechaoperacion);
                        $("#deposito").val(jsonObjDatos.deposito);
                        $("#importe").val(jsonObjDatos.importe);
                        $("#botonaddItem").html('Editar');
                        $("#botonaddItem").css('background-color', 'Red');                        
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

