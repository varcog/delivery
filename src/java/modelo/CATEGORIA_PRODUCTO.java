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
    private boolean ESTADO;
    private Conexion con;

    public CATEGORIA_PRODUCTO(Conexion con) {
        this.con = con;
    }

    public CATEGORIA_PRODUCTO(int ID, String NOMBRE, String IMAGEN, int ID_CATEGORIA_PRODUCTO, boolean ESTADO) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.IMAGEN = IMAGEN;
        this.ID_CATEGORIA_PRODUCTO = ID_CATEGORIA_PRODUCTO;
        this.ESTADO = ESTADO;
    }

    public CATEGORIA_PRODUCTO(int ID, String NOMBRE, String IMAGEN, int ID_CATEGORIA_PRODUCTO, Boolean ESTADO, Conexion con) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.IMAGEN = IMAGEN;
        this.ID_CATEGORIA_PRODUCTO = ID_CATEGORIA_PRODUCTO;
        this.ESTADO = ESTADO;
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

    public boolean isESTADO() {
        return ESTADO;
    }

    public void setESTADO(boolean ESTADO) {
        this.ESTADO = ESTADO;
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
                + "	\"NOMBRE\", \"IMAGEN\", \"ID_CATEGORIA_PRODUCTO\", \"ESTADO\")\n"
                + "	VALUES (?, ?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", NOMBRE, IMAGEN, id_cat, ESTADO);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        Integer id_cat = ID_CATEGORIA_PRODUCTO > 0 ? ID_CATEGORIA_PRODUCTO : null;
        String consulta = "UPDATE public.\"CATEGORIA_PRODUCTO\"\n"
                + "	SET \"NOMBRE\"=?, \"IMAGEN\"=?, \"ID_CATEGORIA_PRODUCTO\"=?, \"ESTADO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, NOMBRE, IMAGEN, id_cat, ESTADO, ID);
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
            obj.put("ID_CATEGORIA_PRODUCTO", rs.getInt("ID_CATEGORIA_PRODUCTO"));
            obj.put("ESTADO", rs.getBoolean("ESTADO"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONArray todosXLevel() throws SQLException, JSONException {
        String consulta = "WITH RECURSIVE hijos AS (\n"
                + "    SELECT \"ID\", \"ID_CATEGORIA_PRODUCTO\", \"NOMBRE\", \"IMAGEN\", \"ESTADO\", 1 as depth\n"
                + "	FROM \"CATEGORIA_PRODUCTO\"\n"
                + "    WHERE \"ID_CATEGORIA_PRODUCTO\" IS NULL\n"
                + "    UNION\n"
                + "    SELECT m.\"ID\", m.\"ID_CATEGORIA_PRODUCTO\", m.\"NOMBRE\", m.\"IMAGEN\", m.\"ESTADO\",  depth + 1\n"
                + "    FROM \"CATEGORIA_PRODUCTO\" m\n"
                + "    INNER JOIN hijos ON m.\"ID_CATEGORIA_PRODUCTO\" = hijos.\"ID\")\n"
                + "SELECT * FROM hijos\n"
                + "ORDER BY depth, \"NOMBRE\"";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            obj.put("ID_CATEGORIA_PRODUCTO", rs.getInt("ID_CATEGORIA_PRODUCTO"));
            obj.put("ESTADO", rs.getBoolean("ESTADO"));
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
            m.setESTADO(rs.getBoolean("ESTADO"));
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
        obj.put("ESTADO", ESTADO);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
}
