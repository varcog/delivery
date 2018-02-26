var url = "../ASIGNAR_SERVICIO_COMPANIA_CONTROLLER";
var tabla;
var servicio;
var cmps;

$(document).ready(function () {
    init();
});

function init() {
    tabla = $("#dataTable").DataTable(dataTable_conf);
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);
        cargar_companias(json.COMPANIAS);
        cambiar_servicio();
    });

}

function cargar_companias(companias) {
    var html = "";
    $.each(companias, function (i, obj) {
        html += "<option value='" + obj.ID + "'>" + obj.RAZON_SOCIAL + "</option>";
    });
    $("#companias").html(html);
}

function cambiar_servicio() {
    mostrarCargando();
    servicio = parseInt($("#servicio").val());
    switch (servicio) {
        case 0: // CLINICAS
            $(".icono_servicio").removeClass("fa-user-md");
            $(".icono_servicio").removeClass("fa-plus-square");
            $(".icono_servicio").addClass("fa-hospital-o");
            $(".medico_tabla").css("display", "none");
            $(".titulo_servicio").text("CLÍNICA");
            break;
        case 1: // FARMACIAS
            $(".icono_servicio").removeClass("fa-user-md");
            $(".icono_servicio").removeClass("fa-hospital-o");
            $(".icono_servicio").addClass("fa-plus-square");
            $(".medico_tabla").css("display", "none");
            $(".titulo_servicio").text("FARMACIA");
            break;
        case 2: // MEDICOS
            $(".icono_servicio").removeClass("fa-hospital-o");
            $(".icono_servicio").removeClass(" fa-plus-square");
            $(".icono_servicio").addClass("fa-user-md");
            $(".medico_tabla").css("display", "");
            $(".titulo_servicio").text("MÉDICOS");
            break;
    }
    cargar_servicios();
}

function cargar_servicios() {
    mostrarCargando();
    tabla.destroy();
    cmps = [];
    $.post(url, {evento: "listar_servicios", servicio: servicio}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        if (servicio === 2) {
            $.each(json, function (i, obj) {
                html += servicio_tr_html_medico(obj.ID, obj.NOMBRES, obj.ID_CIUDAD, obj.CIUDAD);
                cmps.push(obj.ID);
            });
        } else {
            $.each(json, function (i, obj) {
                html += servicio_tr_html(obj.ID, obj.NOMBRES);
                cmps.push(obj.ID);
            });
        }
        $("#cuerpo_tabla").html(html);
        if (servicio === 2) {
            tabla = $('#dataTable').DataTable(dataTable_conf_2);
        } else {
            tabla = $('#dataTable').DataTable(dataTable_conf);
        }
        servicios_asignados();
    });
}

function servicio_tr_html(id, nombres) {
    var html = "<tr class='serv_" + id + "'>";
    html += "<td>" + nombres + "</td>";
    html += "<td style='display:none'></td>";
    html += "<td class='text-center' id='td_" + id + "'>" + html_check(id, false) + "</td>";
    html += "</tr>";
    return html;
}

function servicio_tr_html_medico(id, nombres, id_ciudad, ciudad) {
    var html = "<tr class='serv_" + id + "' " + (id_ciudad > 0 ? "data-id_ciudad='" + id_ciudad + "'" : "") + " >";
    html += "<td>" + nombres + "</td>";
    html += "<td>" + (ciudad || "") + "</td>";
    html += "<td class='text-center' id='td_" + id + "'>" + html_check(id, false) + "</td>";
    html += "</tr>";
    return html;
}

function html_check(id, checked) {
    return "<input type='checkbox' onclick='seleccionar_servicio(" + id + ", this)' " + (checked ? " checked " : "") + "/>";
}

function servicios_asignados() {
    mostrarCargando();
    var id_compania = $("#companias").val();
    $.post(url, {evento: "servicios_asignados", id_compania: id_compania, servicio: servicio}, function (resp) {
        change_check_all_cmp(false);
        var json = $.parseJSON(resp);
        for (var i = 0; i < json.length; i++) {
            change_check_cmp(json[i], true);
        }
        is_check_all_cmp();
        ocultarCargando();
    });
}

function seleccionar_servicio(id_servicio, ele) {
    mostrarCargando();
    var estado = $(ele).prop("checked");
    var id_compania = $("#companias").val();
    $.post(url, {evento: "seleccionar_servicio", id_compania: id_compania, id_servicio: id_servicio, servicio: servicio, estado: estado}, function (resp) {
        if (resp === "false") {
            change_check_cmp(id_servicio, !estado);
        } else {
            change_check_cmp(id_servicio, estado);
            is_check_all_cmp();
        }
        ocultarCargando();
    });
}

function seleccionar_todos_servicios(ele) {
    mostrarCargando();
    var estado = $(ele).prop("checked");
    var id_compania = $("#companias").val();
    $.post(url, {evento: "seleccionar_todos_servicios", id_compania: id_compania, estado: estado, servicio: servicio}, function (resp) {
        if (resp === "false")
            $(".seleccionar_todos_servicios").prop("checked", !estado);
        else {
            $(".seleccionar_todos_servicios").prop("checked", estado);
            change_check_all_cmp(estado);
        }
        ocultarCargando();
    });
}

function change_check_all_cmp(checked) {
    for (var i = 0; i < cmps.length; i++) {
        change_check_cmp(cmps[i], checked);
    }
}

function change_check_cmp(id, checked) {
    tabla.cell('#td_' + id).data(html_check(id, checked));
}

function is_check_all_cmp() {
    var all_check = true;
    tabla.rows().every(function (rowIdx, tableLoop, rowLoop) {
        var data = this.data();
        if (data[2].search("checked") === -1) {
            all_check = false;
            return false;
        }
    });
    $(".seleccionar_todos_servicios").prop("checked", all_check);
    return all_check;
}


