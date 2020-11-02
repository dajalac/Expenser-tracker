package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracker.domain.entity.Expenser;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItensAdapter extends RecyclerView.Adapter <ItensAdapter.ViewHolderExpense> {

    private List<Expenser> data;


    public ItensAdapter (List<Expenser> data){
        this.data = data;

    }


    @NonNull
    @Override
    public ItensAdapter.ViewHolderExpense onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.new_itemlist_framgnet, parent,false);

        ViewHolderExpense viewHolderExpense = new ViewHolderExpense(view,parent.getContext());

        return viewHolderExpense;
    }

    @Override
    public void onBindViewHolder(ItensAdapter.ViewHolderExpense holder, int position) {

        /**
        if((data != null) && (data.size()>0)) {

            Expenser expen = data.get(position);

            // to format date
           SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            spf.setLenient(false);
            String dateStr = expen.itemDate;
            try {
                Date date = spf.parse(dateStr);
                spf = new SimpleDateFormat("MMM dd, yyyy");
                String dates = spf.format(date);
                holder.txtItemDateDisplay.setText("Date: "+ dates);
            } catch(Exception e) {
                e.printStackTrace();
                System.out.print("you get the Exception" + e);
            }
         **/


        if((data != null) && (data.size()>0)) {

            Expenser expen = data.get(position);

            // to format date
            SimpleDateFormat spfMonth = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat spfDay = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat spfYear = new SimpleDateFormat("yyyy-MM-dd");
            spfMonth.setLenient(false);
            spfDay.setLenient(false);
            spfYear.setLenient(false);
            String dateStr2 = expen.itemDate;
            try {
                //month
                Date dateMonth = spfMonth.parse(dateStr2);
                spfMonth = new SimpleDateFormat("MMM");
                String months = spfMonth.format(dateMonth);
                holder.txtMonth.setText(months.toUpperCase());

                // day
                Date dateDay = spfDay.parse(dateStr2);
                spfDay = new SimpleDateFormat("dd");
                String days = spfDay.format(dateDay);
                holder.txtDay.setText(days);


                // year

                Date dateYear = spfYear.parse(dateStr2);
                spfYear = new SimpleDateFormat("yyy");
                String year = spfYear.format(dateYear);
                holder.txtYear.setText(year);


            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("you get the Exception" + e);
            }


            // format value currence
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
            String currency = format.format(expen.intemValue);
            holder.txtItemValuesDispay.setText(currency);

            holder.txtItemCategoryDisplay.setText(expen.itemCategory);
            holder.txtItemPayment.setText("Payment: " + expen.itemPayment);
            holder.txtItemNote.setText("Note: "+ expen.itemNote);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class  ViewHolderExpense extends RecyclerView.ViewHolder{


        public TextView txtItemCategoryDisplay;
        public TextView txtItemValuesDispay;
        public TextView txtItemDateDisplay;
        public TextView txtItemPayment;
        public TextView txtItemNote;
        public TextView txtMonth;
        public TextView txtDay;
        public TextView txtYear;


        public ViewHolderExpense(@NonNull View itemView, final Context context) {
            super(itemView);

            txtItemCategoryDisplay = itemView.findViewById(R.id.categoryTxtView);
            txtItemValuesDispay=itemView.findViewById(R.id.valueTxtView);
            //txtItemDateDisplay= itemView.findViewById(R.id.dateTxtView);
            txtItemPayment = itemView.findViewById(R.id.paymentMethodTxtView);
            txtItemNote = itemView.findViewById(R.id.noteTxtView);
            txtMonth = itemView.findViewById(R.id.textViewForMonth);
            txtDay = itemView.findViewById(R.id.textViewForDay);
            txtYear = itemView.findViewById(R.id.textViewForYear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Bundle args = new Bundle();
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                    if(data.size()>0) {

                        Expenser expenser = data.get(getLayoutPosition());

                        EditOrDeliteActivity editOrDeleteDialog = new EditOrDeliteActivity();

                        args.putSerializable("EXPENSE SELECTED",expenser);

                        editOrDeleteDialog.setArguments(args);

                        editOrDeleteDialog.show(manager, "show edit or delete dialog");
                    }





                   /**
                   if(data.size()>0) {
                       Expenser expenser = data.get(getLayoutPosition());
                       Intent intent = new Intent(context,AddExpenseActivity.class);
                       intent.putExtra("EXPENSE SELECTED",expenser);
                       context.startActivity(intent);
                   }

                    **/

                }

            });

        }
    }
}
