package controller;

import conexion.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.INSUMO;
import modelo.INSUMO_GRUPO;
import modelo.INSUMO_GRUPO_DETALLE;
import modelo.USUARIO;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author benja
 */
@WebServlet(name = "ALMACEN_INSUMOS_GRUPO_CONTROLLER", urlPatterns = {"/ALMACEN_INSUMOS_GRUPO_CONTROLLER"})
public class ALMACEN_INSUMOS_GRUPO_CONTROLLER extends HttpServlet {

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
                case "guardar_insumo_grupo":
                    html = guardar_insumo_grupo(request, con);
                    break;
                case "eliminar_insumo_grupo":
                    html = eliminar_insumo_grupo(request, con);
                    break;
                case "insumo_grupo_detalle":
                    html = insumo_grupo_detalle(request, con);
                    break;
                case "guardar_insumo_grupo_detalle":
                    html = guardar_insumo_grupo_detalle(request, con);
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
        json.put("INSUMOS", new INSUMO(con).todosConUM());
        json.put("INSUMOS_GRUPO", new INSUMO_GRUPO(con).todos());
        return json.toString();
    }

    private String guardar_insumo_grupo(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        if (id > 0) {
            INSUMO_GRUPO in = new INSUMO_GRUPO(con).buscar(id);
            if (in == null) {
                return "false";
            }
            in.setDESCRIPCION(descripcion);
            in.update();
            return in.toJSONObject().toString();
        } else {
            INSUMO_GRUPO in = new INSUMO_GRUPO(id, descripcion, con);
            in.insert();
            return in.toJSONObject().toString();
        }
    }

    private String eliminar_insumo_grupo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        INSUMO_GRUPO in = new INSUMO_GRUPO(con);
        in.setID(id);
        in.delete();
        return "true";
    }

    private String insumo_grupo_detalle(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id_insumo_grupo = Integer.parseInt(request.getParameter("id_insumo_grupo"));
        return new INSUMO_GRUPO_DETALLE(con).todosXID_INSUMO_GRUPO_JSONArray(id_insumo_grupo).toString();
    }

    private String guardar_insumo_grupo_detalle(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id_insumo_grupo = Integer.parseInt(request.getParameter("id_insumo_grupo"));
        int length = Integer.parseInt(request.getParameter("length"));
        INSUMO_GRUPO_DETALLE igd = new INSUMO_GRUPO_DETALLE(con);
        igd.deleteXID_INSUMO_GRUPO(id_insumo_grupo);
        for (int i = 0; i < length; i++) {
            igd.setID(0);
            igd.setID_INSUMO_GRUPO(id_insumo_grupo);
            igd.setID_INSUMO(Integer.parseInt(request.getParameter("lista[" + i + "][id_insumo]")));
            igd.setCANTIDAD(Double.parseDouble(request.getParameter("lista[" + i + "][cantidad]")));
            igd.insert();
        }
        return "true";
    }

}
