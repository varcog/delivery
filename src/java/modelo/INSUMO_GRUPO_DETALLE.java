package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class INSUMO_GRUPO_DETALLE {

    private int ID;
    private int ID_INSUMO;
    private int ID_INSUMO_GRUPO;
    private double CANTIDAD;
    private Conexion con;

    public INSUMO_GRUPO_DETALLE(Conexion con) {
        this.con = con;
    }

    public INSUMO_GRUPO_DETALLE(int ID, int ID_INSUMO, int ID_INSUMO_GRUPO, double CANTIDAD) {
        this.ID = ID;
        this.ID_INSUMO = ID_INSUMO;
        this.ID_INSUMO_GRUPO = ID_INSUMO_GRUPO;
        this.CANTIDAD = CANTIDAD;
    }

    public INSUMO_GRUPO_DETALLE(int ID_INSUMO, int ID_INSUMO_GRUPO, double CANTIDAD) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_INSUMO_GRUPO = ID_INSUMO_GRUPO;
        this.CANTIDAD = CANTIDAD;
    }

    public INSUMO_GRUPO_DETALLE(int ID_INSUMO, int ID_INSUMO_GRUPO, double CANTIDAD, Conexion con) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_INSUMO_GRUPO = ID_INSUMO_GRUPO;
        this.CANTIDAD = CANTIDAD;
        this.con = con;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public int getID_INSUMO_GRUPO() {
        return ID_INSUMO_GRUPO;
    }

    public void setID_INSUMO_GRUPO(int ID_INSUMO_GRUPO) {
        this.ID_INSUMO_GRUPO = ID_INSUMO_GRUPO;
    }

    public double getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(double CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"INSUMO_GRUPO_DETALLE\"(\n"
                + "	\"ID_INSUMO\", \"ID_INSUMO_GRUPO\", \"CANTIDAD\")\n"
                + "	VALUES (?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", ID_INSUMO, ID_INSUMO_GRUPO, CANTIDAD);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"INSUMO_GRUPO_DETALLE\"\n"
                + "	SET \"ID_INSUMO\"=?, \"ID_INSUMO_GRUPO\"=?, \"CANTIDAD\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID_INSUMO, ID_INSUMO_GRUPO, CANTIDAD, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"INSUMO_GRUPO_DETALLE\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"INSUMO_GRUPO_DETALLE\"\n"
                + "ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_INSUMO", rs.getInt("ID_INSUMO"));
            obj.put("ID_INSUMO_GRUPO", rs.getInt("ID_INSUMO_GRUPO"));
            obj.put("CANTIDAD", rs.getDouble("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("ID_INSUMO", ID_INSUMO);
        obj.put("ID_INSUMO_GRUPO", ID_INSUMO_GRUPO);
        obj.put("CANTIDAD", CANTIDAD);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONArray todosXID_INSUMO_GRUPO(int id_insumo_grupo) throws SQLException, JSONException {
        String consulta = "SELECT \"INSUMO_GRUPO_DETALLE\".*\n"
                + "     FROM public.\"INSUMO_GRUPO_DETALLE\"\n"
                + "         INNER JOIN public.\"INSUMO_GRUPO\" ON \"INSUMO_GRUPO\".\"ID\" = \"INSUMO_GRUPO_DETALLE\".\"ID_INSUMO_GRUPO\"\n"
                + "     ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_INSUMO", rs.getInt("ID_INSUMO"));
            obj.put("ID_INSUMO_GRUPO", rs.getInt("ID_INSUMO_GRUPO"));
            obj.put("CANTIDAD", rs.getDouble("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public void deleteXID_INSUMO_GRUPO(int id_insumo_grupo) throws SQLException {
        String consulta = "DELETE FROM public.\"INSUMO_GRUPO_DETALLE\"\n"
                + "	WHERE \"ID_INSUMO_GRUPO\"=?;";
        con.EjecutarSentencia(consulta, id_insumo_grupo);
    }
}
