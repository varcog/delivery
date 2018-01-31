function REPORTE_PDF() {
    var logoEmpresa = undefined;
    var contenidoPdf;
    var notificarTermino;
    //<editor-fold defaultstate="collapsed" desc="CARGAR IMAGENES">
    var cargarImagen = function (imagen, url, carga, error, outputFormat) {
        imagen.onError = function () {
            error();
        };
        imagen.onload = function () {
            carga();
        };
        imagen.src = url;
    };

    var cargarLogoEmpresa = function (funcionCarga) {
        if (logoEmpresa === undefined) {
            logoEmpresa = new Image();
        }
        var url = "../img/LOGO_COLOR.png";
        cargarImagen(logoEmpresa, url, funcionCarga, function () {
            notificarTermino(false);
            logoEmpresa = undefined;
        });
    };
    //</editor-fold>

    var getFechaActualCompleta = function () {
        var date = new Date();
        var mes = (date.getMonth() + 1) + "";
        mes = mes.length === 1 ? "0" + mes : mes;
        var dia = date.getDate() + "";
        dia = dia.length === 1 ? "0" + dia : dia;
        var año = date.getFullYear();
        var hora = date.getHours() + "";
        hora = hora.length === 1 ? "0" + hora : hora;
        var min = date.getMinutes() + "";
        min = min.length === 1 ? "0" + min : min;
        var seg = date.getSeconds() + "";
        seg = seg.length === 1 ? "0" + seg : seg;
        return  dia + "/" + mes + "/" + año + " " + hora + ":" + min + ":" + seg;
    };

    this.setContenidoPdf = function (contenido) {
        contenidoPdf = contenido;
    };

    this.generarPDF = function () {

//        if (logoEmpresa === undefined) {
//            cargarLogoEmpresa(this.generarPDF);
//            return;
//        }

        var header = function (data) {
//            doc.addImage(logoEmpresa, 'PNG', 19.84, 19.84, 141.73, 28.35);
            doc.setFontSize(12);
            doc.setFontType("bold");
            doc.text(contenidoPdf.titulo, anchoPagina / 2, 28.35, "center");
            doc.setFontSize(8);
            doc.setFontType("bold");
            if(contenidoPdf.colorSubtitulo){
                doc.setTextColor(contenidoPdf.colorSubtitulo);
            }
            doc.text(contenidoPdf.subtitulo, anchoPagina / 2, 42.52, "center");
            doc.setTextColor(0,0,0);
            if (typeof contenidoPdf.descripcion === 'function') {
                top_tabla = contenidoPdf.descripcion(doc, 19.84, 65.20, margen);
                top_tabla += 10;
            } else {
                doc.text(19.84, 65.20, contenidoPdf.descripcion);
            }
        };

        var footer = function (data) {
            var tam_fp = 20 + tam_pie_pag;
            dataAuto = data;
            if (contenidoPdf.mostrarFecha)
                doc.text("Fecha realizada:  " + fecha, data.settings.margin.left, doc.internal.pageSize.height - 20);
            var str = "Pagina " + data.pageCount;
//            pageCount = data.pageCount;
            // Total page number plugin only available in jspdf v1.0+
            if (typeof doc.putTotalPages === 'function') {
                str = str + " de " + totalPagesExp;
            }
            doc.text(str, data.settings.margin.left + doc.internal.pageSize.width, doc.internal.pageSize.height - 20, "right");
        };

        var margen = {
            left: 19.84, right: 19.84, bottom: 28.34
        };
        var dataAuto;
        var totalPagesExp = "{total_pages_count_string}";
        var fecha = getFechaActualCompleta();
//        var anchoPagina = 612;
        var orientacionHoja = contenidoPdf.orientacion || "p";
        // letter | legal
        var pagina = contenidoPdf.pagina || "letter";
//        if (orientacionHoja === "l") {
//            anchoPagina = 792;
//        }
        var doc = new jsPDF(orientacionHoja, "pt", pagina);
        var anchoPagina = doc.internal.pageSize.width;
        var top_tabla = 70;
        if (typeof contenidoPdf.descripcion === 'function') {
            header();
            doc = new jsPDF(orientacionHoja, "pt", pagina);
        }
        var tam_pie_pag = 0;
        if (typeof contenidoPdf.piePagina === 'function') {
            tam_pie_pag = contenidoPdf.piePagina();
            doc = new jsPDF(orientacionHoja, "pt", pagina);
        }



        var options = {beforePageContent: header,
            afterPageContent: footer,
            margin: {top: top_tabla, left: margen.left, right: margen.right, bottom: margen.bottom - tam_pie_pag},
            styles: {overflow: 'linebreak', lineWidth: 0.5, lineColor: 0, fillStyle: 'S', textColor: 0, valign: 'middle', cellPadding: 2.83},
            headerStyles: {
                fontSize: 5,
                fontStyle: 'bold',
                halign: 'center'
            },
            bodyStyles: {
                fontSize: 5
            },
            drawCell: function (cell, data) {
                if (contenidoPdf.colSpan) {
                    if (contenidoPdf.colSpan[data.row.index + ""]) {
                        return false;
                    }
                }
                if (data.row.index === data.table.rows.length - 1 && contenidoPdf.pieTabla) {
                    var pie = contenidoPdf.pieTabla;
                    // DEFINO ESTILOS
                    if (pie.estilo && pie.estilo.estiloFuente)
                        doc.setFontStyle(pie.estilo.estiloFuente);
                    if (pie.contenido && pie.contenido[data.column.dataKey]) {
                        if (pie.contenido[data.column.dataKey] === true) {
                            return;
                        }
                        var col = pie.contenido[data.column.dataKey];
                        // border
                        if (col.borde) {
                            var dibujar_borde = false;
                            if (col.borde instanceof Array && col.borde.length === 2 || col.borde.length === 4) {
                                dibujar_borde = true;
                                if (col.borde.length === 2) {
                                    col.borde = col.borde.concat(col.borde);
                                }
                            }
                            if (typeof col.borde == 'boolean' && col.borde === true) {
                                col.borde = [col.borde, col.borde, col.borde, col.borde];
                                dibujar_borde = true;
                            }
                            if (dibujar_borde) {
                                if (col.borde[0]) {
                                    doc.line(cell.x, cell.y, cell.x + cell.width, cell.y);
                                }
                                if (col.borde[1]) {
                                    doc.line(cell.x + cell.width, cell.y, cell.x + cell.width, cell.y + cell.height);
                                }
                                if (col.borde[2]) {
                                    doc.line(cell.x, cell.y + cell.height, cell.x + cell.width, cell.y + cell.height);
                                }
                                if (col.borde[3]) {
                                    doc.line(cell.x, cell.y, cell.x, cell.y + cell.height);
                                }
                            }
                        }
                        //text
                        if (col.texto) {
                            var xxx = cell.x + 5;
                            if (col.alineacion === 'right') {
                                xxx = cell.x + cell.width - 5;
                            } else {
                                col.alineacion === 'left';
                            }
                            doc.autoTableText(col.texto, xxx, cell.y + cell.height / 2, {
                                halign: col.alineacion,
                                valign: 'middle'
                            });
                        }
                    }
                    return false;
                }
                if (contenidoPdf.cuerpoTablaSpan) {
                    if (contenidoPdf.cuerpoTablaSpan[data.row.index + ""]) {
                        var fila = contenidoPdf.cuerpoTablaSpan[data.row.index + ""];
                        for (var i = 0, max = fila.length; i < max; i++) {
                            var span = fila[i];
                            if (span.columnas[0] == data.column.dataKey) {
                                span.posicion = {
                                    x: cell.x,
                                    y: cell.y
                                };
                                span.textPos = {
                                    x: cell.textPos.x,
                                    y: cell.textPos.y
                                };
                                contenidoPdf.cuerpoTablaSpan.estado = true;
                            }
                            if (span.columnas[span.columnas.length - 1] == data.column.dataKey) {
                                var ww = cell.x + cell.width - span.posicion.x;
                                doc.rect(span.posicion.x, span.posicion.y, ww, cell.height, 'S');
                                var xxx = span.posicion.x + 5;
                                if (span.alineacion === 'right') {
                                    xxx = span.posicion.x + ww - 5;
                                } else if (span.alineacion === 'center') {
                                    xxx = span.posicion.x + (ww / 2);
                                }
                                doc.autoTableText(span.texto, xxx, cell.y + cell.height / 2, {
                                    halign: span.alineacion,
                                    valign: 'middle'
                                });
                                contenidoPdf.cuerpoTablaSpan.estado = false;
                                return false;
                            }
                        }
                    }
                    if (contenidoPdf.cuerpoTablaSpan.estado) {
                        return false;
                    }
                }
            },
            drawRow: function (row, data) {
                // Colspan
                if (contenidoPdf.colSpan) {
                    doc.setFontStyle('bold');
                    doc.setFontSize(5);
                    if (contenidoPdf.colSpan[row.index + ""]) {
                        if (contenidoPdf.colSpan[row.index + ""].fondo) {
                            var fon = contenidoPdf.colSpan[row.index + ""].fondo;
                            doc.setDrawColor(0);
                            doc.setFillColor(fon[0], fon[1], fon[2]);
                            doc.rect(data.settings.margin.left, row.y, data.table.width, row.height, 'FD');
                        } else {
                            doc.rect(data.settings.margin.left, row.y, data.table.width, row.height, 'S');
                        }
                        doc.autoTableText(contenidoPdf.colSpan[row.index + ""].texto, data.settings.margin.left + 20, row.y + row.height / 2, {
                            valign: 'middle'
                        });
                    }
                }
                if (contenidoPdf.estiloFila) {
                    if (contenidoPdf.estiloFila[row.index + ""].fondo) {
                        var fon = contenidoPdf.estiloFila[row.index + ""].fondo;
                        doc.setDrawColor(0);
                        doc.setFillColor(fon[0], fon[1], fon[2]);
                        doc.rect(data.settings.margin.left, row.y, data.table.width, row.height, 'FD');
                    }
                }
            }
        };
        if (contenidoPdf.estiloColumnas) {
            options.columnStyles = contenidoPdf.estiloColumnas;
        }
        if (contenidoPdf.ocultarCabeceraTabla === true) {
            options.showHeader = 'never';
        }

        if (contenidoPdf.estiloCabecera) {
            $.extend(true, options.headerStyles, contenidoPdf.estiloCabecera);
        }
        if (contenidoPdf.estiloCuerpo) {
            $.extend(true, options.bodyStyles, contenidoPdf.estiloCuerpo);
        }
        doc.autoTable(contenidoPdf.cabeceraTabla, contenidoPdf.cuerpotabla, options);

        if (typeof contenidoPdf.afterTable === 'function') {
            contenidoPdf.afterTable(doc, doc.autoTableEndPosY() + 30, options.margin, footer, dataAuto);
        }

        // Total page number plugin only available in jspdf v1.0+
        if (typeof doc.putTotalPages === 'function') {
            doc.putTotalPages(totalPagesExp);
        }
        doc.save(contenidoPdf.titulo + '.pdf');
        if (typeof notificarTermino === 'function') {
            notificarTermino(true);
        }
    };

    this.setListenerNotificarTermino = function (listener) {
        notificarTermino = listener;
    };
}
function escribir_varias_lineas_pdf(doc, texto, size, x, y, tamano, alineado, medida) {
//    var conversion = 0.0393701;
//    var tam = tamano * conversion;
    var med = medida || "mm";
    var lineas = doc.splitTextToSize(texto || "", tamano);
    if (alineado && alineado !== "left") {
        doc.text(lineas, x, y, alineado);
    } else {
        doc.text(x, y, lineas);
    }
    var factor;
    switch (med) {
        case "mm":
            factor = 25.4;
            break;
        case "in":
            factor = 1;
            break;
        case "pt":
            factor = 72;
            break;
    }
    return ((lineas.length - 1) * size / 72) * 1.15 * factor;
}
function alto_varias_lineas(doc, texto, size, tamano, medida) {
    var med = medida || "mm";
    var lineas = doc.splitTextToSize(texto, tamano);
    var factor;
    switch (med) {
        case "mm":
            factor = 25.4;
            break;
        case "in":
            factor = 1;
            break;
        case "pt":
            factor = 72;
            break;
    }
    var alto = ((lineas.length - 1) * size / 72) * 1.15 * factor;
    return {
        texto: lineas,
        alto: alto
    };
}
function alto_string(size, medida) {
    var med = medida || "mm";
    var factor;
    switch (med) {
        case "mm":
            factor = 25.4;
            break;
        case "in":
            factor = 1;
            break;
        case "pt":
            factor = 72;
            break;
    }
    var alto = (size / 72) * 1.15 * factor;
    return alto;
}
function escribir_varias_lineas_pdf_restringido(doc, texto, size, x, y, tamano, alto, alineado, medida) {
//    var conversion = 0.0393701;
//    var tam = tamano * conversion;
    var med = medida || "mm";
    var lineas = doc.splitTextToSize(texto, tamano);
    var factor;
    switch (med) {
        case "mm":
            factor = 25.4;
            break;
        case "in":
            factor = 1;
            break;
        case "pt":
            factor = 72;
            break;
    }
    var alt = alto_string(size, medida);
    var x_ = alt;
    var i = 0;
    while ((x_ <= alto || i === 0) && i < lineas.length) {
        if (alineado && alineado !== "left") {
            doc.text(lineas[i], x, y, alineado);
        } else {
            doc.text(x, y, lineas[i]);
        }
        x_ += alt;
        i++;
    }
    //return ((lineas.length - 1) * size / 72) * 1.15 * factor;
}

function escribir_varias_lineas_pdf_restringido_cuadrado(doc, texto, size, x, y, tamano, alto, alineado, medida) {
//    var conversion = 0.0393701;
//    var tam = tamano * conversion;
    var med = medida || "mm";
    var lineas = doc.splitTextToSize((texto + "").trim(), tamano);
    var factor;
    switch (med) {
        case "mm":
            factor = 25.4;
            break;
        case "in":
            factor = 1;
            break;
        case "pt":
            factor = 72;
            break;
    }
    var alt = alto_string(size, medida);
    var cantli = lineas.length;
    if (cantli > 1) {
        cantli = parseInt((alto / alt));
        if (cantli < 1)
            cantli = 1;
        if (cantli < lineas.length) {
            var para_puntos = lineas[cantli - 1];
            lineas[cantli - 1] = para_puntos.substr(0, para_puntos.length - 4) + "...";
        } else {
            cantli = lineas.length;
        }
    }

    var y_ = y + alt;
    var i = 0;
    while (i < cantli) {
        if (alineado && alineado !== "left") {
            doc.text(lineas[i], x, y_, alineado);
        } else {
            doc.text(x, y_, lineas[i]);
        }
        y_ += alt;
        i++;
    }
    //return ((lineas.length - 1) * size / 72) * 1.15 * factor;
}


if (jsPDF && jsPDF.API) {
    (function (jsPDFAPI) {
        'use strict';

        jsPDFAPI.replaceWith = function (pageExpression, newContent) {
            'use strict';
            var replaceExpression = new RegExp(pageExpression, 'g');
            for (var n = 1; n <= this.internal.getNumberOfPages(); n++) {
                for (var i = 0; i < this.internal.pages[n].length; i++)
                    this.internal.pages[n][i] = this.internal.pages[n][i].replace(replaceExpression, newContent);
            }
            return this;
        };

    })(jsPDF.API);
} else {
    console.error("Importar el plugin de jsPDF");
}

(function ($) {

    function REPORTE_PDF_2() {
        var logoEmpresa = undefined;
        var contenidoPdf;
        var notificarTermino;
        //<editor-fold defaultstate="collapsed" desc="CARGAR IMAGENES">
        var cargarImagen = function (imagen, url, carga, error, outputFormat) {
            imagen.onError = function () {
                error();
            };
            imagen.onload = function () {
                carga();
            };
            imagen.src = url;
        };

        var cargarLogoEmpresa = function (funcionCarga) {
            if (logoEmpresa === undefined) {
                logoEmpresa = new Image();
            }
            var url = "../img/LOGO_COLOR.png";
            cargarImagen(logoEmpresa, url, funcionCarga, function () {
                notificarTermino(false);
                logoEmpresa = undefined;
            });
        };
        //</editor-fold>

        var getFechaActualCompleta = function () {
            var date = new Date();
            var mes = (date.getMonth() + 1) + "";
            mes = mes.length === 1 ? "0" + mes : mes;
            var dia = date.getDate() + "";
            dia = dia.length === 1 ? "0" + dia : dia;
            var año = date.getFullYear();
            var hora = date.getHours() + "";
            hora = hora.length === 1 ? "0" + hora : hora;
            var min = date.getMinutes() + "";
            min = min.length === 1 ? "0" + min : min;
            var seg = date.getSeconds() + "";
            seg = seg.length === 1 ? "0" + seg : seg;
            return  dia + "/" + mes + "/" + año + " " + hora + ":" + min + ":" + seg;
        };

        this.setContenidoPdf = function (contenido) {
            contenidoPdf = contenido;
        };

        this.generarPDF = function () {

            if (logoEmpresa === undefined) {
                cargarLogoEmpresa(this.generarPDF);
                return;
            }

            var header = function (data) {
                doc.addImage(logoEmpresa, 'PNG', 19.84, 19.84, 141.73, 28.35);
                doc.setFontSize(12);
                doc.setFontType("bold");
                doc.text(contenidoPdf.titulo, anchoPagina / 2, 28.35, "center");
                doc.setFontSize(8);
                doc.setFontType("bold");
                if(contenidoPdf.colorSubtitulo){
                    doc.setTextColor(contenidoPdf.colorSubtitulo);
                }
                doc.text(contenidoPdf.subtitulo, anchoPagina / 2, 42.52, "center");
                doc.setTextColor(0,0,0);
                if (typeof contenidoPdf.descripcion === 'function') {
                    top_tabla = contenidoPdf.descripcion(doc, 19.84, 65.20, margen);
                    top_tabla += 10;
                } else {
                    doc.text(19.84, 65.20, contenidoPdf.descripcion);
                }
            };

            var footer = function (data) {
                var tam_fp = 20 + tam_pie_pag;
                dataAuto = data;
                if (contenidoPdf.mostrarFecha)
                    doc.text("Fecha realizada:  " + fecha, data.settings.margin.left, doc.internal.pageSize.height - 20);
                var str = "Pagina " + data.pageCount;
                if (typeof doc.putTotalPages === 'function') {
                    str = str + " de " + totalPagesExp;
                }
                doc.text(str, data.settings.margin.left + doc.internal.pageSize.width, doc.internal.pageSize.height - 20, "right");
            };

            var margen = {
                left: 19.84, right: 19.84, bottom: 28.34
            };

            var dataAuto;
            var totalPagesExp = "{total_pages_count_string}";
            var fecha = getFechaActualCompleta();
//            var anchoPagina = 612;
            var orientacionHoja = contenidoPdf.orientacion || "p";
            // letter | legal
            var pagina = contenidoPdf.pagina || "letter";
//            if (orientacionHoja === "l") {
//                anchoPagina = 792;
//            }
            var doc = new jsPDF(orientacionHoja, "pt", pagina);
            var anchoPagina = doc.internal.pageSize.width;
            var docAux = new jsPDF(orientacionHoja, "pt", pagina);
            var top_tabla = 70;
            if (typeof contenidoPdf.descripcion === 'function') {
                header();
                doc = new jsPDF(orientacionHoja, "pt", pagina);
            }
            var tam_pie_pag = 0;
            if (typeof contenidoPdf.piePagina === 'function') {
                tam_pie_pag = contenidoPdf.piePagina();
                doc = new jsPDF(orientacionHoja, "pt", pagina);
            }
            var startY = top_tabla;
            var tablas = contenidoPdf.tablas;
            var tabla, auxY;
            for (var j = 0; j < tablas.length; j++) {
                tabla = tablas[j];
                var options = {
                    beforePageContent: header,
                    afterPageContent: footer,
                    margin: {top: top_tabla, left: margen.left, right: margen.right, bottom: margen.bottom - tam_pie_pag},
                    styles: {overflow: 'linebreak', lineWidth: 0.5, lineColor: 0, fillStyle: 'S', textColor: 0, valign: 'middle', cellPadding: 2.83},
                    headerStyles: {
                        fontSize: 5,
                        fontStyle: 'bold',
                        halign: 'center'
                    },
                    bodyStyles: {
                        fontSize: 5
                    },
                    drawCell: function (cell, data) {
                        if (tabla.colSpan) {
                            if (tabla.colSpan[data.row.index + ""]) {
                                return false;
                            }
                        }
                        if (data.row.index === data.table.rows.length - 1 && tabla.pieTabla) {
                            var pie = tabla.pieTabla;
                            // DEFINO ESTILOS
                            if (pie.estilo && pie.estilo.estiloFuente)
                                doc.setFontStyle(pie.estilo.estiloFuente);
                            if (pie.contenido && pie.contenido[data.column.dataKey]) {
                                if (pie.contenido[data.column.dataKey] === true) {
                                    return;
                                }
                                var col = pie.contenido[data.column.dataKey];
                                // border
                                if (col.borde) {
                                    var dibujar_borde = false;
                                    if (col.borde instanceof Array && col.borde.length === 2 || col.borde.length === 4) {
                                        dibujar_borde = true;
                                        if (col.borde.length === 2) {
                                            col.borde = col.borde.concat(col.borde);
                                        }
                                    }
                                    if (typeof col.borde == 'boolean' && col.borde === true) {
                                        col.borde = [col.borde, col.borde, col.borde, col.borde];
                                        dibujar_borde = true;
                                    }
                                    if (dibujar_borde) {
                                        if (col.borde[0]) {
                                            doc.line(cell.x, cell.y, cell.x + cell.width, cell.y);
                                        }
                                        if (col.borde[1]) {
                                            doc.line(cell.x + cell.width, cell.y, cell.x + cell.width, cell.y + cell.height);
                                        }
                                        if (col.borde[2]) {
                                            doc.line(cell.x, cell.y + cell.height, cell.x + cell.width, cell.y + cell.height);
                                        }
                                        if (col.borde[3]) {
                                            doc.line(cell.x, cell.y, cell.x, cell.y + cell.height);
                                        }
                                    }
                                }
                                //text
                                if (col.texto) {
                                    var xxx = cell.x + 5;
                                    if (col.alineacion === 'right') {
                                        xxx = cell.x + cell.width - 5;
                                    } else {
                                        col.alineacion === 'left';
                                    }
                                    doc.autoTableText(col.texto, xxx, cell.y + cell.height / 2, {
                                        halign: col.alineacion,
                                        valign: 'middle'
                                    });
                                }
                            }
                            return false;
                        }
                        if (tabla.cuerpoTablaSpan) {
                            if (tabla.cuerpoTablaSpan[data.row.index + ""]) {
                                var fila = tabla.cuerpoTablaSpan[data.row.index + ""];
                                for (var i = 0, max = fila.length; i < max; i++) {
                                    var span = fila[i];
                                    if (span.columnas[0] == data.column.dataKey) {
                                        span.posicion = {
                                            x: cell.x,
                                            y: cell.y
                                        };
                                        span.textPos = {
                                            x: cell.textPos.x,
                                            y: cell.textPos.y
                                        };
                                        tabla.cuerpoTablaSpan.estado = true;
                                    }
                                    if (span.columnas[span.columnas.length - 1] == data.column.dataKey) {
                                        var ww = cell.x + cell.width - span.posicion.x;
                                        doc.rect(span.posicion.x, span.posicion.y, ww, cell.height, 'S');
                                        var xxx = span.posicion.x + 5;
                                        if (span.alineacion === 'right') {
                                            xxx = span.posicion.x + ww - 5;
                                        } else if (span.alineacion === 'center') {
                                            xxx = span.posicion.x + (ww / 2);
                                        }
                                        doc.autoTableText(span.texto, xxx, cell.y + cell.height / 2, {
                                            halign: span.alineacion,
                                            valign: 'middle'
                                        });
                                        tabla.cuerpoTablaSpan.estado = false;
                                        return false;
                                    }
                                }
                            }
                            if (tabla.cuerpoTablaSpan.estado) {
                                return false;
                            }
                        }
                    },
                    drawRow: function (row, data) {
                        // Colspan
                        if (tabla.colSpan) {
                            doc.setFontStyle('bold');
                            doc.setFontSize(5);
                            if (tabla.colSpan[row.index + ""]) {
                                if (tabla.colSpan[row.index + ""].fondo) {
                                    var fon = tabla.colSpan[row.index + ""].fondo;
                                    doc.setDrawColor(0);
                                    doc.setFillColor(fon[0], fon[1], fon[2]);
                                    doc.rect(data.settings.margin.left, row.y, data.table.width, row.height, 'FD');
                                } else {
                                    doc.rect(data.settings.margin.left, row.y, data.table.width, row.height, 'S');
                                }
                                doc.autoTableText(tabla.colSpan[row.index + ""].texto, data.settings.margin.left + 20, row.y + row.height / 2, {
                                    valign: 'middle'
                                });
                            }
                        }
                        if (tabla.estiloFila) {
                            if (tabla.estiloFila[row.index + ""].fondo) {
                                var fon = tabla.estiloFila[row.index + ""].fondo;
                                doc.setDrawColor(0);
                                doc.setFillColor(fon[0], fon[1], fon[2]);
                                doc.rect(data.settings.margin.left, row.y, data.table.width, row.height, 'FD');
                            }
                        }
                    }
                };

                if (tabla.estiloColumnas) {
                    options.columnStyles = tabla.estiloColumnas;
                }
                if (tabla.ocultarCabeceraTabla === true) {
                    options.showHeader = 'never';
                }
                if (tabla.estiloCabecera) {
                    $.extend(true, options.headerStyles, tabla.estiloCabecera);
                }
                if (tabla.estiloCuerpo) {
                    $.extend(true, options.bodyStyles, tabla.estiloCuerpo);
                }

                if (typeof tabla.antes === 'function') {
                    auxY = tabla.antes(docAux, startY);
                    if (auxY + 100 >= doc.internal.pageSize.height - options.margin.bottom) {
                        doc.addPage();
                        startY = top_tabla;
                    }
                    startY = tabla.antes(doc, startY);
                }
                startY += 10;
                options.startY = startY;
                doc.autoTable(tabla.cabecera, tabla.cuerpo, options);
                startY = doc.autoTableEndPosY();
                if (typeof tabla.despues === 'function')
                    startY = tabla.despues(doc, startY);
                startY += 10;
            }

            if (typeof contenidoPdf.afterTable === 'function') {
                contenidoPdf.afterTable(doc, doc.autoTableEndPosY() + 30, options.margin, footer, dataAuto);
            }

            // Total page number plugin only available in jspdf v1.0+
            if (typeof doc.putTotalPages === 'function') {
                doc.putTotalPages(totalPagesExp);
            }
            doc.save(contenidoPdf.titulo + '.pdf');
            if (typeof notificarTermino === 'function') {
                notificarTermino(true);
            }
        };

        this.setListenerNotificarTermino = function (listener) {
            notificarTermino = listener;
        };
    }
    $.ReporteTablasPDF = new REPORTE_PDF_2();
})(jQuery);

