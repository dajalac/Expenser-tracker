package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.example.expensetracker.database.DadosOpenHelper;
import com.example.expensetracker.domain.entity.Expenser;
import com.example.expensetracker.domain.repository.ExpenseRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class SearchActivity extends AppCompatActivity implements FilterExpense.OnFilterExpense {

    // tooblar widget

    MenuItem filter;
    MenuItem clearFilter;

    //widget


    //tags
    private static final String TAG = "SearchActivity";

    // dataBase connection
    private RecyclerView listOfItem;
    private ItensAdapter itensAdapter;
    private ExpenseRepository expenseRepository;
    private DadosOpenHelper dateOpenHelper;
    private SQLiteDatabase conetion;

    // variables
    private String selectedCategory, selectedPayment, selectedAmount, datesFrom, datesTo;
    boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setBarName(); // set bar name
        setActionBarBtn(); //set bar action


        listOfItem = findViewById(R.id.listOfData);
        listOfItem.setHasFixedSize(true);

        showAllItens();

    }

    public void setBarName(){
        getSupportActionBar().setTitle("View Items");
    }

    public void setActionBarBtn(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show the arrow button
        // to set action for the arrow button, you should enable it at the manifest]
        // I did it in manisfest line 23
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // to inflate the menu layout
            getMenuInflater().inflate(R.menu.filtering_menu, menu);

            filter = menu.findItem(R.id.filterIcon);
            clearFilter= menu.findItem(R.id.clearFilter);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.filterIcon:

                FilterExpense filterExpense = new FilterExpense();
                filterExpense.show(getSupportFragmentManager(), "filter expense");

                return true;

            case R.id.clearFilter:
                showAllItens();
                filter.setVisible(true);
                clearFilter.setVisible(false);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setFilter(String category, String payment, String amount, String dateFrom,
                          String dateTo, boolean flag) {
        Log.d(TAG, "setFilter: found filterSetting");

        datesFrom = dateFrom;
        datesTo=dateTo;
        selectedCategory = category;
        selectedPayment = payment;
        selectedAmount= amount;

        this.flag = flag;

        if (flag)
        {
            showFilter();

        }

        filter.setVisible(false);
        clearFilter.setVisible(true);


    }

    public void showFilter (){


        // show filter itens no recycleview
        expenseRepository =new ExpenseRepository(this);
        List<Expenser> filters = expenseRepository.retriveFilter(selectedCategory,selectedPayment,datesFrom, datesTo);


        itensAdapter=new ItensAdapter(filters);
        listOfItem.setAdapter(itensAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listOfItem.setLayoutManager(linearLayoutManager);



    }

    public void showAllItens(){

        // show filter itens no recycleview
        expenseRepository =new ExpenseRepository(this);
        List<Expenser> data = expenseRepository.retriveAll();
        itensAdapter=new ItensAdapter(data);

        listOfItem.setAdapter(itensAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listOfItem.setLayoutManager(linearLayoutManager);

    }

}
