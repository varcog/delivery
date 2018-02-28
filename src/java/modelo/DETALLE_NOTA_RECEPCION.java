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
    private int ID_INSUMO;
    private int ID_INSUMO_GRUPO;
    private double CANTIDAD;
    private double PRECIO;
    private Conexion con;

    public DETALLE_NOTA_RECEPCION(Conexion con) {
        this.con = con;
    }

    public DETALLE_NOTA_RECEPCION(int ID, int ID_NOTA_RECEPCION, int ID_PRODUCTO, double CANTIDAD, int ID_INSUMO_GRUPO, int PRECIO) {
        this.ID = ID;
        this.ID_NOTA_RECEPCION = ID_NOTA_RECEPCION;
        this.ID_INSUMO = ID_PRODUCTO;
        this.CANTIDAD = CANTIDAD;
        this.ID_INSUMO_GRUPO = ID_INSUMO_GRUPO;
        this.PRECIO = PRECIO;
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

    public double getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(double CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public int getID_INSUMO_GRUPO() {
        return ID_INSUMO_GRUPO;
    }

    public void setID_INSUMO_GRUPO(int ID_INSUMO_GRUPO) {
        this.ID_INSUMO_GRUPO = ID_INSUMO_GRUPO;
    }

    public double getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(double PRECIO) {
        this.PRECIO = PRECIO;
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
                + "	\"ID_NOTA_RECEPCION\", \"ID_INSUMO\", \"CANTIDAD\", \"PRECIO\", \"ID_INSUMO_GRUPO\")\n"
                + "	VALUES (?, ?, ?, ?, ?);";
        int id = con.EjecutarInsert(consulta, "ID", ID_NOTA_RECEPCION, ID_INSUMO, CANTIDAD, PRECIO, (ID_INSUMO_GRUPO > 0 ? ID_INSUMO_GRUPO : null));
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
                + "       \"INSUMO\".\"CODIGO\", \n"
                + "       \"INSUMO\".\"DESCRIPCION\" AS INSUMO, \n"
                + "       \"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\",\n"
                + "       \"DETALLE_NOTA_RECEPCION\".\"PRECIO\"\n"
                + "	FROM public.\"DETALLE_NOTA_RECEPCION\"\n"
                + "    	 INNER JOIN public.\"INSUMO\" ON \"INSUMO\".\"ID\" = \"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\"\n"
                + "    WHERE \"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = ?\n"
                + "    ORDER BY \"INSUMO\".\"CODIGO\";";
//        String consulta = "SELECT \"DETALLE_NOTA_RECEPCION\".\"ID\", \n"
//                + "       \"INSUMO\".\"CODIGO\", \n"
//                + "       \"INSUMO\".\"DESCRIPCION\" AS INSUMO, \n"
//                + "       \"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\",\n"
//                + "       \"DETALLE_NOTA_RECEPCION\".\"PRECIO\",\n"
//                + "       \"INSUMO_GRUPO\".\"ID\" AS ID_INSUMO_GRUPO,\n"
//                + "       \"INSUMO_GRUPO\".\"DESCRIPCION\" AS INSUMO_GRUPO\n"
//                + "	FROM public.\"DETALLE_NOTA_RECEPCION\"\n"
//                + "    	     INNER JOIN public.\"INSUMO\" ON \"INSUMO\".\"ID\" = \"DETALLE_NOTA_RECEPCION\".\"INSUMO\"\n"
//                + "    	     LEFT JOIN public.\"INSUMO_GRUPO_DETALLE\" ON \"INSUMO\".\"ID\" = \"INSUMO_GRUPO_DETALLE\".\"ID_INSUMO\"\n"
//                + "    	     LEFT JOIN public.\"INSUMO_GRUPO\" ON \"INSUMO_GRUPO_DETALLE\".\"ID_INSUMO_GRUPO\" = \"INSUMO_GRUPO\".\"ID\"\n"
//                + "    WHERE \"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = ?;\n"
//                + "    ";
        PreparedStatement ps = con.statametObject(consulta, id_nota_recepcion);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("INSUMO", rs.getString("INSUMO"));
            obj.put("CANTIDAD", rs.getDouble("CANTIDAD"));
//            obj.put("INSUMO_GRUPO", rs.getString("INSUMO_GRUPO"));
//            obj.put("IS_GRUPO", rs.getInt("ID_INSUMO_GRUPO") > 0);
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;

    }

}
