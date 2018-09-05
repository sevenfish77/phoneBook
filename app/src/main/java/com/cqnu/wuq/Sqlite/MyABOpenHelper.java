package com.cqnu.wuq.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyABOpenHelper extends SQLiteOpenHelper {
    public MyABOpenHelper(Context context) {
        super(context, "wqandroid", null, 1);//自定义访问sqlite
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("数据库被创建了");
       // db.execSQL("create table users(id int primary key ,name text,pwd text)");//登录界面users表

        db.execSQL("create table contactinfo (id integer primary key autoincrement, name char(20)," +
                "phone varchar(20),email varchar(20),home varchar(20),nickname varchar(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
