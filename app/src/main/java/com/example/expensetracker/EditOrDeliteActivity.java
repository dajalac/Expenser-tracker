package com.example.expensetracker;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.expensetracker.database.DadosOpenHelper;
import com.example.expensetracker.domain.entity.Expenser;
import com.example.expensetracker.domain.repository.ExpenseRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class EditOrDeliteActivity extends DialogFragment {

    //widgets
    private Button deletebtn, editBtn,saveEditBtn, cancelEditBtn;
    private EditText expenseValue;
    private EditText exepenseNote;
    private TextView currentDate, categorylbl, dateblbl, selectPaymentlbl, amountlbl, notelbl;
    private ImageButton selectDate;
    private Spinner spinnerEditCategory, spinnerEditPayment;

    //variable
    int itemId;
    Expenser expenser;
    private String selectedDate, noteItem, categoryItem, paymentItem;
    public double valueItem;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edite_or_delete, container);

        createWidgets();
        verifyParameters();
        createSpinners();


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeLayout();

            }

        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmDelete();
                deleteData();
            }
        });
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewValues();
                saveData();
                dismiss();
            }
        });

        cancelEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });


        return view;

    }

    private void createWidgets() {

        currentDate = view.findViewById(R.id.calendarTextView);

        categorylbl = view.findViewById(R.id.textViewCategory);
        dateblbl = view.findViewById(R.id.textViewDatelbl);
        selectPaymentlbl = view.findViewById(R.id.textViewPayMethodlbl);
        amountlbl = view.findViewById(R.id.textViewAmountlbl);
        notelbl = view.findViewById(R.id.textViewNotelbl);

        expenseValue = view.findViewById(R.id.valueEditTxt);
        exepenseNote = view.findViewById(R.id.itemNameEditTxt);

        //buttons
        deletebtn = view.findViewById(R.id.deleteBtn);
        editBtn = view.findViewById(R.id.editBtn);
        selectDate = view.findViewById(R.id.imageButton);
        cancelEditBtn = view.findViewById(R.id.cancelEdit);
        saveEditBtn=view.findViewById(R.id.saveEdit);


        /**
        spinnerEditCategory = view.findViewById(R.id.spinnerEditCategories);
        ArrayAdapter<CharSequence> adapterAmount = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerCategories, android.R.layout.simple_spinner_item);
        adapterAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditCategory.setAdapter(adapterAmount);

         spinnerEditCategory = view.findViewById(R.id.spinnerEditCategories);
         ArrayAdapter<String> adapterCategory = new ArrayAdapter<> (getActivity(),android.R.layout.simple_spinner_item);
         adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinnerEditCategory.setAdapter(adapterCategory);

        spinnerEditPayment = view.findViewById(R.id.spinnerEditPayment);
        ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerPayment, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditPayment.setAdapter(adapterPayment);
         **/

    }


    private void verifyParameters() {


        Bundle bundle = getArguments();
        expenser = new Expenser();

        // getActivity().getIntent().getExtras();

        if (bundle != null && (bundle.containsKey("EXPENSE SELECTED"))) {

            expenser = (Expenser) bundle.getSerializable("EXPENSE SELECTED");

            // get Item id
            itemId = expenser.id;

            //showing the selected item components to the screen

            // first, formating the date to show in the textview
            String dates = expenser.itemDate;
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            spf.setLenient(false);
            try {
                Date date = spf.parse(dates);
                spf = new SimpleDateFormat("MMM dd, yyyy");
                String datesformated = spf.format(date);
                currentDate.setText(datesformated);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("you get the Exception" + e);
            }

            //selectPaymentlbl.setText("Payment: " + expenser.itemPayment);
           // categorylbl.setText("Category: " + expenser.itemCategory);
            expenseValue.setText(toString().valueOf(expenser.intemValue));
            exepenseNote.setText(expenser.itemNote);


        }
    }

    private void changeLayout() {

        // set spinners visible
        spinnerEditPayment.setEnabled(true);
        spinnerEditCategory.setEnabled(true);

        expenseValue.setEnabled(true);
        exepenseNote.setEnabled(true);


        // change buttons visibility
        deletebtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);

        cancelEditBtn.setVisibility(View.VISIBLE);
        saveEditBtn.setVisibility(View.VISIBLE);



    }

    private void getNewValues() {
        noteItem = exepenseNote.getText().toString();
        valueItem = Double.parseDouble(expenseValue.getText().toString());
        categoryItem = spinnerEditCategory.getSelectedItem().toString();
        paymentItem = spinnerEditPayment.getSelectedItem().toString();


        expenser.itemNote = noteItem;
        expenser.intemValue = valueItem;
        expenser.itemCategory = categoryItem;
        expenser.itemPayment = paymentItem;


        // formating the date to sent do db
        String dates = currentDate.getText().toString();
        SimpleDateFormat spf = new SimpleDateFormat("MMM dd, yyyy");
        spf.setLenient(false);
        try {
            Date date = spf.parse(dates);
            spf = new SimpleDateFormat("yyyy-MM-dd");
            String datesformated = spf.format(date);
            expenser.itemDate = datesformated;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("you get the Exception" + e);
        }

    }

    private void chooseDate() {
        int  day, month, year;

        final Calendar c = Calendar.getInstance();
        DatePickerDialog changeDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                currentDate.setText(selectedDate);
            }

        }, year = c.get(Calendar.YEAR), month= c.get(Calendar.MONTH), day = c.get(Calendar.DAY_OF_MONTH));

        changeDate.show();
    }

    private void saveData(){
      // DadosOpenHelper dateOpenHelper = new DadosOpenHelper(getContext());
       ExpenseRepository expenseRepository = new ExpenseRepository(getContext());

        // send to database
            if(expenser.id!=0) {
                expenseRepository.update(expenser);
                dismissDialog();
                 Intent intent = new Intent(getActivity(), SearchActivity.class);
                 startActivity(intent);

            }

    }

    private void createSpinners(){

        final List<String> list = new ArrayList<>();
        list.add(expenser.itemCategory);
        list.add("Beauty");
        list.add("Bills");
        list.add("Clothing");
        list.add("Education");
        list.add("Electronics");
        list.add("Gift");
        list.add("Grocery");
        list.add("Health");
        list.add("Insurance");
        list.add("Kids");
        list.add("Laundry");
        list.add("Pet");
        list.add("Public\nTransportation");
        list.add("Recreation");
        list.add("Rent");
        list.add("Restaurant");
        list.add("Social");
        list.add("Sports");
        list.add("Tax");
        list.add("Utilities");
        list.add("Vehicle");
        list.add("Others");

        /**
        // remove duplicates
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(list);
        list.clear();
        list.addAll(hashSet);
         **/


        spinnerEditCategory = view.findViewById(R.id.spinnerEditCategory);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<> (getContext(),android.R.layout.simple_spinner_item,list);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditCategory.setAdapter(adapterCategory);
        spinnerEditCategory.setEnabled(false);


        final List<String> listPayment = new ArrayList<>();
        listPayment.add(expenser.itemPayment);
        listPayment.add("Cash");
        listPayment.add("Cheque");
        listPayment.add("Credit\nCard");
        listPayment.add("Coupons");
        listPayment.add("Debit\nCard");
        listPayment.add("Others");


        /**
        // remove duplicates
        HashSet<String> hashSetPayment = new HashSet<>();
        hashSetPayment.addAll(listPayment);
        listPayment.clear();
        listPayment.addAll(hashSet);
         **/

        spinnerEditPayment = view.findViewById(R.id.spinnerEditPayment);
        ArrayAdapter<String> adapterPayment =new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,listPayment);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditPayment.setAdapter(adapterPayment);
        spinnerEditPayment.setEnabled(false);



        }

    private void dismissDialog(){

        this.dismiss();

    }

    private void deleteData(){
        ExpenseRepository expenseRepository = new ExpenseRepository(getContext());

        // send to database
        if(expenser.id!=0) {
            expenseRepository.exclude(expenser.id);
        }

    }

    private void confirmDelete(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Confirm item exclusion ");
        dialog.setMessage("Are you sure do you \nwant to delete this item?");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData();
                dismissDialog();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);


            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

