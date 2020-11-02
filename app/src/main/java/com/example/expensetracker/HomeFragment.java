package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracker.domain.repository.ExpenseRepository;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ColorFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    // widgets
    private TextView incomelbl,balancelbl, showBalance, showIncome, showExpense, expenselbl;
    private FloatingActionButton floatBtn;
    private PieChart pieChart;


    //variables
    private double currentIncome;
    private double currentBalance,expenseTotal;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        createVariables(view);
        showDate();
        expenseDeduction();
        displayInfo ();
        setPieChart();

        floatBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 callAddingFragment();
             }
         });

        return view;


    }

    private void createVariables(View view){

       // balancelbl = view.findViewById(R.id.balanceLabelTxtView);
        //showBalance = view.findViewById(R.id.showBalanceTextView);

        incomelbl = view.findViewById(R.id.incomeTxtView);
        showIncome = view.findViewById(R.id.showInomeTextView);

        expenselbl = view.findViewById(R.id.expenseTxtView);
        showExpense= view.findViewById(R.id.showExpenseTextView);

        floatBtn = view.findViewById(R.id.floatingActionButton);

        pieChart = view.findViewById(R.id.pieChart);


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Shared", Context.MODE_PRIVATE);
        String myIncome = sharedPreferences.getString("input","0.00");
        currentIncome = Double.parseDouble(myIncome);


    }


    public void callAddingFragment() {

        Intent intentSetting = new Intent(getContext(), AddExpenseActivity.class);
        startActivity(intentSetting);

    }

    private void expenseDeduction(){

        ExpenseRepository expenseRepository =new ExpenseRepository (getActivity());
        expenseTotal= expenseRepository.retriveTotalExpense();


        currentBalance = (currentIncome - expenseTotal);

    }

    private void displayInfo(){


        NumberFormat format=NumberFormat.getCurrencyInstance(Locale.US);
       // String currencyBaln= format.format(currentBalance);
        String currencyIncome = format.format(currentIncome);
        String currencyExpense = format.format(expenseTotal);


        //showBalance.setText(currencyBaln);
        showIncome.setText(currencyIncome);
        showExpense.setText(currencyExpense);


    }

    private void showDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, Y");
        String dateString = sdf.format(new Date());
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(dateString);

    }

    private void setPieChart (){

        float floatBalance = (float) currentBalance;
        //float floatIncome = (float) currentIncome;
        float floatExpense = (float) expenseTotal;

        ArrayList<PieEntry> value = new ArrayList<PieEntry>();
        //value.add(new PieEntry(floatIncome,"Income"));
        value.add(new PieEntry(floatBalance,"Income"));
        value.add(new PieEntry(floatExpense,"Expense"));

        PieDataSet pieDataSet = new PieDataSet(value, "");
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        // to add % to pie chart slices
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setUsePercentValues(true);

        // to remove texts inside slices
        pieChart.setDrawEntryLabels(false);

        // increase the text size
        pieData.setValueTextSize(15f);

        // increase labels size

        Legend legend = pieChart.getLegend();
        legend.setTextSize(15f);
        //legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


        // delete the description label
        pieChart.getDescription().setEnabled(false);


        // set pie chart color
       pieDataSet.setColors(Color.parseColor("#00796B"),Color.parseColor("#B71C1C"));


       // set center text

        NumberFormat format=NumberFormat.getCurrencyInstance(Locale.US);
        String currencyBaln= format.format(currentBalance);

        pieChart.setCenterText("Balance: " +currencyBaln );
        pieChart.setCenterTextSize(18f);
        pieChart.setCenterTextColor(Color.parseColor("#424242"));

    }


}
