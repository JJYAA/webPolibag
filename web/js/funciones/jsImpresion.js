function setCalendariosMasivo(calendariosDependientes) {
    calendariosDependientes["obj1"] = (calendariosDependientes["obj1"] != undefined) ? calendariosDependientes["obj1"] : "";
    calendariosDependientes["obj2"] = (calendariosDependientes["obj2"] != undefined) ? calendariosDependientes["obj2"] : "";
    calendariosDependientes["minDate"] = (calendariosDependientes["minDate"] != undefined) ? calendariosDependientes["minDate"] : "";
    calendariosDependientes["minYear"] = (calendariosDependientes["minYear"] != undefined) ? calendariosDependientes["minYear"] : "1970";
    calendariosDependientes["maxYear"] = (calendariosDependientes["maxYear"] != undefined) ? calendariosDependientes["maxYear"] : "2070";

    $(calendariosDependientes["obj1"]).datepicker({
        //defaultDate: "+1w",
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
        yearRange: '1970:2070',
        minDate: calendariosDependientes["minDate"],
        onClose: function (selectedDate) {
            $(calendariosDependientes["obj2"]).datepicker("option", "minDate", selectedDate);
            var parts = selectedDate.split('/');
            var fecha = new Date(parts[2], parts[1] - 1, parts[0]);
            var nuevaF = new Date(fecha.setDate(fecha.getDate() + 6));
            var year = nuevaF.getFullYear();
            var month = (1 + nuevaF.getMonth()).toString();
            month = month.length > 1 ? month : '0' + month;
            var day = nuevaF.getDate().toString();
            day = day.length > 1 ? day : '0' + day;
            var fechaAum = day + '/' + month + '/' + year;
            $(calendariosDependientes["obj2"]).datepicker("option", "maxDate", fechaAum);
        }
    });
    $(calendariosDependientes["obj2"]).datepicker({
        //defaultDate: "+1w",
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
            var parts = selectedDate.split('/');
            var fecha = new Date(parts[2], parts[1] - 1, parts[0]);
            var nuevaF = new Date(fecha.setDate(fecha.getDate() - 6));
            var year = nuevaF.getFullYear();
            var month = (1 + nuevaF.getMonth()).toString();
            month = month.length > 1 ? month : '0' + month;
            var day = nuevaF.getDate().toString();
            day = day.length > 1 ? day : '0' + day;
            var fechaAum = day + '/' + month + '/' + year;      
            $(calendariosDependientes["obj1"]).datepicker("option", "minDate", fechaAum);
        }
    });
}


function muestraDocumentos(){
    var filtro="0";
     if ($("#chkPor").is(":checked")) 
         filtro="1";
     else
         filtro="0";
    
        $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "ImpresionAction.do?operacion=listaDocumentosFacturados",
        cache: false,
        data: {
            fechaIni:$("#fechaInicial").val(),
            fechaFin:$("#fechaFinal").val(),
            tipoDocu:"%"        
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
                        alert(jsonObjTabla.tabla);
                        $("#c_tablaListaDocumentos").html(jsonObjTabla.tabla);
                        iniDataTable({
                            tabla: "#tablaListaDocumentos",
                            filasXpagina:25,
                            cajaBuscar: true,
                            responsive: false,
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