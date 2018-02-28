package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PRODUCTO_INSUMO {

    private int ID;
    private int ID_INSUMO;
    private int ID_PRODUCTO;
    private double CANTIDAD;
    private Conexion con;

    public PRODUCTO_INSUMO(Conexion con) {
        this.con = con;
    }

    public PRODUCTO_INSUMO(int ID, int ID_INSUMO, int ID_PRODUCTO, double CANTIDAD) {
        this.ID = ID;
        this.ID_INSUMO = ID_INSUMO;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
    }

    public PRODUCTO_INSUMO(int ID_INSUMO, int ID_PRODUCTO, double CANTIDAD) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
    }

    public PRODUCTO_INSUMO(int ID_INSUMO, int ID_PRODUCTO, double CANTIDAD, Conexion con) {
        this.ID_INSUMO = ID_INSUMO;
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

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
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
        String consulta = "INSERT INTO public.\"PRODUCTO_INSUMO\"(\n"
                + "	\"ID_INSUMO\", \"ID_PRODUCTO\", \"CANTIDAD\")\n"
                + "	VALUES (?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", ID_INSUMO, ID_PRODUCTO, CANTIDAD);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"PRODUCTO_INSUMO\"\n"
                + "	SET \"ID_INSUMO\"=?, \"ID_PRODUCTO\"=?, \"CANTIDAD\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID_INSUMO, ID_PRODUCTO, CANTIDAD, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"PRODUCTO_INSUMO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"PRODUCTO_INSUMO\"\n"
                + "ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_INSUMO", rs.getInt("ID_INSUMO"));
            obj.put("ID_PRODUCTO", rs.getInt("ID_PRODUCTO"));
            obj.put("CANTIDAD", rs.getDouble("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("ID_INSUMO", ID_INSUMO);
        obj.put("ID_PRODUCTO", ID_PRODUCTO);
        obj.put("CANTIDAD", CANTIDAD);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONArray todosXID_PRODUCTO_JSONArray(int id_producto) throws SQLException, JSONException {
        String consulta = "SELECT \"PRODUCTO_INSUMO\".*\n"
                + "     FROM public.\"PRODUCTO_INSUMO\"\n"
                + "         INNER JOIN public.\"PRODUCTO\" ON \"PRODUCTO\".\"ID\" = \"PRODUCTO_INSUMO\".\"ID_PRODUCTO\"\n"
                + "     WHERE \"PRODUCTO_INSUMO\".\"ID_PRODUCTO\" = " + id_producto + "\n"
                + "     ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("ID_INSUMO", rs.getInt("ID_INSUMO"));
            obj.put("ID_PRODUCTO", rs.getInt("ID_PRODUCTO"));
            obj.put("CANTIDAD", rs.getDouble("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public ArrayList<PRODUCTO_INSUMO> todosXID_PRODUCTO(int id_producto) throws SQLException, JSONException {
        String consulta = "SELECT \"PRODUCTO_INSUMO\".*\n"
                + "     FROM public.\"PRODUCTO_INSUMO\"\n"
                + "         INNER JOIN public.\"PRODUCTO\" ON \"PRODUCTO\".\"ID\" = \"PRODUCTO_INSUMO\".\"ID_PRODUCTO\"\n"
                + "     WHERE \"PRODUCTO_INSUMO\".\"ID_PRODUCTO\" = " + id_producto + "\n"
                + "     ORDER BY \"ID\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        ArrayList<PRODUCTO_INSUMO> lista = new ArrayList<>();
        PRODUCTO_INSUMO igd;
        while (rs.next()) {
            igd = new PRODUCTO_INSUMO(con);
            igd.setID(rs.getInt("ID"));
            igd.setID_INSUMO(rs.getInt("ID_INSUMO"));
            igd.setID_PRODUCTO(rs.getInt("ID_PRODUCTO"));
            igd.setCANTIDAD(rs.getDouble("CANTIDAD"));
            lista.add(igd);
        }
        rs.close();
        ps.close();
        return lista;
    }

    public void deleteXID_PRODUCTO(int id_producto) throws SQLException {
        String consulta = "DELETE FROM public.\"PRODUCTO_INSUMO\"\n"
                + "	WHERE \"ID_PRODUCTO\"=?;";
        con.EjecutarSentencia(consulta, id_producto);
    }
}
