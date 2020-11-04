package com.example.expensetracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class StatisticActivity extends AppCompatActivity implements GetMonthandYear.OnGetMonthandYear{

    MenuItem selectMonth;
    BarChart chart;
    //LineChart lineChart;
    ExpenseRepository expenseRepository;

    private TextView monthTxtView, yearTxtView,valueTxtView, lableTxtView;
    double currentIncome;
    int month_selected;
    int year_selected;
    float totalBalance;
    Boolean flag= false;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // to inflate the menu layout
        getMenuInflater().inflate(R.menu.month_menu, menu);
        selectMonth = menu.findItem(R.id.filterIcon);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        setBarName(); // set bar name
        setActionBarBtn(); //set bar action
        createVariables();
        createBarchart();
        showBalance();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.filterIcon){

            GetMonthandYear getMonthandYear= new GetMonthandYear();
            getMonthandYear.show(getSupportFragmentManager(), "filter expense");

        }

        return super.onOptionsItemSelected(item);

    }
    @Override
    public void setFilter(int month, int year) {


        month_selected = month;
        year_selected = year;
        flag = true;
        clearData();
        createBarchart();
        showBalance();

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
        //lineChart = findViewById(R.id.lineChart);
        monthTxtView =findViewById(R.id.monthTxtView);
        yearTxtView=findViewById(R.id.yearTxtView);
        valueTxtView=findViewById(R.id.amountTxtView);
        lableTxtView = findViewById(R.id.valueTxtView);

        Log.d("selected m", String.valueOf(month_selected));
        Log.d("selected y", String.valueOf(year_selected));


        expenseRepository =new ExpenseRepository (this);

        // retrieve current income
        SharedPreferences sharedPreferences = this.getSharedPreferences("Shared", Context.MODE_PRIVATE);
        String myIncome = sharedPreferences.getString("input","0.00");
        currentIncome = Double.parseDouble(myIncome);
    }

    private void createBarchart(){


        if(!flag){
            final Calendar c = Calendar.getInstance();
            month_selected = c.get(Calendar.MONTH) +1 ;
            year_selected =c.get(Calendar.YEAR);
        }


        Log.d("selected mes", String.valueOf(month_selected));
        Log.d("selected year", String.valueOf(year_selected));



        totalBalance = 0.0f;

        // create 24 possible barsets
        BarDataSet set1,set2,set3,set4,set5,set6,set7,set8,set9,set10,set11,set12,set13,set14,
                set15,set16,set17,set18,set19,set20,set21,set22,set23,set24;

       final  ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();

        int i = 0;
       // ArrayList<BarEntry> entries = new ArrayList<>();
        if(expenseRepository.category("Beauty",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Beauty",month_selected,year_selected)));
            set1= new BarDataSet(entries, "Beauty");
            set1.setColor(Color.parseColor("#FFEBEE"));
            dataSets.add(set1);
            totalBalance+= expenseRepository.category("Beauty",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Bills",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Bills",month_selected,year_selected)));
            set2= new BarDataSet(entries, "Bills");
            set2.setColor(Color.parseColor("#EDE7F6"));
            dataSets.add(set2);
            totalBalance+= expenseRepository.category("Bills",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Clothing",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Clothing",month_selected,year_selected)));
            set3= new BarDataSet(entries, "Clothing");
            set3.setColor(Color.parseColor("#4CAF50"));
            dataSets.add(set3);
            totalBalance+= expenseRepository.category("Clothing",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Education",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Education",month_selected,year_selected)));
            set4= new BarDataSet(entries, "Education");
            set4.setColor(Color.parseColor("#FF6F00"));
            dataSets.add(set4);
            totalBalance+= expenseRepository.category("Education",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Electronics",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Electronics",month_selected,year_selected)));
            set5= new BarDataSet(entries, "Electronics");
            set5.setColor(Color.parseColor("#FFE57F"));
            dataSets.add(set5);
            totalBalance+= expenseRepository.category("Electronics",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Gift",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Gift",month_selected,year_selected)));
            set6= new BarDataSet(entries, "Gift");
            set6.setColor(Color.parseColor("#F4511E"));
            dataSets.add(set6);
            totalBalance+= expenseRepository.category("Gift",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Grocery",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Grocery",month_selected,year_selected)));
            set7= new BarDataSet(entries, "Grocery");
            set7.setColor(Color.parseColor("#D7CCC8"));
            dataSets.add(set7);
            totalBalance+= expenseRepository.category("Grocery",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Health",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Health",month_selected,year_selected)));
            set8= new BarDataSet(entries, "Health");
            set8.setColor(Color.parseColor("#FF5722"));
            dataSets.add(set8);
            totalBalance+= expenseRepository.category("Health",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("House",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("House",month_selected,year_selected)));
            set9= new BarDataSet(entries, "House");
            set9.setColor(Color.parseColor("#424242"));
            dataSets.add(set9);
            totalBalance+= expenseRepository.category("House",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Insurance",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Insurance",month_selected,year_selected)));
            set10= new BarDataSet(entries, "Insurance");
            set10.setColor(Color.parseColor("#90A4AE"));
            dataSets.add(set10);
            totalBalance+= expenseRepository.category("Insurance",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Kids",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Kids",month_selected,year_selected)));
            set11= new BarDataSet(entries, "Kids");
            set11.setColor(Color.parseColor("#FFFFFF"));
            totalBalance+= expenseRepository.category("Kids",month_selected,year_selected);
            dataSets.add(set11);
            i++;
        }
        if(expenseRepository.category("Laundry",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Laundry",month_selected,year_selected)));
            set12= new BarDataSet(entries, "Laundry");
            set12.setColor(Color.parseColor("#8D6E63"));
            dataSets.add(set12);
            totalBalance+= expenseRepository.category("Laundry",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Pet",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Pet",month_selected,year_selected)));
            set13= new BarDataSet(entries, "Pet");
            set13.setColor(Color.parseColor("#D81B60"));
            dataSets.add(set13);
            totalBalance+= expenseRepository.category("Pet",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Public Transportation",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Public Transportation",month_selected,year_selected)));
            set14= new BarDataSet(entries, "Public Transportation");
            set14.setColor(Color.parseColor("#827717"));
            dataSets.add(set14);
            totalBalance+= expenseRepository.category("Public Transportation",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Recreation",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Recreation",month_selected,year_selected)));
            set15= new BarDataSet(entries, "Recreation");
            set15.setColor(Color.parseColor("#880E4F"));
            dataSets.add(set15);
            totalBalance+= expenseRepository.category("Recreation",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Rent",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Rent",month_selected,year_selected)));
            set16= new BarDataSet(entries, "Rent");
            set16.setColor(Color.parseColor("#4A148C"));
            dataSets.add(set16);
            totalBalance+= expenseRepository.category("Rent",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Restaurant",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Restaurant",month_selected,year_selected)));
            set17= new BarDataSet(entries, "Restaurant");
            set17.setColor(Color.parseColor("#D50000"));
            dataSets.add(set17);
            totalBalance+= expenseRepository.category("Restaurant",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Social",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Social",month_selected,year_selected)));
            set18= new BarDataSet(entries, "Social");
            set18.setColor(Color.parseColor("#00E676"));
            dataSets.add(set18);
            totalBalance+= expenseRepository.category("Social",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Sports",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Sports",month_selected,year_selected)));
            set19= new BarDataSet(entries, "Sports");
            set19.setColor(Color.parseColor("#FFE0B2"));
            dataSets.add(set19);
            totalBalance+= expenseRepository.category("Sports",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Tax",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Tax",month_selected,year_selected)));
            set20= new BarDataSet(entries, "Tax");
            set20.setColor(Color.parseColor("#01579B"));
            dataSets.add(set20);
            totalBalance+= expenseRepository.category("Tax",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Travel",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Travel",month_selected,year_selected)));
            set21= new BarDataSet(entries, "Travel");
            set21.setColor(Color.parseColor("#FF1744"));
            dataSets.add(set21);
            totalBalance+= expenseRepository.category("Travel",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Utilities",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Utilities",month_selected,year_selected)));
            set22= new BarDataSet(entries, "Utilities");
            set22.setColor(Color.parseColor("#5C6BC0"));
            dataSets.add(set22);
            totalBalance+= expenseRepository.category("Utilities",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Vehicle",month_selected,year_selected) != 0) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1, expenseRepository.category("Vehicle",month_selected,year_selected)));
            set23= new BarDataSet(entries, "Vehicle");
            set23.setColor(Color.parseColor("#C0CA33"));
            dataSets.add(set23);
            totalBalance+= expenseRepository.category("Vehicle",month_selected,year_selected);
            i++;
        }
        if(expenseRepository.category("Others",month_selected,year_selected) != 0){
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(i+1,expenseRepository.category("Others",month_selected,year_selected)));
            set24= new BarDataSet(entries, "Others");
            set24.setColor(Color.parseColor("#212121"));
            dataSets.add(set24);
            totalBalance+= expenseRepository.category("Others",month_selected,year_selected);
            i++;
        }

        Log.d("total balance", String.valueOf(totalBalance));

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

    /**
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
     **/



    public void showBalance(){

        String monthDisplay = new DateFormatSymbols().getMonths()[month_selected-1];
        monthTxtView.setText(monthDisplay);
        yearTxtView.setText(String.valueOf(year_selected));

        // format value currence
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        String currency = format.format(totalBalance);
        valueTxtView.setText(currency);

    }

    public void clearData(){

        try {
            chart.clearValues();
            chart.clear();
        }
        catch (Exception e){
            Log.d("exeption",String.valueOf(e));
        }

    }

}



