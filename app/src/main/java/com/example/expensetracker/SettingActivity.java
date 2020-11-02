package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "Shared";
    public static final String NAME_INPUT = "name";
    public static final String INCOME_INPUT = "input";
    private String nameInput;
    private  String incomeInput;
    private EditText nameEditText;
    public static EditText incomeEditText;
    private Button save;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        createVariables(); // method to create variables
        setBarName(); // set bar name
        setActionBarBtn(); //set bar action
        setEditText();
        display();


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               enableEdit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                display();
                unableSave();

            }

        });

    }

    private void createVariables() {

        nameEditText = findViewById(R.id.nameEditText);
        incomeEditText = findViewById(R.id.icomeInputEditTxt);
        save = findViewById(R.id.settingSaveBtn);
        edit = findViewById(R.id.editButton);
    }

    public void  enableEdit(){
        nameEditText.setEnabled(true);
        incomeEditText.setEnabled(true);
        nameEditText.addTextChangedListener(inputTextWatch);
        incomeEditText.addTextChangedListener(inputTextWatch);

    }

    public void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //save name
        editor.putString(NAME_INPUT, nameEditText.getText().toString());

        // save income

        editor.putString(INCOME_INPUT, incomeEditText.getText().toString());
        editor.apply();

        setEditText();

        // display toast save
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }

    public void setEditText(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        nameInput = sharedPreferences.getString(NAME_INPUT, "Name");
        incomeInput = sharedPreferences.getString(INCOME_INPUT, "0.00");

    }

    public void display(){


        nameEditText.setText(nameInput);
        nameEditText.setEnabled(false);
        incomeEditText.setText(incomeInput);
        incomeEditText.setEnabled(false);

    }

    private void unableSave(){

        // unable save button
        save.setEnabled(false);
    }

    private TextWatcher inputTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String checkName = nameEditText.getText().toString().trim();
            String checkIncome= incomeEditText.getText().toString().trim();

            save.setEnabled(!checkName.isEmpty() && !checkIncome.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void setBarName(){
        getSupportActionBar().setTitle("Add Income");
    }

    public void setActionBarBtn(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show the arrow button
        // to set action for the arrow button, you should enable it at the manifest]
        // I did it in manisfest line 25
    }


}

