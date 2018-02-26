package controller;

import conexion.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.UNIDAD_MEDIDA;
import modelo.USUARIO;
import org.json.JSONException;

/**
 *
 * @author benja
 */
@WebServlet(name = "ALMACEN_UNIDAD_MEDIDA_CONTROLLER", urlPatterns = {"/ALMACEN_UNIDAD_MEDIDA_CONTROLLER"})
public class ALMACEN_UNIDAD_MEDIDA_CONTROLLER extends HttpServlet {

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
                case "guardar_undidad_medida":
                    html = guardar_undidad_medida(request, con);
                    break;
                case "eliminar_unidad_medida":
                    html = eliminar_unidad_medida(request, con);
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
        return new UNIDAD_MEDIDA(con).todos().toString();
    }

    private String guardar_undidad_medida(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        String abreviacion = request.getParameter("abreviacion");
        if (id > 0) {
            UNIDAD_MEDIDA um = new UNIDAD_MEDIDA(con).buscar(id);
            if (um == null) {
                return "false";
            }
            um.setDESCRIPCION(descripcion);
            um.setABREVIACION(abreviacion);
            um.update();
            return um.toJSONObject().toString();
        } else {
            UNIDAD_MEDIDA um = new UNIDAD_MEDIDA(id, descripcion, abreviacion, con);
            um.insert();
            return um.toJSONObject().toString();
        }
    }

    private String eliminar_unidad_medida(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        UNIDAD_MEDIDA c = new UNIDAD_MEDIDA(con);
        c.setID(id);
        c.delete();
        return "true";
    }

}
