$(document).ready(function() {

    $('#chkPor').change(function() {
        if($(this).is(":checked")) {           
            $(".c_cliProducto").hide();
            $(".c_cliFamilia").show();
        }
        else
        {
            $(".c_cliFamilia").hide();
            $(".c_cliProducto").show();
        }      
    });
    
    $(".textos").keypress(function(){
      alert('1111');
    });
            
});


function muestraCatalogo(){
    var filtro="0";
     if ($("#chkPor").is(":checked")) 
         filtro="1";
     else
         filtro="0";
    
        $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CatalogoAction.do?operacion=muestraCatalogo",
        cache: false,
        data: {
            filtro:filtro,
            codigoProducto:$("#codigoProducto").val(),
            familia:$("#familia").val(),
            clase:$("#clase").val(),
            grupo:$("#grupo").val()            
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
                        toastError("No Existe informaci&oacute;n que procesar");
                        break;
                    case "exito":
                        
                        $("#c_tablaListaCatalogo").html(jsonObjTabla.tabla);
//                        iniDataTable({
//                            tabla: "#tablaListaItem",
//                            filasXpagina: -1,
//                            cajaBuscar: true,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });

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

function EliminarItem(valor){
        $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CatalogoAction.do?operacion=EliminarItem",
        cache: false,
        data: {
            numeroParte: valor
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
                var jsonObjTotal = data.objTotal;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#total").val(jsonObjTotal.total);
                        $("#c_tablaListaItem").html(jsonObjTabla.tabla);
//                        iniDataTable({
//                            tabla: "#tablaListaItem",
//                            filasXpagina: -1,
//                            cajaBuscar: true,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });

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
function AgregarItemdd(parm,parm2){
    alert(parm);
    alert(parm2);
} 
function AgregarItem(parm,parm2){
        $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "CatalogoAction.do?operacion=AgregarItem",
        cache: false,
        data: {
            numeroParte: parm,
            precio:parm2
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var msgError = $.trim(jsonObjMsg.msgError);
                var jsonObjTabla = data.objTabla;
                var jsonObjTotal = data.objTotal;
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#total").val(jsonObjTotal.total);
                        $("#c_tablaListaItem").html(jsonObjTabla.tabla);
                        
                        var Total = $.nullNum(jsonObjTotal.total);
                       
                        if (Total>0)
                            $("#btnConFacRep").prop("disabled", true);
                        else
                            $("#btnConFacRep").prop("disabled", true);
                            
//                        iniDataTable({
//                            tabla: "#tablaListaItem",
//                            filasXpagina: -1,
//                            cajaBuscar: true,
//                            responsive: true,
//                            cboPaginas: false,
//                            infoNroReg: false
//                        });

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


function btnBuscarProductoItem(){
     $("#modalPreciosProductos").modal("show");
}

function muestraListaStock() {
    var valor="1";
    var c = $("#chkvalor");
    if (c.is(":checked")) {
         valor="1";
    } else {
        valor="0";
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProductosAction.do?operacion=muestraStockProductos",
        cache: false,
        data: {
            numeroParte: $("#numeroParte").val(),
            chkvalor:valor
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
                        $("#c_tablaPartesProductos").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaPartesProductos",
                            filasXpagina: -1,
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

function VerProducto(parm){
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ProductosAction.do?operacion=VerProducto",
        cache: false,
        data: {
            numeroParte: parm
        },
        success: function (data) 
        {
            try 
            {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);
                var jsonObjParte = data.objParte;
                switch (tipoMsg) 
                {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#inputNumeroParte").val(jsonObjParte.producto);
                        $("#numeroparteAux").val(jsonObjParte.producto); 
                        $("#descripcion").val(jsonObjParte.descripcion);     
                        $("#vvpSoles").val(jsonObjParte.vvpSoles);     
                        $("#vvpDolar").val(jsonObjParte.vvpDolar);                             
                        if (jsonObjParte.Activo==="1")
                            $("#chkActivo").prop('checked', true);
                        else
                            $("#chkActivo").prop('checked', true);
                        
                        if (jsonObjParte.masPrecios==="1")
                            $("#chkMasPrecios").prop('checked', true);
                        else
                            $("#chkMasPrecios").prop('checked', true);
                        
                        $("#cantidadCaja").val(jsonObjParte.cantidadCaja);
                        $("#duas").val(jsonObjParte.duas);
                        $("#familia").val(jsonObjParte.familia);
                        $("#clase").val(jsonObjParte.clase);
                        $("#grupo").val(jsonObjParte.grupo); 
                        $("#btnCancelarProducto").prop("disabled", false); 
                        $("#btnAdicionarProducto").prop("disabled", false); 
                        $("#btnConfirmarProducto").prop("disabled", false);
                        $("#descripcion").attr("readonly","readonly");
                        $("#inputNumeroParte").attr("readonly","readonly");
                        $("#theFile").prop("disabled", false); 
                        $("#btnMasPrecios").prop("disabled", false);                        
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

function AdicionarProducto(){
    $("#btnAdicionarProducto").prop("disabled", true); 
    $("#descripcion").removeAttr("readonly");
    $("#inputNumeroParte").removeAttr("readonly");
    $("#inputNumeroParte").val("");
    $("#numeroparteAux").val(""); 
    $("#descripcion").val("");     
    $("#vvpSoles").val("");     
    $("#vvpDolar").val("");     
    $("#Activo").val("");  
    $("#masPrecios").val("");
    $("#cantidadCaja").val("");
    $("#duas").val("");
    $("#familia").val("");
    $("#clase").val("");
    $("#grupo").val("");    
    $("#inputNumeroParte").focus();    
}
function CancelarProducto(){
    $("#btnCancelarProducto").prop("disabled", true); 
    $("#btnAdicionarProducto").prop("disabled", true); 
    $("#btnConfirmarProducto").prop("disabled", true); 
    $("#btnbuscarProductoItem").prop("disabled", false); 

    
    $("#theFile").prop("disabled", true); 
    $("#btnMasPrecios").prop("disabled", true);     
    $("#inputNumeroParte").val("");
    $("#numeroparteAux").val(""); 
    $("#descripcion").val("");     
    $("#vvpSoles").val("");     
    $("#vvpDolar").val("");     
    $("#Activo").val("");  
    $("#masPrecios").val("");
    $("#cantidadCaja").val("");
    $("#duas").val("");
    $("#familia").val("");
    $("#clase").val("");
    $("#grupo").val("");    
}

function buscarProductoEnter(e) {
    var key = checkKeyCode(e);
    if (key === 13) {
        muestraListaStock();
    }
}

function buscarProductoBoton() {
    muestraListaStock();
}


function grabarFotoProducto22222(){  
    $.ajax({
        type: "POST",
        cache: false,
        processData: false, // para que jQuery no procese la data
        contentType: "text", // para que jQuery no setee el contentType      
        url: "ProductosAction.do?operacion=grabarFotoProducto",
        data:new FormData(document.getElementById("frm_generico")),
        success: function (data) 
        {       
             var resultado = $.trim(data);
            try 
            {
                alert(resultado);
            } 
            catch (error) {
                toastError(data);
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


function grabarFotoProducto(){  
    $.ajax({
        type: "POST",
        url: "ProductosAction.do?operacion=grabarFotoProducto",
        cache: false,
        processData: false,  // Important!
        contentType: false,        
        data:new FormData(document.getElementById("frm_generico")),
        success: function (data) 
        {
            muestraListaStock();
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

function GrabarProducto(){    
    if ($("#inputNumeroParte").val().length===0){
         toastError("Se debe ingresar el codigo del producto");
         return false;
    }
    if ($("#descripcion").val().length===0){
         toastError("Se debe ingresar la Descripcion del producto");
         return false;
    } 
    $.ajax({
        type: "POST",
        url: "ProductosAction.do?operacion=GrabarProducto",
        cache: false,
        processData: false,  // Important!
        contentType: false,        
        data:new FormData(document.getElementById("frm_generico")),
        success: function (data) 
        {
            muestraListaStock();
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