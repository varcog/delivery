var url = "../ALMACEN_INSUMOS_GRUPO_CONTROLLER";
var tabla, tablaInsumoGrupoDetalle;
var insumos;
$(document).ready(function () {
    $('#insumoGrupoDetalleModal').on('shown.bs.modal', opened_pop_insumo_grupo_detalle);
    init();
});

function init() {
    mostrarCargando();
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);

        //////////////// UM 
        var html = "";
        insumos = json.INSUMOS;
        $.each(json.INSUMOS, function (i, insumo) {
            html += "<option value='" + insumo.ID + "' data-pos='" + i + "' class='lin_" + insumo.ID + "'> " + (insumo.CODIGO || "") + "-" + (insumo.DESCRIPCION || "") + "</option>";
        });
        $("#ing_insumos").html(html);
        ocultarCargando();

        // insumos

        html = "";
        $.each(json.INSUMOS_GRUPO, function (i, obj) {
            html += insumoGrupoFilaHtml(obj);
        });
        $("#cuerpo").html(html);

        tabla = $('#tabla').DataTable({
            "language": {
                "url": "../plugins/datatables/i18n/spanish.json"
            }
        });


    });
}

function  insumoGrupoFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='ing_" + obj.ID + "'>";
    tr += "<td>" + (obj.DESCRIPCION || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_insumo_grupo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-gear text-muted' title='Detalle' onclick='pop_insumo_grupo_detalle(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='pop_eliminar_insumo_grupo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_insumo_grupo() {
    $("#ing_id").val(0);
    $("#ing_descripcion").val("").removeClass("bg-error");
    $('#boton_insumo_grupo').text("Crear");
    openModal('#insumoGrupoModal');
}

function guardar_insumo_grupo() {
    mostrarCargando();
    var id = $("#ing_id").val();
    var descripcion = $("#ing_descripcion").val().trim();
    $("#in_descripcion").removeClass("bg-error");
    if (descripcion.length === 0) {
        $("#in_codigo").addClass("bg-error");
        return;
    }
    $.post(url, {
        evento: "guardar_insumo_grupo",
        id: id,
        descripcion: descripcion
    }, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (id_aux > 0) {
                tabla.cell(".ing_" + id_aux + " > td:eq(0)").data((json.DESCRIPCION || ""));
            } else {
                tabla.row.add($(insumoGrupoFilaHtml(json))).draw(false);
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

function pop_modificar_insumo_grupo(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#ing_id").val(id);
    $("#ing_descripcion").val(_tr.find("td:eq(0)").text());
    $('#boton_insumo_grupo').text("Modificar");
    openModal('#insumoGrupoModal');
}

function pop_eliminar_insumo_grupo(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el Insumo Grupo <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_insumo_grupo);
    openModal('#eliminarModal');
}

function eliminar_insumo_grupo() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_insumo_grupo", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row(".ing_" + id).remove().draw(false);
            tabla.rows().invalidate();
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Eliminado Correctamente.");
        }
        ocultarCargando();
        cerrar_modal();
        openModal('#alertModal');
    });
}

function pop_insumo_grupo_detalle(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "insumo_grupo_detalle", id_insumo_grupo: id}, function (resp) {
        $("#ing_insumos").find("option").removeClass("hide");
        $("#ing_id_insumo_grupo").val(id);
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            $("#ing_insumos").find("option.lin_" + obj.ID_INSUMO).addClass("hide");
            html += insumoGrupoDetalleFilaHtml(obj.ID_INSUMO, obj.CANTIDAD);
        });
        $("#cuerpoInsumoGrupoDetalle").html(html);
        $("#insumoGrupoDetalleModalLabel").text($(ele).closest("tr").find("td:eq(0)").text());
        seleccionarInsumoNoHide();
        openModal('#insumoGrupoDetalleModal');
        ocultarCargando();
    });
}

function opened_pop_insumo_grupo_detalle() {
//    if (tablaInsumoGrupoDetalle)
//        tablaInsumoGrupoDetalle.rows().invalidate();
//    else {
//        tablaInsumoGrupoDetalle = $('#tablaSubMenu').DataTable({
//            "language": {
//                "url": "../plugins/datatables/i18n/spanish.json",
//                paging: false
//            }
//        });
////        $("#tablaSubMenu").parent().css("overflow-y", "auto");
//    }
}

function insumoGrupoDetalleFilaHtml(id_insumo, cantidad) {
    var pos = parseInt($("#ing_insumos").find("option.lin_" + id_insumo).data("pos"));
    var ins = insumos[pos];

    var tr = "<tr data-id='" + id_insumo + "' class='ingd_" + id_insumo + "'>";
    tr += "<td>" + (ins.DESCRIPCION || "") + "</td>";
    tr += "<td>" + (ins.UNIDAD_MEDIDA || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<input type='text' class='form-control' value='" + cantidad + "'/>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='eliminar_insumo_grupo_detalle(" + id_insumo + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function agregar_insumo_detalle() {
    var id_insumo = $("#ing_insumos").val();
    $("#ing_insumos").find("option.lin_" + id_insumo).addClass("hide");
    seleccionarInsumoNoHide();
    var fila = insumoGrupoDetalleFilaHtml(id_insumo, 0);
    $("#cuerpoInsumoGrupoDetalle").append(fila);
}

function eliminar_insumo_grupo_detalle(id_insumo, ele) {
    var tr = $(ele).closest("tr");
    $("#ing_insumos").find("option.lin_" + id_insumo).removeClass("hide");
    $(ele).closest("tr").remove();
}

function guardar_insumo_grupo_detalle() {
    mostrarCargando();
    var id_insumo_grupo = $("#ing_id_insumo_grupo").val();
    var lista = [];
    var error = false;
    $("#cuerpoInsumoGrupoDetalle").find("tr").each(function (i, tr) {
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
        evento: "guardar_insumo_grupo_detalle",
        id_insumo_grupo: id_insumo_grupo,
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

