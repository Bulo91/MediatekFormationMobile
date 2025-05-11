package com.example.mediatekformationmobile.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FavorisSQLite extends SQLiteOpenHelper {

    private static final String BDD_NAME = "favoris.db";
    private static final int BDD_VERSION = 1;

    private static final String TABLE_FAVORIS = "favoris";
    private static final String COL_ID = "idFormation";

    public FavorisSQLite(Context context) {
        super(context, BDD_NAME, null, BDD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requete = "CREATE TABLE " + TABLE_FAVORIS + " (" +
                COL_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(requete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        onCreate(db);
    }

    public void ajouterFavori(int idFormation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, idFormation);
        db.insert(TABLE_FAVORIS, null, values);
        db.close();
    }

    public void supprimerFavori(int idFormation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORIS, COL_ID + " = ?", new String[]{String.valueOf(idFormation)});
        db.close();
    }

    public boolean estFavori(int idFormation) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORIS, new String[]{COL_ID},
                COL_ID + " = ?", new String[]{String.valueOf(idFormation)},
                null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }

    public ArrayList<Integer> getTousLesFavoris() {
        ArrayList<Integer> favoris = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORIS, new String[]{COL_ID},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            favoris.add(cursor.getInt(0));
        }

        cursor.close();
        db.close();
        return favoris;
    }
}
