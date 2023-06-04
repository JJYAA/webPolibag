


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
    
    
    
   $("#banco").val("");
   $("#deposito").val("");
   bloqueaCombos(combos);     
   $("#deposito").prop("readonly", true);
   
   $('#formapago').change(function() {
        var combos = ["banco"]; // solo libera la moneda
        if ($(this).val() === "01") {
            $("#banco").val("");
            $("#deposito").val("");            
            bloqueaCombos(combos);         
            $("#deposito").prop("readonly", true);
            $("#banco").val("");
            $("#deposito").val("");            
        } else {
            $("#deposito").prop("readonly", false);      
           desbloqueaCombos(combos);     
        }   
    });   
   
});


function helpClientesCliente(){
    
    openModalHelpClientes();

}

function openModalHelpClientes(){   //help proveedores
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CobranzasAction.do?operacion=lista_Clientes",
        cache: false,
        data: {
            txtcodigo: $("#txtbuscar").val()
        },
        success: function (data)
        {
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
                        $("#c_tablaClientes").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaClientes",
                            filasXpagina: 15,
                            cajaBuscar: false,
                            responsive: true,
                            cboPaginas: false,
                            infoNroReg: false
                        });
                         $("#modalClientes").modal("show");
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





function muestraListaCobranzas() {
    var ls_fecIni = $("#fechaIni").val().substring(6,9) + $("#fechaIni").val().substring(3,5) ;
    var ls_fecFin = $("#fechaFin").val().substring(6,9) + $("#fechaFin").val().substring(3,5) ;
    if (ls_fecIni!==ls_fecFin) {
         toastError("Debe estar en el mismo PERIODO");
        return;
    }
        
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CobranzasAction.do?operacion=muestraListaPagosRealizados",
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



$(function () {

        $('#confirm-delete').on('show.bs.modal',function(){
                    $('.btn-ok').click(function(){
                               showLoader();
            $("#operacion").val("conEliminarAsiento");
            $("#frm_generico").submit();
            });
        });    

        $("#btnConAsientoCobranza").click(function () {              
            if ($("#docSelected").val()===""){
                toastError("Debe seleccionar algun Item");
                return false;
            }
            
            
            showLoader();
            $("#operacion").val("conAsientoCobranza");
            $("#frm_generico").submit();

    });   
});



function EliminarAsientos(){
    if ($("#docSelectedDel").val()===""){
                 toastError("Debe seleccionar algun Item");
                return false;
            }
    $('#confirm-delete').modal('show');   
}


function excelCobranzas() {   
    $("#operacion").val("exportarExcel_cobranzas");
    $("#asiento").val($("#asiento").val());
    $("#frm_generico").submit();          
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



function muestraListaDepositos(){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CobranzasAction.do?operacion=muestraListaDepositos",
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
                        $("#c_tablaDepositos").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaDepositos",
                            filasXpagina: 30,
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



function excelDepositos() {
    
           // showLoader();
           
            $("#operacion").val("muestraExcelDepositos");
            $("#frm_generico").submit();    
}





function muestraListaCorte(){
   
    var tipoReporte="";
    if ($("#auxproceso").val()==="T")
        tipoReporte="CobranzasAction.do?operacion=muestraListaCorte";
    else
        tipoReporte="CobranzasAction.do?operacion=muestraListaCorteDetalle";

    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: tipoReporte,
        cache: false,
        data: {
            fechaIni: $("#fechaIni").val(),
            fechaFin: $("#fechaFin").val(),
            tipo: $("#auxtipo").val(),
            codigo: $("#codigo").val() ,
            forma:$("#auxForma").val()
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
                        $("#c_tablaDepositos").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaDepositos",
                            filasXpagina: 30,
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



function excelConCorte() {
   
           // showLoader();
           $("#tipo").val($("#auxtipo").val());
           $("#forma").val($("#auxforma").val());
        //   $("#codigo").val("");
            if ($("#auxproceso").val()==="T") {
                $("#operacion").val("muestraListaCorte_excel");
            }
            else {
                $("#operacion").val("muestraListaCorteDetalle_Excel");
            }
                
            
            $("#frm_generico").submit();    
}


function eligeCliente(cod){
    $("#codigo").val(cod);
    $("#modalClientes").modal("hide");    
}

function buscasBottonClientes(){
    openModalHelpClientes();    
}





function buscarporcodigoCliente(e){
 var key = checkKeyCode(e);
    if (key === 13) {
        openModalHelpClientes();
    }    
   
}