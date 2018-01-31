package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UNIDAD_MEDIDA {

    private int ID;
    private String DESCRIPCION;
    private String ABREVIACION;
    private Conexion con;

    public UNIDAD_MEDIDA(Conexion con) {
        this.con = con;
    }

    public UNIDAD_MEDIDA(int ID, String DESCRIPCION, String ABREVIACION) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;
        this.ABREVIACION = ABREVIACION;
    }

    public UNIDAD_MEDIDA(String DESCRIPCION, String ABREVIACION) {
        this.DESCRIPCION = DESCRIPCION;
        this.ABREVIACION = ABREVIACION;
    }

    public UNIDAD_MEDIDA(String DESCRIPCION, String ABREVIACION, Conexion con) {
        this.DESCRIPCION = DESCRIPCION;
        this.ABREVIACION = ABREVIACION;
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

    public String getABREVIACION() {
        return ABREVIACION;
    }

    public void setABREVIACION(String ABREVIACION) {
        this.ABREVIACION = ABREVIACION;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"UNIDAD_MEDIDA\"(\n"
                + "	\"DESCRIPCION\", \"ABREVIACION\")\n"
                + "	VALUES (?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", DESCRIPCION, ABREVIACION);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"UNIDAD_MEDIDA\"\n"
                + "	SET \"DESCRIPCION\"=?, \"ABREVIACION\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, DESCRIPCION, ABREVIACION, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"UNIDAD_MEDIDA\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"UNIDAD_MEDIDA\"\n"
                + "ORDER BY \"DESCRIPCION\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("ABREVIACION", rs.getString("ABREVIACION"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }
}
