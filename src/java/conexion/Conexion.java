package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.USUARIO;

public class Conexion {

    private String driver;
    private String url;
    private String host;
    private int puerto;
    private String baseDatos;
    private String usuarioBase;
    private String passwordBase;
    private Connection con;
    private boolean conectado;
    private USUARIO usuario;

    public Conexion() {
        this.driver = "org.postgresql.Driver";
        this.host = "181.188.128.142";
        this.puerto = 5432;
        this.baseDatos = "delivery";
        this.usuarioBase = "delivery";
        this.passwordBase = "delivery";
        this.url = "jdbc:postgresql://" + host + ":" + puerto + "/" + baseDatos;
        conectado = false;
        open();
    }

    public USUARIO getUsuario() {
        return usuario;
    }

    public void setUsuario(USUARIO usuario) {
        this.usuario = usuario;
    }

    private void open() {
        try {
            if (!conectado) {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
                }
                con = DriverManager.getConnection(url, usuarioBase, passwordBase);
                conectado = true;
//                boolean valid = con.isValid(50000);
//                System.out.println(valid ? "TEST OK" : "TEST FAIL");
            }
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error al conectar con la base de datos de PostgreSQL (" + url + "): " + sqle);
        }
    }

    public void EjecutarSentencia(String sentencia) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sentencia);
        ps.execute();
        ps.close();
    }

    public void EjecutarSentencia(String sentencia, Object... campos) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sentencia);
        for (int i = 0; i < campos.length; i++) {
            ps.setObject(i + 1, campos[i]);
        }
        ps.execute();
        ps.close();
    }

    public void EjecutarSentencia(String sentencia, String... campos) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sentencia);
        for (int i = 0; i < campos.length; i++) {
            ps.setString(i + 1, campos[i]);
        }
        ps.execute();
        ps.close();
    }

    public int EjecutarInsert(String consulta, String columnID, Object... campos) throws SQLException {
        consulta = consulta.replaceAll(";", "");
        consulta += "\nRETURNING \"" + columnID + "\";";
        PreparedStatement ps = con.prepareStatement(consulta);
        for (int i = 0; i < campos.length; i++) {
            ps.setObject(i + 1, campos[i]);
        }
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(columnID);
        }
        ps.close();
        return id;
    }

    public int EjecutarInsert(String consulta, String columnID, String... campos) throws SQLException {
        consulta = consulta.replaceAll(";", "");
        consulta += "\nRETURNING \"" + columnID + "\";";
        PreparedStatement ps = con.prepareStatement(consulta);
        for (int i = 0; i < campos.length; i++) {
            ps.setString(i + 1, campos[i]);
        }
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(columnID);
        }
        ps.close();
        return id;
    }

    public void Close() {
        if (conectado) {
            try {
                con.close();
                conectado = false;
            } catch (SQLException ex) {
                conectado = false;
            }
        }
    }

    public ResultSet EjecutarConsulta(String consulta) throws SQLException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet EjecutarConsulta(String consulta, Object ... campos) throws SQLException {
        PreparedStatement ps;
        try {
            ps = statametObject(consulta);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            rollback();
            e.printStackTrace();
            return null;
        }
    }

    public PreparedStatement statamet(String sql) {
        try {
            return con.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PreparedStatement statametString(String sql, String[] campos) {
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < campos.length; i++) {
                ps.setString(i + 1, campos[i]);
            }
            return ps;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PreparedStatement statametObject(String sql, Object... campos) {
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < campos.length; i++) {
                ps.setObject(i + 1, campos[i]);
            }
            return ps;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean isConectado() {
        return conectado;
    }

    public void Transacction() {
        try {
            if (con.getAutoCommit()) {
                con.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Transacction_end() {
        try {
            if (!con.getAutoCommit()) {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commit() {
        try {
            con.commit();
        } catch (SQLException ex) {
        }
    }

    public void rollback() {
        try {
            con.rollback();
        } catch (SQLException ex) {
        }
    }

    public void error(Object obj, Exception ex) {
        rollback();
        Logger.getLogger(obj.getClass().getName()).log(Level.SEVERE, "USUARIO=" + usuario.getNOMBRES() + "\n ERROR:" + ex.getMessage(), ex.getMessage());
        ex.printStackTrace();
    }

    private static Conexion conexion;

    public static Conexion getConeccion() {
        if (conexion == null || !conexion.isConectado()) {
            conexion = new Conexion();
        }
        return conexion;
    }

    public static void main(String[] args) {
        getConeccion();
    }
}
