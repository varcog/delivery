package modelo;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public static void main(String[] args) throws SQLException {
        Conexion con = Conexion.getConeccion();
        USUARIO u = new USUARIO(con);
        if (u.Buscar("delivery", "delivery") == null) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }
}
