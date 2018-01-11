package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SUB_MENU {

    private int ID;
    private String DESCRIPCION;
    private String IMAGEN;
    private String URL;
    private int ID_MENU;
    private Conexion con;

    public SUB_MENU(Conexion con) {
        this.con = con;
    }

    public SUB_MENU(int ID, String DESCRIPCION, String IMAGEN, String URL, int ID_MENU) {
        this.ID = ID;
        this.DESCRIPCION = DESCRIPCION;
        this.IMAGEN = IMAGEN;
        this.URL = URL;
        this.ID_MENU = ID_MENU;
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

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getID_MENU() {
        return ID_MENU;
    }

    public void setID_MENU(int ID_MENU) {
        this.ID_MENU = ID_MENU;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
    
    public JSONArray bucarSubMenuXCargo(int idCargo) throws SQLException, JSONException {
        String consulta = "SELECT \"SUB_MENU\".\"DESCRIPCION\",\n"
                + "               \"SUB_MENU\".\"URL\",\n"
                + "               \"SUB_MENU\".\"IMAGEN\"\n"
                + "	FROM public.\"SUB_MENU\",\n"
                + "         public.\"PERMISO\"\n"
                + "    WHERE \"PERMISO\".\"ID_CARGO\" = " + idCargo + "\n"
                + "    	  AND \"PERMISO\".\"ID_SUB_MENU\" = \"SUB_MENU\".\"ID\"\n";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("DESCRIPCION", rs.getString("DESCRIPCION"));
            obj.put("URL", rs.getString("URL"));
            obj.put("IMAGEN", rs.getString("IMAGEN"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
        
            
    }
}
