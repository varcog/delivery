package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class MENU {

    private int ID;
    private String DESCRIPCION;
    private Conexion con;

    public MENU(Conexion con) {
        this.con = con;
    }

    public MENU(int ID, String DESCRIPCION) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    public JSONObject bucarMenuYSubMenuXCargo(int idCargo) throws SQLException, JSONException {
        String consulta = "SELECT \"MENU\".\"DESCRIPCION\"\n"
                + "	FROM public.\"MENU\", \n"
                + "    	 public.\"SUB_MENU\",\n"
                + "         public.\"PERMISO\"\n"
                + "    WHERE \"PERMISO\".\"ID_CARGO\" = " + idCargo + "\n"
                + "    	  AND \"PERMISO\".\"ID_SUB_MENU\" = \"SUB_MENU\".\"ID\"\n"
                + "          AND \"SUB_MENU\".\"ID_MENU\" = \"MENU\".\"ID\"";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject json = new JSONObject();
        SUB_MENU sub_menu = new SUB_MENU(con);
        while (rs.next()) {
            json.put(rs.getString("DESCRIPCION"), sub_menu.bucarSubMenuXCargo(idCargo));
        }
        rs.close();
        ps.close();
        return json;
    }
}
