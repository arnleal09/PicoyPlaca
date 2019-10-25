package com.innovatechmobile.picoyplaca.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovatechmobile.picoyplaca.db.entity.Log;
import com.innovatechmobile.picoyplaca.R;
import com.innovatechmobile.picoyplaca.databinding.LogItemBinding;


import java.util.ArrayList;
import java.util.List;

public class AdapterLog extends RecyclerView.Adapter<AdapterLog.MyViewHolder> {
    private List<Log> mDataset = new ArrayList<>();


    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private final LogItemBinding binding;

        MyViewHolder(LogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // will be used for item click event
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterLog() {
        //no need to implement
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AdapterLog.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LogItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.log_item, parent, false);
        return new AdapterLog.MyViewHolder(binding);
    }

    // set DataSourcer
    public void setWords(List<Log> logs) {
        mDataset = logs;
        notifyDataSetChanged();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull AdapterLog.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that elements
        holder.binding.tVConsult.setText(holder.itemView.getResources().getString(R.string.number, mDataset.get(position).id));
        holder.binding.tVLogHour.setText(holder.itemView.getResources().getString(R.string.hour_of_register, mDataset.get(position).getDateLog()));
        holder.binding.tVLogPlate.setText(holder.itemView.getResources().getString(R.string.plate, mDataset.get(position).getPlate()));
        holder.binding.tVResult.setText(holder.itemView.getResources().getString(R.string.result, mDataset.get(position).getResult()));
        if (!mDataset.get(position).getResult().equals(holder.itemView.getResources().getString(R.string.circulation))) {
            holder.binding.tVResult.setTextColor(Color.RED);
        } else {
            holder.binding.tVResult.setTextColor(Color.BLACK);
        }
        holder.binding.tVHourConsulted.setText(holder.itemView.getResources().getString(R.string.hour_consulted, mDataset.get(position).getDateConsulted()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnItemClickListener {
    }

}
