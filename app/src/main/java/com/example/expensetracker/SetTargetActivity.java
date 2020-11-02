package com.example.expensetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.expensetracker.domain.TargetAdapter;
import com.example.expensetracker.domain.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;

public class SetTargetActivity extends AppCompatActivity implements  PickTargetDialog.OnFilterTarget{

    //tags
    private static final String TAG = "SetTargetActivity";

    //menu item
    MenuItem addBtn, editBtn;


    // variables

    RecyclerView listOfItem;
    String categorySelected;
    double targetAmount;
    List<TargetData> data;
    boolean flag= false;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_set_target);

        listOfItem = findViewById(R.id.progressivesBar);
        listOfItem.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // to inflate the menu layout
        getMenuInflater().inflate(R.menu.target_menu, menu);

        addBtn = menu.findItem(R.id.add);
        editBtn = menu.findItem(R.id.editTarget);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add:

                PickTargetDialog pickTargetDialog = new PickTargetDialog();
                pickTargetDialog.show(getSupportFragmentManager(), "pick target");

                flag=true;

                return true;

            case R.id.editTarget:

                // do stuffs here

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setTarget (String category, double value) {
        Log.d(TAG, "setFilter: found pickTarget");

       categorySelected = category;
       targetAmount= value;

       if(flag) {
           fill_with_data();
           addItemsRecycleView();
       }


    }

    public void addItemsRecycleView(){


        TargetAdapter targetAdapter = new TargetAdapter(data);
        listOfItem.setAdapter(targetAdapter);

        listOfItem.setLayoutManager(linearLayoutManager);


    }

    public  void fill_with_data() {


        ExpenseRepository repository = new ExpenseRepository(this);

        double temporary = (double) repository.category(categorySelected);

        double expensePercent = temporary * 100 / targetAmount;
        int expenseInteger = (int) expensePercent;


        List<TargetData> data = new ArrayList<>();

        data.add(new TargetData(categorySelected, targetAmount, repository.category(categorySelected),expenseInteger));


        flag=false;

    }
}