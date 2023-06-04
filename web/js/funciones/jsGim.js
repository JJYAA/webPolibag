
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
;
var FilaGlobal ;


$(function () 
{
 
  $("#btnConfirmarDatos").click(function(){
            showLoader();       
            $("#operacion").val("actualizaDatos");
            $("#frm_generico").submit();          
    });

  $("#btnConfirmarRuta").click(function(){
            showLoader();       
            $("#operacion").val("grabaPath");
            $("#frm_generico").submit();         
    });


   $('#anho').change(function() {
        muestraSecuencia();
    });   
    
       $('#mes').change(function() {
        muestraSecuencia() ;
    });   
    
    


});



function muestraSecuencia(){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "SecuenciaAction.do?operacion=muestraSecuencia",
        cache: false,
        data: {
            anho: $("#anho").val(),
            mes: $("#mes").val()
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
                       
                        $("#c_tablaSecuencia").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaSecuencia",
                            filasXpagina: 30,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                        $(".numeros").numeric({
                            allowMinus: false,
                            allowThouSep: false,
                            maxDecimalPlaces: 2
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