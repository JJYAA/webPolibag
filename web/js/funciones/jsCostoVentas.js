$(function () {
    setCalendariosDependientes({
        obj1: "#fechaIni",
        obj2: "#fechaFin"
    });
    
        $('#confirm-delete').on('show.bs.modal',function(){
                    $('.btn-ok').click(function(){
            showLoader();
            $("#operacion").val("conEliminarAsiento");
            $("#frm_generico").submit();
            });
        });    

    $("#btnConAsientoVentas").click(function () {              
            if ($("#docSelected").val()===""){
                toastError("Debe seleccionar algun Item");
                return false;
            }
            
            
            showLoader();
            $("#operacion").val("conAsientoCostos");
            $("#frm_generico").submit();

    });      
    
    $("#exportButton").click(function () {              
            if ($("#docSelectedXls").val()===""){
                toastError("Debe seleccionar algun Item");
                return false;
            }
            
            
           
            $("#operacion").val("exportarExcel_ventas");
            $("#frm_generico").submit();

    });      
      
    
});

/*
function excelVentas() {    
    var lenXls = $("#docSelectedXls").val().length;
    if (lenXls===0){
        alert("Debe seleccionar algun Item");
        return false;
    } else 
    {
       $("#operacion").val("exportarExcel_ventas");
       $("#asiento").val($("#asiento").val());
       $("#frm_generico").submit();           
    }          
}
*/





function muestraListaRegistroVentas() {
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin)  {
        alert('Deben pertenecer al mismo PERIODO');
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "costoVentaAction.do?operacion=listaCosto_ventas",
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
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#c_tablaCostodeVentas").html(jsonObjMsg.tabla);
                        iniDataTable({
                            tabla: "#tablaCostodeVentas",
                            filasXpagina: 30,
                            cajaBuscar: true,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
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



function excelClientes()
{    
    $("#operacion").val("exportarExcel_clientes");
    $("#asiento").val($("#asiento").val());
    $("#frm_generico").submit();          
}

function seleccinadosTodos()
{
    var texto="";
    $('#tablaCostodeVentas').DataTable().rows().iterator('row', function(context, index)
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
    $('#tablaCostodeVentas').DataTable().rows().iterator('row', function(context, index)
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

function EliminarAsientos(parm){
    if ($("#docSelectedDel").val()===""){
                 toastError("Debe seleccionar algun Item");
                return false;
            }
    $('#confirm-delete').modal('show');   
}





function seleccinadosDel() 
{
    var texto="";
    $('#tablaCostodeVentas').DataTable().rows().iterator('row', function(context, index)
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
    $('#tablaCostodeVentas').DataTable().rows().iterator('row', function(context, index)
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
    $('#tablaCostodeVentas').DataTable().rows().iterator('row', function(context, index)
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
    $('#tablaCostodeVentas').DataTable().rows().iterator('row', function(context, index)
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