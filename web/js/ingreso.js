var url = "INGRESO_CONTROLLER";

$(document).ready(function () {
    $.post(url, {evento: "obtener_menu"}, function (resp) {
        if (resp === "false")
            location.href = "index.html";
        else {
            $(".elmenu").remove();
            var json = $.parseJSON(resp);
            var html = "";
            $.each(json, function (menu, submenus) {
                html += "<li class='treeview'>";
                html += "<a href='#'>";
                // fa-asterisk
                // fa-certificate
                // fa-circle
                html += "<i class='fa fa-asterisk'></i>";
                html += "<span>" + menu + "</span>";
                html += "<span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span>";
                html += "</a>";
                html += "<ul class='treeview-menu'>";
                $.each(submenus, function (i, sub_menu) {
                    html += "<li><a onclick='cambiar_menu(\"" + sub_menu.URL + "\")'><i class='fa fa-circle-o'></i> " + sub_menu.DESCRIPCION + "</a></li>";
                });
                html += "</ul>";
                html += "</li>";
            });
            $("#menu").append(html);
        }
    });
});

function cambiar_menu(url) {
    $("#content_frame").attr("src", url);
}

