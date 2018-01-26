package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PRODUCTO {

    private int ID;
    private String NOMBRE;
    private String IMAGEN;
    private double PRECIO_COMPRA;
    private double PRECIO_VENTA;
    private Conexion con;

    public PRODUCTO(Conexion con) {
        this.con = con;
    }

    public PRODUCTO(int ID, String NOMBRE, String IMAGEN, double PRECIO_COMPRA, double PRECIO_VENTA) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.IMAGEN = IMAGEN;
        this.PRECIO_COMPRA = PRECIO_COMPRA;
        this.PRECIO_VENTA = PRECIO_VENTA;
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

    public double getPRECIO_COMPRA() {
        return PRECIO_COMPRA;
    }

    public void setPRECIO_COMPRA(double PRECIO_COMPRA) {
        this.PRECIO_COMPRA = PRECIO_COMPRA;
    }

    public double getPRECIO_VENTA() {
        return PRECIO_VENTA;
    }

    public void setPRECIO_VENTA(double PRECIO_VENTA) {
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"PRODUCTO\"(\n"
                + "	\"NOMBRE\", \"PRECIO_COMPRA\", \"PRECIO_VENTA\", \"IMAGEN\")\n"
                + "	VALUES (?, ?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", NOMBRE, PRECIO_COMPRA, PRECIO_VENTA, IMAGEN);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"PRODUCTO\"\n"
                + "	SET \"NOMBRE\"=?, \"PRECIO_COMPRA\"=?, \"PRECIO_VENTA\"=?, \"IMAGEN\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, NOMBRE, PRECIO_COMPRA, PRECIO_VENTA, IMAGEN, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"PRODUCTO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"PRODUCTO\"\n"
                + "ORDER BY \"NOMBRE\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", ID);
            obj.put("NOMBRE", NOMBRE);
            obj.put("IMAGEN", IMAGEN);
            obj.put("PRECIO_COMPRA", PRECIO_COMPRA);
            obj.put("PRECIO_VENTA", PRECIO_VENTA);
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

}
