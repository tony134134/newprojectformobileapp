package com.example.mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {

    private static final String DATABASE="DATABASE";
    private static final int VERSION=2;

    public Helper(@Nullable Context context) {

        super(context, DATABASE, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists notes");

    }

    public void insertData(String title, String description){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        database.insert("notes", null, values);

    }

    public Cursor showData(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from notes", null);

        return cursor;

    }

    public void deleteData(String id){

        SQLiteDatabase database = this.getWritableDatabase();

        database.delete("notes", "id=?", new String[]{id});

    }

    public void updateData(String title, String description, String id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);

        sqLiteDatabase.update("notes", values, "id=?", new String[]{id});

    }

}
