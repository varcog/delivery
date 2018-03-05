var url = "../VENTA_REGISTRAR_CATEGORIA_CONTROLLER";
$(document).ready(function () {
    init();
});

function init() {
    mostrarCargando();
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);
        var html;
        $.each(json, function (i, obj) {
            html = categoriaFilaHtml(obj);
            $("#ul_cat_" + obj.ID_CATEGORIA_PRODUCTO).append(html);
        });
        $('#ul_cat_0').treed({openedClass: 'glyphicon-chevron-right', closedClass: 'glyphicon-chevron-down'});
        ocultarCargando();
    });
}

function categoriaFilaHtml(obj) {
    var html = "<li data-id='" + obj.ID + "' data-id_categoria_producto='" + obj.ID_CATEGORIA_PRODUCTO + "' " + (obj.IMAGEN ? "data-imagen='" + obj.IMAGEN + "'" : "") + ">";
    html += "<i class='fa fa-plus-circle text-success fs_img' title='ADICIONAR'></i>";
    html += "<i class='fa fa-edit text-warning fs_img' title='MODIFICAR'></i>";
    html += "<i class='fa fa-remove text-danger fs_img' title='ELIMINAR'></i>";
    html += "<input type='checkbox' class='fs_img' title='ESTADO'/>";
    html += "<span class='nombre_cat'>" + obj.NOMBRE + "</span>";
    html += "<ul id='ul_cat_" + obj.ID + "'></ul>";
    html += "</li>";
    return html;
}

function pop_registrar_producto() {
    $("#c_id").val(0);
    $("#c_codigo").val("");
    $("#c_descripcion").val("");
    $("#c_precio_venta").val("0");
    $('#c_imagen').val(null);
    $('#c_imagen_ver').removeAttr("src");
    $('#c_imagen_ver').addClass("hidden");
    $('#c_boton_producto').text("Crear Producto");
    openModal('#productoModal');
}

function guardar_producto() {
    if ($("#c_codigo").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario el Código del producto");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }

    if ($("#c_descripcion").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario la Descripción del producto");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    mostrarCargando();
    var value = $("#c_precio_venta").val();
    $("#c_precio_venta").autoNumeric("destroy");
    $("#c_precio_venta").val(value);
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
            formato_decimal("#c_precio_venta");
            if (resp === "false") {
                $("#alertModalLabel").text("Alerta");
                $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
            } else if (resp === "CODIGO_REPETIDO") {
                $("#alertModalLabel").text("Alerta");
                $("#alertModalText").html("El <span class='text-bold'>CODIGO</span> ya existe");
            } else {
                var id_aux = parseFloat($("#c_id").val());
                var json = $.parseJSON(resp);
                if (id_aux > 0) {
                    tabla.cell(".producto_" + id_aux + " > td:eq(0)").data((json.CODIGO || ""));
                    tabla.cell(".producto_" + id_aux + " > td:eq(1)").data((json.NOMBRE || ""));
                    $(tabla.cell(".producto_" + id_aux + " > td:eq(3)")).autoNumeric("set", json.PRECIO_VENTA + "");
                    if (json.IMAGEN) {
                        $(".producto_" + id_aux).attr("data-imagen", json.IMAGEN);
                        $(".producto_" + id_aux).data("imagen", json.IMAGEN);
                        tabla.cell(".producto_" + id_aux + " > td:eq(2)").data("<img src='" + json.IMAGEN + "' class='x70'>");
                    } else {
                        tabla.cell(".producto_" + id_aux + " > td:eq(2)").data("");
                    }
                    tabla.rows().invalidate();
                } else {
                    var tr = $(categoriaFilaHtml(json));
                    formato_decimal(tr.find(".numero_decimal"));
                    tabla.row.add(tr).draw(false);
                }
                tabla.rows().invalidate();

                $("#alertModalLabel").text("Información");
                $("#alertModalText").text("Guardado Correctamente.");
                cerrar_modal();
            }
            ocultarCargando();
            openModal('#alertModal');
        },
        error: function (jqXHR, textStatus, errorThrown)
        {
            formato_decimal("#c_precio_venta");
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
    $("#c_codigo").val(_tr.find("td:eq(0)").text());
    $("#c_descripcion").val(_tr.find("td:eq(1)").text());
    $("#c_precio_venta").val(_tr.find("td:eq(3)").autoNumeric("get"));
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
    $("#eliminarModalText").html("¿Esta seguro de eliminar el producto <strong>" + _tr.find("td:eq(0)").text() + "</strong> - " + _tr.find("td:eq(1)").text() + "?\n")
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


//<editor-fold defaultstate="collapsed" desc="INSUMOS">
function pop_insumos(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "insumo_producto", id_insumo_grupo: id}, function (resp) {
        $("#ing_insumos").find("option").removeClass("hide");
        $("#ing_id_producto").val(id);
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            $("#ing_insumos").find("option.lin_" + obj.ID_INSUMO).addClass("hide");
            html += insumoProductoDetalleFilaHtml(obj.ID_INSUMO, obj.CANTIDAD);
        });
        $("#cuerpoInsumoProductoDetalle").html(html);
        $("#insumoGrupoDetalleModalLabel").text($(ele).closest("tr").find("td:eq(1)").text());
        seleccionarInsumoNoHide();
        openModal('#insumoGrupoDetalleModal');
        ocultarCargando();
    });
}

function insumoProductoDetalleFilaHtml(id_insumo, cantidad) {
    var pos = parseInt($("#ing_insumos").find("option.lin_" + id_insumo).data("pos"));
    var ins = insumos[pos];

    var tr = "<tr data-id='" + id_insumo + "' class='ingd_" + id_insumo + "'>";
    tr += "<td>" + (ins.DESCRIPCION || "") + "</td>";
    tr += "<td>" + (ins.UNIDAD_MEDIDA || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<input type='text' class='form-control' value='" + cantidad + "'/>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='eliminar_insumo_producto(" + id_insumo + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function agregar_insumo_producto() {
    var id_insumo = $("#ing_insumos").val();
    $("#ing_insumos").find("option.lin_" + id_insumo).addClass("hide");
    seleccionarInsumoNoHide();
    var fila = insumoProductoDetalleFilaHtml(id_insumo, 0);
    $("#cuerpoInsumoProductoDetalle").append(fila);
}

function eliminar_insumo_producto(id_insumo, ele) {
    var tr = $(ele).closest("tr");
    $("#ing_insumos").find("option.lin_" + id_insumo).removeClass("hide");
    $(ele).closest("tr").remove();
}

function guardar_insumo_producto() {
    mostrarCargando();
    var id_producto = $("#ing_id_producto").val();
    var lista = [];
    var error = false;
    $("#cuerpoInsumoProductoDetalle").find("tr").each(function (i, tr) {
        var cantidad = parseFloat($(tr).find("input").val());
        if (!isNaN(cantidad)) {
            lista.push({
                id_insumo: $(tr).data("id"),
                cantidad: cantidad
            });
        }

    });
    if (error) {
        ocultarCargando();
        return;
    }
    $.post(url, {
        evento: "guardar_insumo_producto",
        id_producto: id_producto,
        lista: lista,
        length: lista.length
    }, function (resp) {
        ocultarCargando();
        cerrar_modal();
    });
}

function seleccionarInsumoNoHide() {
    var lista = $("#ing_insumos").find("option:not(.hide)");
    if (lista.length > 0) {
        lista.eq(0).prop("selected", true);
        $("#agregar_insumo_detalle").prop("disabled", false);
    } else {
        $("#agregar_insumo_detalle").prop("disabled", true);
    }
}
//</editor-fold>