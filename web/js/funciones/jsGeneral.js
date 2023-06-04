/* 
 * Document : jsGeneral.js
 * Version  : 1.2
 * Author   : acabello (adanyc)
 * Created  : 03/2014
 * Updated  : 06/2017
 */

$(function () {
    // BEGIN descomentar en produccion ---------------------------------------//  
    //noBack();
    //noContextMenu();
    //noBackKey();
    //noReload();
    // END descomentar en produccion   ---------------------------------------//

    //scrollUp();
    notPaste(".notpaste");
    //showLoader();

    // Datatables ------------------------------------------------------------//
    /*if (jQuery().dataTable) {
     hasDataTable({tabla: ".data-table"});
     }*/

    // Footables -------------------------------------------------------------//
    if (jQuery().footable) {
        $('.footable').footable();
    }

    // Datepicker ------------------------------------------------------------//
    hasDatePicker(".date-picker");
    hasDatePickerMin(".date-picker-min");
    hasDatePickerMax(".date-picker-max");
    hasDatePickerDefaultDate(".date-picker-now");
    hasDatePickerDefaultDateMin(".date-picker-now-min");

    // Select2 ---------------------------------------------------------------//
    if (jQuery().select2) {
        hasSelect2Simple(".select2-simple");
    }

    // SelectPicker ----------------------------------------------------------//
    //if (jQuery().selectpicker) {
    //hasSelectPicker(".spicker", true);     // CON caja de busqueda    
    //hasSelectPicker(".spicker-ns", false);  // SIN caja de busqueda
    //}

    // File input ------------------------------------------------------------//
    if (jQuery().bootstrapFileInput) {
        $('.file-input').bootstrapFileInput();
    }

    // Deshabilita links -----------------------------------------------------//
    $('a[href^=\\#]').click(function (e) {
        e.preventDefault();
    });

    // Muestra tooltip -------------------------------------------------------//
    $('.show-tooltip').tooltip({container: 'body', delay: {show: 200}});

    // Quita cursor en inputs readonly ---------------------------------------//
    /*$("input,textarea").focus(function () {
     if ($(this).attr("readonly") !== undefined) {
     $(this).blur();
     }
     });*/
    $(document).on('focus', 'input[readonly="readonly"],input[readonly],textarea[readonly="readonly"],textarea[readonly]', function () {
        this.blur();
    });

    // Mostrar gif loading cuando un elemento con la clase es presionado -----//
    /*$(".goloading").on("click", function () {
     showLoader();
     });*/

    $(".goloading").on("click", function (e) {
        showLoader();
        e.preventDefault();
        var goTo = this.getAttribute("href");
        setTimeout(function () {
            window.location = goTo;
        }, 200);
    });

    // Numeric plugin --------------------------------------------------------//
    //if (jQuery().numeric_input) {

    /*$(".decimal").numeric_input({
     decimal: '.'
     });*/
    /*$(".decimal-2").numeric_input({
     decimal: '.',
     numberOfDecimals: 2
     });*/
    /*$(".decimal-3").numeric_input({
     decimal: '.',
     numberOfDecimals: 3
     });*/

    $(".decimal").numeric({
        allowMinus: false,
        allowThouSep: false
    });
    $(".decimal-2").numeric({
        allowMinus: false,
        allowThouSep: false,
        maxDecimalPlaces: 2
    });
    $(".decimal-3").numeric({
        allowMinus: false,
        allowThouSep: false,
        maxDecimalPlaces: 3
    });

    /*$(".decimal-neg").numeric_input({
     decimal: '.',
     allowNegative: true
     });
     
     $(".decimal-neg-2").numeric_input({
     decimal: '.',
     allowNegative: true,
     numberOfDecimals: 2
     });
     
     $(".decimal-neg-3").numeric_input({
     decimal: '.',
     allowNegative: true,
     numberOfDecimals: 3
     });*/

    $(".decimal-neg").numeric({
        allowMinus: true,
        allowThouSep: false
    });
    $(".decimal-neg-2").numeric({
        allowMinus: true,
        allowThouSep: false,
        maxDecimalPlaces: 2
    });
    $(".decimal-neg-3").numeric({
        allowMinus: true,
        allowThouSep: false,
        maxDecimalPlaces: 3
    });

    //}

    // Autotab plugin --------------------------------------------------------//
    if (jQuery().mask) {
        $('.numeros').mask('0#');  // Compatible con navegadores moviles
        //$('.numeros').autotab('filter', 'number');
        //$('.textos').autotab('filter', 'text');
        //$('.letras').autotab('filter', 'alpha');
        $('.letras').mask('S', {translation: {'S': {pattern: /[a-zA-Z\s]/, recursive: true}}}); // Compatible con navegadores moviles
        $('.alpha').mask('A', {translation: {'A': {pattern: /[A-Za-z0-9\s]/, recursive: true}}}); // Compatible con navegadores moviles
        //$('.upper').autotab('filter', {format: 'all', uppercase: true});
        //$('.lower').autotab('filter', {format: 'all', lowercase: true});
    }

    // Textos en Mayuscula, compatible con Moviles (11-2017)
    $("input.upper[type=text],input.upper[type=password],textarea.upper").each(function () {
        this.style.textTransform = "uppercase";
        if (this.value.length > 0) {
            this.value = this.value.toUpperCase();
        }
        this.addEventListener("keyup", function () {
            this.value = this.value.toUpperCase();
        });
    });
    $("input.lower[type=text],input.lower[type=password],textarea.lower").each(function () {
        this.style.textTransform = "lowercase";
        if (this.value.length > 0) {
            this.value = this.value.toLowerCase();
        }
        this.addEventListener("keyup", function () {
            this.value = this.value.toLowerCase();
        });
    });

    // Mega Menu -------------------------------------------------------------//
//    $(document).on('click', '.yamm .dropdown-menu', function (e) {
//        e.stopPropagation();
//    });

    // Multiselect plugin ----------------------------------------------------//   
    if (jQuery().multiselect && !esMobileDevice()) {
        $('.multiple').multiselect({
            includeSelectAllOption: true,
            enableFiltering: false,
            buttonWidth: '100%',
            maxHeight: 300,
            buttonClass: "btn btn-default",
            numberDisplayed: 1,
            selectAllText: 'TODOS',
            allSelectedText: 'TODOS',
            nonSelectedText: 'NINGUNO',
            nSelectedText: 'ELEGIDOS'
        });
        $('.multiple-sm').multiselect({
            includeSelectAllOption: true,
            enableFiltering: false,
            buttonWidth: '100%',
            maxHeight: 300,
            buttonClass: "btn btn-default btn-sm",
            numberDisplayed: 1,
            selectAllText: 'TODOS',
            allSelectedText: 'TODOS',
            nonSelectedText: 'NINGUNO',
            nSelectedText: 'ELEGIDOS'
        });
    } else {
        $('.multiple-sm').css({
            "height": "30px",
            "line-height": "1.5"
        });
    }

    // Textarea mayusculas/minusculas ----------------------------------------//
    /*$('textarea.upper').each(function () {
     $(this).css("text-transform", "uppercase");
     $(this).on("keyup", function () {
     $(this).val($(this).val().toUpperCase());
     });
     });
     
     $('textarea.lower').each(function () {
     $(this).css("text-transform", "lowercase");
     $(this).on("keyup", function () {
     $(this).val($(this).val().toLowerCase());
     });
     });*/

    // Habilita maxlenght para textareas -------------------------------------//
    // (La forma de utilizar es: encerrar en texarea en un div con la 
    // clase 'textarea-maxlength-12', donde el ultimo digito es el maxlength
    // que deseamos que tenga el textarea)
    /*$("div[class^='textarea-maxlength-']").keypress(function (event) {
     if (!event)
     event = window.event;
     var key = event.which || event.keyCode;
     if (key >= 33 || key === 13 || key === 32) {
     var array = $(this).attr("class").split("-");
     if (array.length > 2) {
     var maxLength = array[2];
     var length = $(this).find("textarea").val().length;
     if (length >= maxLength) {
     event.preventDefault();
     }
     }
     }
     });*/

    // DateTime Picker -------------------------------------------------------//
    //hasDateTimePicker(".datetime-picker");
    //hasDateTimePickerDefaultDateTime(".datetime-picker-now");
    //hasDateTimePickerMinute30(".datetime-picker-minute-30");
    hasTimePicker(".time-picker");
    //hasTimePickerDefaultTime(".time-picker-now");
    //hasTimePickerMinute30(".time-picker-minute-30");

    // Plugin Toastr ---------------------------------------------------------//
//    toastr.options = {
//        "closeButton": true,
//        "debug": false,
//        "newestOnTop": false,
//        "progressBar": false,
//        "positionClass": "toast-top-center",
//        //"preventDuplicates": true,
//        "onclick": null,
//        "showDuration": "300",
//        "hideDuration": "1000",
//        "timeOut": "5000",
//        "extendedTimeOut": "1000",
//        /*
//         "showDuration": "300",
//         "hideDuration": "800",
//         "timeOut": "2500",
//         "extendedTimeOut": "1500", 
//         */
//        "showEasing": "swing",
//        "hideEasing": "linear",
//        "showMethod": "fadeIn",
//        "hideMethod": "fadeOut"
//    };
});

// Reinicia el contador del tiempo cuando hay peticiones 
$(document).ajaxStart(function () {
    if (typeof iniTimerGlobal === 'function') {
        iniTimerGlobal();
    }
});

// Loader --------------------------------------------------------------------//
//$(window).load(function () {
//hideLoader();
//});

// Muestra mensaje cargando
function showLoader() {
    //$('body').css({'overflow': 'hidden'});  
    /*$.blockUI({
     css: {
     border: '1px solid #cccccc',
     padding: '10px',
     backgroundColor: '#fff',
     '-webkit-border-radius': '8px',
     '-moz-border-radius': '8px',
     'border-radius': '8px',
     opacity: 0.93,
     color: '#333',
     width: '176px',
     top: '50%',
     left: '50%',
     'margin-top': '-26px',
     'margin-left': '-98px',
     'font-family': 'Arial,Helvetica',
     'font-size': '12px',
     '-webkit-box-shadow': '0 0 8px -2px rgba(153, 153, 153, 0.85)',
     '-moz-box-shadow': '0 0 8px -2px rgba(153, 153, 153, 0.85)',
     'box-shadow': '0 0 8px -2px rgba(153, 153, 153, 0.85)'
     },
     overlayCSS: {
     backgroundColor: 'transparent'
     },
     fadeIn: 150,
     fadeOut: 150,
     message: $('#blockui').html(),
     baseZ: 99999
     });*/
    /*$.LoadingOverlaySetup({
     color: "rgba(170, 170, 170, 0.3)",
     maxSize: "60px"
     });
     $.LoadingOverlay("show");*/
    $('body').addClass("loading");
}

function hideLoader() {
    //$('body').css({'overflow': 'visible'});
    //$.unblockUI();
    // $.LoadingOverlay("hide");
    $('body').removeClass("loading");
}

// Deshabilita F5 y ctrl+r en navegadores
function noReload() {
    document.onkeydown = function (e) {
        var key = checkKeyCode(e);
        if ((navigator.appVersion.indexOf('MSIE') !== -1)) {
            if (key === 116) {
                event.keyCode = 0;
                event.returnValue = false;
                return false;
            }
        } else if (key === 116) {
            e.returnValue = false;
            e.keyCode = 0;
            return false;
        }
    };

    $(document).bind('keypress keydown keyup', function (e) {
        if (e.which === 82 && e.ctrlKey) {
            return false;
        }
    });
}

// KeyCodes ------------------------------------------------------------------//
// (Devuelve el keycode IE7,IE8... (Cross-Browser)
function checkKeyCode(e) {
    if (!e)
        e = window.event;
    var kCd = e.which || e.keyCode;
    return kCd;
}

// Retroceder ----------------------------------------------------------------//
// (Deshabilita back key si el cursor no esta sobre algun input)
function noBackKey() {
    $(document).keydown(function (e) {
        var obj = $(document.activeElement).is("input:focus, textarea:focus");
        if (e.keyCode === 8 && !obj) {
            return false;
        }
    });
}

// (Deshabilita la accion de retroceder de los navegadores)
function noBack() {
    window.history.forward();
    window.onpageshow = function (evt) {
        if (evt.persisted)
            window.onload();
    };
    window.history.forward();
    window.onunload = function () {
        void(0);
    };
}

// Menu contextual -----------------------------------------------------------//
// (Deshabilita el menu contextual en los navegadores)
function noContextMenu() {
    if (document.addEventListener) {
        document.addEventListener('contextmenu', function (e) {
            e.preventDefault();
        }, false);
    } else {
        document.attachEvent('oncontextmenu', function () {
            window.event.returnValue = false;
        });
    }
}

// ScrollUp ------------------------------------------------------------------//
// (Crea un boton para subir al top de la pagina)
//function scrollUp() {
//    $(window).scroll(function () {
//        if ($(this).scrollTop() > 100) {
//            $('#btn-scrollup').fadeIn();
//        } else {
//            $('#btn-scrollup').fadeOut();
//        }
//    });
//    $('#btn-scrollup').click(function () {
//        $("html, body").animate({scrollTop: 0}, 600);
//        return false;
//    });
//
//    if (esMobileDevice()) {
//        $("#btn-scrollup").remove();
//    }
//}

// Devuelve la fecha actual en formato dd/mm/yyyy
function fechaHoy(obj) {
    var oDate = new Date();
    var ndia = oDate.getDate();
    var nmes = oDate.getMonth() + 1;
    var anio = oDate.getFullYear();
    ndia = (ndia < 10) ? "0" + ndia : ndia;
    nmes = (nmes < 10) ? "0" + nmes : nmes;
    var fechaHoy = ndia + "/" + nmes + "/" + anio;
    $(obj).val(fechaHoy);
}

// Limpieza de formulario ----------------------------------------------------//
function limpiarForm(obj) {
    $(':input', $(obj)).each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase();
        if (type === 'text' || type === 'password' || tag === 'textarea' || type === 'hidden') {
            this.value = '';
        } else if (type === 'checkbox' || type === 'radio') {
            this.checked = false;
        } else if (tag === 'select') {
            this.selectedIndex = 0; //-1
        }
    });
}

// Deshabilita copiar y pegar en un control de formulario --------------------//
function notPaste(obj) {
    $(obj).keydown(function (event) {
        var teclasNoPermitidas = new Array('c', 'x', 'v');
        var keyCode = (event.keyCode) ? event.keyCode : event.which;
        var esCtrl;
        esCtrl = event.ctrlKey;
        if (esCtrl) {
            for (var i = 0; i < teclasNoPermitidas.length; i++) {
                if (teclasNoPermitidas[i] === String.fromCharCode(keyCode).toLowerCase()) {
                    return false;
                }
            }
        }
        return true;
    });

    $(obj).bind('contextmenu', function (e) {
        return false;
    });
}

// Envio de formulario -------------------------------------------------------//
// (Permite enviar un formulario cambiandole su Action, su el parametro action 
//  no es indicado, entonces se tomara el action actual del form)
function enviaForm(form) {
    if ((form["loading"] !== undefined) && (form["loading"] === true)) {
        showLoader();
    }

    if ((form["btn"] !== undefined)) {
        var boton = $(form["btn"]);
        /*if ($(boton).find("i").length > 0) {
         var icono_class = $(boton).find("i").attr("class");
         boton.empty().html('<i class="' + icono_class + '"></i>' + " Procesando...");
         } else {
         boton;
         boton.empty().html("Procesando...");
         }*/
        boton.addClass("disabled").attr("disabled", "disabled");
    }

    var f = $("#" + form["id"] + "");
    var action = (form["action"] !== undefined) ? form["action"] : f.attr("action");
    f.attr("action", action);
    setTimeout(function () {
        f.submit();
    }, 1000);
}

// Autocomplete --------------------------------------------------------------//
// (Crea el objeto autocomplete utilizando jquery-ui)
function autocomplete(autocomplete) {
    $("#" + autocomplete["label"]).bind("keyup keypress", function (e) {
        var code = e.keyCode || e.which;
        if (code === 13) {
            e.preventDefault();
            return false;
        }

    });

    // requiere el gif auto-indicator.gif
    $("#" + autocomplete["label"]).autocomplete({
        minLength: 3,
        scroll: true,
        source: function (request, response) {
            $.ajax({
                url: autocomplete["url"],
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    var datos = [];
                    $.each(data, function (i, obj) {
                        datos.push({
                            label: obj.nombre,
                            value: obj.codigo
                        });
                    });
                    response(datos);
                }
            });
        },
        select: function (event, ui) {
            $("#" + autocomplete["value"]).val(ui.item.value);
            $("#" + autocomplete["label"]).val(ui.item.label);
            return false;
        },
        change: function (event, ui) {
            if (ui.item === null) {
                $("#" + autocomplete["label"]).val('').focus();
                $("#" + autocomplete["value"]).val("");
            }
        },
        focus: function (event, ui) {
            $("#" + autocomplete["label"]).val(ui.item.label);
            return false;
        }
    });
}

// Mensajeria ----------------------------------------------------------------//
// (Crea mensajes utilizando los estilos de Bootstrap)
function msgExito(obj, msg) {
    if (msg === undefined) {
        $(obj).html("<div class='alert alert-success fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 \u00c9xito ! ... </strong> La operaci&oacute;n se ha realizado correctamente.</div>");
    } else {
        $(obj).html("<div class='alert alert-success fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 \u00c9xito ! ... </strong> " + msg + "</div>");
    }
}

function msgInfo(obj, msg) {
    if (msg === undefined) {
        $(obj).html("<div class='alert alert-info fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 Informaci&oacute;n ! ... </strong> No se han encontrado registros.</div>");
    } else {
        $(obj).html("<div class='alert alert-info fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 Informaci&oacute;n ! ... </strong> " + msg + "</div>");
    }
}

function msgAlerta(obj, msg) {
    if (msg === undefined) {
        $(obj).html("<div class='alert alert-warning fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 Alerta ! ... </strong> Parece que algo no va tan bien.</div>");
    } else {
        $(obj).html("<div class='alert alert-warning fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 Alerta ! ... </strong> " + msg + "</div>");
    }
}

function msgError(obj, msg) {
    if (msg === undefined) {
        $(obj).html("<div class='alert alert-danger fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 Error ! ... </strong> Algo sali&oacute; mal, vuelve a intentar la operaci&oacute;n.</div>");
    } else {
        $(obj).html("<div class='alert alert-danger fade in alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><strong>\u00A1 Error ! ... </strong> " + msg + "</div>");
    }
}

function msgPreload(obj, msg) {
    if (msg === undefined) {
        $(obj).html("<span class='iblock gray'><img src='images/ajax-loader.gif' class='iblock' />&nbsp;&nbsp;&nbsp;Procesando...</span>");
    } else {
        $(obj).html("<span class='iblock gray'><img src='images/ajax-loader.gif' class='iblock' />&nbsp;&nbsp;&nbsp;" + msg + "</span>");
    }
}

// Botones de envio ----------------------------------------------------------//
// (Cambia el texto del boton de envio, ademas de deshabilitarlo)
//function botonLoadingOn(btn) {
//    $(btn).attr("disabled", "true");
//    $('<img src="images/preload-in-btn.gif" id="icn-lbtn" style="margin-left: 5px;display:inline-block"/></div>').appendTo(btn);
//}

// (Restaura el texto del boton de envio, ademas de habilitarlo)
//function botonLoadingOff(btn) {
//    $(btn).removeAttr("disabled");
//    $('#icn-lbtn').remove();
//}

// Boostrap Modal ------------------------------------------------------------//
// (Crea un modal con bootstrap para mensajeria o alerta)
//function alertaModal(modal) {
//    modal["size"] = (modal["size"] !== undefined) ? modal["size"] : "";
//    modal["body"] = (modal["body"] !== undefined) ? modal["body"] : "";
//    modal["color"] = (modal["color"] !== undefined) ? modal["color"] : "";
//    modal["tipo"] = (modal["tipo"] !== undefined) ? modal["tipo"] : "";
//
//    var a = 1;
//    var b = 100;
//    var randomnumber = (a + Math.floor(Math.random() * b));
//
//    if ($.trim(modal["tipo"]).length === 0) {
//        var modal = '<div class="modal" id="myModal_' + randomnumber + '" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog modal-sm"><div class="modal-content"><div class="modal-header"><h4 class="modal-title" id="myModalLabel">Mensaje del sistema</h4></div><div class="modal-body text-danger"><div style="display:table"><div style="display:table-cell;padding-right:10px"><i class="material-icons">clear</i></div><div style="display:table-cell"><span>' + modal["body"] + '</span></div></div></div> <div class="modal-footer"><button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons">clear</i> Cerrar</button></div></div></div></div>';
//    } else {
//        var modal = '<div class="modal" id="myModal_' + randomnumber + '" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog modal-' + modal["size"] + '"><div class="modal-content"><div class="modal-header"><h4 class="modal-title" id="myModalLabel">Mensaje del sistema</h4></div><div class="modal-body ' + modal["color"] + '">' + modal["body"] + '</div> <div class="modal-footer"><button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons">clear</i> Cerrar</button></div></div></div></div>';
//    }
//
//    $("body").append(modal);
//    $("#myModal_" + randomnumber).modal("show");
//}

// (Crea un modal con bootstrap para confirmacion)
//function confirmModal(modal) {
//    modal["size"] = (modal["size"] !== undefined) ? modal["size"] : "sm";
//    modal["body"] = (modal["body"] !== undefined) ? modal["body"] : "";
//    modal["color"] = (modal["color"] !== undefined) ? modal["color"] : "";
//    modal["textoB1"] = (modal["textoB1"] !== undefined) ? modal["textoB1"] : "Aceptar";
//    modal["textoB2"] = (modal["textoB2"] !== undefined) ? modal["textoB2"] : "Cancelar";
//
//    var a = 1;
//    var b = 100;
//    var randomnumber = (a + Math.floor(Math.random() * b));
//    var rndOkBtn = (a + Math.floor(Math.random() * b));
//    var rndNoBtn = (a + Math.floor(Math.random() * b));
//
//    var dismis;
//    var activate = false;
//    if (modal["accionB2"] === undefined) {
//        dismis = 'data-dismiss="modal"';
//    } else {
//        activate = true;
//    }
//    var confirmModal = $('<div class="modal" id="myModal_' + randomnumber + '" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog modal-' + modal["size"] + '"><div class="modal-content"><div class="modal-header"><h4 class="modal-title" id="myModalLabel">Mensaje del sistema</h4></div><div class="modal-body ' + modal["color"] + '">' + modal["body"] + '</div> <div class="modal-footer"><button type="button" id="btn_' + rndOkBtn + '" class="btn btn-success"><i class="material-icons">done</i> ' + modal["textoB1"] + '</button><button type="button" id="btn_' + rndNoBtn + '" class="btn btn-danger" ' + dismis + '><i class="material-icons">clear</i> ' + modal["textoB2"] + '</button></div></div></div></div>');
//
//    confirmModal.find('#btn_' + rndOkBtn).click(function (event) {
//        if (modal["loadingB1"] !== undefined && modal["loadingB1"] === true) {
//            showLoader();
//        }
//        modal["accionB1"]();
//        $("#myModal_" + randomnumber).modal('hide');
//    });
//
//    if (activate) {
//        confirmModal.find('#btn_' + rndNoBtn).click(function (event) {
//            if (modal["loadingB2"] !== undefined && modal["loadingB2"] === true) {
//                showLoader();
//            }
//            modal["accionB2"]();
//            $("#myModal_" + randomnumber).modal('hide');
//        });
//    }
//    confirmModal.modal("show");
//}

//function alertaModal(modal) {
//    modal.header = modal.header !== undefined ? modal.header : "Mensaje del sistema";
//    modal.body = modal.body !== undefined ? modal.body : "";
//    modal.size = modal.size !== undefined ? modal.size : "";
//
//    var d = new Date();
//    var n = d.getTime();
//
//    var html = '<div id="myModal_' + n + '" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
//    html += '<div class="modal-dialog ' + modal.size + '">';
//    html += '<div class="modal-content">';
//    html += '<div class="modal-header">';
//    html += '<h4>' + modal.header + '</h4>';
//    html += '</div>';
//    html += '<div class="modal-body">';
//    html += modal.body;
//    html += '</div>';
//    html += '<div class="modal-footer">';
//    html += '<span class="btn btn-sm btn-danger" data-dismiss="modal"><i class="material-icons">clear</i> Cerrar</span>';
//    html += '</div>';  // content
//    html += '</div>';  // dialog
//    html += '</div>';  // footer
//    html += '</div>';  // modalWindow
//    $('body').append(html);
//    $('#myModal_' + n).modal();
//    $('#myModal_' + n).modal('show');
//    $('#myModal_' + n).on('hidden.bs.modal', function (e) {
//        $(this).remove();
//    });
//}

// Select2 -------------------------------------------------------------------//
// (Crea un combo con capacidad de filtro)
function hasSelect2Simple(obj) {
    // Solo se activa cuando no es un dispositivo movil
    if (!esMobileDevice()) {
        $(obj).select2({
            language: "es",
            placeholder: "--SELECCIONE--"
        });
    }
}

// SelectPicker --------------------------------------------------------------//
// (Crea un combo con capacidad de filtro o no)
/*function hasSelectPicker(obj, search) {    
 $(obj).selectpicker({
 size: 10,
 liveSearch: search,
 showTick: true
 });
 if (/Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent)) {
 $(obj).selectpicker('mobile');
 }
 }*/


// Datatables ----------------------------------------------------------------//
// (Crea una tabla paginada con el plugin dataTables)
function iniDataTable(dt) {
    // Modo de uso
    // ===========
    /*
     iniDataTable({
     tabla: "#tablaAprCotPro",
     filasXpagina: "todos",
     cajaBuscar: false,
     responsive: true,
     cboPaginas: false,
     infoNroReg: false
     });
     */
    // Numero de Filas a mostrar por Pagina
    dt.filasXpagina = dt.filasXpagina === undefined ? -1 : dt.filasXpagina;
    // Activa o desactiva la caja de busqueda
    dt.cajaBuscar = dt.cajaBuscar === undefined ? false : dt.cajaBuscar;
    // Habilita o deshabilita el modo responsive
    dt.responsive = dt.responsive === undefined ? true : dt.responsive;
    // Activa o desactiva el combo de cantidad de registros
    dt.cboPaginas = dt.cboPaginas === undefined ? false : dt.cboPaginas;
    // Activa o desactiva numero de registro en pie de pagina
    dt.infoNroReg = dt.infoNroReg === undefined ? false : dt.infoNroReg;
    // Activa o desactiva el ordenamiento de columna
    dt.ordenaColumna = dt.ordenaColumna === undefined ? false : dt.ordenaColumna;

    $("" + dt.tabla + "").DataTable({
        responsive: dt.responsive,
        deferRender: true,
        iDisplayLength: dt.filasXpagina,
        scroller: true,
        pagingType: "numbers",
        bSort: dt.ordenaColumna,
        paging: dt.filasXpagina === -1 ? false : true,
        searching: dt.cajaBuscar,
        info: dt.infoNroReg,
        lengthChange: dt.cboPaginas,
        aLengthMenu: [[5, 10, 15, 20, 30, 60, -1], [5, 10, 15, 20, 30, 60, "Todos"]],
        autoWidth: false, // fix para datatables dentro de tabs (hidden)
        language: {
            processing: "Procesando...",
            search: "Buscar :",
            searchPlaceholder: "Texto a buscar...",
            lengthMenu: "Mostrar _MENU_ registros",
            info: "_START_ - _END_ de _TOTAL_ registros",
            infoEmpty: "0 - 0 de 0 registros",
            infoFiltered: "", // (filtrado de un total de _MAX_ registros)
            infoPostFix: "",
            loadingRecords: "Cargando...",
            zeroRecords: "No se encontraron resultados",
            emptyTable: "Tabla sin data disponible",
            paginate: {
                first: "Primero",
                previous: "Anterior",
                next: "Siguiente",
                last: "\u00daltimo"
            },
            aria: {
                sortAscending: ": Ordenar Ascendentemente",
                sortDescending: ": Ordendar Descendentemente"
            }
        }
    });
}

// Datepickers ---------------------------------------------------------------//
// (Crea un calendario libre)
function hasDatePicker(obj) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;

    $(obj).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: "1970:" + year + ""
    });
    $(obj).attr("readonly", true);
}

// (Crea un calendario con la fecha actual como minima pero no seleccionada)
function hasDatePickerMin(obj) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;

    $(obj).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: "1970:" + year + "",
        minDate: 0
    });
    $(obj).attr("readonly", true);
}

// (Crea un calendario con la fecha actual como maxima pero no seleccionada)
function hasDatePickerMax(obj) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;

    $(obj).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: "1915:" + year + "",
        maxDate: 0
    });
    $(obj).attr("readonly", true);
}

// (Crea un calendario con la fecha actual seleccionada)
function hasDatePickerDefaultDate(obj) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;

    $(obj).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: "1970:" + year + ""
    });
    $(obj).attr("readonly", true);
    $(obj).datepicker("setDate", new Date());
}

// (Crea un calendario con la fecha actual seleccionada y como minima)
function hasDatePickerDefaultDateMin(obj) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;

    $(obj).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: "1970:" + year + "",
        minDate: 0
    });
    $(obj).attr("readonly", true);
    $(obj).datepicker("setDate", new Date());
}

// (Configura dos calendarios dependientes de la fecha uno del otro)
function setCalendariosDependientes(calendariosDependientes) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;
    calendariosDependientes["obj1"] = (calendariosDependientes["obj1"] !== undefined) ? calendariosDependientes["obj1"] : "";
    calendariosDependientes["obj2"] = (calendariosDependientes["obj2"] !== undefined) ? calendariosDependientes["obj2"] : "";
    calendariosDependientes["minDate"] = (calendariosDependientes["minDate"] !== undefined) ? calendariosDependientes["minDate"] : "";
    calendariosDependientes["minYear"] = (calendariosDependientes["minYear"] !== undefined) ? calendariosDependientes["minYear"] : "1970";
    calendariosDependientes["maxYear"] = (calendariosDependientes["maxYear"] !== undefined) ? calendariosDependientes["maxYear"] : "2070";

    // Datepicker 1
    $(calendariosDependientes["obj1"]).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: "1970:" + year + "",
        minDate: calendariosDependientes["minDate"],
        onClose: function (selectedDate) {
            $(calendariosDependientes["obj2"]).datepicker("option", "minDate", selectedDate);
        }
    });
    $(calendariosDependientes["obj1"]).attr("readonly", true);

    // Mes o meses de diferencia
    if (calendariosDependientes["mesDif"] !== undefined) {
        var f = new Date();
        f.setMonth(f.getMonth() - calendariosDependientes["mesDif"]);
        $(calendariosDependientes["obj1"]).datepicker("setDate", f);
    }

    // Datepicker 2
    $(calendariosDependientes["obj2"]).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: calendariosDependientes["minYear"] + ':' + calendariosDependientes["maxYear"],
        onClose: function (selectedDate) {
            $(calendariosDependientes["obj1"]).datepicker("option", "maxDate", selectedDate);
        }
    });
    $(calendariosDependientes["obj2"]).attr("readonly", true);
    if (calendariosDependientes["mesDif"] !== undefined) {
        $(calendariosDependientes["obj2"]).datepicker("setDate", new Date());
    }
}

// (Crea un calendario habilitando solo los dias enviados en el array)
function setCalendarioSoloDias(calendario) {
    var c = new Date();
    var year_actual = c.getFullYear();
    var year = year_actual + 20;

    calendario["minDate"] = (calendario["minDate"] !== undefined) ? calendario["minDate"] : "";
    calendario["maxDate"] = (calendario["maxDate"] !== undefined) ? calendario["maxDate"] : "";
    calendario["only"] = (calendario["only"] !== undefined) ? calendario["only"] : "";
    calendario["minYear"] = (calendario["minYear"] !== undefined) ? calendario["minYear"] : "1970";
    calendario["maxYear"] = (calendario["maxYear"] !== undefined) ? calendario["maxYear"] : year;

    $(calendario["obj"]).datepicker({
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: calendario["minYear"] + ':' + calendario["maxYear"],
        minDate: calendario["minDate"],
        maxDate: calendario["maxDate"]
    });
    if (calendario["only"] !== "") {
        $(calendario["obj"]).datepicker("option", {
            beforeShowDay: function (date) {
                if ($.inArray(date.getDay(), calendario["only"]) !== -1) {
                    return [true];
                } else {
                    return [false];
                }
            }
        });
    }
}

// Valida un email -----------------------------------------------------------//
// (Bordes del input se haran rojos si esta mal: 
//  Usar asi onkeyup="validaEmail(this)")
function validaEmail(email) {
    var emailreg = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
    $(email).keyup(function () {
        if (emailreg.test($(email).val())) {
            $(email).removeClass("error");
            return false;
        } else {
            $(email).addClass("error");
            return false;
        }
    });
    $(email).blur(function () {
        if ($.trim($(email).val()).length === 0) {
            $(email).removeClass("error");
        }
    });
}

// Placeholders --------------------------------------------------------------//
// (Util para struts1 que no permite el attr placeholder)
$(function () {
    $('input[type=text],input[type=password],textarea').each(function () {
        $(this).attr('placeholder', $(this).attr('alt'));
    });
});

// Funciones manejadoras del tiempo sesion -----------------------------------//
/*var intervalo_timer;
 var timeout_timer;
 function iniTimerGlobal() {
 var paginaLogout = "relogin.jsp";
 var totalSegundos = 1800; // (30 min x 60 seg = 1800 seg)
 
 clearTimeout(timeout_timer);
 clearInterval(intervalo_timer);
 
 if ($("#g_tiempo_ind").length > 0) {
 intervalo_timer = setInterval(setTime, 1000);
 }
 
 function setTime() {
 if (totalSegundos <= 0) {
 timeout_timer = setTimeout('location="' + paginaLogout + '"', 1);
 }
 
 if (totalSegundos > 0) {
 totalSegundos -= 1;
 }
 
 var min = parseInt((totalSegundos / 60));
 var seg = totalSegundos % 60;
 document.getElementById('g_tiempo_ind').innerHTML = visor(min) + ":" + visor(seg);
 }
 
 function visor(val) {
 var valString = val + "";
 if (valString.length < 2) {
 return "0" + valString;
 } else {
 return valString;
 }
 }
 }*/

// Datetime Pickers ----------------------------------------------------------//
//function hasDateTimePicker(obj) {
//    if (jQuery().datetimepicker) {
//        $(obj).datetimepicker({
//            controlType: 'select'
//        });
//    }
//}
//function hasDateTimePickerDefaultDateTime(obj) {
//    if (jQuery().datetimepicker) {
//        $(obj).datetimepicker({
//            controlType: 'select'
//        });
//        $(obj).datetimepicker('setDate', (new Date()));
//    }
//}
//function hasDateTimePickerMinute30(obj) {
//    if (jQuery().datetimepicker) {
//        $(obj).datetimepicker({
//            controlType: 'select',
//            stepMinute: 30
//        });
//    }
//}
function hasTimePicker(obj) {
    if (jQuery().timepicker) {
        $(obj).timepicker({
            controlType: 'select'
        });
    }
}
//function hasTimePickerDefaultTime(obj) {
//    if (jQuery().timepicker) {
//        $(obj).timepicker({
//            controlType: 'select'
//        });
//        $(obj).timepicker('setDate', (new Date()));
//    }
//}
//function hasTimePickerMinute30(obj) {
//    if (jQuery().timepicker) {
//        $(obj).timepicker({
//            controlType: 'select',
//            stepMinute: 30
//        });
//    }
//}

// Muestra gif en div hasta que el ajax se haya procesado --------------------//
function showAjaxLoader(obj) {
    var objeto = (obj.charAt(0) === "#") ? obj : ("#" + obj);
    //$("#" + obj).html("<div class='text-center'><img width='32' height='32' src='images/ajax-loader.gif' alt='cargando...'/></div>");
    //$(objeto).html('<div style="position:relative;width:100%;top:44%;text-align:center;margin-top:7px;margin-bottom:7px"><img height="32" width="32" style="" alt="Cargando..." src="images/ajax-loader.gif"></div>');    
    var html = '<div style="width:100%;height: 100%;white-space: nowrap;text-align: center; padding: 1em 0;">';
    html += '<span style="display: inline-block;height: 100%;vertical-align: middle;"></span>';
    html += '<img alt="Cargando..." src="images/ajax-loader.gif" style="vertical-align: middle;max-height: 32px;max-width: 32px" />';
    html += '</div>';
    $(objeto).html(html);
}

// Crea tags en el elemento (div) indicado -----------------------------------//
function iniciaTagsInput(obj) {
    if (jQuery().tagsInput) {
        $(obj).tagsInput({
            width: 'auto',
            height: '30px',
            placeholderColor: '#999',
            'onAddTag': function (tag) {
            }
        });
    }
}

// Cargador de Menus ---------------------------------------------------------//
// (Utilizado en Struts-1)
//function cargaPagina(action, parametro, valor) {
//    showLoader();
//    $.ajax({
//        url: action + "?" + parametro + "=" + valor + "",
//        type: "POST",
//        cache: false,
//        success: function (data) {
//            hideLoader();
//            $("#main-content").html(data);
//        },
//        error: function () {
//            hideLoader();
//            msgError("#main-content", "No se pudo cargar la p\u00e1gina solicitada. Por favor intente nuevamente.");
//        }
//    });
//    iniTimerGlobal(); // reinicia contador del tiempo
//}

// Enviar un formulario ------------------------------------------------------//
// (Utilizado con Ajax)
//function enviaFormAjax(form) {
//    if ((form["loading"] !== undefined) && (form["loading"] === true)) {
//        showLoader();
//    }
//
//    if ((form["btn"] !== undefined)) {
//        var boton = $(form["btn"]);
//        if ($(boton).find("i").length > 0) {
//            var icono_class = $(boton).find("i").attr("class");
//            boton.empty().html('<i class="' + icono_class + '"></i>' + " Procesando...");
//        } else {
//            boton;
//            boton.empty().html("Procesando...");
//        }
//        boton.addClass("disabled").attr("disabled", "disabled");
//    }
//
//    var f = $("#" + form["id"] + "");
//    var action = (form["action"] !== undefined) ? form["action"] : f.attr("action");
//    f.attr("action", action);
//
//    $.ajax({
//        url: action,
//        type: "POST",
//        cache: false,
//        data: f.serialize(),
//        success: function (data) {
//            $('#main-content').html(data);
//            hideLoader();
//        },
//        error: function () {
//            msgError("#main-content", "Ocurri\u00f3 un error al procesar la solicitud. Por favor intente nuevamente.");
//            hideLoader();
//        }
//    });
//    iniTimerGlobal(); // reinicia contador del tiempo
//}

// Alerta y Confirm con Jquery Dialog ----------------------------------------//
//function jqAlert(msg) {
//    // Modo de uso
//    // ===========
//    // toastAlerta("Ingresa un dato valido");
//    $('html, body').animate({scrollTop: '0px'}, 0);
//    if (!msg || msg === undefined) {
//        msg = '';
//    }
//    $("<div></div>").html(msg).dialog({
//        title: "Mensaje del Sistema",
//        resizable: false,
//        modal: true,
//        closeOnEscape: false,
//        draggable: false,
//        dialogClass: 'fixed-dialog',
//        open: function (ui) {
//            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
//        },
//        buttons: {
//            "Aceptar": function () {
//                $(this).dialog("close");
//            }}
//    });
//}

//function jqConfirm(msg, fn) {
//    // Modo de uso
//    // ===========
//    // jqConfirm('Deseas eliminar este registro?', function () {
//    //    alert('Hiciste clic en Aceptar');
//    // });
//    $('html, body').animate({scrollTop: '0px'}, 0);
//    $("<div></div>").html(msg).dialog({
//        draggable: false,
//        modal: true,
//        resizable: false,
//        closeOnEscape: false,
//        width: 'auto',
//        dialogClass: 'fixed-dialog',
//        title: 'Mensaje del Sistema' || 'Confirm',
//        minHeight: 75,
//        open: function (ui) {
//            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
//        },
//        buttons: {
//            Aceptar: function () {
//                if (typeof (fn) === 'function') {
//                    setTimeout(fn, 50);
//                }
//                $(this).dialog('destroy');
//            },
//            Cancelar: function () {
//                $(this).dialog('destroy');
//            }
//        }
//    });
//}

function toastConfirm(msg, fn) {
    // Modo de uso
    // ===========
    // toastConfirm('Deseas eliminar este registro?', function () {
    //    alert('Hiciste clic en Aceptar');
    // });
//    $('html, body').animate({scrollTop: '0px'}, 0);
//    $("<div></div>").html(msg).dialog({
//        draggable: false,
//        modal: true,
//        resizable: false,
//        closeOnEscape: false,
//        width: 'auto',
//        dialogClass: 'fixed-dialog',
//        title: 'Mensaje del Sistema' || 'Confirm',
//        minHeight: 75,
//        open: function (ui) {
//            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
//        },
//        buttons: {
//            Aceptar: function () {
//                if (typeof (fn) === 'function') {
//                    setTimeout(fn, 50);
//                }
//                $(this).dialog('destroy');
//            },
//            Cancelar: function () {
//                $(this).dialog('destroy');
//            }
//        }
//    });
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalConfirm_" + n + rndStr;
    var idBtnAceptar = "btnConfirmAce_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-sm">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-warning">\u00A1 Alerta !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + msg + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" id=' + idBtnAceptar + ' class="btn btn-sm btn-default">\u2714 Aceptar</button>';
    html += '<button class="btn btn-sm btn-default" data-dismiss="modal">\u2716 Cancelar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $('#' + idModal).find('#' + idBtnAceptar).click(function (e) {
        if (typeof (fn) === 'function') {
            setTimeout(fn, 50);
        }
        $('#' + idModal).modal('hide');
    });
}

function toastConfirm2(msg, fnAceptar, fnCancelar) {
    // Modo de uso
    // ===========
    // toastConfirm2('Deseas eliminar este registro?', function () {
    //    alert('Hiciste clic en Aceptar');
    // }, function () {
    //    alert('Hiciste clic en Cancelar');
    // });
//    $('html, body').animate({scrollTop: '0px'}, 0);
//    $("<div></div>").html(msg).dialog({
//        draggable: false,
//        modal: true,
//        resizable: false,
//        closeOnEscape: false,
//        width: 'auto',
//        dialogClass: 'fixed-dialog',
//        title: 'Mensaje del Sistema' || 'Confirm',
//        minHeight: 75,
//        open: function (ui) {
//            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
//        },
//        buttons: {
//            Aceptar: function () {
//                if (typeof (fnAceptar) === 'function') {
//                    setTimeout(fnAceptar, 50);
//                }
//                $(this).dialog('destroy');
//            },
//            Cancelar: function () {
//                if (typeof (fnCancelar) === 'function') {
//                    setTimeout(fnCancelar, 50);
//                }
//                $(this).dialog('destroy');
//            }
//        }
//    });
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalConfirm_" + n + rndStr;
    var idBtnAceptar = "btnConfirmAce_" + n + rndStr;
    var idBtnCancelar = "btnConfirmCan_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-sm">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-warning">\u00A1 Alerta !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + msg + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" id=' + idBtnAceptar + ' class="btn btn-sm btn-default">\u2714 Aceptar</button>';
    html += '<button type="button" id=' + idBtnCancelar + ' class="btn btn-sm btn-default">\u2716 Cancelar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $('#' + idModal).find('#' + idBtnAceptar).click(function (e) {
        if (typeof (fnAceptar) === 'function') {
            setTimeout(fnAceptar, 50);
        }
        $('#' + idModal).modal('hide');
    });
    $('#' + idModal).find('#' + idBtnCancelar).click(function (e) {
        if (typeof (fnAceptar) === 'function') {
            setTimeout(fnCancelar, 50);
        }
        $('#' + idModal).modal('hide');
    });
}

// Mensajeria con Plugin Toastr
function toastError(msg) {
    /*var txt;
     if (msg === undefined || $.trim(msg).length === 0) {
     txt = "Algo sali&oacute; mal, vuelve a intentar la operaci&oacute;n.";
     } else {
     txt = msg;
     }
     // se pone esta validacion porque el toast no funciona en ipds antiguos
     if (esIOsDevice()) {
     toastAlerta(msg);
     } else {
     $.toast().reset('all');
     $.toast({
     heading: 'Error',
     text: txt,
     icon: 'error',
     loader: false,
     position: 'mid-center',
     stack: false,
     bgColor: '#bd362f',
     textColor: 'white'
     });
     }*/
    /*toastr.remove();
     var txt;
     if (msg === undefined || $.trim(msg).length === 0) {
     txt = "Algo sali&oacute; mal, vuelve a intentar la operaci&oacute;n.";
     } else {
     txt = msg;
     }
     toastr.error(txt, 'Error');*/
    var mensaje = (msg === undefined || $.trim(msg).length === 0) ? "Ha ocurrido un error." : msg;
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalToastError_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-sm">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-danger">\u00A1 Error !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + mensaje + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">\u2716 Cerrar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $("#" + idModal).find("button").focus();
}
function toastExito(msg) {
//    var txt;
//    if (msg === undefined || $.trim(msg).length === 0) {
//        txt = "La operaci&oacute;n se ha realizado correctamente.";
//    } else {
//        txt = msg;
//    }
//    if (esIOsDevice()) {
//        toastAlerta(msg);
//    } else {
//        $.toast().reset('all');
//        $.toast({
//            heading: '&Eacute;xito',
//            text: txt,
//            icon: 'success',
//            loader: false,
//            position: 'mid-center',
//            stack: false,
//            bgColor: '#51a351',
//            textColor: 'white'
//        });
//    }
    /*toastr.remove();
     var txt;
     if (msg === undefined || $.trim(msg).length === 0) {
     txt = "La operaci&oacute;n se ha realizado correctamente.";
     } else {
     txt = msg;
     }
     toastr.success(txt, 'Exito');*/
    var mensaje = (msg === undefined || $.trim(msg).length === 0) ? "Operaci&oacute;n exitosa." : msg;
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalToastExito_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-sm">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-success">\u00A1 \u00c9xito !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + mensaje + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">\u2716 Cerrar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $("#" + idModal).find("button").focus();
}
function toastInfo(msg) {
//    var txt;
//    if (msg === undefined || $.trim(msg).length === 0) {
//        txt = "No se han encontrado registros.";
//    } else {
//        txt = msg;
//    }
//    if (esIOsDevice()) {
//        toastAlerta(msg);
//    } else {
//        $.toast().reset('all');
//        $.toast({
//            heading: 'Informaci&oacute;n',
//            text: txt,
//            icon: 'info',
//            loader: false,
//            position: 'mid-center',
//            stack: false,
//            bgColor: '#337AB7',
//            textColor: 'white'
//        });
//    }
    /*toastr.remove();
     var txt;
     if (msg === undefined || $.trim(msg).length === 0) {
     txt = "No se han encontrado registros.";
     } else {
     txt = msg;
     }
     toastr.info(txt, 'Informaci&oacute;n');*/
    var mensaje = (msg === undefined || $.trim(msg).length === 0) ? "No se encontraron resultados." : msg;
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalToastInfo_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-sm">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-info">\u00A1 Informaci\u00f3n !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + mensaje + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">\2716 Cerrar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $("#" + idModal).find("button").focus();
}
function toastAlerta(msg) {
//    var txt;
//    if (msg === undefined || $.trim(msg).length === 0) {
//        txt = "Parece que algo no va tan bien.";
//    } else {
//        txt = msg;
//    }
//    if (esIOsDevice()) {
//        toastAlerta(msg);
//    } else {
//        $.toast().reset('all');
//        $.toast({
//            heading: 'Alerta',
//            text: txt,
//            icon: 'warning',
//            loader: false,
//            position: 'mid-center',
//            stack: false,
//            bgColor: '#F27B29',
//            textColor: 'white'
//        });
//    }
    /*toastr.remove();
     var txt;
     if (msg === undefined || $.trim(msg).length === 0) {
     txt = "Parece que algo no va tan bien.";
     } else {
     txt = msg;
     }
     toastr.warning(txt, 'Alerta');*/
    var mensaje = (msg === undefined || $.trim(msg).length === 0) ? "Hay algo que merece tu atenci&oacute;n." : msg;
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalToastAlert_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-sm">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-warning">\u00A1 Alerta !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + mensaje + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">\u2716 Cerrar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $("#" + idModal).find("button").focus();
}

function pasaFoco(objOrigen, objDestino) {
    $("#" + objOrigen).on("keypress", function (e) {
        e.preventDefault();
        var key = checkKeyCode(e);
        if (key === 13) {
            $("#" + objDestino).focus();
        }
    });
}

// Limpia las reglas del plugin jquery validate
function limpiaReglasValidate(obj) {
    $(obj).find('input,textarea,select').each(function () {
        $(this).rules("remove");
    });
}

//function nullFlo(dato) {
//    var num = 0;
//    if (dato !== undefined) {
//        if (!isNaN(dato) && $.trim(dato).length > 0) {
//            num = parseFloat($.trim(dato));
//        }
//    }
//    return num;
//}

$.nullFlo = function (dato) {
    var num = 0;
    if (dato !== undefined) {
        if (!isNaN(dato) && $.trim(dato).length > 0) {
            num = parseFloat($.trim(dato));
        }
    }
    return num;
};

//function nullNum(dato) {
//    var num = 0;
//    if (dato !== undefined) {
//        if (!isNaN(dato) && $.trim(dato).length > 0) {
//            num = parseInt($.trim(dato));
//        }
//    }
//    return num;
//}

$.nullNum = function (dato) {
    var num = 0;
    if (dato !== undefined) {
        if (!isNaN(dato) && $.trim(dato).length > 0) {
            num = parseInt($.trim(dato));
        }
    }
    return num;
};

/*function nullCad(dato) {
 var str = "";
 if (dato !== undefined) {
 if ($.trim(dato).length > 0) {
 str = ($.trim(dato)).toString();
 }
 }
 return str;
 }*/

$.nullCad = function (dato) {
    var str = "";
    if (dato !== undefined) {
        if ($.trim(dato).length > 0) {
            str = ($.trim(dato)).toString();
        }
    }
    return str;
};

// Detectar si un dispositivo es IOs (Apple)
function esIOsDevice() {
    var ios = parseFloat(
            ('' + (/CPU.*OS ([0-9_]{1,5})|(CPU like).*AppleWebKit.*Mobile/i.exec(navigator.userAgent) || [0, ''])[1])
            .replace('undefined', '3_2').replace('_', '.').replace('_', '')
            ) || false;
    return ios;
}

// Detectar si un dispositivo es un Mobil
function esMobileDevice() {
    if (esIOsDevice() || (/android|webos|iphone|ipad|ipod|blackberry|windows phone|opera mini|iemobile|nokia/i.test(navigator.userAgent.toLowerCase()))) {
        return true;
    } else {
        return false;
    }
}

// Comprobar si una propiedad existe en un objeto
// Usualmente usado para JSON
$.isEmpty = function (obj) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key))
            return false;
    }
    return true;
};
//function isEmpty(obj) {
//    for (var key in obj) {
//        if (obj.hasOwnProperty(key))
//            return false;
//    }
//    return true;
//}
// Combos readonly (simulado)
function bloqueaCombos(controles) {
    if ($.isArray(controles)) {
        for (var i = 0; i < controles.length; i++) {
            $("#" + controles[i]).attr("onmousedown", "(function(e){ e.preventDefault(); })(event, this)").css({
                "background-color": "#eeeeee",
                "cursor": "not-allowed",
                "pointer-events": "none"
            });
        }
    } else {
        $("#" + controles).attr("onmousedown", "(function(e){ e.preventDefault(); })(event, this)").css({
            "background-color": "#eeeeee",
            "cursor": "not-allowed",
            "pointer-events": "none"
        });
    }
}
function desbloqueaCombos(controles) {
    if ($.isArray(controles)) {
        for (var i = 0; i < controles.length; i++) {
            $("#" + controles[i]).removeAttr("onmousedown").css({
                "background-color": "#ffffff",
                "cursor": "default",
                "pointer-events": "auto"
            });
        }
    } else {
        $("#" + controles).removeAttr("onmousedown").css({
            "background-color": "#ffffff",
            "cursor": "default",
            "pointer-events": "auto"
        });
    }
}
function soportaToDataURL() {
    var c = document.createElement("canvas");
    var data = c.toDataURL("image/png");
    return (data.indexOf("data:image/png") === 0);
}
function esFechaIniMayorQueFin(fechaInicial, fechaFinal) {
    // Las fechas deben estar en formato dd/mm/yyyy
    var fi = fechaInicial.split("/");
    var ff = fechaFinal.split("/");
    var fechaIni = new Date(fi[2], (fi[1] - 1), fi[0]);
    var fechaFin = new Date(ff[2], (ff[1] - 1), ff[0]);
    if (fechaIni > fechaFin) {
        return "S";
    }
    return "N";
}
function esFechaIniMayorIgualQueFin(fechaInicial, fechaFinal) {
    // Las fechas deben estar en formato dd/mm/yyyy
    var fi = fechaInicial.split("/");
    var ff = fechaFinal.split("/");
    var fechaIni = new Date(fi[2], (fi[1] - 1), fi[0]);
    var fechaFin = new Date(ff[2], (ff[1] - 1), ff[0]);
    if (fechaIni >= fechaFin) {
        return "S";
    }
    return "N";
}

























// -----------------------------------------------------------------------------
// ---------------- Hasta aqui esta estandarizado con Procreador ---------------
// De aqui para abajo son funciones antiguas de TAKA (No utilizarlas)
// Poco a poco ir borrandolas y sustituyendolas con nuevas.
//------------------------------------------------------------------------------

//function validaNumero(e) {
//    var key = checkKeyCode(e);
//    if (
//            key === 8 || // backspace
//            key === 9 || // tab
//            key === 37 || // left arrow
//            key === 38 || // up arrow
//            key === 39 || // right arrow
//            key === 40 || // down arrow
//            key === 46 || // delete
//            key === 13 // enter
//            ) {
//        return true;
//    }
//    var pattern = /[0-9]/;
//    var te = String.fromCharCode(key);
//    return pattern.test(te);
//}
//
//function validaDecimal(e, field) {
//    var key = checkKeyCode(e);
//    if (
//            key === 8 || // backspace
//            key === 9 || // tab
//            key === 37 || // left arrow
//            key === 38 || // up arrow
//            key === 39 || // right arrow
//            key === 40 || // down arrow
//            //key === 46 || // delete
//            key === 13 // enter
//            ) {
//        return true;
//    }
//
//    // 0-9
//    if (key > 47 && key < 58) {
//        if (field.value === "")
//            return true;
//        var regexp = /.[0-9]{}$/; // {2} = 2 decimales
//        return !(regexp.test(field.value));
//    }
//    // .
//    if (key === 46) {
//        if (field.value === "")
//            return false;
//        regexp = /^[0-9]+$/;
//        return regexp.test(field.value);
//    }
//    // other key
//    return false;
//}

//function teclaEvento(evento) {
//    var forbiddenKeys = new Array('a', 'n', 'c', 'x', 'v', 'j');
//    var key;
//    var isCtrl;
//    if (window.event) {
//        key = window.event.keyCode;     //IE
//        if (window.event.ctrlKey)
//            isCtrl = true;
//        else
//            isCtrl = false;
//    }
//    else {
//        key = evento.which;     //firefox
//        if (evento.ctrlKey)
//            isCtrl = true;
//        else
//            isCtrl = false;
//    }
//    if (isCtrl) {
//        for (var i = 0; i < forbiddenKeys.length; i++) {
//            if (forbiddenKeys[i].toLowerCase() === String.fromCharCode(key).toLowerCase()) {
//                alert('Key combination CTRL + '
//                        + String.fromCharCode(key)
//                        + ' has been disabled.');
//                return false;
//            }
//        }
//    }
//    return key;
//}

//function tabular(e, obj) {
//    var tecla = (document.all) ? e.keyCode : e.which;
//    if (tecla !== 13)
//        return;
//    var frm = obj.form;
//    for (var i = 0; i < frm.elements.length; i++)
//        if (frm.elements[i] === obj) {
//            if (i === frm.elements.length - 1)
//                i = -1;
//            break
//        }
//    frm.elements[i + 1].focus();
//    return false;
//}

/*function validaEmail(value) {
 var ExpRegular = /(\w+)(\.?)(\w*)(\@{1})(\w+)(\.?)(\w*)(\.{1})(\w{2,3})/;
 if (ExpRegular.test(value)) {
 return true;
 }
 else {
 return false;
 }
 }*/

//function validaDecimal(e) {
//    if (!e)
//        e = window.event;
//    var key = e.keyCode ? e.keyCode : e.which > 0 ? e.which : e.keyCode;
//    if (key === 8 || key === 9 || key === 46)
//        return true;
//    var pattern = /[0-9]/;
//    var te = String.fromCharCode(key);
//    return pattern.test(te);
//}

//function validaTelefono(e) {
//    if (!e)
//        e = window.event;
//    key = e.keyCode ? e.keyCode : e.which > 0 ? e.which : e.keyCode;
//    if (key === 8 || key === 9 || key === 42)
//        return true;
//    pattern = /[0-9]/;
//    te = String.fromCharCode(key);
//    return pattern.test(te);
//}

//function esTelefono(e, obj, tipoEvento) {
//    if (tipoEvento === 1) {
//        replicar(e, obj);
//    } else if (tipoEvento === 2) {
//        tabular(e, obj);
//        return validaTelefono(e);
//    }
//}

//function esNumero(e, obj, tipoEvento) {
//    if (tipoEvento === 1) {
//        replicar(e, obj);
//    } else if (tipoEvento === 2) {
//        tabular(e, obj);
//        return validaNumero(e);
//    }
//}

//function trim(cadena) {
//    cadena2 = "";
//    len = cadena.length;
//    for (var i = 0; i <= len; i++)
//        if (cadena.charAt(i) !== " ") {
//            cadena2 += cadena.charAt(i);
//        }
//    return cadena2;
//}

//function esrucok(ruc) {
//    return (!(esnulo(ruc) || !esnumero(ruc) || !eslongrucok(ruc) || !valruc(ruc)));
//}
//function esnumero(campo) {
//    return (!(isNaN(campo)));
//}

//function esnulo(campo) {
//    return (campo === null || campo === "");
//}

//function eslongrucok(ruc) {
//    return (ruc.length === 11);
//}

//function valruc(valor) {
//    valor = trim(valor);
//    if (esnumero(valor)) {
//        if (valor.length === 8) {
//            var suma = 0;
//            for (var i = 0; i < valor.length - 1; i++) {
//                var digito = valor.charAt(i) - '0';
//                if (i === 0)
//                    suma += (digito * 2);
//                else
//                    suma += (digito * (valor.length - i));
//            }
//            var resto = suma % 11;
//            if (resto === 1)
//                resto = 11;
//            if (resto + (valor.charAt(valor.length - 1) - '0') === 11) {
//                return true;
//            }
//        } else if (valor.length === 11) {
//            suma = 0;
//            var x = 6;
//            for (i = 0; i < valor.length - 1; i++) {
//                if (i === 4)
//                    x = 8;
//                digito = valor.charAt(i) - '0';
//                x--;
//                if (i === 0)
//                    suma += (digito * x);
//                else
//                    suma += (digito * x);
//            }
//            resto = suma % 11;
//            resto = 11 - resto;
//
//            if (resto >= 10)
//                resto = resto - 10;
//            if (resto === valor.charAt(valor.length - 1) - '0') {
//                return true;
//            }
//        }
//    }
//    return false;
//}

//function abreventanaMax(pagina, nombre) {
//    window.open(pagina, nombre, "width=800, height=600, scrollbars=1, toolbar=0, menubar=0, resizable=yes, location=1, status=1");
//}
//
//function abreventana(laurl, pWindowName, pWidth, pHeight) {
//    window.open(laurl, pWindowName, "width=" + String(pWidth) + ", height=" + String(pHeight) + ", scrollbars=1, toolbar=0, menubar=0, resizable=yes, location=1, status=1");
//}

//function teclaEvento(evento)
//{
//    if (evento.keyCode)
//        return evento.keyCode;
//    else
//        return evento.which;
//}

//function ValidaNum(key, ln_valor)
//{
//    var ln_corr = true;
//    if (key === 46)
//    {
//        if ((ln_valor.length === 0))
//            ln_corr = false;
//        else
//        {
//            var ls_existe = BuscaPunto(ln_valor);
//            if (ls_existe === 1)
//                ln_corr = false;
//        }
//    }
//    else
//    if ((key < 48) || (key > 57))
//        ln_corr = false;
//    else
//    {
//        var Posicion = BuscaPunto(ln_valor);
//
//        if (Posicion !== 0)
//        {
//            var ln_max = ln_valor.length - Posicion;
//            if (ln_max === 3)
//                ln_corr = false;
//        }
//    }
//    return ln_corr;
//}

//function BuscaPunto(cadena) {
//    var existe = 0;
//    for (var i = 0; i < cadena.length; i++) {
//        if (cadena.charAt(i) === ".") {
//            existe = 1;
//            break;
//        }
//    }
//    return existe;
//}

// solo numeros o letras
//function soloNumeros(e) {
//    var key = e.keyCode || e.which;
//    var tecla = String.fromCharCode(key).toLowerCase();
//    //letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
//    var letras = "0123456789";
//    var especiales = [8, 37, 39, 46];
//
//    var tecla_especial = false;
//    for (var i in especiales) {
//        if (key === especiales[i]) {
//            tecla_especial = true;
//            break;
//        }
//    }
//
//    if (letras.indexOf(tecla) === -1 && !tecla_especial)
//        return false;
//}

//------------------------------ Validaciones --------------------------------//
//function validaCorreo(field) {
//    var pattern = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
//    if (pattern.test(field.value)) {
//        return true;
//    } else {
//        return false;
//    }
//}



function toastError_lg(msg) {
    
    var mensaje = (msg === undefined || $.trim(msg).length === 0) ? "Ha ocurrido un error." : msg;
    var d = new Date();
    var n = d.getTime();
    var rndStr = "";
    var posible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 20; i++) {
        rndStr += posible.charAt(Math.floor(Math.random() * posible.length));
    }
    var idModal = "modalToastError_" + n + rndStr;
    var html = '<div id=' + idModal + ' class="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';
    html += '<div class="modal-dialog modal-lg">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h4 class="text-danger">\u00A1 Error !</h4>';
    html += '</div>';
    html += '<div class="modal-body">';
    html += '<p style="font-size:14px">' + mensaje + '</p>';
    html += '</div>';
    html += '<div class="modal-footer">';
    html += '<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">\u2716 Cerrar</button>';
    html += '</div>';  // content
    html += '</div>';  // dialog
    html += '</div>';  // footer
    html += '</div>';  // modalWindow
    $('body').append(html);
    $('#' + idModal).modal();
    $('#' + idModal).modal('show');
    $('#' + idModal).on('hidden.bs.modal', function (e) {
        $(this).remove();
    });
    $("#" + idModal).find("button").focus();
}
