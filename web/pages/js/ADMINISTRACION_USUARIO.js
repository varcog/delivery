var url = "../ADMINISTRACION_USUARIO_CONTROLLER";
var tabla, tablaSubMenu;
$(document).ready(function () {
    init();
});

function init() {
    mostrarCargando();
    $.post(url, {evento: "init"}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json.USUARIOS, function (i, obj) {
            html += usuarioFilaHtml(obj);
        });
        $("#cuerpo").html(html);

        tabla = $('#tabla').DataTable({
            "language": {
                "url": "../plugins/datatables/i18n/spanish.json"
            }
        });

        //////////////// CARGO 
        html = "";
        $.each(json.CARGOS, function (i, cargo) {
            html += "<option value='" + cargo.ID + "'> " + cargo.DESCRIPCION + "</option>";
        });
        $("#u_cargo").html(html);
        ocultarCargando();
    });
}

function usuarioFilaHtml(obj) {
    var tr = "<tr data-id='" + obj.ID + "' class='usuario_" + obj.ID + "'>";
    tr += "<td>" + (obj.CI || "") + "</td>";
    tr += "<td>" + (obj.NOMBRES || "") + (obj.APELLIDOS || "") + "</td>";
    tr += "<td>" + (obj.CARGO || "") + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_usuario(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-gear text-muted' title='Cambiar Contraseña' onclick='pop_cambiar_contrasena(" + obj.ID + ",this);'></i>";
    tr += "</td>";
    tr += "<td class='text-center'>" + html_check_usuario(obj.ID, obj.ESTADO) + "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_usuario() {
    $("#u_id").val(0);
    $("#u_accion").val(0);
    $("#u_usuario").val("").prop("disabled", false);
    $("#u_contrasena").val("").prop("disabled", false);
    $("#u_contrasena_rep").val("").prop("disabled", false);
    $("#u_cargo").prop("disabled", false).find("option:first").prop("selected", true);
    $("#u_ci").val("").prop("disabled", false);
    $("#u_nombres").val("").prop("disabled", false);
    $("#u_apellidos").val("").prop("disabled", false);
    $("#u_fecha_nacimiento").val("").prop("disabled", false);
    $("#u_sexo").val("M").prop("disabled", false);

    $(".usuario_creacion").css("display", "");
    $(".usuario_modificacion").css("display", "");

    $("#u_usuario").removeClass("bg-error");
    $("#u_contrasena").removeClass("bg-error");
    $("#u_contrasena_rep").removeClass("bg-error");
    $("#u_ci").removeClass("bg-error");
    $("#u_nombres").removeClass("bg-error");
    $("#u_apellidos").removeClass("bg-error");

    $('#boton_usuario').text("Crear Usuario");
    openModal('#usuarioModal');
}

function guardar_usuario() {
    $("#u_usuario").removeClass("bg-error");
    $("#u_contrasena").removeClass("bg-error");
    $("#u_contrasena_rep").removeClass("bg-error");
    $("#u_ci").removeClass("bg-error");
    $("#u_nombres").removeClass("bg-error");
    $("#u_apellidos").removeClass("bg-error");
    var id = parseInt($("#u_id").val());
    var accion = parseInt($("#u_accion").val());
    var usuario = $("#u_usuario").val().trim();
    var contrasena = $("#u_contrasena").val().trim();
    var contrasena_rep = $("#u_contrasena_rep").val().trim();
    var cargo = $("#u_cargo").val();
    var ci = $("#u_ci").val().trim();
    var nombres = $("#u_nombres").val().trim();
    var apellidos = $("#u_apellidos").val().trim();
    var fecha_nacimiento = $("#u_fecha_nacimiento").val().trim();
    var sexo = $("#u_sexo").val();
    if (accion === 0 || accion === 2) {
        if (usuario.length === 0 && accion === 0) {
            $("#u_usuario").addClass("bg-error");
            $("#alertModalText").text("Es necesario el usuario");
            $("#alertModalLabel").text("Alerta");
            openModal('#alertModal');
            return;
        }
        if (contrasena.length === 0) {
            $("#u_contrasena").addClass("bg-error");
            $("#alertModalText").text("Es necesario la Contraseña");
            $("#alertModalLabel").text("Alerta");
            openModal('#alertModal');
            return;
        }
        if (contrasena_rep.length === 0) {
            $("#u_contrasena_rep").addClass("bg-error");
            $("#alertModalText").text("Es necesario repetir la Contraseña");
            $("#alertModalLabel").text("Alerta");
            openModal('#alertModal');
            return;
        }
        if (contrasena !== contrasena_rep) {
            $("#u_contrasena").addClass("bg-error");
            $("#u_contrasena_rep").addClass("bg-error");
            $("#alertModalText").text("La contraseña no coincide");
            $("#alertModalLabel").text("Alerta");
            openModal('#alertModal');
            return;
        }
        if (ci.length === 0 && accion === 0) {
            $("#u_ci").addClass("bg-error");
            $("#alertModalText").text("Es necesario el CI");
            $("#alertModalLabel").text("Alerta");
            openModal('#alertModal');
            return;
        }
    }
    if (nombres.length === 0) {
        $("#u_nombres").addClass("bg-error");
        $("#alertModalText").text("Es necesario el Nombre");
        $("#alertModalLabel").text("Alerta");
        openModal('#alertModal');
        return;
    }
    mostrarCargando();
    $.post(url, {
        evento: "guardar_usuario",
        id: id,
        usuario: usuario,
        contrasena: contrasena,
        cargo: cargo,
        ci: ci,
        nombres: nombres,
        apellidos: apellidos,
        fecha_nacimiento: fecha_nacimiento,
        sexo: sexo,
        accion: accion
    }, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("No se Guardo, Intentelo de nuevo.");
        } else {
            var id_aux = id;
            var json = $.parseJSON(resp);
            if (accion === 1) {
                tabla.cell(".usuario_" + id_aux + " > td:eq(1)").data((json.NOMBRES || "") + (json.APELLIDOS || ""));
                tabla.cell(".usuario_" + id_aux + " > td:eq(2)").data($("#u_cargo").find("option:selected").text());
                tabla.rows().invalidate();
            } else if (accion === 0) {
                tabla.row.add($(usuarioFilaHtml(json))).draw(false);
            }
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Guardado Correctamente.");
            cerrar_modal();
        }
        ocultarCargando();
        openModal('#alertModal');
    });
}

function pop_modificar_usuario(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "datos_usuario", id: id}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Intentelo de nuevo.");
            return;
        }
        var json = $.parseJSON(resp);
        $("#u_id").val(id);
        $("#u_accion").val(1);
        $("#u_usuario").val((json.USUARIO || "")).prop("disabled", true);
        $("#u_contrasena").val("").prop("disabled", true);
        $("#u_contrasena_rep").val("").prop("disabled", true);
        $("#u_cargo").val(json.ID_CARGO).prop("disabled", false);
        $("#u_ci").val((json.CI || "")).prop("disabled", true);
        $("#u_nombres").val((json.NOMBRES || "")).prop("disabled", false);
        $("#u_apellidos").val((json.APELLIDOS || "")).prop("disabled", false);
        $("#u_fecha_nacimiento").val((json.FECHA_NACIMIENTO || "")).prop("disabled", false);
        $("#u_sexo").val(json.SEXO).prop("disabled", false);

        $(".usuario_creacion").css("display", "none");
        $(".usuario_modificacion").css("display", "");

        $("#u_usuario").removeClass("bg-error");
        $("#u_contrasena").removeClass("bg-error");
        $("#u_contrasena_rep").removeClass("bg-error");
        $("#u_ci").removeClass("bg-error");
        $("#u_nombres").removeClass("bg-error");
        $("#u_apellidos").removeClass("bg-error");

        $('#boton_usuario').text("Modificar Usuario");
        ocultarCargando();
        openModal('#usuarioModal');
    });
}

function pop_eliminar_usuario(id, ele) {
    var _tr = $(ele).closest("tr");
    $("#eliminarModalText").html("¿Esta seguro de eliminar el Usuario con CI: <strong>" + _tr.find("td:eq(0)").text() + "</strong> y Nombre: <strong>" + _tr.find("td:eq(1)").text() + "</strong>?")
            .data("id", id);
    $("#elminarBotonModal").off("click");
    $("#elminarBotonModal").click(eliminar_usuario);
    openModal('#eliminarModal');
}

function eliminar_usuario() {
    mostrarCargando();
    var id = $("#eliminarModalText").data("id");
    $.post(url, {evento: "eliminar_usuario", id: id}, function (resp) {
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

function pop_cambiar_contrasena(id, ele) {
    mostrarCargando();
    $.post(url, {evento: "datos_usuario", id: id}, function (resp) {
        var json = $.parseJSON(resp);
        $("#u_id").val(id);
        $("#u_accion").val(2);
        $("#u_usuario").val((json.USUARIO || "")).prop("disabled", true);
        $("#u_contrasena").val("").prop("disabled", false);
        $("#u_contrasena_rep").val("").prop("disabled", false);
        $("#u_cargo").val(json.ID_CARGO).prop("disabled", true);
        $("#u_ci").val((json.CI || "")).prop("disabled", true);
        $("#u_nombres").val((json.NOMBRES || "")).prop("disabled", true);
        $("#u_apellidos").val((json.APELLIDOS || "")).prop("disabled", true);
        $("#u_fecha_nacimiento").val((json.FECHA_NACIMIENTO || "")).prop("disabled", true);
        $("#u_sexo").val(json.SEXO).prop("disabled", true);

        $(".usuario_creacion").css("display", "");
        $(".usuario_modificacion").css("display", "none");

        $("#u_usuario").removeClass("bg-error");
        $("#u_contrasena").removeClass("bg-error");
        $("#u_contrasena_rep").removeClass("bg-error");
        $("#u_ci").removeClass("bg-error");
        $("#u_nombres").removeClass("bg-error");
        $("#u_apellidos").removeClass("bg-error");

        $('#boton_usuario').text("Cambiar Contraseña");
        ocultarCargando();
        openModal('#usuarioModal');
    });
}

////////////////////////////////////////////////////////////////////////////////
function html_check_usuario(id, checked) {
    return "<input type='checkbox' onclick='cambiar_estado_usuario(" + id + ", this)' " + (checked ? " checked " : "") + "/>";
}

function cambiar_estado_usuario(id_usuario, ele) {
    mostrarCargando();
    var estado = $(ele).prop("checked");
    $.post(url, {evento: "cambiar_estado_usuario", id_usuario: id_usuario, estado: estado}, function (resp) {
        if (resp === "false") {
            tabla.cell($(ele).parent()[0]).data(html_check_usuario(id_usuario, !estado));
        } else {
            tabla.cell($(ele).parent()[0]).data(html_check_usuario(id_usuario, estado));
        }
        tabla.rows().invalidate();
        ocultarCargando();
    });
}
