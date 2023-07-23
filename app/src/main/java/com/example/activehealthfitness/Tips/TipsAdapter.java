package com.example.activehealthfitness.Tips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activehealthfitness.R;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.viewHolder> {

    ArrayList<TipsModel> list;
    Context context;

    public TipsAdapter(ArrayList<TipsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tips_layout,parent,false);
        return new viewHolder(view);
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textViewTipNo,textViewTipName,textViewTipDescription;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTipNo=itemView.findViewById(R.id.tip_no);
            textViewTipName=itemView.findViewById(R.id.tip_name);
            textViewTipDescription=itemView.findViewById(R.id.tip_description);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        TipsModel model=list.get(position);
        holder.textViewTipNo.setText(model.getTipNo());
        holder.textViewTipName.setText(model.getTipName());
        holder.textViewTipDescription.setText(model.getTipDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
