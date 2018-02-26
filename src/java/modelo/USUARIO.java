package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.StringMD;

public class USUARIO {

    private int ID;
    private String USUARIO;
    private String PASSWORD;
    private String NOMBRES;
    private String APELLIDOS;
    private Date FECHA_NACIMIENTO;
    private Date FECHA_CREACION;
    private String CI;
    private String SEXO;
    private int ID_CARGO;
    private int ID_USUARIO_CREADOR;
    private boolean ESTADO;
    private Conexion con;

    public USUARIO(Conexion con) {
        this.con = con;
    }

    public USUARIO(int ID, String USUARIO, String PASSWORD, String NOMBRES, String APELLIDOS, Date FECHA_NACIMIENTO, Date FECHA_CREACION, String CI, String SEXO, int ID_CARGO, int ID_USUARIO_CREADOR, boolean ESTADO) {
        this.ID = ID;
        this.USUARIO = USUARIO;
        this.PASSWORD = PASSWORD;
        this.NOMBRES = NOMBRES;
        this.APELLIDOS = APELLIDOS;
        this.FECHA_NACIMIENTO = FECHA_NACIMIENTO;
        this.FECHA_CREACION = FECHA_CREACION;
        this.CI = CI;
        this.SEXO = SEXO;
        this.ID_CARGO = ID_CARGO;
        this.ID_USUARIO_CREADOR = ID_USUARIO_CREADOR;
        this.ESTADO = ESTADO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    public Date getFECHA_NACIMIENTO() {
        return FECHA_NACIMIENTO;
    }

    public void setFECHA_NACIMIENTO(Date FECHA_NACIMIENTO) {
        this.FECHA_NACIMIENTO = FECHA_NACIMIENTO;
    }

    public Date getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public void setFECHA_CREACION(Date FECHA_CREACION) {
        this.FECHA_CREACION = FECHA_CREACION;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public String getSEXO() {
        return SEXO;
    }

    public void setSEXO(String SEXO) {
        this.SEXO = SEXO;
    }

    public int getID_CARGO() {
        return ID_CARGO;
    }

    public void setID_CARGO(int ID_CARGO) {
        this.ID_CARGO = ID_CARGO;
    }

    public int getID_USUARIO_CREADOR() {
        return ID_USUARIO_CREADOR;
    }

    public void setID_USUARIO_CREADOR(int ID_USUARIO_CREADOR) {
        this.ID_USUARIO_CREADOR = ID_USUARIO_CREADOR;
    }

    public boolean isESTADO() {
        return ESTADO;
    }

    public void setESTADO(boolean ESTADO) {
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
        java.sql.Date fn = FECHA_NACIMIENTO == null ? null : new java.sql.Date(FECHA_NACIMIENTO.getTime());
        FECHA_CREACION = new Date();
        String consulta = "INSERT INTO public.\"USUARIO\"(\n"
                + "	\"USUARIO\",\"PASSWORD\",\"NOMBRES\",\"APELLIDOS\",\"FECHA_NACIMIENTO\",\"FECHA_CREACION\",\"CI\",\"SEXO\",\"ID_CARGO\",\"ID_USUARIO_CREADOR\",\"ESTADO\")\n"
                + "	VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        int id = con.EjecutarInsert(consulta, "ID", USUARIO, PASSWORD, NOMBRES, APELLIDOS, fn, new java.sql.Date(FECHA_CREACION.getTime()), CI, SEXO, ID_CARGO, ID_USUARIO_CREADOR, true);
        this.ID = id;
        return id;
    }

    public void update() throws SQLException {
        java.sql.Date fn = FECHA_NACIMIENTO == null ? null : new java.sql.Date(FECHA_NACIMIENTO.getTime());
        String consulta = "UPDATE public.\"USUARIO\"\n"
                + "	SET \"USUARIO\"=?,\"PASSWORD\"=?,\"NOMBRES\"=?,\"APELLIDOS\"=?,\"FECHA_NACIMIENTO\"=?,\"CI\"=?,\"SEXO\"=?,\"ID_CARGO\"=?,\"ESTADO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, USUARIO, PASSWORD, NOMBRES, APELLIDOS, fn, CI, SEXO, ID_CARGO, ESTADO, ID);
    }

    public void delete() throws SQLException {
        String consulta = "DELETE FROM public.\"USUARIO\"\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ID);
    }

    public JSONArray todos() throws SQLException, JSONException {
        String consulta = "SELECT * FROM public.\"USUARIO\"\n"
                + "ORDER BY \"USUARIO\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date aux;
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("USUARIO", rs.getString("USUARIO"));
            obj.put("NOMBRES", rs.getString("NOMBRES"));
            obj.put("APELLIDOS", rs.getString("APELLIDOS"));
            aux = rs.getDate("FECHA_NACIMIENTO");
            obj.put("FECHA_NACIMIENTO", aux == null ? "" : f.format(aux));
            aux = rs.getDate("FECHA_CREACION");
            obj.put("FECHA_CREACION", aux == null ? "" : f.format(aux));
            obj.put("CI", rs.getString("CI"));
            obj.put("SEXO", rs.getString("SEXO"));
            obj.put("ID_CARGO", rs.getInt("ID_CARGO"));
            obj.put("ID_USUARIO_CREADOR", rs.getInt("ID_USUARIO_CREADOR"));
            obj.put("ESTADO", rs.getBoolean("ESTADO"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public USUARIO buscar(int id) throws SQLException {
        String consulta = "SELECT * FROM public.\"USUARIO\"\n"
                + "	WHERE \"ID\"=?;";
        PreparedStatement ps = con.statametObject(consulta, id);
        ResultSet rs = ps.executeQuery();
        USUARIO u = new USUARIO(con);
        if (rs.next()) {
            u.setID(rs.getInt("ID"));
            u.setUSUARIO(rs.getString("USUARIO"));
            u.setNOMBRES(rs.getString("NOMBRES"));
            u.setAPELLIDOS(rs.getString("APELLIDOS"));
            u.setFECHA_NACIMIENTO(rs.getDate("FECHA_NACIMIENTO"));
            u.setFECHA_CREACION(rs.getDate("FECHA_CREACION"));
            u.setCI(rs.getString("CI"));
            u.setSEXO(rs.getString("SEXO"));
            u.setID_CARGO(rs.getInt("ID_CARGO"));
            u.setID_USUARIO_CREADOR(rs.getInt("ID_USUARIO_CREADOR"));
            u.setESTADO(rs.getBoolean("ESTADO"));
            return u;
        }
        return null;
    }

    public JSONObject toJSONObject() throws SQLException, JSONException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        JSONObject obj = new JSONObject();
        obj.put("ID", ID);
        obj.put("USUARIO", USUARIO);
        obj.put("NOMBRES", NOMBRES);
        obj.put("APELLIDOS", APELLIDOS);
        obj.put("FECHA_NACIMIENTO", FECHA_NACIMIENTO == null ? "" : f.format(FECHA_NACIMIENTO));
        obj.put("FECHA_CREACION", FECHA_CREACION == null ? "" : f.format(FECHA_CREACION));
        obj.put("CI", CI);
        obj.put("SEXO", SEXO);
        obj.put("ID_CARGO", ID_CARGO);
        obj.put("ID_USUARIO_CREADOR", ID_USUARIO_CREADOR);
        obj.put("ESTADO", ESTADO);
        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////
    public USUARIO Buscar(String usr, String pass) throws SQLException {
        pass = StringMD.getStringMessageDigest(pass, StringMD.SHA512);
        String consulta = "select * from public.\"USUARIO\" where \"USUARIO\" = (?) and \"PASSWORD\" = (?) and \"ESTADO\"=true";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, usr);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        List<USUARIO> lista = Cargar(rs);
        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }
    }

    public List<USUARIO> Cargar(ResultSet rs) throws SQLException {
        List<USUARIO> lista = new ArrayList<USUARIO>();
        while (rs.next()) {
            USUARIO objeto = new USUARIO(con);
            objeto.setID(rs.getInt("ID"));
            objeto.setESTADO(rs.getBoolean("ESTADO"));
            objeto.setUSUARIO(rs.getString("USUARIO"));
            objeto.setPASSWORD(rs.getString("PASSWORD"));
            objeto.setNOMBRES(rs.getString("NOMBRES"));
            objeto.setAPELLIDOS(rs.getString("APELLIDOS"));
            objeto.setCI(rs.getString("CI"));
            objeto.setSEXO(rs.getString("SEXO"));
            objeto.setID_CARGO(rs.getInt("ID_CARGO"));
            objeto.setID_USUARIO_CREADOR(rs.getInt("ID_USUARIO_CREADOR"));
            objeto.setFECHA_NACIMIENTO(rs.getDate("FECHA_NACIMIENTO"));
            objeto.setFECHA_CREACION(rs.getDate("FECHA_CREACION"));
            lista.add(objeto);
        }
        return lista;
    }

    public JSONArray todosConCargo() throws SQLException, JSONException {
        String consulta = "SELECT \"USUARIO\".*, \"CARGO\".\"DESCRIPCION\" AS CARGO\n"
                + "     FROM public.\"USUARIO\"\n"
                + "          LEFT JOIN public.\"CARGO\" ON \"USUARIO\".\"ID_CARGO\" = \"CARGO\".\"ID\"\n"
                + "ORDER BY \"USUARIO\".\"USUARIO\" ASC ";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray json = new JSONArray();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date aux;
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("ID"));
            obj.put("USUARIO", rs.getString("USUARIO"));
            obj.put("NOMBRES", rs.getString("NOMBRES"));
            obj.put("APELLIDOS", rs.getString("APELLIDOS"));
            aux = rs.getDate("FECHA_NACIMIENTO");
            obj.put("FECHA_NACIMIENTO", aux == null ? "" : f.format(aux));
            aux = rs.getDate("FECHA_CREACION");
            obj.put("FECHA_CREACION", aux == null ? "" : f.format(aux));
            obj.put("CI", rs.getString("CI"));
            obj.put("SEXO", rs.getString("SEXO"));
            obj.put("ID_CARGO", rs.getInt("ID_CARGO"));
            obj.put("CARGO", rs.getString("CARGO"));
            obj.put("ID_USUARIO_CREADOR", rs.getInt("ID_USUARIO_CREADOR"));
            obj.put("ESTADO", rs.getBoolean("ESTADO"));
            json.put(obj);
        }
        rs.close();
        ps.close();
        return json;
    }

    public void updateDatos() throws SQLException {
        java.sql.Date fn = FECHA_NACIMIENTO == null ? null : new java.sql.Date(FECHA_NACIMIENTO.getTime());
        String consulta = "UPDATE public.\"USUARIO\"\n"
                + "	SET \"NOMBRES\"=?,\"APELLIDOS\"=?,\"FECHA_NACIMIENTO\"=?,\"SEXO\"=?,\"ID_CARGO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, NOMBRES, APELLIDOS, fn, SEXO, ID_CARGO, ID);
    }

    public void updateContrasena() throws SQLException {
        String consulta = "UPDATE public.\"USUARIO\"\n"
                + "	SET \"PASSWORD\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, PASSWORD, ID);
    }

    public void updateEstado() throws SQLException {
        String consulta = "UPDATE public.\"USUARIO\"\n"
                + "	SET \"ESTADO\"=?\n"
                + "	WHERE \"ID\"=?;";
        con.EjecutarSentencia(consulta, ESTADO, ID);
    }

    public String getNombreCompleto() {
        String res = "";
        if (NOMBRES != null) {
            res += NOMBRES;
        }
        if (APELLIDOS != null) {
            res += APELLIDOS;
        }
        return res;
    }

    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws SQLException {
//        Conexion con = Conexion.getConeccion();
//        USUARIO u = new USUARIO(con);
//        if (u.Buscar("delivery", "delivery") == null) {
//            System.out.println("false");
//        } else {
//            System.out.println("true");
//        }

        Date dia = new Date();
        java.sql.Date name = null;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(f.format(name));
    }
}
