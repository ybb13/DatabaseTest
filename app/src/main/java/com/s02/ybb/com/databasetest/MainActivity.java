package com.s02.ybb.com.databasetest;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button create_btn;
    private Button add_btn;
    private Button delete_btn;
    private Button query_btn;
    private Button updata_btn;
    private Button transaction_btn;
    private TextView book_name;
    private TextView book_author;
    MySQHelper mySqHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySqHelper = new MySQHelper(this, "mytest.db", null, 2);

        create_btn = (Button) findViewById(R.id.create_btn);
        add_btn = (Button) findViewById(R.id.add_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        updata_btn = (Button) findViewById(R.id.updata_btn);
        query_btn = (Button) findViewById(R.id.query_btn);
        transaction_btn = (Button)findViewById(R.id.transaction_btn);

        book_name = (TextView) findViewById(R.id.book_name);
        book_author = (TextView) findViewById(R.id.book_author);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySqHelper.getReadableDatabase();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = mySqHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("author", "tom");
                contentValues.put("price", "55.70");
                contentValues.put("page", "654");
                contentValues.put("name", "java");

                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("author", "lilei");
                contentValues2.put("price", "43.90");
                contentValues2.put("page", "320");
                contentValues2.put("name", "php");

                sqLiteDatabase.insert("book", null, contentValues);
                sqLiteDatabase.insert("book", null, contentValues2);

                query_btn.performClick();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mySqHelper.getWritableDatabase();
                db.execSQL("delete from book where id = (select min(id) from book)");
                query_btn.performClick();
            }
        });

        updata_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mySqHelper.getWritableDatabase();
                ContentValues value = new ContentValues();
                value.put("author", "hanmm");
                db.update("book", value, "name = ?", new String[]{"php"});
                query_btn.performClick();
            }
        });

        transaction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mySqHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    Log.d("SQL","begin");
                    db.execSQL("delete from book");
                    Log.d("SQL","delete is ok");
                    db.execSQL("insert into book (name,page,author,price) values (?,?,?,?)",new String[]{"第一行代码","420","郭胜","52.10"});
                    Log.d("SQL","insert is ok");
                    db.setTransactionSuccessful();
                    Log.d("SQL","transaction is ok");
                }catch (Exception e){
                    e.printStackTrace();;
                }finally {
                    db.endTransaction();
                }
            }
        });

        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mySqHelper.getWritableDatabase();
                Cursor cursor = db.query("book", null, null, null, null, null, null);

                book_name.setText("");
                book_author.setText("");
                if (cursor.moveToFirst()) {
                    String oldname = "";
                    String oldauthor = "";
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));

                        oldname = book_name.getText().toString();
                        oldauthor = book_author.getText().toString();

                        book_name.setText(oldname + "      " + name);
                        book_author.setText(oldauthor + "      " + author);
                    }
                    while (cursor.moveToNext());
                }
            }
        });

    }
}
