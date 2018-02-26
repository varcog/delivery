package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class NOTA_RECEPCION {

    private int ID;
    private int NUMERO;
    private Date FECHA;
    private int ID_SUCURSAL;
    private int ID_USUARIO_ENTREGA;
    private int ID_USUARIO_RECIBE;
    private int ESTADO;
    private Conexion con;

    public NOTA_RECEPCION(Conexion con) {
        this.con = con;
    }

    public NOTA_RECEPCION(int ID, int NUMERO, Date FECHA, int ID_SUCURSAL, int ID_USUARIO_ENTREGA, int ID_USUARIO_RECIBE, int ESTADO) {
        this.ID = ID;
        this.NUMERO = NUMERO;
        this.FECHA = FECHA;
        this.ID_SUCURSAL = ID_SUCURSAL;
        this.ID_USUARIO_ENTREGA = ID_USUARIO_ENTREGA;
        this.ID_USUARIO_RECIBE = ID_USUARIO_RECIBE;
        this.ESTADO = ESTADO;
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

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
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
        String consulta = "INSERT INTO public.\"NOTA_RECEPCION\"(\n"
                + "	\"FECHA\", \"ID_SUCURSAL\", \"ID_USUARIO_ENTREGA\", \"ID_USUARIO_RECIBE\", \"ESTADO\")\n"
                + "	VALUES (?, ?, ?, ?, ?);";
        int id = con.EjecutarInsert(consulta, "ID", new java.sql.Date(FECHA.getTime()), ID_SUCURSAL, ID_USUARIO_ENTREGA, ID_USUARIO_RECIBE, ESTADO);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        String consulta = "UPDATE public.\"NOTA_RECEPCION\"\n"
                + "	SET \"FECHA\"=?, \"ID_SUCURSAL\"=?, \"ID_USUARIO_ENTREGA\"=?, \"ID_USUARIO_RECIBE\"=?, \"ESTADO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, FECHA, ID_SUCURSAL, ID_USUARIO_ENTREGA, ID_USUARIO_RECIBE, ESTADO, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"NOTA_RECEPCION\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONObject notaRececpcionPDF(int id_nota_recepcion) throws SQLException, JSONException {
        String consulta = "SELECT \"NOTA_RECEPCION\".\"ID\",\n"
                + "	   to_char(\"NOTA_RECEPCION\".\"FECHA\", 'DD/MM/YYYY') AS FECHA,\n"
                + "        \"NOTA_RECEPCION\".\"NUMERO\",\n"
                + "        \"SUCURSAL\".\"DESCRIPCION\" AS SUCURSAL,\n"
                + "        \"SUCURSAL\".\"DIRECCION\",\n"
                + "        USUARIO_ENTREGA.\"NOMBRES\" AS USUARIO_ENTREGA,\n"
                + "        USUARIO_RECIBE.\"NOMBRES\" AS USUARIO_RECIBE\n"
                + "	FROM public.\"NOTA_RECEPCION\" \n"
                + "    	INNER JOIN public.\"SUCURSAL\" ON \"NOTA_RECEPCION\".\"ID_SUCURSAL\" = \"SUCURSAL\".\"ID\"\n"
                + "        INNER JOIN public.\"USUARIO\" AS USUARIO_ENTREGA ON \"NOTA_RECEPCION\".\"ID_USUARIO_ENTREGA\" = USUARIO_ENTREGA.\"ID\"\n"
                + "        INNER JOIN public.\"USUARIO\" AS USUARIO_RECIBE ON \"NOTA_RECEPCION\".\"ID_USUARIO_RECIBE\" = USUARIO_RECIBE.\"ID\"\n"
                + "    WHERE \"NOTA_RECEPCION\".\"ID\" = ?\n"
                + "";
        PreparedStatement ps = con.statametObject(consulta, id_nota_recepcion);
        ResultSet rs = ps.executeQuery();
        JSONObject json = new JSONObject();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (rs.next()) {
            json.put("FECHA", rs.getString("FECHA"));
            json.put("SUCURSAL", rs.getString("SUCURSAL"));
            json.put("DIRECCION", rs.getString("DIRECCION"));
            json.put("USUARIO_ENTREGA", rs.getString("USUARIO_ENTREGA"));
            json.put("USUARIO_RECIBE", rs.getString("USUARIO_RECIBE"));
            json.put("NUMERO", rs.getInt("NUMERO"));
            json.put("HOY", f.format(new Date()));
            json.put("DETALLE", new DETALLE_NOTA_RECEPCION(con).detalleNotaRecepcionPdf(id_nota_recepcion));
        }
        rs.close();
        ps.close();
        return json;

    }

}
