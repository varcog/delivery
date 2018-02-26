var url = "../ALMACEN_INSUMOS_CONTROLLER";
var tabla, tablaSubMenu;
$(document).ready(function () {
    init();
});

function init() {
    mostrarCargando();
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);

        //////////////// UM 
        var html = "";
        $.each(json.UNIDAD_MEDIDAS, function (i, um) {
            html += "<option value='" + um.ID + "'> " + um.DESCRIPCION + "</option>";
        });
        $("#in_unidad_medida").html(html);
        ocultarCargando();

        // insumos

        html = "";
        $.each(json.INSUMOS, function (i, obj) {
            html += insumoFilaHtml(obj);
        });
        $("#cuerpo").html(html);

        tabla = $('#tabla').DataTable({
            "language": {
                "url": "../plugins/datatables/i18n/spanish.json"
            }
        });


    });
}

function  insumoFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='in_" + obj.ID + "'>";
    tr += "<td>" + (obj.CODIGO || "") + "</td>";
    tr += "<td>" + (obj.DESCRIPCION || "") + "</td>";
    tr += "<td>" + $("#in_unidad_medida").find("option[value='" + obj.ID_UNIDAD_MEDIDA + "']").text() + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_insumo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='pop_eliminar_insumo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_insumo() {
    $("#in_id").val(0);
    $("#in_codigo").val("").removeClass("bg-error");
    $("#in_descripcion").val("").removeClass("bg-error");
    $("#in_unidad_medida").find("option:first").prop("selected", true);
    $('#boton_insumo').text("Crear");
    openModal('#insumoModal');
}

function guardar_insumo() {
    mostrarCargando();
    var id = $("#in_id").val();
    var codigo = $("#in_codigo").val().trim();
    var descripcion = $("#in_descripcion").val().trim();
    var unidad_medida = $("#in_unidad_medida").val();
    var error = false;
    $("#in_codigo").removeClass("bg-error");
    $("#in_descripcion").removeClass("bg-error");
    if (codigo.length === 0) {
        $("#in_codigo").addClass("bg-error");
        error = true;
    }
    if (descripcion.length === 0) {
        $("#in_descripcion").addClass("bg-error");
        error = true;
    }

    if (error) {
        ocultarCargando();
        return;
    }
    $.post(url, {
        evento: "guardar_insumo",
        id: id,
        descripcion: descripcion,
        codigo: codigo,
        unidad_medida: unidad_medida
    }, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else if (resp === "CODIGO_REPETIDO") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").html("El <span class='text-bold'>CODIGO</span> ya existe");
            $("#in_codigo").removeClass("bg-error");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (id_aux > 0) {
                tabla.cell(".in_" + id_aux + " > td:eq(0)").data((json.CODIGO || ""));
                tabla.cell(".in_" + id_aux + " > td:eq(1)").data((json.DESCRIPCION || ""));
                tabla.cell(".in_" + id_aux + " > td:eq(2)").data($("#in_unidad_medida").find("option[value='" + json.ID_UNIDAD_MEDIDA + "']").text());
            } else {
                tabla.row.add($(insumoFilaHtml(json))).draw(false);
            }
            tabla.rows().invalidate();
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Guardado Correctamente.");
            cerrar_modal();
        }
        ocultarCargando();
        openModal('#alertModal');
    });
}

function pop_modificar_insumo(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "datos_insumo", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            var json = $.parseJSON(resp);
            $("#in_id").val(id);
            $("#in_codigo").val(json.CODIGO).removeClass("bg-error");
            $("#in_descripcion").val(json.DESCRIPCION).removeClass("bg-error");
            $("#in_unidad_medida").val(json.ID_UNIDAD_MEDIDA);
            $('#boton_insumo').text("Modificar");
            openModal('#insumoModal');
        }
        ocultarCargando();
    });
}

function pop_eliminar_insumo(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el Insumo <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_insumo);
    openModal('#eliminarModal');
}

function eliminar_insumo() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_insumo", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row(".in_" + id).remove().draw(false);
            tabla.rows().invalidate();
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Eliminado Correctamente.");
        }
        ocultarCargando();
        cerrar_modal();
        openModal('#alertModal');
    });
}

