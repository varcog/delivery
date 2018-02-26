package controller;

import conexion.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.CARGO;
import modelo.MENU;
import modelo.PERMISO;
import modelo.USUARIO;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author benja
 */
@WebServlet(name = "ADMINISTRACION_CARGO_CONTROLLER", urlPatterns = {"/ADMINISTRACION_CARGO_CONTROLLER"})
public class ADMINISTRACION_CARGO_CONTROLLER extends HttpServlet {

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
                case "guardar_cargo":
                    html = guardar_cargo(request, con);
                    break;
                case "eliminar_cargo":
                    html = eliminar_cargo(request, con);
                    break;
                case "todos_sub_menu_asignados":
                    html = todos_sub_menu_asignados(request, con);
                    break;
                case "asignar_desasignar_sub_menu":
                    html = asignar_desasignar_sub_menu(request, con);
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
        JSONObject json = new JSONObject();
        json.put("CARGOS", new CARGO(con).todos());
        json.put("MENUS", new MENU(con).bucarMenuYSubMenuTodos());
        return json.toString();
    }

    private String guardar_cargo(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        if (id > 0) {
            CARGO c = new CARGO(con).buscar(id);
            if (c == null) {
                return "false";
            }
            c.setDESCRIPCION(descripcion);
            c.update();
            return c.toJSONObject().toString();
        } else {
            CARGO c = new CARGO(id, descripcion, con);
            c.insert();
            return c.toJSONObject().toString();
        }
    }

    private String eliminar_cargo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARGO c = new CARGO(con);
        c.setID(id);
        c.delete();
        return "true";
    }

    private String todos_sub_menu_asignados(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_cargo = Integer.parseInt(request.getParameter("id_cargo"));
        return new PERMISO(con).todosXCargo(id_cargo).toString();
    }

    private String asignar_desasignar_sub_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        boolean asignar = Boolean.parseBoolean(request.getParameter("asignar"));
        int id_cargo = Integer.parseInt(request.getParameter("id_cargo"));
        int id_sub_menu = Integer.parseInt(request.getParameter("id_sub_menu"));
        PERMISO p = new PERMISO(con);
        p.delete(id_cargo, id_sub_menu);
        if (asignar) {
            p.setID_CARGO(id_cargo);
            p.setID_SUB_MENU(id_sub_menu);
            p.setALTA(true);
            p.setBAJA(true);
            p.setMODIFICACION(true);
            p.insert();
        }
        return "true";
    }
}
