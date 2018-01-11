package modelo;

import conexion.Conexion;

public class PERMISO {

    private int ID;
    private int ID_CARGO;
    private int ID_SUB_MENU;
    private boolean ALTA;
    private boolean BAJA;
    private boolean MODIFICACION;
    private Conexion con;

    public PERMISO(Conexion con) {
        this.con = con;
    }

    public PERMISO(int ID, int ID_CARGO, int ID_SUB_MENU, boolean ALTA, boolean BAJA, boolean MODIFICACION) {
        this.ID = ID;
        this.ID_CARGO = ID_CARGO;
        this.ID_SUB_MENU = ID_SUB_MENU;
        this.ALTA = ALTA;
        this.BAJA = BAJA;
        this.MODIFICACION = MODIFICACION;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_CARGO() {
        return ID_CARGO;
    }

    public void setID_CARGO(int ID_CARGO) {
        this.ID_CARGO = ID_CARGO;
    }

    public int getID_SUB_MENU() {
        return ID_SUB_MENU;
    }

    public void setID_SUB_MENU(int ID_SUB_MENU) {
        this.ID_SUB_MENU = ID_SUB_MENU;
    }

    public boolean isALTA() {
        return ALTA;
    }

    public void setALTA(boolean ALTA) {
        this.ALTA = ALTA;
    }

    public boolean isBAJA() {
        return BAJA;
    }

    public void setBAJA(boolean BAJA) {
        this.BAJA = BAJA;
    }

    public boolean isMODIFICACION() {
        return MODIFICACION;
    }

    public void setMODIFICACION(boolean MODIFICACION) {
        this.MODIFICACION = MODIFICACION;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
}
