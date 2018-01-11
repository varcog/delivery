package modelo;

import conexion.Conexion;

public class PRODUCTO {

    private int ID;
    private String NOMBRE;
    private String IMAGEN;
    private double PRECIO_COMPRA;
    private double PRECIO_VENTA;
    private Conexion con;

    public PRODUCTO(Conexion con) {
        this.con = con;
    }

    public PRODUCTO(int ID, String NOMBRE, String IMAGEN, double PRECIO_COMPRA, double PRECIO_VENTA) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.IMAGEN = IMAGEN;
        this.PRECIO_COMPRA = PRECIO_COMPRA;
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public double getPRECIO_COMPRA() {
        return PRECIO_COMPRA;
    }

    public void setPRECIO_COMPRA(double PRECIO_COMPRA) {
        this.PRECIO_COMPRA = PRECIO_COMPRA;
    }

    public double getPRECIO_VENTA() {
        return PRECIO_VENTA;
    }

    public void setPRECIO_VENTA(double PRECIO_VENTA) {
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
}
