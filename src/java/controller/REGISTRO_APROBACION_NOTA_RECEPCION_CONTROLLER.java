package controller;

import conexion.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.PRODUCTO;
import modelo.USUARIO;
import org.json.JSONException;

@WebServlet(name = "REGISTRO_APROBACION_NOTA_RECEPCION_CONTROLLER", urlPatterns = {"/REGISTRO_APROBACION_NOTA_RECEPCION_CONTROLLER"})
public class REGISTRO_APROBACION_NOTA_RECEPCION_CONTROLLER extends HttpServlet {

    private int id_sucursal = 1;

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
                case "todos_productos":
                    html = todos_productos(request, con);
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
        int estado = Integer.parseInt(request.getParameter("estado"));
        if (estado == 0) {
            return new PRODUCTO(con).todos_Almacen(id_sucursal).toString();
        } else {
            return new PRODUCTO(con).stock_Almacen(id_sucursal).toString();
        }
    }

    private String todos_productos(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        return new PRODUCTO(con).todos().toString();
    }

}
