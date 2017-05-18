package com.runmanager.gabriel.runmanager.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.runmanager.gabriel.runmanager.model.Corrida;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 18/05/17.
 */

public class BDHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Corrida.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CRIA_CORRIDAS =
            "CREATE TABLE " + CorridaContract.CorridaEntry.TABLE_NAME + " (" +
                    CorridaContract.CorridaEntry._ID + " INTEGER PRIMARY KEY," +
                    CorridaContract.CorridaEntry.COLUMN_NAME_DISTANCIA + TEXT_TYPE + COMMA_SEP +
                    CorridaContract.CorridaEntry.COLUMN_NAME_DATA + TEXT_TYPE + COMMA_SEP +
                    CorridaContract.CorridaEntry.COLUMN_NAME_STATUS + BOOLEAN_TYPE + " DEFAULT 1 " + " )";

    private static final String SQL_DELETA_CORRIDAS =
            "DROP TABLE IF EXISTS " + CorridaContract.CorridaEntry.TABLE_NAME;

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CRIA_CORRIDAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_CRIA_CORRIDAS);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public List<Corrida> buscarTodasCorridas() {
        List<Corrida> corridas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
//        String[] projecao = {
//                CorridaContract.CorridaEntry._ID,
//                CorridaContract.CorridaEntry.COLUMN_NAME_DISTANCIA,
//                CorridaContract.CorridaEntry.COLUMN_NAME_DATA,
//                CorridaContract.CorridaEntry.COLUMN_NAME_STATUS
//
//        };

//        Cursor c = db.query(CorridaContract.CorridaEntry.TABLE_NAME,
//                projecao,
//                null,
//                null,
//                null,
//                null,
//                null
//        );

        Cursor c = db.rawQuery("SELECT * FROM " + CorridaContract.CorridaEntry.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Corrida corrida = cursorToLivro(c);
                corridas.add(corrida);
            } while (c.moveToNext());

        }
        return corridas;
    }

    private Corrida cursorToLivro(Cursor cursor) {
        int id = Integer.parseInt(cursor.getString(0));
        String distancia = cursor.getString(1);
        String data = cursor.getString(2);
        boolean corridaRealizada = cursor.getInt(3) == 0;
        Corrida corrida = new Corrida(distancia, data, corridaRealizada);
        corrida.setId(id);
        return corrida;
    }

    public void add(Corrida corrida) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CorridaContract.CorridaEntry.COLUMN_NAME_DISTANCIA, corrida.getDistancia());
        values.put(CorridaContract.CorridaEntry.COLUMN_NAME_DATA, corrida.getDataProgramada());
        int status = corrida.isCorridaFeita() ? 0 : 1;
        values.put(CorridaContract.CorridaEntry.COLUMN_NAME_STATUS, status);

        long newRowId = db.insert(CorridaContract.CorridaEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void update(Corrida corrida) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CorridaContract.CorridaEntry.COLUMN_NAME_DISTANCIA, corrida.getDistancia());
        values.put(CorridaContract.CorridaEntry.COLUMN_NAME_DATA, corrida.getDataProgramada());
        int status = corrida.isCorridaFeita() ? 0 : 1;
        values.put(CorridaContract.CorridaEntry.COLUMN_NAME_STATUS, status);

        String selection = CorridaContract.CorridaEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(corrida.getId()) };

        int count = db.update(
                CorridaContract.CorridaEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
    }

    public void delete(int corridaId) {
        String selection = CorridaContract.CorridaEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(corridaId) };
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CorridaContract.CorridaEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
