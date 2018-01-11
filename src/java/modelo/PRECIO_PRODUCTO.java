package modelo;

import conexion.Conexion;
import java.util.Date;

public class PRECIO_PRODUCTO {

    private int ID;
    private int ID_PRODUCTO;
    private double PRECIO_COMPRA;
    private double PRECIO_VENTA;
    private Date FECHA_DESDE;
    private Date FECHA_HASTA;
    private Conexion con;

    public PRECIO_PRODUCTO(Conexion con) {
        this.con = con;
    }

    public PRECIO_PRODUCTO(int ID, int ID_PRODUCTO, double PRECIO_COMPRA, double PRECIO_VENTA, Date FECHA_DESDE, Date FECHA_HASTA) {
        this.ID = ID;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.PRECIO_COMPRA = PRECIO_COMPRA;
        this.PRECIO_VENTA = PRECIO_VENTA;
        this.FECHA_DESDE = FECHA_DESDE;
        this.FECHA_HASTA = FECHA_HASTA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_PRODUCTO() {
        return ID_PRODUCTO;
    }

    public void setID_PRODUCTO(int ID_PRODUCTO) {
        this.ID_PRODUCTO = ID_PRODUCTO;
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

    public Date getFECHA_DESDE() {
        return FECHA_DESDE;
    }

    public void setFECHA_DESDE(Date FECHA_DESDE) {
        this.FECHA_DESDE = FECHA_DESDE;
    }

    public Date getFECHA_HASTA() {
        return FECHA_HASTA;
    }

    public void setFECHA_HASTA(Date FECHA_HASTA) {
        this.FECHA_HASTA = FECHA_HASTA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    ////////////////////////////////////////////////////////////////////////////
}
