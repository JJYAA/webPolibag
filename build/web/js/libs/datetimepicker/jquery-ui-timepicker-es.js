/* Spanish translation for the jQuery Timepicker Addon */
/* Written by Ianaré Sévi */
/* Modified by Carlos Martínez */
(function ($) {
    $.timepicker.regional['es'] = {
        timeOnlyTitle: 'Elegir una hora',
        timeText: 'Hora',
        hourText: 'Horas',
        minuteText: 'Minutos',
        secondText: 'Segundos',
        millisecText: 'Milisegundos',
        microsecText: 'Microsegundos',
        timezoneText: 'Uso horario',
        currentText: 'Hoy',
        closeText: 'Cerrar',
        //timeFormat: 'HH:mm', // no descomentar
        //amNames: ['a.m.', 'AM', 'A'], // no descomentar
        //pmNames: ['p.m.', 'PM', 'P'], // no descomentar
        isRTL: false,
        // alex
        timeFormat: 'hh:mm tt',
        amNames: ['A.M.', 'A'],
        pmNames: ['P.M.', 'P'],
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Juv', 'Vie', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
        weekHeader: 'Sm',
        dateFormat: "dd/mm/yy",
        firstDay: 0,
        showMonthAfterYear: false,
        yearSuffix: '',
        yearRange: '1970:2070',
        changeMonth: true,
        numberOfMonths: 1,
        changeYear: true,
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;'
    };
    $.timepicker.setDefaults($.timepicker.regional['es']);
})(jQuery);
