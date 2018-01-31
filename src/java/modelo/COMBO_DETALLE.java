package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class COMBO_DETALLE {

    private int ID;
    private String DESCRIPCION;
    private int ID_COMBO;
    private Conexion con;

    public COMBO_DETALLE(Conexion con) {
        this.con = con;
    }

    public COMBO_DETALLE(int ID, String DESCRIPCION, int ID_COMBO) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;
        this.ID_COMBO = ID_COMBO;
    }

    public COMBO_DETALLE(String DESCRIPCION, int ID_COMBO) {
        this.DESCRIPCION = DESCRIPCION;
        this.ID_COMBO = ID_COMBO;
    }

    public COMBO_DETALLE(String DESCRIPCION, int ID_COMBO, Conexion con) {
        this.DESCRIPCION = DESCRIPCION;
        this.ID_COMBO = ID_COMBO;
        this.con = con;
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

    public int getID_COMBO() {
        return ID_COMBO;
    }

    public void setID_COMBO(int ID_COMBO) {
        this.ID_COMBO = ID_COMBO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"COMBO_DETALLE\"(\n"
                + "	\"DESCRIPCION\", \"ID_COMBO\")\n"
                + "	VALUES (?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", DESCRIPCION, ID_COMBO);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"COMBO_DETALLE\"\n"
                + "	SET \"DESCRIPCION\"=?, \"ID_COMBO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, DESCRIPCION, ID_COMBO, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"COMBO_DETALLE\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"COMBO_DETALLE\"\n"
                + "ORDER BY \"DESCRIPCION\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("ID_COMBO", rs.getInt("ID_COMBO"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }
}
