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
import android.widget.ImageButton;
import android.widget.TextView;

public class PaymentDialog extends DialogFragment  {

    //interface to send to addExpenseActivity
    public interface OnSelectPayment{
        void sentPayment(String paymentMethod);
    }
    public OnSelectPayment onSelectPayment;

    // tags
    private static final String TAG = "PaymentDialog";

    // widgets
    ImageButton cashBtn, chequeBtn, couponsBtn, creditCardBtn, debitCardbtn, otherPayment;
    TextView cashTxtView, chequeTxtView, couponsTxtView, creditCartTextView, debitCardTextView,
            otherPymentTxtView;

    //variables

    private String paymentType;

    // view
    View view;

    public PaymentDialog(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_payment_type, container);

        createVariable();
        buttonsActions();


        return view;

    }
    private void createVariable (){
        cashBtn = view.findViewById(R.id.ImageButtonCash);
        chequeBtn= view.findViewById(R.id.ImageButtonCheque);
        couponsBtn = view.findViewById(R.id.ImageButtonCoupons);
        creditCardBtn = view.findViewById(R.id.ImageButtonCreditCard);
        debitCardbtn = view.findViewById(R.id.ImageButtonDebitCard);
        otherPayment = view.findViewById(R.id.ImageButtonOtherPayments);

        cashTxtView = view.findViewById(R.id.TextViewCash);
        chequeTxtView = view.findViewById(R.id.TextViewCheque);
        couponsTxtView= view.findViewById(R.id.TextViewCoupons);
        creditCartTextView = view.findViewById(R.id.TextViewCreditCard);
        debitCardTextView = view.findViewById(R.id.TextViewDebitCard);
        otherPymentTxtView = view.findViewById(R.id.TextViewOthersPayment);


    }
    private void buttonsActions(){
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentType = cashTxtView.getText().toString();
                sendData();

            }
        });

        chequeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = chequeTxtView.getText().toString();
                sendData();
            }
        });
        creditCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = creditCartTextView.getText().toString();
                paymentType.replace("\n"," ");
                sendData();
            }
        });

        debitCardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = debitCardTextView.getText().toString();
                paymentType.replace("\n"," ");
                sendData();
            }
        });
        otherPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = otherPymentTxtView.getText().toString();
                sendData();
            }
        });
        couponsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = couponsTxtView.getText().toString();
                sendData();
            }
        });

    }

    private void sendData(){

        onSelectPayment.sentPayment(paymentType);
        this.dismiss();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{

            onSelectPayment = (OnSelectPayment) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "OnAttach: ClassCastException: "+e.getMessage());
        }
    }


}

