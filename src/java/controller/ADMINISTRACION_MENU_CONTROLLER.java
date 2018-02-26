package controller;

import conexion.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.MENU;
import modelo.SUB_MENU;
import modelo.USUARIO;
import org.json.JSONException;

/**
 *
 * @author benja
 */
@WebServlet(name = "ADMINISTRACION_MENU_CONTROLLER", urlPatterns = {"/ADMINISTRACION_MENU_CONTROLLER"})
public class ADMINISTRACION_MENU_CONTROLLER extends HttpServlet {

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
                case "todos":
                    html = todos(request, con);
                    break;
                case "guardar_menu":
                    html = guardar_menu(request, con);
                    break;
                case "eliminar_menu":
                    html = eliminar_menu(request, con);
                    break;
                case "todos_sub_menu":
                    html = todos_sub_menu(request, con);
                    break;
                case "guardar_sub_menu":
                    html = guardar_sub_menu(request, con);
                    break;
                case "eliminar_sub_menu":
                    html = eliminar_sub_menu(request, con);
                    break;
                case "cambiar_estado_sub_menu":
                    html = cambiar_estado_sub_menu(request, con);
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

    private String todos(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        return new MENU(con).todos().toString();
    }

    private String guardar_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        if (id > 0) {
            MENU m = new MENU(con).buscar(id);
            if (m == null) {
                return "false";
            }
            m.setDESCRIPCION(descripcion);
            m.update();
            return m.toJSONObject().toString();
        } else {
            MENU m = new MENU(id, descripcion, con);
            m.insert();
            return m.toJSONObject().toString();
        }
    }

    private String eliminar_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        MENU m = new MENU(con);
        m.setID(id);
        m.delete();
        return "true";
    }

    private String todos_sub_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_menu = Integer.parseInt(request.getParameter("id_menu"));
        return new SUB_MENU(con).bucarSubMenuXMenu(id_menu).toString();
    }

    private String guardar_sub_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        int id_menu = Integer.parseInt(request.getParameter("id_menu"));
        String descripcion = request.getParameter("descripcion");
        String url = request.getParameter("url");
        if (id > 0) {
            SUB_MENU sm = new SUB_MENU(con).buscar(id);
            if (sm == null) {
                return "false";
            }
            sm.setDESCRIPCION(descripcion);
            sm.setURL(url);
            sm.update();
            return sm.toJSONObject().toString();
        } else {
            SUB_MENU sm = new SUB_MENU(id, descripcion, url, id_menu, con);
            sm.insert();
            return sm.toJSONObject().toString();
        }
    }

    private String eliminar_sub_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        SUB_MENU sm = new SUB_MENU(con);
        sm.setID(id);
        sm.delete();
        return "true";
    }

    private String cambiar_estado_sub_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_sub_menu = Integer.parseInt(request.getParameter("id_sub_menu"));
        boolean visible = Boolean.parseBoolean(request.getParameter("visible"));
        SUB_MENU sm = new SUB_MENU(con);
        sm.update_visible(id_sub_menu, visible);
        return "true";
    }

}
