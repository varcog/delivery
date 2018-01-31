package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class COMBO_PRODUCTO {

    private int ID;
    private int ID_COMBO;
    private int ID_PRODUCTO;
    private double CANTIDAD;
    private Conexion con;

    public COMBO_PRODUCTO(Conexion con) {
        this.con = con;
    }

    public COMBO_PRODUCTO(int ID, int ID_COMBO, int ID_PRODUCTO, double CANTIDAD) {
        this.ID = ID;
        this.ID_COMBO = ID_COMBO;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
    }

    public COMBO_PRODUCTO(int ID_COMBO, int ID_PRODUCTO, double CANTIDAD) {
        this.ID_COMBO = ID_COMBO;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
    }

    public COMBO_PRODUCTO(int ID_COMBO, int ID_PRODUCTO, double CANTIDAD, Conexion con) {
        this.ID_COMBO = ID_COMBO;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
        this.con = con;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_COMBO() {
        return ID_COMBO;
    }

    public void setID_COMBO(int ID_COMBO) {
        this.ID_COMBO = ID_COMBO;
    }

    public int getID_PRODUCTO() {
        return ID_PRODUCTO;
    }

    public void setID_PRODUCTO(int ID_PRODUCTO) {
        this.ID_PRODUCTO = ID_PRODUCTO;
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
        String consulta = "INSERT INTO public.\"COMBO_PRODUCTO\"(\n"
                + "	\"ID_COMBO\", \"ID_PRODUCTO\", \"CANTIDAD\")\n"
                + "	VALUES (?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", ID_COMBO, ID_PRODUCTO, CANTIDAD);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"COMBO_PRODUCTO\"\n"
                + "	SET \"ID_COMBO\"=?, \"ID_PRODUCTO\"=?, \"CANTIDAD\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID_COMBO, ID_PRODUCTO, CANTIDAD, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"COMBO_PRODUCTO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"COMBO_PRODUCTO\"\n"
                + "ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_COMBO", rs.getInt("ID_COMBO"));
            obj.put("ID_PRODUCTO", rs.getInt("ID_PRODUCTO"));
            obj.put("CANTIDAD", rs.getDouble("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }
}
