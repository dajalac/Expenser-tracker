package com.example.expensetracker.domain.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensetracker.database.DadosOpenHelper;
import com.example.expensetracker.domain.entity.Expenser;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    private DadosOpenHelper dadosOpenHelper;

    public ExpenseRepository (Context context){
        this.dadosOpenHelper= new DadosOpenHelper(context);
    }

    public void insert (Expenser expenser){

        ContentValues contentValues = new ContentValues();
       // contentValues.put("CODE",expenser.code); so os valores passado pelo usuario
        contentValues.put("ITEM_NOTE ", expenser.itemNote);
        contentValues.put("ITEM_VALUE ",expenser.intemValue );
        contentValues.put("ITEM_DATE ", expenser.itemDate);
        contentValues.put("CATEGORY ",expenser.itemCategory);
        contentValues.put("PAYMENT",expenser.itemPayment);

        dadosOpenHelper.getConnectionDataBase().insertOrThrow("EXPENSE",null, contentValues);

    }

    public void exclude (int id){
        String[] parameter = new String[1];
        parameter[0] = String.valueOf(id);
        dadosOpenHelper.getConnectionDataBase().delete("EXPENSE","ID = ?", parameter);

    }

    public void update (Expenser expenser){

        ContentValues contentValues = new ContentValues();
        // contentValues.put("CODE",expenser.code); so os valores passado pelo usuario
        contentValues.put("ITEM_NOTE ", expenser.itemNote);
        contentValues.put("ITEM_VALUE ",expenser.intemValue );
        contentValues.put("ITEM_DATE ", expenser.itemDate);
        contentValues.put("CATEGORY ",expenser.itemCategory);
        contentValues.put("PAYMENT",expenser.itemPayment);

        String[] parameter = new String[1];
        parameter[0] = String.valueOf(expenser.id);
        dadosOpenHelper.getConnectionDataBase().update("EXPENSE",contentValues,"ID = ?", parameter);


    }

    public List<Expenser> retriveAll (){

        List<Expenser> expensers = new ArrayList<Expenser>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, ITEM_NOTE,ITEM_VALUE,ITEM_DATE, CATEGORY,PAYMENT "); //or SELECT *
        sql.append("FROM EXPENSE ");
        sql.append("ORDER BY ID DESC");

        Cursor result =   dadosOpenHelper.getConnectionDataBase().rawQuery(sql.toString(),null);

        if (result.getCount()>0) {
            result.moveToFirst();

            do{

                Expenser expen = new Expenser();
                expen.id           =  result.getInt(result.getColumnIndexOrThrow("ID"));
                expen.itemNote     =  result.getString(result.getColumnIndexOrThrow("ITEM_NOTE"));
                expen.intemValue   =  result.getDouble(result.getColumnIndexOrThrow("ITEM_VALUE"));
                expen.itemDate        =  result.getString(result.getColumnIndexOrThrow("ITEM_DATE"));
                expen.itemCategory =  result.getString(result.getColumnIndexOrThrow("CATEGORY"));
                expen.itemPayment   =  result.getString(result.getColumnIndexOrThrow("PAYMENT"));

                expensers.add(expen);

            }while (result.moveToNext());
        }

        return expensers;
    }


    public double retriveTotalExpense (){

        Expenser expenser = new Expenser();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(ITEM_VALUE)");
        sql.append("As total ");
        sql.append("FROM EXPENSE ");
        sql.append("WHERE strftime('%Y',ITEM_DATE) = strftime('%Y',date('now','localtime')) " );
        sql.append("AND strftime('%m',ITEM_DATE) = strftime('%m',date('now','localtime'))");


        Cursor result =   dadosOpenHelper.getConnectionDataBase().rawQuery(sql.toString(),null);

        if (result.getCount()>0) {
            result.moveToFirst();

            double total = result.getDouble(result.getColumnIndex("total"));

            return total;
        }

        return 0.00;

    }


    public List<Expenser> retriveFilter (String category, String payment, String dateFrom, String dateTo){

        List<Expenser> expensers = new ArrayList<Expenser>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM EXPENSE ");
        sql.append("WHERE CATEGORY LIKE ? AND PAYMENT LIKE ?");
        sql.append("AND ITEM_DATE BETWEEN ? AND ? ");
        sql.append("ORDER BY ITEM_DATE");



        String[] parameter = new String[4];
        parameter[0] = String.valueOf(category);
        parameter[1] = String.valueOf(payment);
        parameter[2] = String.valueOf(dateFrom);
        parameter[3] = String.valueOf(dateTo);

        Cursor result =   dadosOpenHelper.getConnectionDataBase().rawQuery(sql.toString(),parameter);

        if (result.getCount()>0) {
            result.moveToFirst();

            do{

                Expenser expen = new Expenser();
                expen.id           =  result.getInt(result.getColumnIndexOrThrow("ID"));
                expen.itemNote     =  result.getString(result.getColumnIndexOrThrow("ITEM_NOTE"));
                expen.intemValue   =  result.getDouble(result.getColumnIndexOrThrow("ITEM_VALUE"));
                expen.itemDate        =  result.getString(result.getColumnIndexOrThrow("ITEM_DATE"));
                expen.itemCategory =  result.getString(result.getColumnIndexOrThrow("CATEGORY"));
                expen.itemPayment   =  result.getString(result.getColumnIndexOrThrow("PAYMENT"));

                expensers.add(expen);

            }while (result.moveToNext());
        }

        return expensers;
    }


    public float category (String category){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(ITEM_VALUE)");
        sql.append("As sum ");
        sql.append("FROM EXPENSE ");
        sql.append("WHERE CATEGORY LIKE ?" );
        sql.append("AND strftime('%Y',ITEM_DATE) = strftime('%Y',date('now','localtime')) " );
        sql.append("AND strftime('%m',ITEM_DATE) = strftime('%m',date('now','localtime'))");

        String[] parameter = new String[1];
        parameter[0] = String.valueOf(category);

        Cursor result =   dadosOpenHelper.getConnectionDataBase().rawQuery(sql.toString(),parameter);



        if (result.getCount()>0) {
            result.moveToFirst();

            float total = result.getFloat(result.getColumnIndexOrThrow("sum"));

            return total;
        }

        return 0.0f;
    }

    public ArrayList<Float> retriveDaysAmount (){

        ArrayList<Float> sumDate = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(ITEM_VALUE)");
        sql.append("As total ");
        sql.append("FROM EXPENSE ");
        sql.append("WHERE strftime('%Y',ITEM_DATE) = strftime('%Y',date('now','localtime')) " );
        sql.append("AND strftime('%m',ITEM_DATE) = strftime('%m',date('now','localtime'))");
        sql.append("GROUP BY ITEM_DATE");


        Cursor result =   dadosOpenHelper.getConnectionDataBase().rawQuery(sql.toString(),null);

        while(result.moveToNext()){

            sumDate.add(result.getFloat(result.getColumnIndexOrThrow("total")));
        }
        result.close();

        return sumDate;

    }

    public ArrayList<Float> retriveDays (){

        ArrayList<Float> sumDate = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT strftime('%d',ITEM_DATE) As days ");
        sql.append("FROM EXPENSE ");
        sql.append("WHERE strftime('%Y',ITEM_DATE) = strftime('%Y',date('now','localtime')) " );
        sql.append("AND strftime('%m',ITEM_DATE) = strftime('%m',date('now','localtime'))");
        sql.append("GROUP BY ITEM_DATE");


        Cursor result =   dadosOpenHelper.getConnectionDataBase().rawQuery(sql.toString(),null);

        while(result.moveToNext()){

            sumDate.add(result.getFloat(result.getColumnIndexOrThrow("days")));
        }
        result.close();

        return sumDate;

    }




}
