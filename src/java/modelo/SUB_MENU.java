package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SUB_MENU {

    private int ID;
    private String DESCRIPCION;
    private String IMAGEN;
    private String URL;
    private int ID_MENU;
    private Conexion con;

    public SUB_MENU(Conexion con) {
        this.con = con;
    }

    public SUB_MENU(int ID, String DESCRIPCION, String IMAGEN, String URL, int ID_MENU) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;
        this.IMAGEN = IMAGEN;
        this.URL = URL;
        this.ID_MENU = ID_MENU;
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

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getID_MENU() {
        return ID_MENU;
    }

    public void setID_MENU(int ID_MENU) {
        this.ID_MENU = ID_MENU;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"SUB_MENU\"(\n"
                + "	\"DESCRIPCION\", \"IMAGEN\", \"URL\", \"ID_MENU\")\n"
                + "	VALUES (?,?,?,?)";
        int id = con.EjecutarInsert(consulta, "ID", DESCRIPCION, IMAGEN, URL, ID_MENU);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"SUB_MENU\"\n"
                + "	SET \"DESCRIPCION\"=?, \"IMAGEN\"=?, \"URL\"=?, \"ID_MENU\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, DESCRIPCION, IMAGEN, URL, ID_MENU, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"SUB_MENU\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"SUB_MENU\"\n"
                + "ORDER BY \"DESCRIPCION\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            obj.put("URL", rs.getString("URL"));
            obj.put("ID_MENU", rs.getInt("ID_MENU"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public SUB_MENU buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"SUB_MENU\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        SUB_MENU m = new SUB_MENU(con);
        if (rs.next()) {
            m.setID(rs.getInt("ID"));
            m.setDESCRIPCION(rs.getString("DESCRIPCION"));
            m.setIMAGEN(rs.getString("IMAGEN"));
            m.setURL(rs.getString("URL"));
            m.setID_MENU(rs.getInt("ID_MENU"));
            return m;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("DESCRIPCION", DESCRIPCION);
        obj.put("IMAGEN", IMAGEN);
        obj.put("URL", URL);
        obj.put("ID_MENU", ID_MENU);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONArray bucarSubMenuXCargo(int idCargo) throws SQLException, JSONException {
        String consulta = "SELECT \"SUB_MENU\".\"DESCRIPCION\",\n"
                + "               \"SUB_MENU\".\"URL\",\n"
                + "               \"SUB_MENU\".\"IMAGEN\"\n"
                + "	FROM public.\"SUB_MENU\",\n"
                + "         public.\"PERMISO\"\n"
                + "    WHERE \"PERMISO\".\"ID_CARGO\" = " + idCargo + "\n"
                + "    	  AND \"PERMISO\".\"ID_SUB_MENU\" = \"SUB_MENU\".\"ID\"\n";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("URL", rs.getString("URL"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;

    }

    public JSONArray bucarSubMenuXMenu(int idMenu) throws SQLException, JSONException {
        String consulta = "SELECT \"SUB_MENU\".\"DESCRIPCION\",\n"
                + "               \"SUB_MENU\".\"URL\",\n"
                + "               \"SUB_MENU\".\"IMAGEN\"\n"
                + "	FROM public.\"SUB_MENU\"\n"
                + "    WHERE \"SUB_MENU\".\"ID_MENU\" = " + idMenu + "\n"
                + "";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("URL", rs.getString("URL"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;

    }
}
