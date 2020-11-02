package com.example.expensetracker.database;

public class ScriptDDL {

    public static String getCreatTableExpense(){

        StringBuilder sql = new StringBuilder();

       sql.append("CREATE TABLE IF NOT EXISTS EXPENSE(");
       sql.append(   "ID	       INTEGER		 PRIMARY KEY AUTOINCREMENT NOT NULL,");
       sql.append(   "ITEM_NOTE    VARCHAR(50)   NOT NULL DEFAULT ('N/A'),");
       sql.append(   "ITEM_VALUE   REAL			 NOT NULL DEFAULT ('0.00'),");
       sql.append(   "ITEM_DATE	  VARCHAR(30)    NOT NULL DEFAULT ('2019'),");
       sql.append(  "CATEGORY     VARCHAR(30)	 NOT  NULL DEFAULT  ('Other'),");
       sql.append(  "PAYMENT      VARCHAR(30)	 NOT  NULL DEFAULT  ('N/A'))");


       return sql.toString();
    }
}
