function resize_contenedor() {    
    var height = $(document).find("body").css("height");
    var doc = $(window.parent.document)[0];
    $(doc).find("#content_frame").css("height",height);    
}

var dataTable_conf = {
    "language": {
//        "decimal":        "",
        "emptyTable": "No hay datos disponibles en la tabla",
        "info": "Mostrando de _START_ a _END_ de _TOTAL_ filas",
        "infoEmpty": "Mostrando de 0 a 0 de 0 filas",
        "infoFiltered": "(filtradas desde _MAX_ total filas)",
//        "infoPostFix":    "",
//        "thousands":      ",",
        "lengthMenu": "Mostrar _MENU_ filas por página",
        "loadingRecords": "Cargando...",
        "processing": "Procesando...",
        "search": "Buscar:",
        "zeroRecords": "No se encontraron registros coincidentes",
        "paginate": {
            "first": "Primero",
            "last": "Ultimo",
            "next": "Siguiente",
            "previous": "Anterior"
        },
        "aria": {
            "sortAscending": ": activar para ordenar la columna ascendente",
            "sortDescending": ": activar para ordenar la columna descendente"
        }
    }
};

function mostrarCargando() {
    if ($("#div_cargando_background").length === 0) {
        $("body").append("<div class='overlay-wrapper cargando d-none' id='div_cargando_background'>"
                + " <div class='overlay'>"
                + "     <i class='fa fa-refresh fa-spin'></i>"
                + " </div>"
                + "</div>");
    }
    $("#div_cargando_background").removeClass("d-none");
}

function ocultarCargando() {
    $("#div_cargando_background").addClass("d-none");
}