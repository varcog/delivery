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
import modelo.UNIDAD_MEDIDA;
import modelo.USUARIO;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author benja
 */
@WebServlet(name = "ALMACEN_INSUMOS_CONTROLLER", urlPatterns = {"/ALMACEN_INSUMOS_CONTROLLER"})
public class ALMACEN_INSUMOS_CONTROLLER extends HttpServlet {

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
                case "datos_insumo":
                    html = datos_insumo(request, con);
                    break;
                case "guardar_insumo":
                    html = guardar_insumo(request, con);
                    break;
                case "eliminar_insumo":
                    html = eliminar_insumo(request, con);
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
        json.put("UNIDAD_MEDIDAS", new UNIDAD_MEDIDA(con).todos());
        return json.toString();
    }

    private String guardar_insumo(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        int unidad_medida = Integer.parseInt(request.getParameter("unidad_medida"));
        String descripcion = request.getParameter("descripcion");
        String codigo = request.getParameter("codigo");
        if (id > 0) {
            INSUMO in = new INSUMO(con).buscar(id);
            if (in == null) {
                return "false";
            }
            in.setCODIGO(codigo);
            in.setDESCRIPCION(descripcion);
            in.setID_UNIDAD_MEDIDA(unidad_medida);

            try {
                in.update();
            } catch (Exception e) {
                if (e.getMessage().contains("uq_insumo_codigo")) {
                    return ERROR_CODIGO_REPETIDO;
                } else {
                    throw e;
                }
            }

            return in.toJSONObject().toString();
        } else {
            INSUMO in = new INSUMO(codigo, descripcion, unidad_medida, con);
            try {
                in.insert();
            } catch (Exception e) {
                if (e.getMessage().contains("uq_insumo_codigo")) {
                    return ERROR_CODIGO_REPETIDO;
                } else {
                    throw e;
                }
            }
            return in.toJSONObject().toString();
        }
    }

    private String datos_insumo(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        INSUMO in = new INSUMO(con).buscar(id);
        if (in == null) {
            return "false";
        }
        return in.toJSONObject().toString();
    }

    private String eliminar_insumo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        INSUMO in = new INSUMO(con);
        in.setID(id);
        in.delete();
        return "true";
    }
}
