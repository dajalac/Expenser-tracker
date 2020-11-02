package com.example.expensetracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.domain.repository.ExpenseRepository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class StatisticActivity extends AppCompatActivity {

    BarChart chart;
    LineChart lineChart;


    ExpenseRepository expenseRepository;
    double currentIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        setBarName(); // set bar name
        setActionBarBtn(); //set bar action
        createVariables();
        createBarchart();
        showAcumulativeGraph();


    }

    public void setBarName(){
        getSupportActionBar().setTitle("Summary");
    }

    public void setActionBarBtn(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show the arrow button
        // to set action for the arrow button, you should enable it at the manifest]
        // I did it in manisfest line 24
    }

    private void createVariables(){

        chart = findViewById(R.id.barChart);
        lineChart = findViewById(R.id.lineChart);



        expenseRepository =new ExpenseRepository (this);

        // retrieve current income
        SharedPreferences sharedPreferences = this.getSharedPreferences("Shared", Context.MODE_PRIVATE);
        String myIncome = sharedPreferences.getString("input","0.00");
        currentIncome = Double.parseDouble(myIncome);
    }

    private void createBarchart(){

        // create 24 possible barsets
        BarDataSet set1,set2,set3,set4,set5,set6,set7,set8,set9,set10,set11,set12,set13,set14,
                set15,set16,set17,set18,set19,set20,set21,set22,set23,set24;

       final  ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();

        int i = 0;
       // ArrayList<BarEntry> entries = new ArrayList<>();
        if(expenseRepository.category("Beauty") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Beauty")));
            set1= new BarDataSet(entries, "Beauty");
            set1.setColor(Color.parseColor("#FFEBEE"));
            dataSets.add(set1);
            i++;
        }
        if(expenseRepository.category("Bills") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Bills")));
            set2= new BarDataSet(entries, "Bills");
            set2.setColor(Color.parseColor("#EDE7F6"));
            dataSets.add(set2);
            i++;
        }
        if(expenseRepository.category("Clothing") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Clothing")));
            set3= new BarDataSet(entries, "Clothing");
            set3.setColor(Color.parseColor("#4CAF50"));
            dataSets.add(set3);
            i++;
        }
        if(expenseRepository.category("Education") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Education")));
            set4= new BarDataSet(entries, "Education");
            set4.setColor(Color.parseColor("#FF6F00"));
            dataSets.add(set4);
            i++;
        }
        if(expenseRepository.category("Electronics") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Electronics")));
            set5= new BarDataSet(entries, "Electronics");
            set5.setColor(Color.parseColor("#FFE57F"));
            dataSets.add(set5);
            i++;
        }
        if(expenseRepository.category("Gift") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Gift")));
            set6= new BarDataSet(entries, "Gift");
            set6.setColor(Color.parseColor("#F4511E"));
            dataSets.add(set6);
            i++;
        }
        if(expenseRepository.category("Grocery") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Grocery")));
            set7= new BarDataSet(entries, "Grocery");
            set7.setColor(Color.parseColor("#D7CCC8"));
            dataSets.add(set7);
            i++;
        }
        if(expenseRepository.category("Health") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Health")));
            set8= new BarDataSet(entries, "Health");
            set8.setColor(Color.parseColor("#FF5722"));
            dataSets.add(set8);
            i++;
        }
        if(expenseRepository.category("House") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("House")));
            set9= new BarDataSet(entries, "House");
            set9.setColor(Color.parseColor("#424242"));
            dataSets.add(set9);
            i++;
        }
        if(expenseRepository.category("Insurance") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Insurance")));
            set10= new BarDataSet(entries, "Insurance");
            set10.setColor(Color.parseColor("#90A4AE"));
            dataSets.add(set10);
            i++;
        }
        if(expenseRepository.category("Kids") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Kids")));
            set11= new BarDataSet(entries, "Kids");
            set11.setColor(Color.parseColor("#FFFFFF"));
            dataSets.add(set11);
            i++;
        }
        if(expenseRepository.category("Laundry") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Laundry")));
            set12= new BarDataSet(entries, "Laundry");
            set12.setColor(Color.parseColor("#8D6E63"));
            dataSets.add(set12);
            i++;
        }
        if(expenseRepository.category("Pet") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Pet")));
            set13= new BarDataSet(entries, "Pet");
            set13.setColor(Color.parseColor("#D81B60"));
            dataSets.add(set13);
            i++;
        }
        if(expenseRepository.category("Public Transportation") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Public Transportation")));
            set14= new BarDataSet(entries, "Public Transportation");
            set14.setColor(Color.parseColor("#827717"));
            dataSets.add(set14);
            i++;
        }
        if(expenseRepository.category("Recreation") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Recreation")));
            set15= new BarDataSet(entries, "Recreation");
            set15.setColor(Color.parseColor("#880E4F"));
            dataSets.add(set15);
            i++;
        }
        if(expenseRepository.category("Rent") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Rent")));
            set16= new BarDataSet(entries, "Rent");
            set16.setColor(Color.parseColor("#4A148C"));
            dataSets.add(set16);
            i++;
        }
        if(expenseRepository.category("Restaurant") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Restaurant")));
            set17= new BarDataSet(entries, "Restaurant");
            set17.setColor(Color.parseColor("#D50000"));
            dataSets.add(set17);
            i++;
        }
        if(expenseRepository.category("Social") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Social")));
            set18= new BarDataSet(entries, "Social");
            set18.setColor(Color.parseColor("#00E676"));
            dataSets.add(set18);
            i++;
        }
        if(expenseRepository.category("Sports") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Sports")));
            set19= new BarDataSet(entries, "Sports");
            set19.setColor(Color.parseColor("#FFE0B2"));
            dataSets.add(set19);
            i++;
        }
        if(expenseRepository.category("Tax") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Tax")));
            set20= new BarDataSet(entries, "Tax");
            set20.setColor(Color.parseColor("#01579B"));
            dataSets.add(set20);
            i++;
        }
        if(expenseRepository.category("Travel") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Travel")));
            set21= new BarDataSet(entries, "Travel");
            set21.setColor(Color.parseColor("#FF1744"));
            dataSets.add(set21);
            i++;
        }
        if(expenseRepository.category("Utilities") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Utilities")));
            set22= new BarDataSet(entries, "Utilities");
            set22.setColor(Color.parseColor("#5C6BC0"));
            dataSets.add(set22);
            i++;
        }
        if(expenseRepository.category("Vehicle") != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Vehicle")));
            set23= new BarDataSet(entries, "Vehicle");
            set23.setColor(Color.parseColor("#C0CA33"));
            dataSets.add(set23);
            i++;
        }
        if(expenseRepository.category("Others") != 0){
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1,expenseRepository.category("Others")));
            set24= new BarDataSet(entries, "Others");
            set24.setColor(Color.parseColor("#212121"));
            dataSets.add(set24);
            i++;
        }

        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getXAxis().setDrawAxisLine(false);

        // hid grid lines
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);

        // remove graphic description
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        // wrap labels
        chart.getLegend().setWordWrapEnabled(true);

        chart.getLegend().setTextSize(8f);


        // set events when click bar chart columns

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                BarEntry pe = (BarEntry) e;

                final float yValue = pe.getY();

                final String label = dataSets.get(h.getDataSetIndex()).getLabel();


                Toast toast =Toast.makeText(StatisticActivity.this,"Category: "+ label +
                        "\n Amount:$" + String.valueOf(yValue) , Toast.LENGTH_LONG);

                 toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);

                 toast.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    public void showAcumulativeGraph() {

        ArrayList<Float> valuesAcumulates = expenseRepository.retriveDaysAmount();
        ArrayList<Float> days = expenseRepository.retriveDays();

        // eliminate duplicated days

        Set<Float> set = new HashSet<>(days);
        days.clear();
        days.addAll(set);

       // Line line = new Line (days);


        ArrayList<Entry> entries = new ArrayList<>();

        for(int i = 0; i<valuesAcumulates.size(); i++){
            entries.add(new Entry(days.get(i),valuesAcumulates.get(i)));
        }
        LineDataSet dataSet = new LineDataSet(entries, " ");
        LineData data = new LineData(dataSet);
        lineChart.setData(data);


        // Set the xAxis position to bottom. Default is top
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // to display all the x axis
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(5f);

        float count=0;

        if(days.size()>0) {
            count = days.get(days.size() - 1);
        }

        int intcount = (int)count;
        xAxis.setAxisMaximum(intcount+1);
        xAxis.setLabelCount(intcount+1);





                // hid grid lines
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);


        lineChart.getAxisRight().setEnabled(false);

        // disable legend
        lineChart.getLegend().setEnabled(false);

        // remove graphic description
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        // change line color
        dataSet.setColor(Color.parseColor("#00897B"));
        dataSet.setCircleColor(Color.parseColor("#00897B"));
        dataSet.setDrawCircleHole( false );
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#B2DFDB"));









    }

}


