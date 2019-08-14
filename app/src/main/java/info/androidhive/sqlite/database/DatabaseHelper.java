package info.androidhive.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.sqlite.database.model.Cliente;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gym_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Cliente.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Cliente.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertCliente(Cliente cliente) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Cliente.COLUMN_NOMBRE, cliente.getNombre());
        values.put(Cliente.COLUMN_EDAD, cliente.getEdad());
        values.put(Cliente.COLUMN_ESTATURA, cliente.getEstatura());
        values.put(Cliente.COLUMN_IMC, cliente.getImc());
        values.put(Cliente.COLUMN_PESO, cliente.getPeso());

        // insert row
        long id = db.insert(Cliente.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Cliente getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Cliente.TABLE_NAME,
                new String[]{Cliente.COLUMN_ID, Cliente.COLUMN_NOMBRE, Cliente.COLUMN_EDAD, Cliente.COLUMN_ESTATURA, Cliente.COLUMN_IMC,  Cliente.COLUMN_PESO, Cliente.COLUMN_TIMESTAMP},
                Cliente.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Cliente cliente = new Cliente(
                cursor.getInt(cursor.getColumnIndex(Cliente.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_NOMBRE)),
                cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_EDAD)),
                cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_ESTATURA)),
                cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_IMC)),
                cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_PESO)),
                cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return cliente;
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Cliente.TABLE_NAME + " ORDER BY " +
                Cliente.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndex(Cliente.COLUMN_ID)));
                cliente.setNombre(cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_NOMBRE)));
                cliente.setTimestamp(cursor.getString(cursor.getColumnIndex(Cliente.COLUMN_TIMESTAMP)));

                clientes.add(cliente);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return clientes;
    }

    public int getClientesCount() {
        String countQuery = "SELECT  * FROM " + Cliente.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Cliente.COLUMN_NOMBRE, cliente.getNombre());
        values.put(Cliente.COLUMN_EDAD, cliente.getEdad());
        values.put(Cliente.COLUMN_ESTATURA, cliente.getEstatura());
        values.put(Cliente.COLUMN_IMC, cliente.getImc());
        values.put(Cliente.COLUMN_PESO, cliente.getPeso());

        // updating row
        return db.update(Cliente.TABLE_NAME, values, Cliente.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
    }

    public void deleteNote(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(cliente.TABLE_NAME, cliente.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();
    }
}
