var dataTable_conf2 = {
    "language": {
//        "decimal":        "",
        "emptyTable": "No hay datos disponibles en la tabla",
        "info": "Mostrando de _START_ a _END_ de _TOTAL_ filas",
        "infoEmpty": "Mostrando de 0 a 0 de 0 filas",
        "infoFiltered": "(filtradas desde _MAX_ total filas)",
//        "infoPostFix":    "",
//        "thousands":      ",",
        "lengthMenu": "Mostrar _MENU_ filas por página",
        "loadingRecords": "Cargando...",
        "processing": "Procesando...",
        "search": "Buscar:",
        "zeroRecords": "No se encontraron registros coincidentes",
        "paginate": {
            "first": "Primero",
            "last": "Ultimo",
            "next": "Siguiente",
            "previous": "Anterior"
        },
        "aria": {
            "sortAscending": ": activar para ordenar la columna ascendente",
            "sortDescending": ": activar para ordenar la columna descendente"
        }
    }
};
var dataTable_conf = {
    "language": {"url": "../plugins/datatables/i18n/spanish.json"},
    responsive: true
};


function mostrarCargando() {
    if ($("#div_cargando_background").length === 0) {
        $("body").append("<div class='overlay-wrapper cargando d-none' id='div_cargando_background'>"
                + " <div class='overlay'>"
                + "     <i class='fa fa-refresh fa-spin'></i>"
                + " </div>"
                + "</div>");
    }
    $("#div_cargando_background").removeClass("hidden");
}

function ocultarCargando() {
    $("#div_cargando_background").addClass("hidden");
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//<editor-fold defaultstate="collapsed" desc="FORMATO FECHA">
;
(function ($) {
    $.fn.calendario = function () {
        return $(this).each(function () {
            if (!$(this).data("calendario")) {
                $(this).inputmask();
                $(this).datepicker({
                    format: 'dd/mm/yyyy',
                    language: "es",
                    autoclose: true,
                    todayHighlight: true,
                    disableTouchKeyboard: true,
                    todayBtn: true
                });
                $(this).data("calendario", true);
            }
        });
    };

    $.fn.calendario_hora = function () {
        return $(this).each(function () {
            if (!$(this).data("calendario_hora")) {
                $(this).daterangepicker({
                    singleDatePicker: true,
                    showDropdowns: true,
                    timePicker: true,
                    timePicker24Hour: true,
                    locale: {
                        format: "DD/MM/YYYY HH:mm",
                        //"separator": " - ",
                        applyLabel: "GUARDAR",
                        cancelLabel: "CANCELAR",
                        "fromLabel": "From",
                        "toLabel": "To",
                        "customRangeLabel": "Custom",
                        "weekLabel": "s",
                        "daysOfWeek": [
                            "Do",
                            "Lu",
                            "Ma",
                            "Mi",
                            "Ju",
                            "Vi",
                            "Sa"
                        ],
                        "monthNames": [
                            "Enero",
                            "Febrero",
                            "Marzo",
                            "Abril",
                            "Mayo",
                            "Junio",
                            "Julio",
                            "Augosto",
                            "Septiembre",
                            "Octubre",
                            "Noviembre",
                            "Deciembre"
                        ],
                        "firstDay": 1
                    }
                });
                $(this).data("calendario_hora", true);
            }
        });
    };
}(jQuery));

//<editor-fold defaultstate="collapsed" desc="VALIDACION FECHAS">
/**
 * Funcion que devuelve true o false dependiendo de si la fecha es correcta.
 * Tiene que recibir el dia, mes y año
 */
function isValidDate(day, month, year) {
    var dteDate;

    // En javascript, el mes empieza en la posicion 0 y termina en la 11 
    //   siendo 0 el mes de enero
    // Por esta razon, tenemos que restar 1 al mes
    month = month - 1;
    // Establecemos un objeto Data con los valore recibidos
    // Los parametros son: año, mes, dia, hora, minuto y segundos
    // getDate(); devuelve el dia como un entero entre 1 y 31
    // getDay(); devuelve un num del 0 al 6 indicando siel dia es lunes,
    //   martes, miercoles ...
    // getHours(); Devuelve la hora
    // getMinutes(); Devuelve los minutos
    // getMonth(); devuelve el mes como un numero de 0 a 11
    // getTime(); Devuelve el tiempo transcurrido en milisegundos desde el 1
    //   de enero de 1970 hasta el momento definido en el objeto date
    // setTime(); Establece una fecha pasandole en milisegundos el valor de esta.
    // getYear(); devuelve el año
    // getFullYear(); devuelve el año
    dteDate = new Date(year, month, day);

    //Devuelva true o false...
    return ((day == dteDate.getDate()) && (month == dteDate.getMonth()) && (year == dteDate.getFullYear()));
}

/**
 * Funcion para validar una fecha
 * Tiene que recibir:
 *  La fecha en formato dd/mm/yyyy
 * Devuelve:
 *  true-Fecha correcta
 *  false-Fecha Incorrecta
 */
function validate_fecha(fecha) {
//    var RegExPattern = /^\d{1,2}\/\d{1,2}\/\d{2,4}$/;
//      if ((campo.match(RegExPattern)) && (campo!='')) {
    var patron = new RegExp("^([0-9]{1,2})([/])([0-9]{1,2})([/])([1-9])([0-9]{3})$");
    if (fecha && fecha.search(patron) == 0) {
        var values = fecha.split("/");
        if (isValidDate(values[0], values[1], values[2]))
            return true;
    }
    return false;
}
function validate_fecha_mes_ano(fecha) {
//    var RegExPattern = /^\d{1,2}\/\d{1,2}\/\d{2,4}$/;
//      if ((campo.match(RegExPattern)) && (campo!='')) {
    var patron = new RegExp("^([0-9]{1,2})([/])([1-9])([0-9]{3})$");
    if (fecha && fecha.search(patron) == 0) {
        var values = fecha.split("/");
        if (isValidDate(1, values[0], values[1]))
            return true;
    }
    return false;
}

function validate_fechaMayorQue(fechaInicial, fechaFinal) {
    var valuesStart = fechaInicial.split("/");
    var valuesEnd = fechaFinal.split("/");
    // Verificamos que la fecha no sea posterior a la actual
    var dateStart = new Date(valuesStart[2], (valuesStart[1] - 1), valuesStart[0]);
    var dateEnd = new Date(valuesEnd[2], (valuesEnd[1] - 1), valuesEnd[0]);
    if (dateStart >= dateEnd)
        return 0;
    return 1;
}

function daysInMonth(mes, ano) {
    return new Date(ano || new Date().getFullYear(), mes, 0).getDate();
}

function calculaNumeroDiaSemana(dia, mes, ano) {
    var objFecha = new Date(ano || new Date().getFullYear(), mes - 1, dia);
    var numDia = objFecha.getDay();
    if (numDia == 0)
        numDia = 6;
    else
        numDia--;
    return numDia;
}
function dias_entre_Fechas(f1, f2) {
    try {
        var aFecha1 = f1.split('/');
        var aFecha2 = f2.split('/');
        var fFecha1 = Date.UTC(aFecha1[2], aFecha1[1] - 1, aFecha1[0]);
        var fFecha2 = Date.UTC(aFecha2[2], aFecha2[1] - 1, aFecha2[0]);
        var dif = fFecha2 - fFecha1;
        var dias = Math.floor(dif / (1000 * 60 * 60 * 24));
        return dias;
    } catch (e) {
        return 0;
    }
}
function dias_entre_Fechas_Date(f1, f2) {
    var dif = f2.getTime() - f1.getTime();
    var dias = Math.floor(dif / (1000 * 60 * 60 * 24));
    return dias;
}
function validarFechaMenor(mayor1, menor1) {
    var a = mayor1.split("/");
    var b = menor1.split("/");
    var mayor = new Date(a[2], a[1] - 1, a[0]);
    var menor = new Date(b[2], b[1] - 1, b[0]);
    return menor <= mayor;
}
/**
 * Compara dos fechas<br>
 * Formato de las fechas dd/MM/yyyy
 * @param {String} f1
 * @param {String} f2
 * @returns {Number}  &nbsp;0: f1 = f2<br>-1: f1 &lt; f2<br> &nbsp;1: f1 > f2
 */
function comprarFechas(f1, f2) {
    var a = f1.split("/");
    var b = f2.split("/");
    var d1 = new Date(a[2], a[1] - 1, a[0]);
    var d2 = new Date(b[2], b[1] - 1, b[0]);
    if (d1 === d2)
        return 0;
    if (d1 > d2)
        return 1;
    return -1;
}

function stringToDate(fecha, formato) {
    if (formato === undefined) {
        formato = "dd/MM/yyyy";
    }
    var sf1 = fecha.split('/');
    var fFecha1 = new Date(sf1[2], sf1[1] - 1, sf1[0]);
    return fFecha1;
}
//</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BUSQUEDA">
function buscar(ele, selector) {
    if (!selector)
        selector = ".info-box";
    var texto = ele.value.trim();
    if (texto.length === 0) {
        $(selector).parent().removeClass("hidden");
    } else {
        var rex = new RegExp(texto.toUpperCase());
        $(selector).each(function (i, div) {
            var res = false;
            $(div).find(".busqueda").each(function (j, span) {
                var to = $(span).text().trim();
                if (to.length > 0 && rex.test(to.toUpperCase())) {
                    res = true;
                    return false;
                }
            });
            if (res)
                $(div).parent().removeClass("hidden");
            else
                $(div).parent().addClass("hidden");
        });
    }
}
//</editor-fold>

function addError(selector) {
    $(selector)[0].style.setProperty("background", "#dd4b39", "important");
}

function removeError(selector) {
    $(selector).css("background", "");
}

(function ($) {

    $.fn.addClassError = function () {
        return $(this).each(function () {
            $(this)[0].style.setProperty("background", "#dd4b39", "important");
        });
    };

    $.fn.removeClassError = function () {
        return $(this).each(function () {
            $(this).css("background", "");
        });

    };

    $.fn.solo_numeros = function () {
        return $(this).off('input').on('input', function () {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    };


})(jQuery);

/* *****************************************************************************
 *                              MODAL
 * *****************************************************************************
 */
var modales_cola = [];
function openModal(modal) {
    if (modales_cola.length > 0) {
        var mm = modales_cola[modales_cola.length - 1];
        $(mm).off("hidden.bs.modal");
        $(mm).modal('hide');
    }
    modales_cola.push(modal);
    $(modal).off("hidden.bs.modal");
    $(modal).on('hidden.bs.modal', function (e) {
        cerrar_modal();
    });
    $(modal).modal('show');
}

function cerrar_modal() {
    var modal = modales_cola.pop();
    if (modal) {
        $(modal).off("hidden.bs.modal");
        $(modal).modal('hide');
    }
    if (modales_cola.length > 0) {
        var modal = modales_cola[modales_cola.length - 1];
        $(modal).off("hidden.bs.modal");
        $(modal).on('hidden.bs.modal', function (e) {
            cerrar_modal();
        });
        $(modal).modal('show');
    }
}

