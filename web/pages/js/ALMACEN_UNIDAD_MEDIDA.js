var url = "../ALMACEN_UNIDAD_MEDIDA_CONTROLLER";
var tabla, tablaSubMenu;
$(document).ready(function () {
    init();
});

function init() {
    mostrarCargando();
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += unidadMedidaFilaHtml(obj);
        });
        $("#cuerpo").html(html);

        tabla = $('#tabla').DataTable({
            "language": {
                "url": "../plugins/datatables/i18n/spanish.json"
            }
        });
        ocultarCargando();
    });
}

function unidadMedidaFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='um_" + obj.ID + "'>";
    tr += "<td>" + (obj.DESCRIPCION || "") + "</td>";
    tr += "<td>" + (obj.ABREVIACION || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_um(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='pop_eliminar_um(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_unidad_medida() {
    $("#um_id").val(0);
    $("#um_descripcion").val("").removeClass("bg-error");
    $("#um_abreviacion").val("").removeClass("bg-error");
    $('#boton_unidad_medida').text("Crear");
    openModal('#unidadMedidaModal');
}

function guardar_unidad_medida() {
    mostrarCargando();
    var id = $("#um_id").val();
    var descripcion = $("#um_descripcion").val().trim();
    var abreviacion = $("#um_abreviacion").val().trim();
    var error = false;
    $("#um_descripcion").removeClass("bg-error");
    $("#um_abreviacion").removeClass("bg-error");
    if (descripcion.length === 0) {
        $("#um_descripcion").addClass("bg-error");
        error = true;
    }
    if (abreviacion.length === 0) {
        $("#um_abreviacion").addClass("bg-error");
        error = true;
    }

    if (error) {
        ocultarCargando();
        return;
    }
    $.post(url, {evento: "guardar_undidad_medida", id: id, descripcion: descripcion, abreviacion: abreviacion}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (id_aux > 0) {
                tabla.cell(".um_" + id_aux + " > td:eq(0)").data((json.DESCRIPCION || ""));
                tabla.cell(".um_" + id_aux + " > td:eq(1)").data((json.ABREVIACION || ""));
            } else {
                tabla.row.add($(unidadMedidaFilaHtml(json))).draw(false);
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

function pop_modificar_um(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#um_id").val(id);
    $("#um_descripcion").val(_tr.find("td:eq(0)").text()).removeClass("bg-error");
    $("#um_abreviacion").val(_tr.find("td:eq(1)").text()).removeClass("bg-error");
    $('#boton_unidad_medida').text("Modificar");
    openModal('#unidadMedidaModal');
}

function pop_eliminar_um(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar la Unidad de Medida <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_unidad_medida);
    openModal('#eliminarModal');
}

function eliminar_unidad_medida() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_unidad_medida", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row(".um_" + id).remove().draw(false);
            tabla.rows().invalidate();
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Eliminado Correctamente.");
        }
        ocultarCargando();
        cerrar_modal();
        openModal('#alertModal');
    });
}

