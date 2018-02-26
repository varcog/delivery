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
import modelo.USUARIO;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "INGRESO_CONTROLLER", urlPatterns = {"/INGRESO_CONTROLLER"})
public class INGRESO_CONTROLLER extends HttpServlet {

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
                case "obtener_menu":
                    html = obtener_menu(request, con);
                    break;
                case "obtener_ingreso":
                    html = obtener_ingreso(request, con);
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

    private String obtener_menu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        USUARIO usuario = con.getUsuario();
        MENU menu = new MENU(con);
        return menu.bucarMenuYSubMenuXCargoVisible(usuario.getID_CARGO()).toString();
    }

    private String obtener_ingreso(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        USUARIO usuario = con.getUsuario();
        MENU menu = new MENU(con);
        JSONObject json = new JSONObject();
        json.put("MENU", menu.bucarMenuYSubMenuXCargoVisible(usuario.getID_CARGO()));
        USUARIO u = con.getUsuario();
        json.put("USUARIO", u.getNombreCompleto());
        CARGO c = new CARGO(con).buscar(u.getID_CARGO());
        if (c != null) {
            json.put("CARGO", c.getDESCRIPCION());
        }
        return json.toString();
    }

}
