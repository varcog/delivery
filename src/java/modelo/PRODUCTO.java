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
            obj.put("ID", rs.getInt("ID"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            obj.put("PRECIO_COMPRA", rs.getDouble("PRECIO_COMPRA"));
            obj.put("PRECIO_VENTA", rs.getDouble("PRECIO_VENTA"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public PRODUCTO buscar(int id) throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"PRODUCTO\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        PRODUCTO p = new PRODUCTO(con);
        if (rs.next()) {
            p.setID(rs.getInt("ID"));
            p.setNOMBRE(rs.getString("NOMBRE"));
            p.setIMAGEN(rs.getString("IMAGEN"));
            p.setPRECIO_COMPRA(rs.getDouble("PRECIO_COMPRA"));
            p.setPRECIO_VENTA(rs.getDouble("PRECIO_VENTA"));
            return p;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("NOMBRE", NOMBRE);
        obj.put("IMAGEN", IMAGEN);
        obj.put("PRECIO_COMPRA", PRECIO_COMPRA);
        obj.put("PRECIO_VENTA", PRECIO_VENTA);
        return obj;
    }

    public JSONArray todos_Almacen(int id_sucursal) throws SQLException, JSONException {
        String consulta = "SELECT PRODUCTO.\"ID\",\n"
                + "	   PRODUCTO.\"NOMBRE\",\n"
                + "        PRODUCTO.\"PRECIO_COMPRA\",\n"
                + "        PRODUCTO.\"PRECIO_VENTA\",\n"
                + "        PRODUCTO.\"IMAGEN\",\n"
                + "        SUM(CANTIDAD.\"CANTIDAD\") AS CANTIDAD\n"
                + "	FROM public.\"PRODUCTO\" AS PRODUCTO\n"
                + "         LEFT JOIN (\n"
                + "             SELECT \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_PRODUCTO\",\n"
                + "					\"public\".\"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\"\n"
                + "			   	FROM \"public\".\"DETALLE_NOTA_RECEPCION\"\n"
                + "					INNER JOIN \"public\".\"NOTA_RECEPCION\" ON \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = \"public\".\"NOTA_RECEPCION\".\"ID\"\n"
                + "					INNER JOIN \"public\".\"SUCURSAL\" ON \"public\".\"NOTA_RECEPCION\".\"ID_SUCURSAL\" = \"public\".\"SUCURSAL\".\"ID\" AND \"public\".\"SUCURSAL\".\"ID\" = ? \n"
                + "		 ) AS CANTIDAD\n"
                + "         ON PRODUCTO.\"ID\" = CANTIDAD.\"ID_PRODUCTO\"\n"
                + "    GROUP BY PRODUCTO.\"ID\",\n"
                + "	   		 PRODUCTO.\"NOMBRE\"\n"
                + "    ORDER BY PRODUCTO.\"NOMBRE\" ASC;\n"
                + "    \n"
                + "";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id_sucursal);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            obj.put("PRECIO_COMPRA", rs.getDouble("PRECIO_COMPRA"));
            obj.put("PRECIO_VENTA", rs.getDouble("PRECIO_VENTA"));
            obj.put("CANTIDAD", rs.getInt("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONArray stock_Almacen(int id_sucursal) throws SQLException, JSONException {
        String consulta = "SELECT PRODUCTO.\"ID\",\n"
                + "	   PRODUCTO.\"NOMBRE\",\n"
                + "        PRODUCTO.\"PRECIO_COMPRA\",\n"
                + "        PRODUCTO.\"PRECIO_VENTA\",\n"
                + "        PRODUCTO.\"IMAGEN\",\n"
                + "        SUM(CANTIDAD.\"CANTIDAD\") AS CANTIDAD\n"
                + "	FROM public.\"PRODUCTO\" AS PRODUCTO\n"
                + "         INNER JOIN (\n"
                + "             SELECT \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_PRODUCTO\",\n"
                + "					\"public\".\"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\"\n"
                + "			   	FROM \"public\".\"DETALLE_NOTA_RECEPCION\"\n"
                + "					INNER JOIN \"public\".\"NOTA_RECEPCION\" ON \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = \"public\".\"NOTA_RECEPCION\".\"ID\"\n"
                + "					INNER JOIN \"public\".\"SUCURSAL\" ON \"public\".\"NOTA_RECEPCION\".\"ID_SUCURSAL\" = \"public\".\"SUCURSAL\".\"ID\" AND \"public\".\"SUCURSAL\".\"ID\" = ? \n"
                + "		 ) AS CANTIDAD\n"
                + "         ON PRODUCTO.\"ID\" = CANTIDAD.\"ID_PRODUCTO\"\n"
                + "    GROUP BY PRODUCTO.\"ID\",\n"
                + "	   		 PRODUCTO.\"NOMBRE\"\n"
                + "    ORDER BY PRODUCTO.\"NOMBRE\" ASC;\n"
                + "    \n"
                + "";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id_sucursal);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            obj.put("PRECIO_COMPRA", rs.getDouble("PRECIO_COMPRA"));
            obj.put("PRECIO_VENTA", rs.getDouble("PRECIO_VENTA"));
            obj.put("CANTIDAD", rs.getInt("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

}
