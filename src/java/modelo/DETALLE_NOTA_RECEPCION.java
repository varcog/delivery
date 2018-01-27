package modelo;

import conexion.Conexion;

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

}
