$(function () 
{
 
    $("#frm_login").validate({
        rules: {
            usuario: "required",
            contrasena: "required",
            tienda: "required"
        },
        submitHandler: function (form) {
            //$("#overlay-txt span").text("Autenticando...");            
            $("#clave").blur();
            showLoader();
            form.submit();
        }
        /*invalidHandler: function(form, validator) {
         $("#frm_login").stop().animate({left: '-9px'}, 80, function() {
         $('#frm_login').stop().animate({'left': '9px'}, 80, function() {
         $('#frm_login').stop().animate({'left': '0px'}, 80, function() {
         });
         });
         });
         }*/
    });
    

});


$(function () 
{
   $("#empresa").on("change", function () {       
   
    $.ajax({
       
        type: "POST",
        dataType: "JSON",
        url: "LoginAuxiliarAction.do?operacion=pipili",
        cache: false,
        data: {
            empresa: $("#empresa").val()          
        },
        success: function (data) {
            try {
                var jsonObjMsg = data.objMensaje;
                var tipoMsg = $.trim(jsonObjMsg.tipoMsg);                
                var jsonObjTiendas = data.objTiendas;
                var tiendas = jsonObjTiendas.tiendas;                
                switch (tipoMsg) {
                    case "relogin":
                        window.location = "relogin.jsp";
                        break;
                    case "error":
                        break;
                    case "exito":
                        $("#tienda").empty();
                        $("#tienda").append('<option value="">-- SELECCIONE --</option>');
                        $.each(tiendas, function (key, val) {
                             $("#tienda").append('<option value="' + val.indice + '">' + val.descripcion + '</option>');
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
       
    });
        
    $("#btnConFacRep").click( function(){
       showLoader();                       // Move to a new location or you can do something else
        window.location.href = "LoginAuxiliarAction.do?operacion=validaIngreso&empresa=" + $("#empresa").val()+ "&tienda=" + $("#tienda").val() + " &contrasena=" + $("#clave").val() + "&usuario=" + $("#usuario").val() + "&proceso=01";
    });

});



