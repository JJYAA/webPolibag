/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    //var combos = ["banco"];
    $("#txtPagarMulti").val(0);
    $("#item").val("0");
    $("#auxpagadoItem").val("0");
    setCalendariosDependientes({
        obj1: "#fechaIni"
    });
    setCalendariosDependientes({
        obj2: "#fechaFin"
    });

    setCalendariosDependientes({
        obj2: "#fechaOperacion"
    });
    
    
    setCalendariosDependientes({
        obj1: "#fechacontable"
    });    
    
    
//    $("#fechaIni").val("01/01/2023");
//    $("#fechaFin").val("31/01/2023");
//    
    $("#botonaddItem").html('Adicionar');
    $("#botonaddItem").css('background-color', 'White');
    $("#codigo").val("");
    $("#banco").val("");
    $("#deposito").val("");
 
    
    $("#bttnGrabar").prop("disabled", true);

});






function muestraListaCobranzas() {
    var ls_fecIni = $("#fechaIni").val().substring(6, 10) + '-' + $("#fechaIni").val().substring(3, 5) + '-' + $("#fechaIni").val().substring(0, 2);
    var ls_fecFin = $("#fechaFin").val().substring(6, 10) + '-' + $("#fechaFin").val().substring(3, 5) + '-' + $("#fechaFin").val().substring(0, 2);
    $("#txtCodCli").val("");
    $("#txtPagarMulti").val("");
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CobranzasNuevoAction.do?operacion=muestraListaCobranzas",
        cache: false,
        data: {
            fechaIni: ls_fecIni,
            fechaFin: ls_fecFin,
            codigo: $("#codigo").val()
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
                        $("#c_tablaCobranzas").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaCobranzas",
                            filasXpagina: 30,
                            cajaBuscar: true,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                        $(".decimal-2").numeric({
                           allowMinus: false,
                           allowThouSep: false,
                           maxDecimalPlaces: 2
                           });                        
                        $("#neto").val(jsonObjTotales.neto);
                        $("#igv").val(jsonObjTotales.igv);
                        $("#total").val(jsonObjTotales.total);
                        if ($("#codigo").val().length===0){
                            $("#bttnGrabar").prop("disabled", true);                        
                            $("#bttonBuscar").prop("disabled", false);                               
                        }
                        else
                        {
                            $("#bttnGrabar").prop("disabled", false);                        
                            $("#bttonBuscar").prop("disabled", true);                               
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

//function Pago(tipoDocumento,SerieDocumento,NumeroDocumento,CodCliente,total){
//    $("#botonaddItem").html('Adicionar');
//    $("#botonaddItem").css('background-color', 'White');          
//    $("#importe").val("");
//    $("#tipoDocumento").val(tipoDocumento);
//    $("#serieDocumento").val(SerieDocumento);
//    $("#numeroDocumento").val(NumeroDocumento);
//    $("#codigoCliente").val(CodCliente); 
//    
//    var totAux = $.nullFlo($("#txtPagarMulti").val());
//    if (totAux===0){
//        $("#total").val(total); 
//        $("#auxtotal").val(total);         
//    } else
//    {
//        $("#total").val(totAux); 
//        $("#auxtotal").val(totAux);           
//    }
//
//    $("#deposto").val("");
//    $("#importe").val("");
//    $("#item").val("0");
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: "CobranzasNuevoAction.do?operacion=cargaItemCobranza",
//        cache: false,
//        data: {
//            codigoCliente:$("#codigoCliente").val(),
//            tipoDocumento:$("#tipoDocumento").val(),
//            serieDocumento:$("#serieDocumento").val(),
//            numeroDocumento:$("#numeroDocumento").val()
//        },
//        success: function (data) {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjTabla = data.objTabla;
//                  var jsonObjTotales = data.objTotales;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":
//                        
//                        $("#confirm-pago").modal("show");
//                        muestraItemsDepositos();
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
//}


//function AdidonarPago(){
//    var importe = $.nullFlo($("#importe").val());
//    var ls_banco = $("#banco").val();
//    if (ls_banco===null) ls_banco="";
//    if  ($("#formapago").val()==="02") {
//        if (ls_banco==="") {
//            toastError("Debe seleccionar el BANCO");
//            return;            
//        }
//        if ($("#deposito").val()==="") {
//            toastError("Debe seleccionar el Deposito");
//            return;            
//        }                       
//    }
//    if ($("#fechaOperacion").val().length===0) 
//    {
//            toastError("Debe seleccionar la Fecha de OPERACION");
//            return;            
//    }      
//    if (importe===0) {
//        toastError("Debe ingresar el importe");
//        return;
//    }
//    var pagadoItem = $.nullFlo($("#auxpagadoItem").val());
//    
//    var pagado = $.nullFlo($("#auxpagado").val());
//    var total = $.nullFlo($("#auxtotal").val());
//
//    if ($("#item").val()==="0")
//    {
//        if ((pagado + importe)>total) {
//            toastError("El total supera al saldo, verifique");
//            return;
//        }
//    }
//    else
//    {
//        if ((pagado + importe - pagadoItem )>total) {
//        
//            toastError("El total supera al saldo, verifique");
//            return;            
//        }
//    }
//    
//    
//    
//    
//    
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: "CobranzasNuevoAction.do?operacion=adicionarItem",
//        cache: false,
//        data: {
//            banco: $("#banco").val(),
//            formapago: $("#formapago").val(),
//            importe :$("#importe").val(),
//            deposito :$("#deposito").val(),
//            moneda :$("#moneda").val(),
//            fechaOperacion:$("#fechaOperacion").val(),
//            codigoCliente:$("#codigoCliente").val(),
//            tipoDocumento:$("#tipoDocumento").val(),
//            serieDocumento:$("#serieDocumento").val(),
//            numeroDocumento:$("#numeroDocumento").val(),
//            item:$("#item").val()
//        },
//        success: function (data) {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjTabla = data.objTabla;
//                  var jsonObjTotales = data.objTotales;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":
//                        muestraItemsDepositos();
//                        $("#importe").val("");
//                        $("#banco").val("");
//                        $("#deposito").val("");
//                        $("#fechaOperacion").val("");
//                        $("#item").val("0");
//                        $("#auxpagadoItem").val("0");
//                        $("#botonaddItem").html('Adicionar');
//                        $("#botonaddItem").css('background-color', 'White');   
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
//}


function muestraItemsDepositos() {
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CobranzasNuevoAction.do?operacion=listaDocumentoCliente_Cobranzas",
        cache: false,
        data: {
            codigoCliente: $("#codigoCliente").val(),
            tipoDocumento: $("#tipoDocumento").val(),
            serieDocumento: $("#serieDocumento").val(),
            numeroDocumento: $("#numeroDocumento").val()
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
                        $("#pagado").val(jsonObjMsg.pagado);
                        $("#auxpagado").val(jsonObjMsg.pagado);
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

function GrabarCobranzaNew() {
    var importe = $.nullFlo($("#importe").val());   
    var saldo = 0;    
    var totalPagar = 0;
    var apagar = 0;
    var error = 0;
    var dseleccion = "";    
    $('#tablaCobranzas').DataTable().rows().iterator('row', function (context, index)
    {
        var node = $(this.row(index).node());
        var chk = node.find(".chkbx").eq(0);

        console.log(chk.prop("checked"));
        if (chk.prop("checked") === true)
        {
            saldo = $.nullFlo(node.find(".saldo").val());
            apagar = $.nullFlo(node.find(".pagar").val());
            if ( (apagar ) > saldo) {
                error = 1;
            } else
            {
                totalPagar = totalPagar + (apagar   );  // $.nullFlo(node.find(".pagar").val())  +    ;
                dseleccion = dseleccion + node.find(".valor").val()  +  apagar  + ",";    //node.find(".pagar").val() + ",";
            }
        }
    });
    totalPagar = $.nullFlo(totalPagar.toFixed(2));
    
    if (error === 1) 
    {
        toastError_lg("El importe ingresado supera al saldo del documento");
        return;

    }    
    $("#total").val(totalPagar);
    $("#dseleccion").val(dseleccion);
    if (totalPagar === 0) {
        toastError_lg("No existe documento por pagar");
        return;
    }     
    if (totalPagar !== importe) {
        toastError_lg("La suma de los documentos supera al Importe ingresado");
        return;
    }    
    
    
    var ls_banco = $("#banco").val();
    if (ls_banco === null)
        ls_banco = "";
    if ($("#formapago").val() === "02") {
        if (ls_banco === "") {
            toastError("Debe seleccionar el BANCO");
            return;
        }
        if ($("#deposito").val() === "") {
            toastError("Debe seleccionar el Deposito");
            return;
        }
    }
    if ($("#fechaOperacion").val().length === 0)
    {
        toastError("Debe seleccionar la Fecha de OPERACION");
        return;
    }
    if (importe === 0) {
        toastError("Debe ingresar el importe");
        return;
    }
    var pagadoItem = $.nullFlo($("#auxpagadoItem").val());

    var pagado = $.nullFlo($("#auxpagado").val());
    var total = $.nullFlo($("#auxtotal").val());
    $("#tipo").val($("#auxtipo").val());
    $("#forma").val($("#auxforma").val());
    $("#codigo").val("");
    $("#operacion").val("GrabarCobranza_01");
    $("#frm_generico").submit();
}




//function Eliminar(pIndice){
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: "CobranzasNuevoAction.do?operacion=EliminarItem",
//        cache: false,
//        data: {
//            codigoCliente:$("#codigoCliente").val(),
//            tipoDocumento:$("#tipoDocumento").val(),
//            serieDocumento:$("#serieDocumento").val(),
//            numeroDocumento:$("#numeroDocumento").val(),
//            indice:pIndice
//        },
//        success: function (data) {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjTabla = data.objTabla;
//                  var jsonObjTotales = data.objTotales;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":
//                        muestraItemsDepositos();
//                        $("#importe").val("");                        
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
//}


//function Editar(parm,parm2){
//    $("#formapago").val("");
//    $("#moneda").val("");
//    $("#banco").val("");
//    $("#fechaOperacion").val("");
//    $("#deposito").val("");
//    $("#importe").val("");
//    $("#auxpagadoItem").val(parm2);
//    $("#item").val(parm);
//    $.ajax({
//        type: "POST",
//        dataType: "JSON",
//        url: "CobranzasNuevoAction.do?operacion=EditarItem",
//        cache: false,
//        data: {
//            indice:parm
//        },
//        success: function (data) {
//            try {
//                var jsonObjMsg = data.objMensaje;
//                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
//                var msgError = $.trim(jsonObjMsg.msgError);
//                var jsonObjDatos = data.objDatos;
//                switch (tipoMsg) {
//                    case "relogin":
//                        window.location = "relogin.jsp";
//                        break;
//                    case "error":
//                        break;
//                    case "exito":   
//                        $("#formapago").val(jsonObjDatos.formapago);
//                        $("#moneda").val(jsonObjDatos.moneda);
//                        $("#banco").val(jsonObjDatos.banco);
//                        $("#fechaOperacion").val(jsonObjDatos.fechaoperacion);
//                        $("#deposito").val(jsonObjDatos.deposito);
//                        $("#importe").val(jsonObjDatos.importe);
//                        $("#botonaddItem").html('Editar');
//                        $("#botonaddItem").css('background-color', 'Red');                        
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
//}
function seleccinados() {
    var apagar = 0;
    $("#txttotal").val("");
    $('#tablaCobranzas').DataTable().rows().iterator('row', function (context, index)
    {
        var node = $(this.row(index).node());
        var chk = node.find(".chkbx").eq(0);
        if (chk.prop("checked") === true)
        {
            apagar = apagar + $.nullFlo(node.find(".pagar").val());
        }

    });
    $("#txttotal").val(apagar);
     $("#total").val(apagar);
}
//function seleccinados() 
//{
//    var pagar = 0;
//    var texto="";
//    $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
//    {
//        var node = $(this.row(index).node());         	
//        var chk = node.find(".chkbx").eq(0);
//        
//        console.log(chk.prop("checked"));
//        if (chk.prop("checked")===true)
//        {
//            var myArray = chk.attr("data-selected").split("|");
//            if ($("#txtCodCli").val().length===0)
//            {
//                $("#txtCodCli").val(myArray[0]);
//                texto += chk.attr("data-selected") + ",";
//                
//                pagar = pagar + $.nullFlo(myArray[4]);                       
//            }
//            else
//            {
//                if (myArray[0]!==$("#txtCodCli").val()){
//                    alert('Error en el Codigo del cliente');
//                    chk.prop("checked",false);
//                } else {
//                    texto += chk.attr("data-selected") + ",";
//                    pagar = pagar + $.nullFlo(myArray[4]);
//                }
//            }   
//        }
//        
//    });   
//    $("#dseleccion").val(texto);  
//    $("#txtPagarMulti").val(pagar);  
//    
//    if ($("#dseleccion").val().length===0){
//        $("#txtCodCli").val("");
//        $("#txtPagarMulti").val(0);  
//    }
//}

/*
 * 
 function seleccinados() 
 {
 alert('aa');
 var texto="";
 $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
 {
 var node = $(this.row(index).node());         	
 var chk = node.find(".chkbx").eq(0);
 
 console.log(chk.prop("checked"));
 if (chk.prop("checked")===true)
 {
 texto += chk.attr("data-selected") + ",";
 
 }
 
 });   
 alert('texto');
 $("#pipili").val(texto);  
 } 
 * 
 */
/*
 function PagoMultiple(){
 $.ajax({
 type: "POST",
 dataType: "JSON",
 url: "CobranzasNuevoAction.do?operacion=cargaItemCobranza",
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
 $("#c_tablaCobranzaItems").html(jsonObjTabla.tabla);
 $("#pagado").val(jsonObjMsg.pagado);
 $("#auxpagado").val(jsonObjMsg.pagado); 
 iniDataTable({
 tabla: "#tablaCobranzaItems",
 filasXpagina: 12,
 cajaBuscar: false,
 responsive: true,
 cboPaginas: false,
 infoNroReg: false
 });                        
 
 
 $("#confirm-pago_multi").modal("show");
 muestraItemsDepositos();
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
 * 
 */