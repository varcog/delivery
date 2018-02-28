package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class INSUMO {

    private int ID;
    private String CODIGO;
    private String DESCRIPCION;
    private int ID_UNIDAD_MEDIDA;
    private Conexion con;

    public INSUMO(Conexion con) {
        this.con = con;
    }

    public INSUMO(int ID, String CODIGO, String DESCRIPCION, int ID_UNIDAD_MEDIDA) {
        this.ID = ID;
        this.CODIGO = CODIGO;
        this.DESCRIPCION = DESCRIPCION;
        this.ID_UNIDAD_MEDIDA = ID_UNIDAD_MEDIDA;
    }

    public INSUMO(int ID, String CODIGO, String DESCRIPCION, int ID_UNIDAD_MEDIDA, Conexion con) {
        this.ID = ID;
        this.CODIGO = CODIGO;
        this.DESCRIPCION = DESCRIPCION;
        this.ID_UNIDAD_MEDIDA = ID_UNIDAD_MEDIDA;
        this.con = con;
    }

    public INSUMO(String CODIGO, String DESCRIPCION, int ID_UNIDAD_MEDIDA, Conexion con) {
        this.CODIGO = CODIGO;
        this.DESCRIPCION = DESCRIPCION;
        this.ID_UNIDAD_MEDIDA = ID_UNIDAD_MEDIDA;
        this.con = con;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getID_UNIDAD_MEDIDA() {
        return ID_UNIDAD_MEDIDA;
    }

    public void setID_UNIDAD_MEDIDA(int ID_UNIDAD_MEDIDA) {
        this.ID_UNIDAD_MEDIDA = ID_UNIDAD_MEDIDA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public int insert() throws SQLException {
        String consulta = "INSERT INTO public.\"INSUMO\"(\n"
                + "	\"CODIGO\", \"DESCRIPCION\", \"ID_UNIDAD_MEDIDA\")\n"
                + "	VALUES (?, ?, ?)";
        int id = con.EjecutarInsert(consulta, "ID", CODIGO, DESCRIPCION, ID_UNIDAD_MEDIDA);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"INSUMO\"\n"
                + "	SET \"CODIGO\"=?, \"DESCRIPCION\"=?, \"ID_UNIDAD_MEDIDA\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, CODIGO, DESCRIPCION, ID_UNIDAD_MEDIDA, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"INSUMO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"INSUMO\"\n"
                + "ORDER BY \"CODIGO\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("ID_UNIDAD_MEDIDA", rs.getInt("ID_UNIDAD_MEDIDA"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public INSUMO buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"INSUMO\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        INSUMO p = new INSUMO(con);
        if (rs.next()) {
            p.setID(rs.getInt("ID"));
            p.setCODIGO(rs.getString("CODIGO"));
            p.setDESCRIPCION(rs.getString("DESCRIPCION"));
            p.setID_UNIDAD_MEDIDA(rs.getInt("ID_UNIDAD_MEDIDA"));
            return p;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("CODIGO", CODIGO);
        obj.put("DESCRIPCION", DESCRIPCION);
        obj.put("ID_UNIDAD_MEDIDA", ID_UNIDAD_MEDIDA);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONArray todosConUM() throws SQLException, JSONException {
        String consulta = "SELECT \"INSUMO\".*,\n"
                + "         \"UNIDAD_MEDIDA\".\"DESCRIPCION\" AS UNIDAD_MEDIDA"
                + "     FROM public.\"INSUMO\"\n"
                + "          LEFT JOIN public.\"UNIDAD_MEDIDA\" ON \"INSUMO\".\"ID_UNIDAD_MEDIDA\" = \"UNIDAD_MEDIDA\".\"ID\"\n"
                + "     ORDER BY \"CODIGO\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("UNIDAD_MEDIDA", rs.getString("UNIDAD_MEDIDA"));
            obj.put("ID_UNIDAD_MEDIDA", rs.getInt("ID_UNIDAD_MEDIDA"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONArray todos_Almacen(int id_sucursal) throws SQLException, JSONException {
        String consulta = "SELECT INSUMO.\"ID\",\n"
                + "	   INSUMO.\"CODIGO\",\n"
                + "	   INSUMO.\"DESCRIPCION\",\n"
                + "        \"UNIDAD_MEDIDA\".\"DESCRIPCION\" AS UNIDAD_MEDIDA,\n"
                + "        SUM(CANTIDAD.\"CANTIDAD\") AS CANTIDAD\n"
                + "	FROM public.\"INSUMO\" AS INSUMO\n"
                + "         LEFT JOIN (\n"
                + "             SELECT \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\",\n"
                + "					\"public\".\"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\"\n"
                + "			   	FROM \"public\".\"DETALLE_NOTA_RECEPCION\"\n"
                + "					INNER JOIN \"public\".\"NOTA_RECEPCION\" ON \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = \"public\".\"NOTA_RECEPCION\".\"ID\"\n"
                + "					INNER JOIN \"public\".\"SUCURSAL\" ON \"public\".\"NOTA_RECEPCION\".\"ID_SUCURSAL\" = \"public\".\"SUCURSAL\".\"ID\" AND \"public\".\"SUCURSAL\".\"ID\" = ? \n"
                + "		 ) AS CANTIDAD\n"
                + "         ON INSUMO.\"ID\" = CANTIDAD.\"ID_INSUMO\"\n"
                + "         LEFT JOIN public.\"UNIDAD_MEDIDA\" ON \"UNIDAD_MEDIDA\".\"ID\" = INSUMO.\"ID_UNIDAD_MEDIDA\" \n"
                + "    GROUP BY INSUMO.\"ID\",\n"
                + "             INSUMO.\"CODIGO\",\n"
                + "             INSUMO.\"DESCRIPCION\",\n"
                + "             \"UNIDAD_MEDIDA\".\"DESCRIPCION\"\n"
                + "    ORDER BY INSUMO.\"DESCRIPCION\" ASC;\n"
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
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("UNIDAD_MEDIDA", rs.getString("UNIDAD_MEDIDA"));
            obj.put("CANTIDAD", rs.getInt("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONArray stock_Almacen(int id_sucursal) throws SQLException, JSONException {
        String consulta = "SELECT INSUMO.\"ID\",\n"
                + "	   INSUMO.\"CODIGO\",\n"
                + "	   INSUMO.\"DESCRIPCION\",\n"
                + "        \"UNIDAD_MEDIDA\".\"DESCRIPCION\" AS UNIDAD_MEDIDA,\n"
                + "        SUM(CANTIDAD.\"CANTIDAD\") AS CANTIDAD\n"
                + "	FROM public.\"INSUMO\" AS INSUMO\n"
                + "         INNER JOIN (\n"
                + "             SELECT \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\",\n"
                + "					\"public\".\"DETALLE_NOTA_RECEPCION\".\"CANTIDAD\"\n"
                + "			   	FROM \"public\".\"DETALLE_NOTA_RECEPCION\"\n"
                + "					INNER JOIN \"public\".\"NOTA_RECEPCION\" ON \"public\".\"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = \"public\".\"NOTA_RECEPCION\".\"ID\"\n"
                + "					INNER JOIN \"public\".\"SUCURSAL\" ON \"public\".\"NOTA_RECEPCION\".\"ID_SUCURSAL\" = \"public\".\"SUCURSAL\".\"ID\" AND \"public\".\"SUCURSAL\".\"ID\" = ? \n"
                + "		 ) AS CANTIDAD\n"
                + "         ON INSUMO.\"ID\" = CANTIDAD.\"ID_INSUMO\"\n"
                + "         LEFT JOIN public.\"UNIDAD_MEDIDA\" ON \"UNIDAD_MEDIDA\".\"ID\" = INSUMO.\"ID_UNIDAD_MEDIDA\" \n"
                + "    GROUP BY INSUMO.\"ID\",\n"
                + "             INSUMO.\"CODIGO\",\n"
                + "             INSUMO.\"DESCRIPCION\",\n"
                + "             \"UNIDAD_MEDIDA\".\"DESCRIPCION\"\n"
                + "    ORDER BY INSUMO.\"NOMBRE\" DESCRIPCION;\n"
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
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("UNIDAD_MEDIDA", rs.getString("UNIDAD_MEDIDA"));
            obj.put("CANTIDAD", rs.getInt("CANTIDAD"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public JSONArray todosUnidadMedidaPrecio() throws SQLException, JSONException {
        String consulta = "SELECT \"INSUMO\".*,\n"
                + "       \"UNIDAD_MEDIDA\".\"DESCRIPCION\",\n"
                + "       ULTIMO_PRECIO.\"PRECIO\"\n"
                + "FROM public.\"INSUMO\"\n"
                + "     LEFT JOIN public.\"UNIDAD_MEDIDA\" ON \"UNIDAD_MEDIDA\".\"ID\" = \"INSUMO\".\"ID_UNIDAD_MEDIDA\"\n"
                + "     LEFT JOIN (\n"
                + "        SELECT \"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\", \n"
                + "               \"DETALLE_NOTA_RECEPCION\".\"PRECIO\"\n"
                + "        FROM (\n"
                + "            SELECT \"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\", \n"
                + "                   MAX(\"NOTA_RECEPCION\".\"FECHA\") AS FECHA, \n"
                + "                   MAX(\"NOTA_RECEPCION\".\"ID\") AS ID_NOTA_RECEPCION\n"
                + "                FROM public.\"DETALLE_NOTA_RECEPCION\"\n"
                + "                     INNER JOIN public.\"NOTA_RECEPCION\" ON \"NOTA_RECEPCION\".\"ID\" = \"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\"\n"
                + "                GROUP BY \"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\"\n"
                + "            ) MAXIMO,\n"
                + "            public.\"DETALLE_NOTA_RECEPCION\"\n"
                + "        WHERE  \"DETALLE_NOTA_RECEPCION\".\"ID_NOTA_RECEPCION\" = MAXIMO.ID_NOTA_RECEPCION\n"
                + "               AND MAXIMO.\"ID_INSUMO\" = \"DETALLE_NOTA_RECEPCION\".\"ID_INSUMO\"\n"
                + "        ) ULTIMO_PRECIO ON ULTIMO_PRECIO.\"ID_INSUMO\" = \"INSUMO\".\"ID\"\n"
                + "ORDER BY \"CODIGO\" ASC";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("CODIGO", rs.getString("CODIGO"));
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("UNIDAD_MEDIDA", rs.getString("UNIDAD_MEDIDA"));
            obj.put("PRECIO", rs.getDouble("PRECIO"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }
    
}
