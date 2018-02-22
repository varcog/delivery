package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MENU {

    private int ID;
    private String DESCRIPCION;
    private Conexion con;

    public MENU(Conexion con) {
        this.con = con;
    }

    public MENU(int ID, String DESCRIPCION) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"MENU\"(\n"
                + "	\"DESCRIPCION\")\n"
                + "	VALUES (?)";
        int id = con.EjecutarInsert(consulta, "ID", DESCRIPCION);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"MENU\"\n"
                + "	SET \"DESCRIPCION\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, DESCRIPCION, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"MENU\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"MENU\"\n"
                + "ORDER BY \"DESCRIPCION\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public MENU buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"MENU\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        MENU m = new MENU(con);
        if (rs.next()) {
            m.setID(rs.getInt("ID"));
            m.setDESCRIPCION(rs.getString("DESCRIPCION"));
            return m;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("DESCRIPCION", DESCRIPCION);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONObject bucarMenuYSubMenuXCargo(int idCargo) throws SQLException, JSONException {
        String consulta = "SELECT \"MENU\".\"ID\",\n"
                + "	     \"MENU\".\"DESCRIPCION\"\n"
                + "	FROM public.\"MENU\", \n"
                + "    	     public.\"SUB_MENU\",\n"
                + "          public.\"PERMISO\"\n"
                + "    WHERE \"PERMISO\".\"ID_CARGO\" = " + idCargo + "\n"
                + "    	     AND \"PERMISO\".\"ID_SUB_MENU\" = \"SUB_MENU\".\"ID\"\n"
                + "          AND \"SUB_MENU\".\"ID_MENU\" = \"MENU\".\"ID\"";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject json = new JSONObject();
        SUB_MENU sub_menu = new SUB_MENU(con);
        while (rs.next()) {
            json.put(rs.getString("DESCRIPCION"), sub_menu.bucarSubMenuXMenu(rs.getInt("ID")));
        }
        rs.close();
        ps.close();
        return json;
    }
}
