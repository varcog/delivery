var url = "../REGISTRAR_SERVICIO_CONTROLLER";
var servicio = 0;
var regional = 0;
var color_servicio;
var icono_servicio;
var _id, _i;
var ciudadesJSON;
var especialidadesJSON;
var especialidadClinicaJSON;
var tabla;
// bg-warning amarillo
// bg-primary azul
// bg-success verde medico
// bg-danger rojo farmacia

$(document).ready(function () {
    $("[data-mask]").inputmask();
    $(".timepicker").timepicker({
        showInputs: false,
        showMeridian: false,
        showSeconds: false,
        defaultTime: 'current'
    });
    $('#especialidadClinicaModal').on('hidden.bs.modal', function () {
        $("#registrarModal").modal("show");
    });
    $('#especialidadModal').on('hidden.bs.modal', function () {
        $("#registrarModal").modal("show");
    });
    init();
});

function init() {
    tabla = $('#dataTable').DataTable(dataTable_conf);
    $.post(url, {evento: "init_registrar"}, function (resp) {
        if (resp === "false") {
            reviseSuConexion();
            return;
        }
        var json = $.parseJSON(resp);
        ciudadesJSON = json.CIUDADES;
        especialidadesJSON = json.ESPECIALIDADES;
        especialidadClinicaJSON = json.ESPECIALIDAD_CLINICA;
        especialidad_clinica_html();
        cambiar_servicio();
    });
}

function cambiar_servicio() {
    mostrarCargando();
    servicio = parseInt($("#servicio").val());
    switch (servicio) {
        case 0: // CLINICAS
            $(".icono_servicio").removeClass("fa-user-md");
            $(".icono_servicio").removeClass("fa-plus-square");
            $(".icono_servicio").addClass("fa-hospital-o");
            $(".titulo_servicio").text("CLÍNICA");
            break;
        case 1: // FARMACIAS
            $(".icono_servicio").removeClass("fa-user-md");
            $(".icono_servicio").removeClass("fa-hospital-o");
            $(".icono_servicio").addClass("fa-plus-square");
            $(".titulo_servicio").text("FARMACIA");
            break;
        case 2: // MEDICOS
            $(".icono_servicio").removeClass("fa-hospital-o");
            $(".icono_servicio").removeClass(" fa-plus-square");
            $(".icono_servicio").addClass("fa-user-md");
            $(".titulo_servicio").text("MÉDICOS");
            break;
    }
    cargar_servicios();
}

function cargar_servicios() {
    mostrarCargando();
    tabla.destroy();
    $.post(url, {evento: "listar_servicios", tipo: servicio}, function (resp) {
        if (resp === "false") {
            reviseSuConexion();
            return;
        }
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += plantilla_html(obj.ID, obj.NOMBRES);
        });
        $("#cuerpo_tabla").html(html);
        tabla = $('#dataTable').DataTable(dataTable_conf);
        ocultarCargando();
    });
}

function plantilla_html(id, nombres, descripcion) {
    var html = "<tr class='serv_" + id + "'>";
    html += "<td>" + nombres + "</td>";
    //html += "<td>" + descripcion + "</td>";
    html += "<td class='text-center'>";
    html += "<i class='fa fa-edit text-warning' title='Editar' onclick='editar(" + id + ",this);'></i>";
    html += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='save_data(" + id + ",this);' data-target='#confirmModal' data-toggle='modal' ></i>";
    html += "</td>";
    html += "</tr>";
    return html;
}

//<editor-fold defaultstate="collapsed" desc="ELIMINAR">
function eliminar() {
    $('#confirmModal').modal('toggle');
    mostrarCargando();
    $.post(url, {evento: "eliminar", id: _id, servicio: servicio}, function (resp) {
        if (resp === "false") {
            $("#alertModalLabel").text("Alerta");
            $("#alertModalText").text("Revise Dependencias.");
        } else {
            tabla.row('.serv_' + _id).remove().draw(false);
            $("#alertModalLabel").text("Información");
            $("#alertModalText").text("Eliminado Correctamente.");
        }
        ocultarCargando();
        $('#alertModal').modal('show');
    });
}

function cancelar() {
    $('#confirmModal').modal('hide');
}

function save_data(id, i) {
    _id = id;
    _i = i;
}
//</editor-fold>

function add_tipo() {
    $(".oculto").css("display", "none");
    switch (servicio) {
        case 0: // CLINICAS
            $("#registrarModalLabel").text("Registrar Clinica");
            $(".hclinica").css("display", "");
            break;
        case 1: // FARMACIAS
            $("#registrarModalLabel").text("Registrar Farmacia");
            $(".hfarmacia").css("display", "");
            break;
        case 2: // MEDICOS
            $("#registrarModalLabel").text("Registrar Medico");
            $(".hmedico").css("display", "");
            break;
    }
    vaciar();
    $("input[name=id_select]").val(0);
    $("#btn_crear").text("CREAR");
    $("#registrarModal").modal("show");
}

//<editor-fold defaultstate="collapsed" desc="TABLA UBICACION">
function add_ubicacion() {
    $("#cuerpo_tabla_ubicacion").append(ubicacion_html());
    $("[data-mask]").inputmask();
    $("#cuerpo_tabla_ubicacion .timepicker").timepicker({
        showInputs: false,
        showMeridian: false,
        showSeconds: false,
        defaultTime: 'current'
    });
}

function ubicacion_html(direccion, telefono, ciudad, coordenada, atencion) {
    var html = "<tr>";
    html += "<td><textarea class='direccion' style='min-width:150px; width: 100%'>" + (direccion || "") + "</textarea></td>";
//    html += "<td><textarea class='direccion' onkeyup='verificar_copiado(this, event);' style='min-width:150px; width: 100%'>" + (direccion || "") + "</textarea></td>";
    if (servicio === 0) {
        var telefonos = (telefono ? telefono.split("-") : ["", "", ""]);
        html += "<td><input type='text' class='telefono' data-inputmask='\"mask\": \"(9) 99-99999\"' data-mask value='" + (telefonos[0] || "") + "'></td>";
        html += "<td><input type='text' class='telefono2' data-inputmask='\"mask\": \"(9) 99-99999\"' data-mask value='" + (telefonos[1] || "") + "'></td>";
        html += "<td><input type='text' class='telefono3' data-inputmask='\"mask\": \"(9) 99-99999\"' data-mask value='" + (telefonos[2] || "") + "'></td>";
    } else
        html += "<td><input type='text' class='telefono' data-inputmask='\"mask\": \"(9) 99-99999\"' data-mask value='" + (telefono || "") + "'></td>";
    html += "<td>" + cuidad_html(ciudad) + "</td>";
    if (servicio === 1) {
        var att = atencion ? atencion.split("-") : ["", ""];
        html += "<td><div class='input-group'>";
        html += "    <label>Desde:</label>";
        html += "    <input type='text' class='form-control timepicker atde' style='min-width: 70px;' value='" + att[0] + "'>";
        html += "    <div class='input-group-addon'>";
        html += "        <i class='fa fa-clock-o'></i>";
        html += "    </div>";
        html += "</div>";
        html += "<div class='input-group'>";
        html += "    <label>Hasta :</label>";
        html += "    <input type='text' class='form-control timepicker aths' style='min-width: 70px;' value='" + att[1] + "'>";
        html += "    <div class='input-group-addon'>";
        html += "        <i class='fa fa-clock-o'></i>";
        html += "    </div>";
        html += "</div></td>";
    }
    // text-success
    if (coordenada)
        html += "<td class='text-center'><i class='fa fa-map-pin ubicacion text-success' data-coordenada='" + coordenada + "' onclick='mostrar_mapa(this)'></i></td>";
    else
        html += "<td class='text-center'><i class='fa fa-map-pin ubicacion' onclick='mostrar_mapa(this)'></i></td>";
    html += "<td class='text-center'><i class='fa fa-remove text-danger' onclick='elminar_fila(this);'></i></td>";
    html += "</tr>";
    return html;
}

function cuidad_html(ciudad) {
    var html = "<select class='ciudad'>";
    $.each(ciudadesJSON, function (i, obj) {
        html += "<option data-ubicacion='" + obj.UBICACION + "' value='" + obj.ID + "' " + (ciudad === obj.ID ? "selected" : "") + ">" + obj.DESCRIPCION + "</option>";
    });
    html += "</select>";
    return html;
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="TABLA ESPECIALIDADES">
function add_especialidad() {
    $("#cuerpo_tabla_especialidad").append(especialidad_tr_html());
}

function especialidad_tr_html(especialidad) {
    var html = "<tr>";
    html += "<td>" + especialidades_select_html(especialidad) + "<i class='fa fa-plus-circle text-success' title='CREAR ESPECIALIDAD' onclick='crear_especialidad(this);'></i> </td>";
    html += "<td class='text-center'><i class='fa fa-remove text-danger' onclick='elminar_fila(this);'></i></td>";
    html += "</tr>";
    return html;
}

function especialidades_select_html(especialidad) {
    var html = "<select class='especialidad' style='width:calc(100% - 35px);'>";
    $.each(especialidadesJSON, function (i, obj) {
        html += especialidad_option_html(obj, especialidad);
    });
    html += "</select>";
    return html;
}

function especialidad_option_html(obj, especialidad) {
    return "<option value='" + obj.ID + "' " + (especialidad === obj.ID ? "selected" : "") + ">" + obj.DESCRIPCION + "</option>";
}

function elminar_fila(ele) {
    $(ele).closest("tr").remove();
}

var ele_crear_esp;
function crear_especialidad(ele) {
    ele_crear_esp = ele;
    $("#descripcion_esp").val("");
    $("#registrarModal").modal("hide");
    $("#especialidadModal").modal("show");
}
function crear_especialidad_medica() {
    mostrarCargando();
    var descripcion = $("#descripcion_esp").val().trim();
    if (descripcion.length === 0) {
        $("#alertModalLabel").text("Alerta");
        $("#alertModalText").text("La descripcion no debe estar vacia.");
        $('#alertModal').modal('show');
        return;
    }
    $.post(url, {evento: "crear_especialidad_medica", descripcion: descripcion}, function (resp) {
        if (resp !== "false") {
            var json = $.parseJSON(resp);
            especialidadesJSON.push(json);
            $(".especialidad").append(especialidad_option_html(json));
            $(ele_crear_esp).closest("tr").find(".especialidad").val(json.ID);
            ocultarCargando();
            $("#especialidadModal").modal("hide");
        }
    });
}
function salir_especialidad() {
    $("#especialidadModal").modal("hide");
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ESPECIALIDAD CLINICA">
function especialidad_clinica_html() {
    var html = "";
    $.each(especialidadClinicaJSON, function (i, obj) {
        html += especialidad_clinica_option(obj);
    });
    $("#especialidad_clinica").html(html);
}

function especialidad_clinica_option(obj) {
    return "<option value='" + obj.ID + "'>" + obj.DESCRIPCION + "</option>";
}

function modal_especialidad_clinica() {
    $("#descripcion_esp_clinica").val("");
    $("#registrarModal").modal("hide");
    $("#especialidadClinicaModal").modal("show");
}
function crear_especialidad_clinica() {
    mostrarCargando();
    var descripcion = $("#descripcion_esp_clinica").val().trim();
    if (descripcion.length === 0) {
        $("#alertModalLabel").text("Alerta");
        $("#alertModalText").text("La descripcion no debe estar vacia.");
        $('#alertModal').modal('show');
        return;
    }
    $.post(url, {evento: "crear_especialidad_clinica", descripcion: descripcion}, function (resp) {
        if (resp !== "false") {
            var json = $.parseJSON(resp);
            especialidadClinicaJSON.push(json);
            $("#especialidad_clinica").append(especialidad_clinica_option(json));
            $("#especialidad_clinica").val(json.ID);
            ocultarCargando();
            $("#especialidadClinicaModal").modal("hide");
        }
    });
}
function salir_especialidad_clinica() {
    $("#especialidadClinicaModal").modal("hide");
}
//</editor-fold>

function salir() {
    $("#registrarModal").modal("hide");
    vaciar();
}

function ok_crear() {
    mostrarCargando();
    var nombres = $("#nombres").val();
    var observacion = $("#observacion").val();
    var celular = $("#celular").inputmask('unmaskedvalue');
    var telf1 = $("#telf1").inputmask('unmaskedvalue');
    var telf2 = $("#telf2").inputmask('unmaskedvalue');
    var direccion = $("#direccion").val();
    var at1 = $("#at1de").val() + "-" + $("#at1hs").val();
    var at2 = $("#at2de").val() + "-" + $("#at2hs").val();
    var id_especialidad_clinica = $("#especialidad_clinica").val();
    var id = $("input[name=id_select]").val();
    var id_aux = id;
    var cant_ubi = 0;
    var ubicaciones = [];
    var direccion_u, telefono_u, count_err;
    var guardar = true;
    var ubi_u;
    if (servicio === 0 || servicio === 1) {
        $("#cuerpo_tabla_ubicacion").find("input, textarea").removeClass("bg-danger");
        $("#cuerpo_tabla_ubicacion").find("tr").each(function (i, tr) {
            direccion_u = $(tr).find(".direccion").val().trim();
            telefono_u = $(tr).find(".telefono").inputmask('unmaskedvalue').trim();
            if (servicio === 0) {
                telefono_u += "-" + $(tr).find(".telefono2").inputmask('unmaskedvalue').trim();
                telefono_u += "-" + $(tr).find(".telefono3").inputmask('unmaskedvalue').trim();
            }
            count_err = 0;
            if (direccion_u.length === 0) {
                $(tr).find(".direccion").addClass("bg-danger");
                count_err++;
            }
            if (telefono_u.length === 0) {
                $(tr).find(".telefono").addClass("bg-danger");
                count_err++;
            }
            if (count_err === 2) {
                guardar = false;
            }
            ubi_u = {
                direccion: direccion_u,
                telefono: telefono_u,
                coordenadas: $(tr).find(".ubicacion").data("coordenada"),
                ciudad: $(tr).find(".ciudad").val()
            };
            if (servicio === 1) {
                ubi_u["atencion"] = $(tr).find(".atde").val() + "-" + $(tr).find(".aths").val();
            }
            ubicaciones.push(ubi_u);
            cant_ubi++;
        });

    }
    var cant_esp = 0;
    var especialidades = [];
    if (servicio === 2) {
        $("#cuerpo_tabla_especialidad").find("tr").each(function (i, tr) {
            especialidades.push({
                especialidad: $(tr).find(".especialidad").val()
            });
            cant_esp++;
        });
    }
    if (!guardar) {
        ocultarCargando();
        $("#alertModalLabel").text("Alerta");
        $("#alertModalText").text("Hay Datos Vacios.");
        $('#alertModal').modal('show');
        return;
    }
    $.post(url, {
        id: id,
        evento: "ok_crear",
        nombres: nombres,
        observacion: observacion,
        celular: celular,
        telf1: telf1,
        telf2: telf2,
        direccion: direccion,
        at1: at1,
        at2: at2,
        cant_ubi: cant_ubi,
        ubicaciones: ubicaciones,
        id_especialidad_clinica: id_especialidad_clinica,
        cant_esp: cant_esp,
        especialidades: especialidades,
        tipo: servicio
    }, function (resp) {
        if (resp === "false") {
            reviseSuConexion();
            return;
        }
        switch (servicio) {
            case 0: // CLINICAS
                $("#registrarModalLabel").text("Registrar Clinica");
                $(".hclinica").css("display", "");
                break;
            case 1: // FARMACIAS
                $("#registrarModalLabel").text("Registrar Farmacia");
                $(".hfarmacia").css("display", "");
                break;
            case 2: // MEDICOS
                $("#registrarModalLabel").text("Registrar Medico");
                $(".hmedico").css("display", "");
                break;
        }
        if (id_aux > 0) {
            tabla.cell(".serv_" + id_aux + " > td:eq(0)").data(nombres);
        } else {
            tabla.row.add($(plantilla_html(resp, nombres))).draw(false);
        }
        vaciar();
        $("#registrarModal").modal("hide");
        ocultarCargando();
    });
}

function editar(id, i) {
    _i = i;
    $("#btn_crear").text("MODIFICAR");
    vaciar();
    $.post(url, {evento: "editar", id: id, tipo: servicio}, function (resp) {
        if (resp === "false") {
            reviseSuConexion();
            return;
        }
        var obj = $.parseJSON(resp);
        $("input[name=id_select]").val(id);
        $(".oculto").css("display", "none");
        switch (servicio) {
            case 0: // CLINICAS
                $("#registrarModalLabel").text("Modificar Clinica " + obj.NOMBRES);
                $(".hclinica").css("display", "");
                $("#nombres").val(obj.NOMBRES || "");
                $("#observacion").val(obj.DESCRIPCION || "");
                $("#especialidad_clinica").val(obj.ID_ESPECIALIDAD_CLINICA);
                var html = "";
                $.each(obj.UBICACIONES, function (i, ubi) {
                    html += ubicacion_html(ubi.DIRECCION, ubi.TELEFONO, ubi.ID_CIUDAD, ubi.CORDENADAS);
                });
                $("#cuerpo_tabla_ubicacion").html(html);
                $("[data-mask]").inputmask();
                break;
            case 1: // FARMACIAS
                $("#registrarModalLabel").text("Modificar Farmacia " + obj.NOMBRES);
                $(".hfarmacia").css("display", "");
                $("#nombres").val(obj.NOMBRES);
                var html = "";
                $.each(obj.UBICACIONES, function (i, ubi) {
                    html += ubicacion_html(ubi.DIRECCION, ubi.TELEFONO, ubi.ID_CIUDAD, ubi.CORDENADAS, ubi.ATENCION);
                });
                $("#cuerpo_tabla_ubicacion").html(html);
                $("[data-mask]").inputmask();
                break;
            case 2: // MEDICOS
                $("#registrarModalLabel").text("Modificar Medico " + obj.NOMBRES);
                $(".hmedico").css("display", "");
                $("#nombres").val(obj.NOMBRES);
                $("#celular").val(obj.CELULAR);
                $("#telf1").val(obj.TELEFONO1);
                $("#telf2").val(obj.TELEFONO2);
                $("#direccion").val(obj.DIRECCION);
                var aux = obj.AT1.split("-");
                $("#at1de").val(aux[0]);
                $("#at1hs").val(aux[1]);
                aux = obj.AT2.split("-");
                $("#at2de").val(aux[0]);
                $("#at2hs").val(aux[1]);
                var html = "";
                $.each(obj.ESPECIALIDADES, function (i, esp) {
                    html += especialidad_tr_html(esp.ID_ESPECIALIDAD);
                });
                $("#cuerpo_tabla_especialidad").append(html);
                break;
        }
        $("#registrarModal").modal("show");
    });
}

function vaciar() {
    $("#nombres").val("");
    $("#observacion").val("");
    $("#celular").val("");
    $("#telf1").val("");
    $("#telf2").val("");
    $("#direccion").val("");
    $("#at1de").val("");
    $("#at1hs").val("");
    $("#at2de").val("");
    $("#at2hs").val("");
    $("#cuerpo_tabla_ubicacion").html("");
    $("#cuerpo_tabla_especialidad").html("");
    $("#especialidad_clinica").find("option:first").prop("selected", true);
}

var ele_sel_map;
function ubicacion_up() {
    try {
        var ubicacionSucu = $(ele_sel_map).data("coordenada");
        ubicacionSucu = ubicacionSucu.split(",");
        if (ubicacionSucu.length === 2) {
            if (parseFloat(ubicacionSucu[0]) != NaN && parseFloat(ubicacionSucu[1]) != NaN) {
                cargarMapa(ubicacionSucu[0], ubicacionSucu[1], true, 'map');
                return;
            }
        } else {
            ubicacion_defecto();
        }
    } catch (e) {
        ubicacion_defecto();
    }
}

function ubicacion_defecto() {
    var ubiDef = $(ele_sel_map).closest("tr").find(".ciudad option:selected").data("ubicacion");
    ubiDef = ubiDef ? ubiDef.split(",") : ["-17.794618", "-63.1922383"];
    if (ubiDef.length !== 2)
        ubiDef = ["-17.794618", "-63.1922383"];
//        cargarUbicacionActual("", "", false, 'map');
    cargarMapa(ubiDef[0], ubiDef[1], false, 'map');
}
function ubicacion_down() {
    var marcador = obtenerUbicacionMarcada();
    $(ele_sel_map).data("coordenada", marcador);
    if (marcador)
        $(ele_sel_map).addClass("text-success");
    else
        $(ele_sel_map).removeClass("text-success");
    $('#registrarModal').modal('show');
}
function mostrar_mapa(ele) {
    ele_sel_map = ele;
    $('#mapaModal').off('shown.bs.modal');
    $('#mapaModal').off('hidden.bs.modal');
    $('#mapaModal').on('shown.bs.modal', ubicacion_up);
    $('#mapaModal').on('hidden.bs.modal', ubicacion_down);
    $('#registrarModal').modal('hide');
    $('#mapaModal').modal('show');
}


function verificar_copiado(ta, event) {
    if (event.keyCode === 86) {
        if (servicio === 1) { // Ubicaciones 
            var enters = $(ta).val().split("\n");
            var tbody = $(ta).closest("tbody");
            for (var i = 0; i < enters.length; i++) {
                var tabs = enters[i].split("\t");
                for (var j = 0; j < tabs.length; j++) {
                    $(tbody).find("tr:eq(" + i + ")").find("td:eq(" + j + ")").children().val(tabs[j]);
                }
            }
        } else if (servicio === 0) {

        } else if (servicio === 2) {

        }
    }
}



