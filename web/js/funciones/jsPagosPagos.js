/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    var combos = ["banco"];
    
    setCalendariosDependientes({
        obj1: "#fechaIni"        
    });
    setCalendariosDependientes({
        obj2:"#fechaFin"
    });    
    
    setCalendariosDependientes({
        obj2:"#fechaOperacion"
    });     
    
    
    
  
   
});





function muestraListaPagos() {
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin) {
         toastError("Debe estar en el mismo PERIODO");
        return;
    }
        
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "PagosAction.do?operacion=muestraListaPagosRealizados",
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            fechaFin: $("#fechaFin").val(),
            codigo :$("#codigo").val()
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




function seleccinadosDel() 
{
    var texto="";
    $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
    {
        var node = $(this.row(index).node());         	
        var chk = node.find(".chkbxDel").eq(0);
        
        console.log(chk.prop("checked"));
        if (chk.prop("checked")===true)
        {
            texto += chk.attr("data-selected") + ",";
        }
    });   
    $('#docSelectedDel').val(texto);  

}

function seleccinadosElimina()
{    
    var texto="";
    $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
    {
        var node = $(this.row(index).node());         	
        var chk = node.find(".chkbxDel").eq(0);  
        if ($("#chkbxTodosDel").prop("checked")===true)
            chk.prop("checked",true);
        else            
            chk.prop("checked",false);        
        
        if (chk.prop("checked")===true)
        {
            texto += chk.attr("data-selected") + ",";
        }    
    });   
    texto = texto.substring(0, texto.length - 1);
    $('#docSelectedDel').val(texto);  
}



function seleccinadosTodos()
{
    var texto="";
    $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
    {
        var node = $(this.row(index).node());         	
        var chk = node.find(".chkbx").eq(0);  
        if ($("#chkbxTodos").prop("checked")===true)
            chk.prop("checked",true);
        else            
            chk.prop("checked",false);        
        
        if (chk.prop("checked")===true)
        {
            texto += chk.attr("data-selected") + ",";
        }    
    });   
    texto = texto.substring(0, texto.length - 1);
    $('#docSelected').val(texto);  
}

function seleccinados() 
{
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
    $('#docSelected').val(texto);  
}



function EliminarAsientos(){
    if ($("#docSelectedDel").val()===""){
                 toastError("Debe seleccionar algun Item");
                return false;
            }
    $('#confirm-delete').modal('show');   
}



$(function () {

        $('#confirm-delete').on('show.bs.modal',function(){
                    $('.btn-ok').click(function(){
                               showLoader();
            $("#operacion").val("conEliminarAsiento");
            $("#frm_generico").submit();
            });
        });    

        $("#btnConAsientoPagos").click(function () {              
            if ($("#docSelected").val()===""){
                toastError("Debe seleccionar algun Item");
                return false;
            }
            
            
            showLoader();
            $("#operacion").val("conAsientoPagos");
            $("#frm_generico").submit();

        });  
        
        $("#exportButton").click(function () {              
            if ($("#docSelectedXls").val()===""){
                toastError("Debe seleccionar algun Item");
                return false;
            }
            
            
            
            $("#operacion").val("exportarExcel_Pagos");
            $("#asiento").val($("#asiento").val());
            $("#frm_generico").submit(); 

        });          
        
        
        
});

function seleccinadosTodosXls()
{
    var texto="";
    $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
    {
        var node = $(this.row(index).node());         	
        var chk = node.find(".chkbxXls").eq(0);  
        if ($("#chkbxTodosXls").prop("checked")===true)
            chk.prop("checked",true);
        else            
            chk.prop("checked",false);        
        
        if (chk.prop("checked")===true)
        {
            texto += chk.attr("data-selected") + ",";
        }    
    });   
    texto = texto.substring(0, texto.length - 1);
   
    $('#docSelectedXls').val(texto);  
}

function seleccinadosXls() 
{
    var texto="";
    $('#tablaRegistroVenta').DataTable().rows().iterator('row', function(context, index)
    {
        var node = $(this.row(index).node());         	
        var chk = node.find(".chkbxXls").eq(0);
        
        console.log(chk.prop("checked"));
        if (chk.prop("checked")===true)
        {
            texto += chk.attr("data-selected") + ",";
        }
    });   
    $('#docSelectedXls').val(texto);  
}


