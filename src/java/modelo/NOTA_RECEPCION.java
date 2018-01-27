package modelo;

import conexion.Conexion;
import java.util.Date;

public class NOTA_RECEPCION {

    private int ID;
    private int NUMERO;
    private Date FECHA;
    private int ID_SUCURSAL;
    private int ID_USUARIO_ENTREGA;
    private int ID_USUARIO_RECIBE;
    private Conexion con;

    public NOTA_RECEPCION(Conexion con) {
        this.con = con;
    }

    public NOTA_RECEPCION(int ID, int NUMERO, Date FECHA, int ID_SUCURSAL, int ID_USUARIO_ENTREGA, int ID_USUARIO_RECIBE) {
        this.ID = ID;
        this.NUMERO = NUMERO;
        this.FECHA = FECHA;
        this.ID_SUCURSAL = ID_SUCURSAL;
        this.ID_USUARIO_ENTREGA = ID_USUARIO_ENTREGA;
        this.ID_USUARIO_RECIBE = ID_USUARIO_RECIBE;
    }

}
