package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DETALLE_NOTA_RECEPCION {

    private int ID;
    private int ID_NOTA_RECEPCION;
    private int ID_PRODUCTO;
    private int CANTIDAD;
    private Conexion con;

    public DETALLE_NOTA_RECEPCION(Conexion con) {
        this.con = con;
    }

    public DETALLE_NOTA_RECEPCION(int ID, int ID_NOTA_RECEPCION, int ID_PRODUCTO, int CANTIDAD) {
        this.ID = ID;
        this.ID_NOTA_RECEPCION = ID_NOTA_RECEPCION;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_NOTA_RECEPCION() {
        return ID_NOTA_RECEPCION;
    }

    public void setID_NOTA_RECEPCION(int ID_NOTA_RECEPCION) {
        this.ID_NOTA_RECEPCION = ID_NOTA_RECEPCION;
    }

    public int getID_PRODUCTO() {
        return ID_PRODUCTO;
    }

    public void setID_PRODUCTO(int ID_PRODUCTO) {
        this.ID_PRODUCTO = ID_PRODUCTO;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
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
        String consulta = "INSERT INTO public.\"DETALLE_NOTA_RECEPCION\"(\n"
                + "	\"ID_NOTA_RECEPCION\", \"ID_PRODUCTO\", \"CANTIDAD\")\n"
                + "	VALUES (?, ?, ?);";
        int id = con.EjecutarInsert(consulta, "ID", ID_NOTA_RECEPCION, ID_PRODUCTO, CANTIDAD);
        this.ID = id;
        return id;
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"DETALLE_NOTA_RECEPCION\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray detalleNotaRecepcionPdf(int id_nota_recepcion) throws SQLException, JSONException {
        String consulta = "SELECT \"DETALLE_NOTA_RECEPCION\".\"ID\", \n"
                + "       \"PRODUCTO\".\"CODIGO\", \n"
                + "       \"PRODUCTO\".\"NOMBRE\", \n"
                + "       \"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\"\n"
                + "	FROM public.\"DETALLE_NOTA_RECEPCION\"\n"
                + "    	 INNER JOIN public.\"PRODUCTO\" ON \"PRODUCTO\".\"ID\" = \"DETALLE_NOTA_RECEPCION\".\"ID_PRODUCTO\"\n"
                + "    WHERE \"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = ?;\n"
                + "    ";
        PreparedStatement ps = con.statametObject(consulta, id_nota_recepcion);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("NOMBRE", rs.getString("NOMBRE"));
            obj.put("CANTIDAD", rs.getInt("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;

    }

}
