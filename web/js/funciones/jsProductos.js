$(function () 
{
      $('#chkvalor').prop('checked', true);
      muestraListaStock();
});

function muestraDownloadImg(){
     $("#myModalDownloadImg").modal("show");
}

function btnBuscarProductoItem(){
     $("#modalPreciosProductos").modal("show");
}

function verImagen(baseStr64){
    $("#imagen").attr('src', baseStr64);   
    $('#imagen').height('220px');
    $('#imagen').width('220px');
    $("#myModalImagen").modal("show");
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
                        $("#btnNuevoProducto").prop("disabled", false); 
                        $("#btnCancelarProducto").prop("disabled", false); 
                        $("#btnConfirmarProducto").prop("disabled", false);
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

function buscarProductoItemEnter(e){
    var key = checkKeyCode(e);
    if (key === 13) {
        buscarProductoItem();
    }    
}
function buscarProductoItem(){
    VerProducto($("#inputNumeroParte").val());
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
                            $('#selectActivo').prop('checked', true);                             
                        else
                            $('#selectActivo').prop('checked', false);
                        
                        if (jsonObjParte.masPrecios==="1")
                            $('#selectMasPrecios').prop('checked', true); 
                        else
                            $('#selectMasPrecios').prop('checked', false); 
                        
                        $("#cantidadCaja").val(jsonObjParte.cantidadCaja);
                        $("#duas").val(jsonObjParte.duas);
                        $("#familia").val(jsonObjParte.familia);
                        $("#clase").val(jsonObjParte.clase);
                        $("#grupo").val(jsonObjParte.grupo); 
                        
                        $("#precios01").val(jsonObjParte.precio01); 
                        $("#precios02").val(jsonObjParte.precio02); 
                        $("#precios03").val(jsonObjParte.precio03); 
                        $("#precios04").val(jsonObjParte.precio04); 
                        $("#precios05").val(jsonObjParte.precio05); 
                        
                        
                        
                        $("#costoPromedio").val(jsonObjParte.costoPromedio);     
                        $("#ultimoCosto").val(jsonObjParte.ultimoCosto);     
                        $("#stockTotal").val(jsonObjParte.total);  
                        $("#stockDisponible").val(jsonObjParte.disponible);
                        $("#stockSeguridad").val(jsonObjParte.seguridad);
                        $("#stockAlmacen").val(jsonObjParte.almacenes);
                        $("#stockTemporal").val(jsonObjParte.temporal);
                        $("#ultimoIngreso").val(jsonObjParte.ultimoIngreso);
                        $("#ultimaSalida").val(jsonObjParte.ultimaSalida);                                                 
                        $('.nav-tabs a[href="#home"]').tab('show')                                                
                        $("#descripcion").attr("readonly","readonly");
                        $("#inputNumeroParte").attr("readonly","readonly");
                        $("#btnFile").prop("disabled", false); 
                        $("#btnMasPrecios").prop("disabled", false);    
                        $("#btnCancelarProducto").prop("disabled", false);   
                        $("#btnbuscarProductoItem").prop("disabled", true); 
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
    $("#btnbuscarProductoItem").prop("disabled", false); 
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
    $("#btnCancelarProducto").prop("disabled", false); 
    $("#btnAdicionarProducto").prop("disabled", false); 
    $("#btnConfirmarProducto").prop("disabled", false); 
    $("#btnbuscarProductoItem").prop("disabled", false); 
    
//    $("#theFile").prop("disabled", true); 
//    $("#btnMasPrecios").prop("disabled", true);     
//    $("#inputNumeroParte").val("");
//    $("#numeroparteAux").val(""); 
//    $("#descripcion").val("");     
//    $("#vvpSoles").val("");     
//    $("#vvpDolar").val("");     
//    $("#Activo").val("");  
//    $("#masPrecios").val("");
//    $("#cantidadCaja").val("");
//    $("#duas").val("");
//    $("#familia").val("");
//    $("#clase").val("");
//    $("#grupo").val("");    
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


function fotoProducto(){  
    $.ajax({
        type: "POST",
        url: "ProductosAction.do?operacion=grabarFotoProducto",
        cache: false,                                                                                      
        processData: false,  // Important!
        contentType: false,   
        mimeType:"multipart/form-data",
        data:new FormData(document.getElementById("frm_generico")),
        success: function (data) 
        {
            $("#myModalDownloadImg").modal("hide");
        },
        beforeSend: function () {                                                      
            showLoader();
        },                                                                    
        complete: function () {
            hideLoader();
        },
        error: function (xhr, status, error) {
        }
    });
  
}

function GrabarProducto(){    
    var chkActivo = $("#selectActivo");
    if (chkActivo.is(":checked"))
         $("#activo").val("1");
    else 
        $("#activo").val("0");
    var chkMasPrecios = $("#selectMasPrecios");
    if (chkMasPrecios.is(":checked"))
         $("#masPrecios").val("1");
    else 
        $("#masPrecios").val("0");
    
    if ($("#inputNumeroParte").val().length===0){
         toastError("Se debe ingresar el codigo del producto");
         return false;
    }
    if ($("#descripcion").val().length===0){
         toastError("Se debe ingresar la Descripcion del producto");
         return false;
    } 
    $("#numeroparteAux").val($("#inputNumeroParte").val());
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