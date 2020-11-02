package com.example.expensetracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.database.DadosOpenHelper;
import com.example.expensetracker.domain.entity.Expenser;
import com.example.expensetracker.domain.repository.ExpenseRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class AddExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
            CategoryDialog.OnSelectedCategory, PaymentDialog.OnSelectPayment {

    // tags
    private static final String TAG = "AddExpenseActivity";

    // widgets
    private Button saveBtn;
    private EditText expenseValue;
    private EditText exepenseNote;
    private TextView currentDate, categorylbl, selectedCategoryTxtView,dateblbl,selectPaymentlbl,
             selectedPaymentTxtView,amountlbl, notelbl;
    private ImageButton selectDate, chooseCategoryBtn, choosePaymentBtn;

    // variables
    private String selectedDate, noteItem, categoryItem, paymentItem, formatedDateForDB;
    public double valueItem;
    private int thisDay, thisYear,thisMonth;


    // database connection
    private SQLiteDatabase conetion;
    private DadosOpenHelper dateOpenHelper;
    private ConstraintLayout addLayout;
    private ExpenseRepository expenseRepository;
    private Expenser expenserr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_new_item);

        createWidgets();
        setBarName(); // set bar name
        setActionBarBtn(); //set bar action
        showCurrentDate(); //
        enableSave();
        testDBconection();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
                sendDatabase();
            }
        });


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseDate();

            }
        });

        chooseCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDialog();
            }
        });

        choosePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentDialog();
            }
        });

    }

    private void chooseDate(){

        // you can also create a datePicker as I did in the filterExpense class. No needs to create
        //a fragment and implement DatePickerDialog.OnDateSetListener.
        // I will let these two ways to do for future reference, even though I know that it is not
        //necessary
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"Date Picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        thisMonth = c.get(Calendar.MONTH);
        thisDay = c.get(Calendar.DAY_OF_MONTH);
        thisYear =c.get(Calendar.YEAR);


        // show the date at textView
        selectedDate= DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        currentDate.setText(selectedDate);

    }

    private void createWidgets(){

        //buttons
        saveBtn= findViewById(R.id.saveBtn);
        selectDate = findViewById(R.id.imageButton);
        chooseCategoryBtn = findViewById(R.id.imageButtonChooseCategory);
        choosePaymentBtn = findViewById(R.id.imageButtonChoosePayment);

        //textView for output
        selectedCategoryTxtView= findViewById(R.id.textViewSelectedCategory);
        selectedPaymentTxtView = findViewById(R.id.textViewSelectedPayment);
        currentDate =findViewById(R.id.calendarTextView);

        // labels
        categorylbl = findViewById(R.id.textViewCategory);
        dateblbl=findViewById(R.id.textViewDatelbl);
        selectPaymentlbl = findViewById(R.id.textViewPayMethodlbl);
        amountlbl = findViewById(R.id.textViewAmountlbl);
        notelbl = findViewById(R.id.textViewNotelbl);

        // EditText for user input
        expenseValue =findViewById(R.id.valueEditTxt);
        exepenseNote = findViewById(R.id.itemNameEditTxt);

        // layout
        addLayout = findViewById(R.id.addLayout);


    }

    public void setBarName(){
        getSupportActionBar().setTitle("New Item ");
    }

    private void setActionBarBtn(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show the arrow button
        // to set action for the arrow button, you should enable it at the manifest]
        // I did it in manisfest line 24
    }

    private void showCurrentDate (){

        final Calendar c = Calendar.getInstance();
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.DAY_OF_MONTH);
        selectedDate= DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        currentDate.setText(selectedDate);

        thisMonth= c.get(Calendar.MONTH);
        thisDay = c.get(Calendar.DAY_OF_MONTH);
        thisYear =c.get(Calendar.YEAR);
    }

    private void enableSave(){

        exepenseNote.addTextChangedListener(inputTextWatch);
        expenseValue.addTextChangedListener(inputTextWatch);

    }


    private void save() {

        if (exepenseNote.getText().toString().equals("")){
            noteItem ="N/A";
        }
        else {
            noteItem = exepenseNote.getText().toString();
        }
        valueItem = Double.parseDouble(expenseValue.getText().toString());

        if(selectedCategoryTxtView.getText().equals("Choose a category ")){
            categoryItem = "Other";
        }
        else {
            categoryItem = selectedCategoryTxtView.getText().toString();
        }
        if(selectedPaymentTxtView.getText().equals("Choose a payment")){
            paymentItem = "Other";
        }
        else {
            paymentItem = selectedPaymentTxtView.getText().toString();
        }


            expenserr.itemNote = noteItem;
            expenserr.intemValue = valueItem;
            expenserr.itemCategory = categoryItem;
            expenserr.itemPayment = paymentItem;


            // formating the date to sent do db
            String dates = currentDate.getText().toString();
            SimpleDateFormat spf = new SimpleDateFormat("MMM dd, yyyy");
            spf.setLenient(false);
            try {
                Date date = spf.parse(dates);
                spf = new SimpleDateFormat("yyyy-MM-dd");
                String datesformated = spf.format(date);
                expenserr.itemDate = datesformated;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("you get the Exception" + e);
            }





        saveBtn.setEnabled(false);
        expenseValue.setEnabled(false);
        exepenseNote.setEnabled(false);
        Toast.makeText(AddExpenseActivity.this, "Saved", Toast.LENGTH_SHORT).show();

    }


    private TextWatcher inputTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String checkvalue = expenseValue.getText().toString().trim();

            saveBtn.setEnabled(!checkvalue.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    private void  testDBconection(){

        expenserr = new Expenser();

        try{

            dateOpenHelper = new DadosOpenHelper(this);
            conetion=dateOpenHelper.getWritableDatabase();

            Snackbar.make(addLayout, "Connected with success!",Snackbar.LENGTH_SHORT)
                    .setAction("Ok",null).show();

            expenseRepository = new ExpenseRepository(this);


        }catch (SQLException ex){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error!");
            dialog.setMessage(ex.getMessage());
            dialog.setNeutralButton("Ok",null);
            dialog.show();

        }

    }

    private void sendDatabase(){

        // send to database
        try {
            if(expenserr.id==0) {
                expenseRepository.insert(expenserr);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();
            }

        }catch (SQLException ex) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error!");
            dialog.setMessage(ex.getMessage());
            dialog.setNeutralButton("Ok", null);
            dialog.show();
        }
    }

    private void openCategoryDialog(){

        CategoryDialog categoryDialog = new CategoryDialog();
        categoryDialog.show(getSupportFragmentManager(),"choose category");
    }

    private void openPaymentDialog(){
        PaymentDialog paymentDialog = new PaymentDialog();
        paymentDialog.show(getSupportFragmentManager(), "choose payment");

    }


    @Override
    public void sentCategory(String selectedCategory) {
        Log.d(TAG, "sentCategory: found input category" + selectedCategory);
        selectedCategoryTxtView.setText(selectedCategory);
    }

    @Override
    public void sentPayment(String paymentMethod) {
        Log.d(TAG, "sentPayment: found input payment" + paymentMethod);
        selectedPaymentTxtView.setText(paymentMethod);
    }


}
