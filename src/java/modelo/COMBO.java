package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class COMBO {

    private int ID;
    private String NOMBRE;
    private double PRECIO;
    private String IMAGEN;
    private Conexion con;

    public COMBO(Conexion con) {
        this.con = con;
    }

    public COMBO(int ID, String NOMBRE, double PRECIO, String IMAGEN) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.PRECIO = PRECIO;
        this.IMAGEN = IMAGEN;
    }

    public COMBO(String NOMBRE, double PRECIO, String IMAGEN) {
        this.NOMBRE = NOMBRE;
        this.PRECIO = PRECIO;
        this.IMAGEN = IMAGEN;
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

    public double getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(double PRECIO) {
        this.PRECIO = PRECIO;
    }

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"COMBO\"(\n"
                + "	\"NOMBRE\", \"PRECIO\", \"IMAGEN\")\n"
                + "	VALUES (?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", NOMBRE, PRECIO, IMAGEN);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"COMBO\"\n"
                + "	SET \"NOMBRE\"=?, \"PRECIO\"=?, \"IMAGEN\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, NOMBRE, PRECIO, IMAGEN, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"COMBO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"COMBO\"\n"
                + "ORDER BY \"NOMBRE\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("PRECIO", rs.getDouble("PRECIO"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

}
