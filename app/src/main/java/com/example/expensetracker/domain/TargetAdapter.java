//package com.example.expensetracker.domain;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.example.expensetracker.EditOrDeliteActivity;
//import com.example.expensetracker.ItensAdapter;
//import com.example.expensetracker.R;
//import com.example.expensetracker.TargetData;
//import com.example.expensetracker.domain.entity.Expenser;
//import com.example.expensetracker.domain.repository.ExpenseRepository;
//
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class TargetAdapter extends RecyclerView.Adapter <TargetAdapter.ViewHolderTarget> {
//
//   // ExpenseRepository expenseRepository;
//   // String category;
//   // double amount;
//   // int count;
//
//    List<TargetData>list;
//
//    public TargetAdapter (List<TargetData>list){
//        this.list = list;
//}
//
//
//    @NonNull
//    @Override
//    public TargetAdapter.ViewHolderTarget onCreateViewHolder( ViewGroup parent, int viewType) {
//
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//
//        View view = layoutInflater.inflate(R.layout.new_target_category, parent,false);
//
//        TargetAdapter.ViewHolderTarget viewHolderTarget = new TargetAdapter.ViewHolderTarget(view,parent.getContext());
//
//        return viewHolderTarget;
//    }
//
//    @Override
//    public void onBindViewHolder(TargetAdapter.ViewHolderTarget holder, int position) {
//
//
//            // format value currence
//            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
//            String currency = format.format(list.get(position).targetValue);
//            holder.targetValueLbl.setText("Target: " + currency);
//
//            String currency2 = format.format(list.get(position).targetExpenseSum);
//            holder.expendedlbl.setText("Expended:" + currency2);
//
//
//            holder.categoryChooseLbl.setText(list.get(position).targetCategory);
//
//            holder.progressBar.setMax(100);
//            holder.progressBar.setProgress(list.get(position).expensePercent);
//
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if(list !=null) {
//            return list.size();
//        }
//        else{
//            return 0;
//        }
//    }
//
//    public class ViewHolderTarget extends RecyclerView.ViewHolder{
//
//
//        public TextView categoryChooseLbl;
//        public TextView targetValueLbl;
//        public  TextView expendedlbl;
//        public ProgressBar progressBar;
//
//
//        public ViewHolderTarget(@NonNull View itemView, final Context context) {
//            super(itemView);
//
//            categoryChooseLbl = itemView.findViewById(R.id.categoryName);
//            targetValueLbl = itemView.findViewById(R.id.target);
//            progressBar= itemView.findViewById(R.id.progress_horizontal);
//            expendedlbl = itemView.findViewById(R.id.expended);
//
//
//        }
//    }
//}




