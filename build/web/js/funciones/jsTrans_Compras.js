$(function () {
    setCalendariosDependientes({
        obj1: "#fechaIni",
        obj2: "#fechaFin"
    });
});


function muestraListaRegistroCompras() {
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin)  {
        alert('Deben pertenecer al mismo PERIODO');
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "Trans_ComprasAction.do?operacion=lista_compras",
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            fechaFin: $("#fechaFin").val()
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
                        $("#exportCompras").prop("disabled", false);
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

function excel() {
            if ($("#docSelectedXls").val()===""){
                alert("Debe seleccionar algun Item para XLS");
                return false;
            } 
            else
            {
                $("#operacion").val("exportarExcel_compras");
                $("#asiento").val($("#asiento").val());
                $("#frm_generico").submit();    
            }
}

function excelProveedores() {
           // showLoader();
            $("#operacion").val("exportarExcel_proveedores");
            $("#asiento").val($("#asiento").val());
            $("#frm_generico").submit();    
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
function EliminarAsientosCompras(parm){
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

    $("#btnConAsientoCompras").click(function () {              
//            var ls_asiento = $("#asiento").val().length;
//            if (ls_asiento===0){
//                toastError("Debe ingresar el asiento");
//                return false;                
//            }
            if ($("#docSelected").val()===""){
                toastError("Debe seleccionar algun Item");
                return false;
            }
            
            
            showLoader();
            $("#operacion").val("conAsientoCompras");
            $("#frm_generico").submit();

    });
    
    
    
});



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