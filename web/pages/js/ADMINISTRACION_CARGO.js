var url = "../ADMINISTRACION_CARGO_CONTROLLER";
var tabla, tablaSubMenu;
$(document).ready(function () {
    init();
});

function init() {
    mostrarCargando();
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json.CARGOS, function (i, obj) {
            html += cargoFilaHtml(obj);
        });
        $("#cuerpo").html(html);

        tabla = $('#tabla').DataTable({
            "language": {
                "url": "../plugins/datatables/i18n/spanish.json"
            }
        });

        //////////////// MENU 
        html = "";
        $.each(json.MENUS, function (menu, sub_menus) {
            if (sub_menus.length > 0) {
                html += "<li><span class='text-bold'>" + menu + "</span>";
                html += "<ul>";
                $.each(sub_menus, function (i, sm) {
                    html += "<li>";
                    html += "<input type='checkbox' id='sm_" + sm.ID + "' class='sm_as' onclick='asignar_desasignar_sub_menu(" + sm.ID + ", this)'/>";
                    html += "<label for='sm_" + sm.ID + "' class='text-normal'>" + sm.DESCRIPCION + "</label>";
                    html += "</li>";
                });
            }
            html += "</ul>";
            html += "</li>";
        });
        $("#lista_menu_as").html(html);
        ocultarCargando();
    });
}

function cargoFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='cargo_" + obj.ID + "'>";
    tr += "<td>" + (obj.DESCRIPCION || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_cargo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-gear text-muted' title='Ver Sub Menu' onclick='pop_permiso_cargo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='pop_eliminar_cargo(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_cargo() {
    $("#c_id").val(0);
    $("#c_descripcion").val("");
    $('#boton_cargo').text("Crear Cargo");
    openModal('#cargoModal');
}

function guardar_cargo() {
    if ($("#c_descripcion").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario la descripcion del Cargo");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    var id = $("#c_id").val();
    var descripcion = $("#c_descripcion").val();
    mostrarCargando();
    $.post(url, {evento: "guardar_cargo", id: id, descripcion: descripcion}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (id_aux > 0) {
                tabla.cell(".cargo_" + id_aux + " > td:eq(0)").data((json.DESCRIPCION || ""));
                tabla.rows().invalidate();
            } else {
                tabla.row.add($(cargoFilaHtml(json))).draw(false);
            }
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Guardado Correctamente.");
            cerrar_modal();
        }
        ocultarCargando();
        openModal('#alertModal');
    });
}

function pop_modificar_cargo(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#c_id").val(id);
    $("#c_descripcion").val(_tr.find("td:eq(0)").text());
    $('#boton_cargo').text("Modificar Cargo");
    openModal('#cargoModal');
}

function pop_eliminar_cargo(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el Cargo <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_cargo);
    openModal('#eliminarModal');
}

function eliminar_cargo() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_cargo", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row(".cargo_" + id).remove().draw(false);
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

////////////////////////////////////////////////////////////////////////////////
function pop_permiso_cargo(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "todos_sub_menu_asignados", id_cargo: id}, function (resp) {
        $("#as_id_cargo").val(id);
        $(".sm_as").prop("checked", false);
        var json = $.parseJSON(resp);
        $.each(json, function (i, obj) {
            $("#sm_" + obj.ID_SUB_MENU).prop("checked", true);
        });
        $("#asignarPermisoModalLabel").text($(ele).closest("tr").find("td:eq(0)").text());
        openModal('#asignarPermisoModal');
        ocultarCargando();
    });
}

function asignar_desasignar_sub_menu(id_sub_menu, ele) {
    mostrarCargando();
    var id_cargo = $("#as_id_cargo").val();
    var asignar = $(ele).prop("checked");
    $.post(url, {evento: "asignar_desasignar_sub_menu", id_sub_menu: id_sub_menu, id_cargo: id_cargo, asignar: asignar}, function (resp) {
        if (resp === "false") {
            $(ele).prop("checked", !asignar);
        } else {
            $(ele).prop("checked", asignar);
        }
        ocultarCargando();
    });
}
