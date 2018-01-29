var url = "../REGISTRO_APROBACION_NOTA_RECEPCION_CONTROLLER";
var tabla, tabla_stock_add, tabla_stock_add_verify;
var html_aux;
$(document).ready(function () {
    tabla = $('#tabla').DataTable({"language": {"url": "../plugins/datatables/i18n/spanish.json"}});
    tabla_stock_add = $('#tabla_stock_add').DataTable({"language": {"url": "../plugins/datatables/i18n/spanish.json"}});
//    tabla_stock_add_verify = $('#tabla_stock_add_verify').DataTable({"language": {"url": "../plugins/datatables/i18n/spanish.json"}});
    todos();
});

function todos(estado) {
    mostrarCargando();
    estado = estado || 0;
    $.post(url, {evento: "todos", estado: estado}, function (resp) {
        tabla.destroy();
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += productoFilaHtml(obj);
        });
        $("#cuerpo").html(html);
        tabla = $('#tabla').DataTable({"language": {"url": "../plugins/datatables/i18n/spanish.json"}});
        ocultarCargando();
    });
}

function productoFilaHtml(obj) {
    var tr = "<tr " + (obj.IMAGEN ? "data-imagen='" + obj.IMAGEN + "'" : "") + " data-id='" + obj.ID + "' class='producto_" + obj.ID + "'>";
    tr += "<td>" + (obj.NOMBRE || "") + "</td>";
    tr += "<td class='text-right'>" + obj.PRECIO_COMPRA + "</td>";
    tr += "<td class='text-right'>" + obj.PRECIO_VENTA + "</td>";
    tr += "<td class='text-center'>";
    if (obj.IMAGEN)
        tr += "<img src='" + obj.IMAGEN + "' class='x70' />";
    tr += "</td>";
    tr += "<td class='text-right'>" + obj.CANTIDAD + "</td>";
    tr += "</tr>";
    return tr;
}

function ver_todos_stock(ele) {
    var estado = parseInt($(ele).data("estado"));
    if (estado === 0) {
        $(ele).text("Ver Todos");
        $(ele).data("estado", 1);
        estado = 1;
    } else {
        $(ele).text("Ver Solo en Stock");
        $(ele).data("estado", 0);
        estado = 0;
    }
    todos(estado);
}

function pop_aumentar_stock() {
    mostrarCargando();
    tabla_stock_add.destroy();
    $.post(url, {evento: "todos_productos"}, function (resp) {
        var html = "";
        var json = $.parseJSON(resp);
        $.each(json, function (i, obj) {
            html += "<tr " + (obj.IMAGEN ? "data-imagen='" + obj.IMAGEN + "'" : "") + " data-id='" + obj.ID + "' class='producto_" + obj.ID + "'>";
            html += "<td>" + (obj.NOMBRE || "") + "</td>";
            html += "<td class='text-center'>";
            if (obj.IMAGEN)
                html += "<img src='" + obj.IMAGEN + "' class='x70' />";
            html += "</td>";
            html += "<td><input type='number' data-id='" + obj.ID + "' class='stock_cantidad text-right' value='0'/></td>";
            html += "</tr>";
        });
        html_aux = html;
        $("#cuerpo_stock_add").html(html_aux);
        $(".stock_cantidad").solo_numeros();
        tabla_stock_add = $('#tabla_stock_add').DataTable({"language": {"url": "../plugins/datatables/i18n/spanish.json"}});
        openModal('#aumentarStockModal');
        ocultarCargando();
    });
}

var lista_select;
function verificar_aumentar_stock() {
    mostrarCargando();
//    tabla_stock_add_verify.detroy();
    var ll = tabla_stock_add.data().length;
    var input, valor;
    lista_select = [];
    var html = "";
    for (var i = 0; i < ll; i++) {
        input = $(tabla_stock_add.cell(i, 2).node()).children();
        valor = parseInt(input.val());
        if (!isNaN(valor) && valor > 0) {
            html += "<tr>";
            html += "<td>" + tabla_stock_add.cell(i, 0).data() + "</td>";
            html += "<td class='text-center'>" + tabla_stock_add.cell(0, 1).data() + "</td>";
            html += "<td class='text-right'>" + valor + "</td>";
            html += "</tr>";
            lista_select.push({
                id: input.data("id"),
                cantidad: valor
            });
        }
    }
    if (lista_select.length > 0) {
        $("#cuerpo_stock_add_verify").html(html);
    } else {
        $("#alertModalLabel").text("Alerta");
        $("#alertModalText").text("Por lo menos un producto debe tener cantidad mayor a 0.");
        openModal('#alertModal');
    }
    ocultarCargando();
}

function aumentar_stock() {
    mostrarCargando();
    $.post(url, {evento: "aumentar_stock", productos: lista_select}, function (resp) {
        ocultarCargando();
    });
}

function pop_reducir_stock() {

}

function reducir_stock() {

}




function pop_registrar_producto() {
    $("#c_id").val(0);
    $("#c_descripcion").val("");
    $("#c_precio_compra").val("0");
    $("#c_precio_venta").val("0");
    $('#c_imagen').val(null);
    $('#c_imagen_ver').removeAttr("src");
    $('#c_imagen_ver').addClass("hidden");
    $('#c_boton_producto').text("Crear Producto");
    openModal('#productoModal');
}

function guardar_producto() {
    if ($("#c_descripcion").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario la Descripción del producto");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    mostrarCargando();
    var formData = new FormData($("#formregistrarProducto")[0]);
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false,
        success: function (resp, textStatus, jqXHR)
        {
            if (resp === "false") {
                $("#alertModalLabel").text("Alerta");
                $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
            } else {
                var id_aux = parseFloat($("#c_id").val());
                var json = $.parseJSON(resp);
                if (id_aux > 0) {
                    tabla.cell(".producto_" + id_aux + " > td:eq(0)").data((json.NOMBRE || ""));
                    tabla.cell(".producto_" + id_aux + " > td:eq(1)").data(json.PRECIO_COMPRA);
                    tabla.cell(".producto_" + id_aux + " > td:eq(2)").data(json.PRECIO_VENTA);
                    if (json.IMAGEN)
                        $(".producto_" + id_aux).attr("data-imagen", json.IMAGEN);
                    tabla.rows().invalidate();
                } else {
                    tabla.row.add($(productoFilaHtml(json))).draw(false);
                }

                $("#alertModalLabel").text("Información");
                $("#alertModalText").text("Guardado Correctamente.");
                cerrar_modal();
            }
            ocultarCargando();
            openModal('#alertModal');
        },
        error: function (jqXHR, textStatus, errorThrown)
        {
            ocultarCargando();
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
            $("#alertModalLabel").text("Alerta");
            openModal('#alertModal');
        }
    });
}

function pop_modificar_producto(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#c_id").val(id);
    $("#c_descripcion").val(_tr.find("td:eq(0)").text());
    $("#c_precio_compra").val(_tr.find("td:eq(1)").text());
    $("#c_precio_venta").val(_tr.find("td:eq(2)").text());
    $('#c_imagen').val(null);
    if (_tr.data("imagen")) {
        $('#c_imagen_ver').attr("src", _tr.data("imagen"));
        $('#c_imagen_ver').removeClass("hidden");
    } else {
        $('#c_imagen_ver').removeAttr("src");
        $('#c_imagen_ver').addClass("hidden");
    }
    $('#c_boton_producto').text("Modificar Producto");
    openModal('#productoModal');
}

function pop_eliminar_producto(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el producto <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    openModal('#eliminarModal');
}

function eliminar_producto() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_producto", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row(".producto_" + id).remove().draw(false);
            tabla.rows().invalidate();
//            tabla.row(".producto_" + id).remove().draw(false);
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Eliminado Correctamente.");
        }
        ocultarCargando();
        cerrar_modal();
        openModal('#alertModal');
    });
}

function subir_imagen(ele) {
    $(ele).prev().click();
}
function ver_imagen(ele) {
    var imagen = $("#c_imagen_ver");
    var file = ele.files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        imagen.attr("src", reader.result);
        imagen.removeClass("hidden");
    };

    if (file) {
        reader.readAsDataURL(file);
    } else {
        imagen.removeAttr("src");
        imagen.addClass("hidden");
    }
}
