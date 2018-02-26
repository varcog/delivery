package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PERMISO {

    private int ID;
    private int ID_CARGO;
    private int ID_SUB_MENU;
    private boolean ALTA;
    private boolean BAJA;
    private boolean MODIFICACION;
    private Conexion con;

    public PERMISO(Conexion con) {
        this.con = con;
    }

    public PERMISO(int ID, int ID_CARGO, int ID_SUB_MENU, boolean ALTA, boolean BAJA, boolean MODIFICACION) {
        this.ID = ID;
        this.ID_CARGO = ID_CARGO;
        this.ID_SUB_MENU = ID_SUB_MENU;
        this.ALTA = ALTA;
        this.BAJA = BAJA;
        this.MODIFICACION = MODIFICACION;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_CARGO() {
        return ID_CARGO;
    }

    public void setID_CARGO(int ID_CARGO) {
        this.ID_CARGO = ID_CARGO;
    }

    public int getID_SUB_MENU() {
        return ID_SUB_MENU;
    }

    public void setID_SUB_MENU(int ID_SUB_MENU) {
        this.ID_SUB_MENU = ID_SUB_MENU;
    }

    public boolean isALTA() {
        return ALTA;
    }

    public void setALTA(boolean ALTA) {
        this.ALTA = ALTA;
    }

    public boolean isBAJA() {
        return BAJA;
    }

    public void setBAJA(boolean BAJA) {
        this.BAJA = BAJA;
    }

    public boolean isMODIFICACION() {
        return MODIFICACION;
    }

    public void setMODIFICACION(boolean MODIFICACION) {
        this.MODIFICACION = MODIFICACION;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"PERMISO\"(\n"
                + "	\"ID_CARGO\", \"ID_SUB_MENU\", \"ALTA\", \"BAJA\", \"MODIFICACION\")\n"
                + "	VALUES (?,?,?,?,?)";
        int id = con.EjecutarInsert(consulta, "ID", ID_CARGO, ID_SUB_MENU, ALTA, BAJA, MODIFICACION);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"PERMISO\"\n"
                + "	SET \"ID_CARGO\"=?, \"ID_SUB_MENU\"=?, \"ALTA\"=?, \"BAJA\"=?, \"MODIFICACION\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID_CARGO, ID_SUB_MENU, ALTA, BAJA, MODIFICACION, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"PERMISO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"PERMISO\"\n"
                + "ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_CARGO", rs.getInt("ID_CARGO"));
            obj.put("ID_SUB_MENU", rs.getInt("ID_SUB_MENU"));
            obj.put("ALTA", rs.getBoolean("ALTA"));
            obj.put("BAJA", rs.getBoolean("BAJA"));
            obj.put("MODIFICACION", rs.getBoolean("MODIFICACION"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public PERMISO buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"PERMISO\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        PERMISO p = new PERMISO(con);
        if (rs.next()) {
            p.setID(rs.getInt("ID"));
            p.setID_CARGO(rs.getInt("ID_CARGO"));
            p.setID_SUB_MENU(rs.getInt("ID_SUB_MENU"));
            p.setALTA(rs.getBoolean("ALTA"));
            p.setBAJA(rs.getBoolean("BAJA"));
            p.setMODIFICACION(rs.getBoolean("MODIFICACION"));
            return p;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("ID_CARGO", ID_CARGO);
        obj.put("ID_SUB_MENU", ID_SUB_MENU);
        obj.put("ALTA", ALTA);
        obj.put("BAJA", BAJA);
        obj.put("MODIFICACION", MODIFICACION);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONArray todosXCargo(int id_cargo) throws SQLException, JSONException {
        String consulta = "SELECT \"PERMISO\".*\n"
                + "	FROM public.\"PERMISO\"\n"
                + "     WHERE \"PERMISO\".\"ID_CARGO\" = ?";
        PreparedStatement ps = con.statametObject(consulta, id_cargo);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_CARGO", rs.getInt("ID_CARGO"));
            obj.put("ID_SUB_MENU", rs.getInt("ID_SUB_MENU"));
            obj.put("ALTA", rs.getBoolean("ALTA"));
            obj.put("BAJA", rs.getBoolean("BAJA"));
            obj.put("MODIFICACION", rs.getBoolean("MODIFICACION"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public void delete(int id_cargo, int id_sub_menu) throws SQLException {
        String consulta = "DELETE FROM public.\"PERMISO\"\n"
                + "	WHERE \"PERMISO\".\"ID_CARGO\"=?\n"
                + "           AND \"PERMISO\".\"ID_SUB_MENU\"=?;";
        con.EjecutarSentencia(consulta, id_cargo, id_sub_menu);
    }
}
