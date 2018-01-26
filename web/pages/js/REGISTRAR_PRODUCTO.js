var url = "../REGISTRAR_PRODUCTO_CONTROLLER";

$(document).ready(function () {
    $('#example1').DataTable({
        "language": {
            "url": "../plugins/datatables/i18n/spanish.json"
        }
    });
    resize_contenedor();
});


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
