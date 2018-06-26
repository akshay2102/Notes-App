package com.example.akshay.sqlite_example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akshay on 03-03-2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String name = "notes.db";
    private static final int version = 1;

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table NOTES(ID INTEGER PRIMARY KEY AUTOINCREMENT , NOTE TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//this is used when database version is changed . You can drop the old table if previous version data is not required in the current version else use alter command.
        //String query = "alter table NOTES add createdOn text";
        String query = "Drop table NOTES if exists";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    //cursor is record set , the output of this method will be stored in cursor object which we can access later
    public Cursor getNotes(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from NOTES";
        return db.rawQuery(query,null);
    }

    public String saveNote(String note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues param = new ContentValues();
        param.put("NOTE",note);//"Note" is column name .  note is value
        db.insert("NOTES",null,param);
        return "Success";
    }

    public String updateNote(String id,String note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues param = new ContentValues();
        param.put("NOTE",note);//"Note" is column name .  note is value
        db.update("NOTES",param,"ID=?",new String[]{id});
        return "Success";
    }

    public String deleteNote(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("NOTES","ID=?",new String[]{id});
        return "Success";
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db,"NOTES");
        db.close();
        return count;
    }
}
