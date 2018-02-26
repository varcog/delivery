var url = "../ADMINISTRACION_MENU_CONTROLLER";
var tabla, tablaSubMenu;
$(document).ready(function () {
    $('#asignarSubMenuModal').on('shown.bs.modal', opened_pop_ver_sub_menu);
    todos();
});

function todos() {
    mostrarCargando();
    $.post(url, {evento: "todos"}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += menuFilaHtml(obj);
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

function menuFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='menu_" + obj.ID + "'>";
    tr += "<td>" + (obj.DESCRIPCION || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_menu(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-gear text-muted' title='Ver Sub Menu' onclick='pop_ver_sub_menu(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='pop_eliminar_menu(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_menu() {
    $("#m_id").val(0);
    $("#m_descripcion").val("");
    $('#boton_menu').text("Crear Menu");
    openModal('#menuModal');
}

function guardar_menu() {
    if ($("#m_descripcion").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario la descripcion del menu");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    var id = $("#m_id").val();
    var descripcion = $("#m_descripcion").val();
    mostrarCargando();
    $.post(url, {evento: "guardar_menu", id: id, descripcion: descripcion}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (id_aux > 0) {
                tabla.cell(".menu_" + id_aux + " > td:eq(0)").data((json.DESCRIPCION || ""));
                tabla.rows().invalidate();
            } else {
                tabla.row.add($(menuFilaHtml(json))).draw(false);
            }
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Guardado Correctamente.");
            cerrar_modal();
        }
        ocultarCargando();
        openModal('#alertModal');
    });
}

function pop_modificar_menu(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#m_id").val(id);
    $("#m_descripcion").val(_tr.find("td:eq(0)").text());
    $('#boton_menu').text("Modificar Menu");
    openModal('#menuModal');
}

function pop_eliminar_menu(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el menu <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_menu);
    openModal('#eliminarModal');
}

function eliminar_menu() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_menu", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row(".menu_" + id).remove().draw(false);
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
function pop_ver_sub_menu(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "todos_sub_menu", id_menu: id}, function (resp) {
        $("#sm_id_menu").val(id);
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += subMenuFilaHtml(obj);
        });
        $("#cuerpoSubMenu").html(html);
        $("#asignarSubMenuModalLabel").text($(ele).closest("tr").find("td:eq(0)").text());
        openModal('#asignarSubMenuModal');
        ocultarCargando();
    });
}

function opened_pop_ver_sub_menu() {
    if (tablaSubMenu)
        tablaSubMenu.rows().invalidate();
    else {
        tablaSubMenu = $('#tablaSubMenu').DataTable({
            "language": {
                "url": "../plugins/datatables/i18n/spanish.json",
                "scrollX": true
            }
        });
//        $("#tablaSubMenu").parent().css("overflow-y", "auto");
    }
}

function subMenuFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='sub_menu_" + obj.ID + "'>";
    tr += "<td>" + (obj.DESCRIPCION || "") + "</td>";
    tr += "<td>" + (obj.URL || "") + "</td>";
    tr += "<td id='sm_vis_" + obj.ID + "' class='text-center'>" + html_check_sub_menu(obj.ID, obj.VISIBLE) + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_sub_menu(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='pop_eliminar_sub_menu(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_sub_menu() {
    $("#sm_id").val(0);
    $("#sm_descripcion").val("");
    $("#sm_url").val("");
    $('#boton_subMenu').text("Crear Sub Menu");
    openModal('#subMenuModal');
}

function guardar_sub_menu() {
    if ($("#sm_descripcion").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario la descripcion del sub menu");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    if ($("#sm_url").val().trim().length === 0) {
        $("#alertModalText").text("Es necesario la url del sub menu");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    var id = $("#sm_id").val();
    var descripcion = $("#sm_descripcion").val();
    var url1 = $("#sm_url").val();
    var id_menu = $("#sm_id_menu").val();
    mostrarCargando();
    $.post(url, {evento: "guardar_sub_menu", id: id, descripcion: descripcion, url: url1, id_menu: id_menu}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (id_aux > 0) {
                tablaSubMenu.cell(".sub_menu_" + id_aux + " > td:eq(0)").data((json.DESCRIPCION || ""));
                tablaSubMenu.cell(".sub_menu_" + id_aux + " > td:eq(1)").data((json.URL || ""));
                tablaSubMenu.rows().invalidate();
            } else {
                tablaSubMenu.row.add($(subMenuFilaHtml(json))).draw(false);
                tablaSubMenu.rows().invalidate();
            }
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Guardado Correctamente.");
            cerrar_modal();
        }
        ocultarCargando();
        openModal('#alertModal');
    });
}

function pop_modificar_sub_menu(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#sm_id").val(id);
    $("#sm_descripcion").val(_tr.find("td:eq(0)").text());
    $("#sm_url").val(_tr.find("td:eq(1)").text());
    $('#boton_subMenu').text("Modificar Sub Menu");
    openModal('#subMenuModal');
}

function pop_eliminar_sub_menu(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el sub menu <strong>" + _tr.find("td:eq(0)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_sub_menu);
    openModal('#eliminarModal');
}

function eliminar_sub_menu() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_sub_menu", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tablaSubMenu.row(".sub_menu_" + id).remove().draw(false);
            tablaSubMenu.rows().invalidate();
//            tabla.row(".producto_" + id).remove().draw(false);
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Eliminado Correctamente.");
        }
        ocultarCargando();
        cerrar_modal();
        openModal('#alertModal');
    });
}

function html_check_sub_menu(id, checked) {
    return "<input type='checkbox' onclick='cambiar_estado_sub_menu(" + id + ", this)' " + (checked ? " checked " : "") + "/>";
}

function cambiar_estado_sub_menu(id_sub_menu, ele) {
    mostrarCargando();
    var visible = $(ele).prop("checked");
    $.post(url, {evento: "cambiar_estado_sub_menu", id_sub_menu: id_sub_menu, visible: visible}, function (resp) {
        if (resp === "false") {
            tablaSubMenu.cell($(ele).parent()[0]).data(html_check_sub_menu(id_sub_menu, !visible));
        } else {
            tablaSubMenu.cell($(ele).parent()[0]).data(html_check_sub_menu(id_sub_menu, visible));
        }
        tablaSubMenu.rows().invalidate();
        ocultarCargando();
    });
}
