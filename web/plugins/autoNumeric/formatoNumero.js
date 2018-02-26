var clase_formato_decimal = "numero_decimal";
var selector_formato_decimal = "." + clase_formato_decimal;
var no_mutar;

function formato_decimal(selector, formato) {
    selector = selector || ".numero_decimal";
    var forma = formato || {};
    var defa = {
        mDec: 50,
        aPad: false,
        aSep: '.',
        dGroup: '3',
        aDec: ','
    };
    $.extend(true, defa, forma);
    $(selector).autoNumeric('init', defa);
}

function formato_decimal2(selector) {
    selector = selector || ".numero_decimal2";
    var forma = {
        mDec: 2,
        aPad: true
    };
    formato_decimal(selector, forma);
}

function formato_decimal_all() {
    formato_decimal();
    formato_decimal2();
}

function formato_decimal_conta(selector, formato) {
    selector = selector || ".numero_decimal";
    var forma = formato || {};
    var defa = {
        mDec: 50,
        aPad: false,
        aSep: ',',
        dGroup: '3',
        aDec: '.'
    };
    $.extend(true, defa, forma);
    $(selector).autoNumeric('init', defa);
}

function formato_decimal2_conta(selector) {
    selector = selector || ".numero_decimal2";
    var forma = {
        mDec: 2,
        aPad: true
    };
    formato_decimal_conta(selector, forma);
}

function formato_decimal_all_conta() {
    formato_decimal_conta();
    formato_decimal2_conta();
}

function format_to_Number(valorString) {
    return valorString.replaceAll(".", "").replaceAll(",", ".");
}

function format_to_Number_conta(valorString) {
    return  valorString.replaceAll(",", "");
}

var identifi_formateador_numero_GER2 = undefined;
function getformatoNumeroDecimal2(numero) {
    if (!identifi_formateador_numero_GER2) {
        identifi_formateador_numero_GER2 = "GER" + new Date().getTime() + "STRASOL2";
        $("body").append("<div style='display:none;' id='" + identifi_formateador_numero_GER2 + "'></div>");
        formato_decimal2("#" + identifi_formateador_numero_GER2);
    }
    $("#" + identifi_formateador_numero_GER2).autoNumeric("set", numero);
    return $("#" + identifi_formateador_numero_GER2).text();
}
var identifi_formateador_numero_GER = undefined;
function getformatoNumeroDecimal(numero) {
    if (!identifi_formateador_numero_GER) {
        identifi_formateador_numero_GER = "GER" + new Date().getTime() + "STRASOL";
        $("body").append("<div style='display:none;' id='" + identifi_formateador_numero_GER + "'></div>");
        formato_decimal("#" + identifi_formateador_numero_GER);
    }
    $("#" + identifi_formateador_numero_GER).autoNumeric("set", numero);
    return $("#" + identifi_formateador_numero_GER).text();
}

// ------------------------------------------------- BIG DECIMAL

if (typeof BigNumber !== 'undefined') {
    BigNumber.config({
        FORMAT: {
            decimalSeparator: ',',
            groupSeparator: '.',
            groupSize: 3
        }
    });

    BigNumber.prototype.toFixedG = function (dp, rm) {
        if (typeof dp === 'undefined') {
            var monto = +this;
            return monto + "";
        } else {
            return BigNumber.prototype.toFixed.apply(this, Array.prototype.slice.call(arguments));

        }
    };
}

(function ($) {
    $.fn.autoNumericSet = function (valor) {
        var $this = $(this),
                settings = $this.data('autoNumeric');
        if (typeof settings !== 'object') {
            console.log("You must initialize autoNumeric('init', {options}) prior to calling the 'get' method");
            $this.text(valor);
        } else {
            $this.autoNumeric("set", valor);
        }
        return $this;
    };
    $.fn.autoNumericGet = function (mensaje, aceptar, cancelar, opciones) {
        var $this = $(this),
                settings = $this.data('autoNumeric');
        if (typeof settings !== 'object') {
            console.log("You must initialize autoNumeric('init', {options}) prior to calling the 'get' method");
            return $this.text();
        } else {
            return $this.autoNumeric("get");
        }
    };
})(jQuery);





