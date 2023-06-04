
$(function () {
    
    
    setCalendariosDependientes({
        obj1: "#fechaIni",
        obj2: "#fechaFin"
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





function muestraListaUtilidad() {
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin)  {
        alert('Deben pertenecer al mismo PERIODO');
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "UtilidadAction.do?operacion=listaUtilidad_ventas",
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
                        $("#c_tableUtilidad").html(jsonObjMsg.tabla);
                        iniDataTable({
                            tabla: "#tableUtilidad",
                            cajaBuscar: true,
                            responsive: true
                        });                        
//                        iniDataTable({
//                            tabla: "#tablaCostodeVentas",
//                            filasXpagina: 30,
//                            cajaBuscar: true,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });
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




function muestraListaUtilidadVendedor() {
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin)  {
        alert('Deben pertenecer al mismo PERIODO');
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "UtilidadAction.do?operacion=listaUtilidad_vendedor",
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            fechaFin: $("#fechaFin").val(),
            seleccion:$("#seleccion").val()
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
                        $("#c_table_tree-utilidad").html(jsonObjMsg.tabla);
//                        iniDataTable({
//                            tabla: "#table_tree-utilidad",
//                            filasXpagina: 30,
//                            cajaBuscar: true,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });
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

function visualiza(ptipo,pserie,pdocumento){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "UtilidadAction.do?operacion=listaVisualizaDocumento_detalle",
        cache: false,
        data: {
            tipodocumento: ptipo,
            serie: pserie,
            documento: pdocumento
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
                        
                        $("#detalle").modal("show");
                        $("#c_tableDetalleDocumento").html(jsonObjMsg.tabla);
                        iniDataTable({
                            tabla: "#tableDetalleDocumento",
                            filasXpagina: 35,
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

function cerrar(){
    $("#detalle").modal("hide");
}