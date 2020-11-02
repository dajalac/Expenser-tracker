package com.example.expensetracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Script;

public class DadosOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;

    public DadosOpenHelper(Context context) {
        super(context, "EXPENSE",null, DATABASE_VERSION);
    }


    //where I should create my dataBase table
    @Override
    public void onCreate(SQLiteDatabase db) {

        //To create expense table
       db.execSQL(ScriptDDL.getCreatTableExpense());
    }

    //where should make atualizações na data base
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop old version table
        db.execSQL(ScriptDDL.getCreatTableExpense());

        // Create New Version table
        onCreate(db);

    }



    // To create a database connection

    public SQLiteDatabase getConnectionDataBase(){

        return this. getWritableDatabase();
    }
}
