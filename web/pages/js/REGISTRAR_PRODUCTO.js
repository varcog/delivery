var url = "../REGISTRAR_PRODUCTO_CONTROLLER";
var tabla;
$(document).ready(function () {
    todos();
});

function todos() {
    mostrarCargando();
    $.post(url, {evento: "todos"}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += productoFilaHtml(obj);
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

function productoFilaHtml(obj) {
    var tr = "<tr data-imagen='\"" + (obj.IMAGEN || "") + "\"' data-id='" + obj.ID + "'>";
    tr += "<td>" + (obj.NOMBRE || "") + "</td>";
    tr += "<td class='text-right'>" + obj.PRECIO_COMPRA + "</td>";
    tr += "<td class='text-right'>" + obj.PRECIO_VENTA + "</td>";
    tr += "<td class='text-center'>";
    tr += "<i class='fa fa-edit text-warning' title='Editar' onclick='pop_modificar_producto(" + obj.ID + ",this);'></i>";
    tr += "<i class='fa fa-remove text-danger' title='Eliminar' onclick='save_data(" + obj.ID + ",this);' data-target='#confirmModal' data-toggle='modal' ></i>";
    tr += "</td>";
    tr += "</tr>";
    return tr;
}

function pop_registrar_producto(){
    
}

function crear() {
    mostrarCargando();
    var formData = new FormData($(input).parent()[0]);
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false,
        success: function (data, textStatus, jqXHR)
        {
            $(input).prev().css("display", "");
            $(input).prev().data("contenido", data);
            ocultarCargando();
        },
        error: function (jqXHR, textStatus, errorThrown)
        {
            ocultarCargando();
            $("#alert").openAlertError("No se logro insertar correctamente.");
        }
    });
}

function openModal() {
    $('#myModal').modal();
}

function pop_modificar_producto(){
    
}
