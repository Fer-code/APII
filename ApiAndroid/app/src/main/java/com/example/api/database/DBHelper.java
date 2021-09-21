package com.example.api.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.api.models.ArtClass;
import com.example.api.models.ExpoClass;
import com.example.api.models.UserClass;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EXPObd";
    public static final int DATABASE_VERSION = 2;

    //TABELA ARTISTAS
    public static final String ARTISTA_TABLE_NAME = "TBArtista";
    public static final String ARTISTA_COLUMN_ID = "idArt";
    public static final String ARTISTA_COLUMN_NAME = "NomeArt";
    public static final String ARTISTA_COLUMN_NAMEART = "NomeArtArt";
    public static final String ARTISTA_COLUMN_GENDER = "genArt";
    public static final String ARTISTA_COLUMN_LOCALNASC = "localNascArt";
    public static final String ARTISTA_COLUMN_ANONASC = "anoNascArt";
    public static final String ARTISTA_COLUMN_LOCALMORT = "LocalMortArt";
    public static final String ARTISTA_COLUMN_CULTURA = "culArt";

    //TABELA EXPOSIÇÃO
    public static final String EXPO_TABLE_NAME = "TBExpo";
    public static final String EXPO_COLUMN_ID = "IDExpo";
    public static final String EXPO_COLUMN_TITLE = "TitleExpo";
    public static final String EXPO_COLUMN_DI = "DIExpo";
    public static final String EXPO_COLUMN_DF = "DFExpo";
    public static final String EXPO_COLUMN_DESC = "DescExpo";
    public static final String EXPO_COLUMN_ORDEMTEMP = "OTExpo";

    //TABELA USER
    public static final String USER_TABLE_NAME = "TBuser";
    public static final String USER_COLUMN_ID = "IDuser";
    public static final String USER_COLUMN_NAME = "NameUser";
    public static final String USER_COLUMN_EMAIL = "EmailUser";
    public static final String USER_COLUMN_PASSWORD = "PasswordUser";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_ARTISTA = "CREATE TABLE " + ARTISTA_TABLE_NAME
                + " ( " + ARTISTA_COLUMN_ID + " INTEGER PRIMARY KEY, " + ARTISTA_COLUMN_NAME + "  TEXT UNIQUE, " +
                ARTISTA_COLUMN_NAMEART + " TEXT, " + ARTISTA_COLUMN_GENDER + " TEXT, " + ARTISTA_COLUMN_LOCALNASC + " TEXT, " +
                ARTISTA_COLUMN_ANONASC + " TEXT, " + ARTISTA_COLUMN_LOCALMORT + " TEXT, " + ARTISTA_COLUMN_CULTURA + " TEXT); ";

        String QUERY_EXPO = "CREATE TABLE " + EXPO_TABLE_NAME
                + " ( " + EXPO_COLUMN_ID + " INTEGER PRIMARY KEY, " + EXPO_COLUMN_TITLE + "  TEXT, " +
                EXPO_COLUMN_DI + " TEXT, " + EXPO_COLUMN_DF + " TEXT, " + EXPO_COLUMN_DESC + " TEXT, " +
                EXPO_COLUMN_ORDEMTEMP + " TEXT); ";

        String QUERY_CLI = "CREATE TABLE " + USER_TABLE_NAME
                + " ( " + USER_COLUMN_ID + " INTEGER PRIMARY KEY, " + USER_COLUMN_NAME + "  TEXT, " +
                USER_COLUMN_EMAIL + " TEXT, " + USER_COLUMN_PASSWORD + " TEXT); ";

        db.execSQL(QUERY_ARTISTA);
        db.execSQL(QUERY_EXPO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ARTISTA_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + EXPO_TABLE_NAME + ";");
        onCreate(db);
    }

    public void addUser(UserClass user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_COLUMN_ID, user.getIdUser());
        values.put(USER_COLUMN_NAME, user.getNameUser());
        values.put(USER_COLUMN_EMAIL, user.getEmailUser());
        values.put(USER_COLUMN_PASSWORD, user.getPasswordUser());

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public void addExpo(ExpoClass expo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EXPO_COLUMN_TITLE, expo.getTitleExpo());
        values.put(EXPO_COLUMN_DI, expo.getDIExpo());
        values.put(EXPO_COLUMN_DF, expo.getDFExpo());
        values.put(EXPO_COLUMN_DESC, expo.getDesc());
        values.put(EXPO_COLUMN_ORDEMTEMP, expo.getTemp());

        db.insert(EXPO_TABLE_NAME, null, values);
        db.close();
    }

    public void addArtista(ArtClass artista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ARTISTA_COLUMN_NAME, artista.getNomeArt());
        values.put(ARTISTA_COLUMN_NAMEART, artista.getNomeArtArt());
        values.put(ARTISTA_COLUMN_GENDER, artista.getGenero());
        values.put(ARTISTA_COLUMN_LOCALNASC, artista.getLocalNasc());
        values.put(ARTISTA_COLUMN_ANONASC, artista.getAnoNasc());
        values.put(ARTISTA_COLUMN_LOCALMORT, artista.getLocalMorte());
        values.put(ARTISTA_COLUMN_CULTURA, artista.getCulArt());

        db.insert(ARTISTA_TABLE_NAME, null, values);
        db.close();
    }

    //Listar todos os artistas
    public List<ArtClass> listaTodosArtistas() {
        List<ArtClass> listaArtista = new ArrayList<ArtClass>();
        String query = "SELECT * FROM " + ARTISTA_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                ArtClass art = new ArtClass();
                art.setCodArt(Integer.parseInt(c.getString(0)));
                art.setNomeArt(c.getString(1));
                art.setNomeArtArt(c.getString(2));
                art.setGenero(c.getString(3));
                art.setLocalNasc(c.getString(4));
                art.setAnoNasc(c.getString(5));
                art.setLocalMorte(c.getString(6));
                art.setCulArt(c.getString(7));

                listaArtista.add(art);
            } while (c.moveToNext());
        }
        return listaArtista;
    }

    //Listar todas as exposicoes
    public List<ExpoClass> listaTodosExpo() {
        List<ExpoClass> listaExpo = new ArrayList<ExpoClass>();
        String query = "SELECT * FROM " + EXPO_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                ExpoClass expo = new ExpoClass();
                expo.setCodExpo(Integer.parseInt(c.getString(0)));
                expo.setTitleExpo(c.getString(1));
                expo.setDIExpo(c.getString(2));
                expo.setDFExpo(c.getString(3));
                expo.setDesc(c.getString(4));
                expo.setTemp(c.getString(5));

                listaExpo.add(expo);
            } while (c.moveToNext());
        }
        return listaExpo;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from TBArtista where idArt=" + id + "", null);
        return res;
    }

    public Cursor getExpo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from TBExpo where IDExpo=" + id + "", null);
        return res;
    }

    public boolean autenticaUsuario(UserClass usuario){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql_busca_usuario =
                "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_EMAIL + " = " + "'" + usuario.getEmailUser() + "'";
        Cursor c = db.rawQuery(sql_busca_usuario, null);
        while(c.moveToNext()){
            if(usuario.getEmailUser().equals(c.getString(c.getColumnIndex(USER_COLUMN_EMAIL)))){
                if(usuario.getPasswordUser().equals(c.getString(c.getColumnIndex(USER_COLUMN_PASSWORD)))){
                    return true;
                }

            }
        }
        db.close();
        c.close();

        return false;
    }

    //Busca id do usuário pelo email
    /*
    public int pegaCodUser(String nome) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USUARIO_TABLE_NAME, new String[]{USUARIO_COLUMN_ID},
                USUARIO_COLUMN_NAME + " = ?" ,
                new String[] {String.valueOf(nome)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int cod = cursor.getInt(0);

        cursor.close();

        return cod;

    }*/
}

