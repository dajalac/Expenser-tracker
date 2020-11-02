package com.example.expensetracker;

import java.util.ArrayList;
import java.util.List;

public class TargetData {

    public String targetCategory;
    public double targetValue;
    public double targetExpenseSum;
    public int expensePercent;


    public TargetData(String targetCategory, double targetValue, double targetExpenseSum, int expensePercent){

       this.targetCategory = targetCategory;
       this.targetValue=targetValue;
       this.targetExpenseSum = targetExpenseSum;
       this.expensePercent=expensePercent;

    }



}
