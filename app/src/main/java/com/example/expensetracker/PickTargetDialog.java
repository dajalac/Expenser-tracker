package com.example.expensetracker;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.expensetracker.domain.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;

public class PickTargetDialog extends DialogFragment {


    //interface to sent data to searchActivity
    public interface OnFilterTarget{
        void setTarget(String category, double amount);
    }

    public OnFilterTarget onFilterTarget;

    //tags
    private static final String TAG = "target ";




    // widgets
    EditText setTarget;
    Spinner spinnerCategory;
    Button done;


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_set_new_target, container);


        setTarget = view.findViewById(R.id.setTargetEditTxt);
        done=view.findViewById(R.id.targetSet);

        spinnerCategory=view.findViewById(R.id.spinnerTargetCategory);
        ArrayAdapter<CharSequence> adapterAmount = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerCategories, android.R.layout.simple_spinner_item);
        adapterAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterAmount);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return view;
    }

    public void save (){


        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        double targetValue = Double.parseDouble(setTarget.getText().toString());

     ///////////
/**
        ExpenseRepository repository = new ExpenseRepository(getActivity());

        double temporary = (double) repository.category(selectedCategory);

        double expensePercent = temporary * 100 / targetValue ;
        int expenseInteger = (int) expensePercent;


        List<TargetData> data = new ArrayList<>();

        data.add(new TargetData(selectedCategory, targetValue , repository.category(selectedCategory),expenseInteger));

 **/
        //send to activity setTargetActivity

     try{
        onFilterTarget.setTarget(selectedCategory,targetValue);

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

            onFilterTarget = (OnFilterTarget) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "OnAttach: ClassCastException: "+e.getMessage());
        }
    }


    public void dismissFilter() {
        this.dismiss();

    }


}


