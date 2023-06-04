
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
;
var FilaGlobal ;


$(function () 
{
    setCalendariosDependientes({
        obj2:"#fecha"
    });     
    
    $("#oc").focus();


});

function buscaOrdenCompra (){
    buscarOc();
}

function buscarOc(){
    if ($("#oc").val()==="") {
        return false;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "GimAction.do?operacion=buscaOC",
        cache: false,
        data: {
            oc: $("#oc").val()
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
                        toastError(msgError);
                        break;
                    case "exito":
                        $("#proveedor").val(jsonObjDatos.proveedor);
                        $("#oc").val(jsonObjDatos.ordencompra);                        
                        $("#fecha").val(jsonObjDatos.fecha);
                        $("#factura").val(jsonObjDatos.factura);
                        $("#razonsocial").val(jsonObjDatos.razonsocial);                        
                        $("#referencia").val(jsonObjDatos.referencia);  
                        $("#proveedor").prop("readonly", false);                       
                        $("#factura").prop("readonly", false);
                        $("#referencia").prop("readonly", false);
                        
                        $("#oc").prop("readonly", true);
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


function myCalculo(event){
var target = $( event.target );
    FilaGlobal = target.closest("tr");  
    FilaGlobal.find("#modificado").val(1);                       
}

function GrabarSecuencia(){
   
    var anho = "";
    var mes = "";
    var modifica = "";
    var tipo_comprobante = "";
    var contador = "";
    var cadena = "";
    
     
    $('#tablaSecuencia').DataTable().rows().iterator('row', function(context, index)
        {
            var node = $(this.row(index).node());         
            var celdas =   node.find("td"); //devolverá las celdas de una fila		
            modifica = node.find("#modificado" ).val(); 
            if (modifica==='1'){
                    anho = node.find("#anho").val();
                    mes=  node.find("#mes").val();
                    tipo_comprobante = node.find("#tipo_comprobante" ).val();
                    contador = node.find("#secuencia" ).val();
                    cadena = cadena + anho.trim() + '|' + mes.trim() + '|' + tipo_comprobante + '|' + contador  +  '|,';
                     
            }                                                
     });      
     cadena = cadena.substring(0,cadena.length - 1 ) ;
    $("#selected").val(cadena);

     if (cadena.length>0){
             showLoader();
            $("#operacion").val("grabaSecuencia");
            $("#frm_generico").submit();        
     }     
     
}




function actualizaOC(){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "GimAction.do?operacion=actualizaOC",
        cache: false,
        data: {
            oc: $("#oc").val(),
            ruc: $("#proveedor").val(),
            fecha: $("#fecha").val(),
            numerofactura: $("#factura").val(),
            referencia: $("#referencia").val()
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
                        toastError(msgError);
                        break;
                    case "exito":
                          toastError("Proceso termino con exito");
                          location.reload();
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


function EnterCodigo(e){
var key = checkKeyCode(e);
    if (key === 13) {
        buscarOc();
    }    
}