package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FilterExpense extends DialogFragment {

    //interface to sent data to searchActivity
    public interface OnFilterExpense{
        void setFilter(String category, String payment, String amount, String dateFrom,
                       String dateTo, boolean flag);
    }

    public OnFilterExpense onFilterExpense;

    //tags
    private static final String TAG = "FilterExpense";

    // widgets
    TextView filterBylbl, categorylbl, amountlbl,paymentlbl, initialDatelbl,
            finalDatelbl, cancelBtn, applybtb;
    Button fromDatebtb, toDatebtn;
    Spinner spinnerCategory, spinnerAmount, spinnerPayment;
    View view;

    // variables
    private String selectedDate, selectedCategory, selectedPayment, selectedAmount;
    boolean flag;

    public FilterExpense() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_filtering, container);

        createVariables();
        showCurrentDate();

        fromDatebtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromDate();
            }
        });

        toDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseToDate ();
            }
        });

        applybtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                sentData();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = false;
                dismissFilter();

            }
        });


        return view;

    }

    public void createVariables(){
        filterBylbl = view.findViewById(R.id.filterBylbl);
        categorylbl = view.findViewById(R.id.categorylbl);
       // amountlbl = view.findViewById(R.id.amountlbl);
        paymentlbl = view.findViewById(R.id.paymentlbl);
        initialDatelbl = view.findViewById(R.id.initialDatelbl);
        finalDatelbl = view.findViewById(R.id.finalDatelbl);

        fromDatebtb = view.findViewById(R.id.fromBtn);
        toDatebtn = view.findViewById(R.id.toBtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        applybtb = view.findViewById(R.id.applyBtn);

        spinnerCategory= view.findViewById(R.id.spinnerCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerCategories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapter);

        /**
        spinnerAmount= view.findViewById(R.id.spinnerAmount);
        ArrayAdapter<CharSequence> adapterAmount = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerAmount, android.R.layout.simple_spinner_item);
        adapterAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAmount.setAdapter(adapterAmount);

         **/

        spinnerPayment= view.findViewById(R.id.spinnerPayment);
        ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerPayment, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayment.setAdapter(adapterPayment);

    }

    public void showCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int  currentMonth = c.get(Calendar.MONTH);
        int currentYear =c.get(Calendar.YEAR);
        int firstDayOfMonth = Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH);
        int lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        // FORMATE THE DATE TO BE DISPLAY
        String setFromLabelTexts = firstDayOfMonth+"-"+ toString().valueOf(currentMonth+1)+"-"+toString().valueOf(currentYear);
        String setToLabelTexts = lastDayOfMonth+"-"+ toString().valueOf(currentMonth+1)+"-"+toString().valueOf(currentYear);


        SimpleDateFormat fromSpf = new SimpleDateFormat("d-M-yyyy");
        SimpleDateFormat toSpf = new SimpleDateFormat("d-M-yyyy");
        fromSpf.setLenient(false);
        toSpf.setLenient(false);
        try {
            Date dateFrom = fromSpf.parse(setFromLabelTexts);
            Date dateTo = toSpf.parse(setToLabelTexts);
            fromSpf = new SimpleDateFormat("MMM dd, yyyy");
            toSpf = new SimpleDateFormat("MMM dd, yyyy");
            String fromDates = fromSpf.format(dateFrom);
            String toDates = toSpf.format(dateTo);
            initialDatelbl.setText(fromDates);
            finalDatelbl.setText(toDates);

        } catch(Exception e) {
            e.printStackTrace();
            System.out.print("you get the Exception" + e);
        }


    }
    private void chooseFromDate() {

        int  fromDay, fromMonth, fromYear;

       final Calendar c = Calendar.getInstance();
        DatePickerDialog fromDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                initialDatelbl.setText(selectedDate);
            }

        }, fromYear = c.get(Calendar.YEAR), fromMonth= c.get(Calendar.MONTH), fromDay = c.get(Calendar.DAY_OF_MONTH));

        fromDate.show();


    }

    private void chooseToDate (){

        int  toDay, toMonth, toYear;
        final Calendar c = Calendar.getInstance();
        DatePickerDialog toDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                finalDatelbl.setText(selectedDate);
            }

        }, toYear = c.get(Calendar.YEAR), toMonth= c.get(Calendar.MONTH), toDay = c.get(Calendar.DAY_OF_MONTH));

        toDate.show();
    }

    private void sentData (){

        if (spinnerCategory.getSelectedItem().toString().trim().equals("All"))
        {
            selectedCategory ="%";
        }else {
            selectedCategory = spinnerCategory.getSelectedItem().toString();
        }
//        selectedAmount = spinnerAmount.getSelectedItem().toString();

        if(spinnerPayment.getSelectedItem().toString().trim().equals("All"))
        {
            selectedPayment = "%";
        }else {
            selectedPayment = spinnerPayment.getSelectedItem().toString();
        }

        String from = initialDatelbl.getText().toString();
        String to = finalDatelbl.getText().toString();

        //to formate the date to send to db

        SimpleDateFormat fromSpf = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat toSpf = new SimpleDateFormat("MMM dd, yyyy");
        fromSpf.setLenient(false);
        toSpf.setLenient(false);
        try {
            Date dateFrom = fromSpf.parse(from);
            Date dateTo = toSpf.parse(to);
           fromSpf = new SimpleDateFormat("yyyy-MM-dd");
           toSpf = new SimpleDateFormat("yyyy-MM-dd");
           String fromDate = fromSpf.format(dateFrom);
           String toDate = toSpf.format(dateTo);

           //sent all info to search activity

            onFilterExpense.setFilter(selectedCategory,selectedPayment,selectedAmount,fromDate,toDate, flag);

        } catch(Exception e) {
            e.printStackTrace();
            System.out.print("you get the Exception" + e);
        }

        dismissFilter();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{

            onFilterExpense = (OnFilterExpense) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "OnAttach: ClassCastException: "+e.getMessage());
        }
    }

    public void dismissFilter(){
        this.dismiss();

    }
}