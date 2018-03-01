package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CATEGORIA_PRODUCTO {

    private int ID;
    private String NOMBRE;
    private String IMAGEN;
    private int ID_CATEGORIA_PRODUCTO;
    private Conexion con;

    public CATEGORIA_PRODUCTO(Conexion con) {
        this.con = con;
    }

    public CATEGORIA_PRODUCTO(int ID, String NOMBRE, String IMAGEN, int ID_CATEGORIA_PRODUCTO) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.IMAGEN = IMAGEN;
        this.ID_CATEGORIA_PRODUCTO = ID_CATEGORIA_PRODUCTO;
    }

    public CATEGORIA_PRODUCTO(int ID, String NOMBRE, String IMAGEN, int ID_CATEGORIA_PRODUCTO, Conexion con) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.IMAGEN = IMAGEN;
        this.ID_CATEGORIA_PRODUCTO = ID_CATEGORIA_PRODUCTO;
        this.con = con;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public int getID_CATEGORIA_PRODUCTO() {
        return ID_CATEGORIA_PRODUCTO;
    }

    public void setID_CATEGORIA_PRODUCTO(int ID_CATEGORIA_PRODUCTO) {
        this.ID_CATEGORIA_PRODUCTO = ID_CATEGORIA_PRODUCTO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        Integer id_cat = ID_CATEGORIA_PRODUCTO > 0 ? ID_CATEGORIA_PRODUCTO : null;
        String consulta = "INSERT INTO public.\"CATEGORIA_PRODUCTO\"(\n"
                + "	\"NOMBRE\", \"IMAGEN\", \"ID_CATEGORIA_PRODUCTO\")\n"
                + "	VALUES (?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", NOMBRE, IMAGEN, id_cat);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        Integer id_cat = ID_CATEGORIA_PRODUCTO > 0 ? ID_CATEGORIA_PRODUCTO : null;
        String consulta = "UPDATE public.\"CATEGORIA_PRODUCTO\"\n"
                + "	SET \"NOMBRE\"=?, \"IMAGEN\"=?, \"ID_CATEGORIA_PRODUCTO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, NOMBRE, IMAGEN, id_cat, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"CATEGORIA_PRODUCTO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"CATEGORIA_PRODUCTO\"\n"
                + "ORDER BY \"NOMBRE\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            obj.put("ID_CATEGORIA_PRODUCTO", rs.getString("ID_CATEGORIA_PRODUCTO"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public CATEGORIA_PRODUCTO buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"CATEGORIA_PRODUCTO\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        CATEGORIA_PRODUCTO m = new CATEGORIA_PRODUCTO(con);
        if (rs.next()) {
            m.setID(rs.getInt("ID"));
            m.setNOMBRE(rs.getString("NOMBRE"));
            m.setIMAGEN(rs.getString("IMAGEN"));
            m.setID_CATEGORIA_PRODUCTO(rs.getInt("ID_CATEGORIA_PRODUCTO"));
            return m;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("NOMBRE", NOMBRE);
        obj.put("IMAGEN", IMAGEN);
        obj.put("ID_CATEGORIA_PRODUCTO", ID_CATEGORIA_PRODUCTO);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
}
