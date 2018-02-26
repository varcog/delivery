package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CARGO {

    private int ID;
    private String DESCRIPCION;
    private Conexion con;

    public CARGO(Conexion con) {
        this.con = con;
    }

    public CARGO(int ID, String DESCRIPCION) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;
    }
    
    public CARGO(int ID, String DESCRIPCION, Conexion con) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;
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

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"CARGO\"(\n"
                + "	\"DESCRIPCION\")\n"
                + "	VALUES (?)";
        int id = con.EjecutarInsert(consulta, "ID", DESCRIPCION);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"CARGO\"\n"
                + "	SET \"DESCRIPCION\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, DESCRIPCION, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"CARGO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"CARGO\"\n"
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

    public CARGO buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"CARGO\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        CARGO m = new CARGO(con);
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
}
