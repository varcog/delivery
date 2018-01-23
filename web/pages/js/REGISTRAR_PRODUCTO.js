var url = "../REGISTRAR_PRODUCTO_CONTROLLER";

$(document).ready(function () {
    $('#example1').DataTable({
        "language": {
            "url": "../plugins/datatables/i18n/spanish.json"
        }
    });
    resize_contenedor();
});


function cargar() {
    $.post(url, {evento: "cargar"}, function (resp) {
        
    });
}
