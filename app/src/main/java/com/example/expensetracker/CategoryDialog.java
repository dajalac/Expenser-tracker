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


public class CategoryDialog extends DialogFragment  {

    private static final String TAG = "CategoryDialog";

    // interface to send data to activity
    public interface OnSelectedCategory{
        void sentCategory(String selectedCategory);
    }

    public OnSelectedCategory onSelectedCategory;

    // widgets
    ImageButton beautyBtn, billsBtn, clothingBtn,educationBtn, entertainmentBtn,electronicsBtn, giftBtn,
            groceryBtn,healthBtn, houseBtn, insuranceBtn, kidsBtn, laundryBtn, petBtn,publicTransportBtn,
            rentBtn, restaurantBtn, socialBtn, sportBtn, taxBtn, travelBtn, utilitiesBtn,
            vehicleBtn, othersBtn;

    TextView  beautyTxtView, billsTxtView, clothingTxtView, educationTxtView, entertainmentTxtView,
            electronicsTxtView,
            gifitTxtView, groceryTxtView, healthTxtView, houseTxtView, insuranceTxtView, kidsTxtView,
            loundryTxtView, petTxtView, publicTransportTxtView, rentTxtView, restaurantTxtView,
            socialTxtView, sportTxtView, taxTxtView, travelTxtView, utilitiesTxtView, vehicleTxtView,
            othersTxtView;

    private String category;

    View view;

    public CategoryDialog (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_categories, container);

        createVariables();

        buttonsActions ();


        return view;



    }



    public void createVariables(){
        beautyBtn = view.findViewById(R.id.imageButtonBeauty);
        billsBtn= view.findViewById(R.id.imageButtonBills);
        clothingBtn = view .findViewById(R.id.imageButtonClothing);
        educationBtn = view.findViewById(R.id.imageButtonEducation);
        entertainmentBtn = view.findViewById(R.id.imageButtonEntertainment);
        electronicsBtn = view.findViewById(R.id.imageButtonEletronics);
        giftBtn = view.findViewById(R.id.imageButtonGift);
        groceryBtn =view.findViewById(R.id.imageButtonGrocery);
        healthBtn =view.findViewById(R.id.imageButtonHealth);
        houseBtn =view.findViewById(R.id.imageButtonHome);
        insuranceBtn =view.findViewById(R.id.imageButtonInsurance);
        kidsBtn =view.findViewById(R.id.imageButtonKids);
        laundryBtn =view.findViewById(R.id.imageButtonLaundry);
        petBtn =view.findViewById(R.id.imageButtonPet);
        publicTransportBtn =view.findViewById(R.id.imageButtonPublicTransport);
        rentBtn = view.findViewById(R.id.imageButtonRenting);
        restaurantBtn =view.findViewById(R.id.imageButtonRestaurant);
        socialBtn =view.findViewById(R.id.imageButtonSocial);
        sportBtn =view.findViewById(R.id.imageButtonSports);
        taxBtn =view.findViewById(R.id.imageButtonTax);
        travelBtn =view.findViewById(R.id.imageButtonTravel);
        utilitiesBtn =view.findViewById(R.id.imageButtonUtilities);
        vehicleBtn =view.findViewById(R.id.imageButtonVehicle);
        othersBtn =view.findViewById(R.id.imageButtonOthers);


        beautyTxtView =view.findViewById(R.id.TextViewBeauty);
        billsTxtView =view.findViewById(R.id.TextViewBills);
        clothingTxtView =view.findViewById(R.id.TextViewClothing);
        educationTxtView=view.findViewById(R.id.TextViewEducation);
        entertainmentTxtView =view.findViewById(R.id.TextViewEntertainment);
        electronicsTxtView =view.findViewById(R.id.TextViewEletronics);
        gifitTxtView =view.findViewById(R.id.TextViewGift);
        groceryTxtView =view.findViewById(R.id.TextViewGrocery);
        healthTxtView =view.findViewById(R.id.TextViewHealth);
        houseTxtView =view.findViewById(R.id.TextViewHouse);
        insuranceTxtView =view.findViewById(R.id.TextViewInsurance);
        kidsTxtView =view.findViewById(R.id.TextViewKids);
        loundryTxtView =view.findViewById(R.id.TextViewLaundry);
        petTxtView =view.findViewById(R.id.TextViewPet);
        publicTransportTxtView =view.findViewById(R.id.TextViewPublicTransport);
        rentTxtView =view.findViewById(R.id.TextViewRent);
        restaurantTxtView =view.findViewById(R.id.TextViewRestaurant);
        socialTxtView =view.findViewById(R.id.TextViewSocial);
        sportTxtView =view.findViewById(R.id.TextViewSports);
        taxTxtView =view.findViewById(R.id.TextViewTax);
        travelTxtView =view.findViewById(R.id.TextViewTravel);
        utilitiesTxtView =view.findViewById(R.id.TextViewUtilities);
        vehicleTxtView =view.findViewById(R.id.TextViewVehicle);
        othersTxtView =view.findViewById(R.id.TextViewOthers);
    }


    private void buttonsActions (){
        beautyBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = beautyTxtView.getText().toString();
                sendData();

            }
        });
        billsBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = billsTxtView.getText().toString();
                sendData();

            }
        });

        clothingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = clothingTxtView.getText().toString();
                sendData();

            }
        });

        educationBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = educationTxtView.getText().toString();
                sendData();

            }
        });

        entertainmentBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = entertainmentTxtView.getText().toString();
                sendData();

            }
        });

        electronicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = electronicsTxtView.getText().toString();
                sendData();

            }
        });
        giftBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = gifitTxtView.getText().toString();
                sendData();

            }
        });

        groceryBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = groceryTxtView.getText().toString();
                sendData();

            }
        });

        healthBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = healthTxtView.getText().toString();
                sendData();

            }
        });

        houseBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = houseTxtView.getText().toString();
                sendData();

            }
        });

        insuranceBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = insuranceTxtView.getText().toString();
                sendData();

            }
        });

        kidsBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = kidsTxtView.getText().toString();
                sendData();

            }
        });

        laundryBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = healthTxtView.getText().toString();
                sendData();

            }
        });

        petBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = petTxtView.getText().toString();
                sendData();

            }
        });
        publicTransportBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = publicTransportTxtView.getText().toString();
                category.replace("\n"," ");
                sendData();

            }
        });
        rentBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = rentTxtView.getText().toString();
                sendData();

            }
        });
        restaurantBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = restaurantTxtView.getText().toString();
                category.replace("-","");
                sendData();

            }
        });

        socialBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = socialTxtView.getText().toString();
                sendData();

            }
        });
        sportBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = sportTxtView.getText().toString();
                sendData();

            }
        });
        taxBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = taxTxtView.getText().toString();
                sendData();

            }
        });
        travelBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = travelTxtView.getText().toString();
                sendData();

            }
        });
        utilitiesBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = utilitiesTxtView.getText().toString();
                sendData();

            }
        });
        vehicleBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = vehicleTxtView.getText().toString();
                sendData();

            }
        });
        othersBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = othersTxtView.getText().toString();
                sendData();

            }
        });

    }


    public String getCategory () {

        return category;
    }

    public void sendData() {

        onSelectedCategory.sentCategory(category);

        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{

            onSelectedCategory = (OnSelectedCategory)getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "OnAttach: ClassCastException: "+e.getMessage());
        }
    }
}
