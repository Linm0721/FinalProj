package com.example.linm.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ACER on 2017/12/31.
 */

public class MyDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "Contacts.db";
    public static final String TABLE_NAME = "Todolist2";
    private static final int DB_VERSION = 1;

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MyDB(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + "(id integer PRIMARY KEY AUTOINCREMENT,"
                +"title TEXT,"
                + "type integer,"
                + "content TEXT,"
                + "ddl TEXT,"
                + "setuptime TEXT)";
        Log.e("debug","创建数据库成功");
        db.execSQL(CREATE_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void insert(String title, int type,String content, String ddl,String setuptime){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("type",type);
        values.put("content",content);
        values.put("ddl",ddl);
        values.put("setuptime",setuptime);
        db.insert(TABLE_NAME, null, values);
        Log.e("debug","插入成功");
        db.close();
    }

    public void update(int id, String title, int type,String content, String ddl,String setuptime){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?" ;
        String[] whereArgs = {id+""};
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("type",type);
        values.put("content",content);
        values.put("ddl",ddl);
        values.put("setuptime",setuptime);
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {id+""};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public Cursor ifexit(String title){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from Todolist2 where title = \"" +title+"\"";
        Log.e("debug",query);
        return db.rawQuery(query,null);
    }

    public Cursor selectall( ){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from Todolist2 ";
        return db.rawQuery(query,null);
    }

    public Cursor selectbyid(int id ){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from Todolist2 where id = " +id+"" ;
        return db.rawQuery(query,null);
    }

}
