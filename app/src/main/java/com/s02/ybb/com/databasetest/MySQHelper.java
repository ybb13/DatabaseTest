package com.s02.ybb.com.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/2.
 */
public class MySQHelper extends SQLiteOpenHelper{

    public static final String CREATE_BOOK = "create table book ("+
            "id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "page integer,"+
            "name text)";

    public static  final String CREATE_CATEGORY = "create table category("+
            "id integer primary key autoincrement,"+
            "categort_name text,"+
            "category_code intger"
            +")";

    private Context mcontext;
    public MySQHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mcontext,"创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists category");
        onCreate(db);
    }
}
