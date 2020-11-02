package com.example.expensetracker.domain.entity;

import java.io.Serializable;

public class Expenser implements Serializable {

    public int id;
    public String itemNote;
    public double intemValue;
    public String itemDate;
    public String itemCategory;
    public String itemPayment;

    public Expenser(){
        id = 0;
    }
}
