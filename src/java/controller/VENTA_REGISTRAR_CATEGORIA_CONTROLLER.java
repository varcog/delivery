package controller;

import conexion.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.CATEGORIA_PRODUCTO;
import modelo.USUARIO;
import org.json.JSONException;
import util.SIS_EVENTOS;

@MultipartConfig
@WebServlet(name = "VENTA_REGISTRAR_CATEGORIA_CONTROLLER", urlPatterns = {"/VENTA_REGISTRAR_CATEGORIA_CONTROLLER"})
public class VENTA_REGISTRAR_CATEGORIA_CONTROLLER extends HttpServlet {

    public static final String ERROR_CODIGO_REPETIDO = "CODIGO_REPETIDO";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        USUARIO usuario = ((USUARIO) request.getSession().getAttribute("usr"));
        if (usuario == null) {
            response.getWriter().write("false");
            return;
        }
        Conexion con = usuario.getCon();
        if (con == null) {
            response.getWriter().write("false");
            return;
        }
        con.Transacction();
        try {
            String html = "";
            String evento = request.getParameter("evento");
            switch (evento) {
                case "init":
                    html = init(request, con);
                    break;
                case "guardar_categoria":
                    html = guardar_categoria(request, con);
                    break;
                case "eliminar_categoria":
                    html = eliminar_categoria(request, con);
                    break;
            }
            con.commit();
            response.getWriter().write(html);
        } catch (SQLException ex) {
            con.error(this, ex);
            response.getWriter().write("false");
        } catch (JSONException ex) {
            con.error(this, ex);
            response.getWriter().write("false");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String init(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        return new CATEGORIA_PRODUCTO(con).todosXLevel().toString();
    }

    private String guardar_categoria(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));
        String imagen = null;
        String ruta = null;
        Part file = request.getPart("imagen");
        SIS_EVENTOS ev = new SIS_EVENTOS();
        boolean ok_subir = false;
        String ruta_folder = this.getServletContext().getRealPath("/img") + File.separator + "productos" + File.separator;
        String ruta_web = this.getServletContext().getContextPath() + "/img/productos/";
        Date d = new Date();
        if (id > 0) {
            CATEGORIA_PRODUCTO cp = new CATEGORIA_PRODUCTO(con).buscar(id);
            if (cp == null) {
                return "false";
            }
            if (file != null && file.getSize() > 0 && file.getContentType().contains("image")) {
                String tipo[] = file.getContentType().split("/");
                if (tipo.length > 1) {
                    imagen = ruta_web + "categoria_producto_" + id + "_" + d.getTime() + "." + tipo[1];
                    ruta = ruta_folder + "categoria_producto_" + id + "_" + d.getTime() + "." + tipo[1];
                } else {
                    imagen = ruta_web + "categoria_producto_" + id + "_" + d.getTime() + "_" + file.getSubmittedFileName();
                    ruta = ruta_folder + "categoria_producto_" + id + "_" + d.getTime() + "_" + file.getSubmittedFileName();
                }
                ok_subir = ev.guardarImagenEnElSistemaDeFicheros(file.getInputStream(), ruta);
            }

            if (!ok_subir) {
                imagen = cp.getIMAGEN();
            }
            String antImagen = cp.getIMAGEN();
            cp.setNOMBRE(nombre);
            cp.setIMAGEN(imagen);
            cp.setID_CATEGORIA_PRODUCTO(id_categoria);
            boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
            cp.setESTADO(estado);
            try {
                cp.update();
            } catch (Exception e) {
                ev.eliminarImagenEnElSistemaDeFicheros(ruta);
                throw e;
            }

            if (antImagen != null && ok_subir) {
                String ims[] = antImagen.split("/");
                if (ims.length > 0) {
                    ev.eliminarImagenEnElSistemaDeFicheros(ruta_folder + ims[ims.length - 1]);
                }
            }
            return cp.toJSONObject().toString();
        } else {
            CATEGORIA_PRODUCTO cp = new CATEGORIA_PRODUCTO(id, nombre, imagen, id_categoria, true, con);
            cp.setCon(con);
            id = cp.insert();
            if (file != null && file.getSize() > 0 && file.getContentType().contains("image")) {
                String tipo[] = file.getContentType().split("/");
                if (tipo.length > 1) {
                    imagen = ruta_web + "categoria_producto_" + id + "_" + d.getTime() + "." + tipo[1];
                    ruta = ruta_folder + "categoria_producto_" + id + "_" + d.getTime() + "." + tipo[1];
                } else {
                    imagen = ruta_web + "categoria_producto_" + id + "_" + d.getTime() + "_" + file.getSubmittedFileName();
                    ruta = ruta_folder + "categoria_producto_" + id + "_" + d.getTime() + "_" + file.getSubmittedFileName();
                }
                ok_subir = ev.guardarImagenEnElSistemaDeFicheros(file.getInputStream(), ruta);
            }
            if (ok_subir) {
                cp.setIMAGEN(imagen);
                cp.setID(id);
                cp.update();
            }
            return cp.toJSONObject().toString();
        }
    }

    private String eliminar_categoria(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CATEGORIA_PRODUCTO cp = new CATEGORIA_PRODUCTO(con);
        cp.setID(id);
        cp.delete();
        return "true";
    }

}
