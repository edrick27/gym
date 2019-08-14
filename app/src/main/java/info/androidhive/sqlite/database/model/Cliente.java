package info.androidhive.sqlite.database.model;

/**
 * Created by ravi on 20/02/18.
 */

public class Cliente {
    public static final String TABLE_NAME = "clientes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_EDAD = "edad";
    public static final String COLUMN_ESTATURA = "estatura";
    public static final String COLUMN_IMC = "IMC";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String nombre;
    private String edad;
    private String estatura;
    private String imc;
    private String peso;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT,"
                    + COLUMN_EDAD + " TEXT,"
                    + COLUMN_ESTATURA + " TEXT,"
                    + COLUMN_IMC + " TEXT,"
                    + COLUMN_PESO + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Cliente() { }

    public Cliente(int id, String nombre, String edad, String estatura, String imc, String peso, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
        this.imc = imc;
        this.peso = peso;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
