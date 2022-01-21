package com.example.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBRuns extends SQLiteOpenHelper {

    public DBRuns(Context context){
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        //myDB.execSQL("create Table runs(date datetime, timeOfRun time, distance INTEGER, speed Float)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists runs");
    }
}
