package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NOTA_RECEPCION {

    private int ID;
    private int NUMERO;
    private Date FECHA;
    private int ID_SUCURSAL;
    private int ID_USUARIO_ENTREGA;
    private int ID_USUARIO_RECIBE;
    private Conexion con;

    public NOTA_RECEPCION(Conexion con) {
        this.con = con;
    }

    public NOTA_RECEPCION(int ID, int NUMERO, Date FECHA, int ID_SUCURSAL, int ID_USUARIO_ENTREGA, int ID_USUARIO_RECIBE) {
        this.ID = ID;
        this.NUMERO = NUMERO;
        this.FECHA = FECHA;
        this.ID_SUCURSAL = ID_SUCURSAL;
        this.ID_USUARIO_ENTREGA = ID_USUARIO_ENTREGA;
        this.ID_USUARIO_RECIBE = ID_USUARIO_RECIBE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(int NUMERO) {
        this.NUMERO = NUMERO;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    public int getID_SUCURSAL() {
        return ID_SUCURSAL;
    }

    public void setID_SUCURSAL(int ID_SUCURSAL) {
        this.ID_SUCURSAL = ID_SUCURSAL;
    }

    public int getID_USUARIO_ENTREGA() {
        return ID_USUARIO_ENTREGA;
    }

    public void setID_USUARIO_ENTREGA(int ID_USUARIO_ENTREGA) {
        this.ID_USUARIO_ENTREGA = ID_USUARIO_ENTREGA;
    }

    public int getID_USUARIO_RECIBE() {
        return ID_USUARIO_RECIBE;
    }

    public void setID_USUARIO_RECIBE(int ID_USUARIO_RECIBE) {
        this.ID_USUARIO_RECIBE = ID_USUARIO_RECIBE;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"NOTA_RECEPCION\"(\n"
                + "	\"FECHA\", \"ID_SUCURSAL\", \"ID_USUARIO_ENTREGA\", \"ID_USUARIO_RECIBE\")\n"
                + "	VALUES (?, ?, ?, ?);";
        int id = con.EjecutarInsert(consulta, "ID", new java.sql.Date(FECHA.getTime()), ID_SUCURSAL, ID_USUARIO_ENTREGA, ID_USUARIO_RECIBE);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"NOTA_RECEPCION\"\n"
                + "	SET \"FECHA\"=?, \"ID_SUCURSAL\"=?, \"ID_USUARIO_ENTREGA\"=?, \"ID_USUARIO_RECIBE\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, FECHA, ID_SUCURSAL, ID_USUARIO_ENTREGA, ID_USUARIO_RECIBE, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"NOTA_RECEPCION\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray buscarDetalleimpresionNota() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"NOTA_RECEPCION\"\n"
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

}
