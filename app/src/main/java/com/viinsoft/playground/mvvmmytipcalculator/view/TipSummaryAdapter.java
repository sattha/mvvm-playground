package com.viinsoft.playground.mvvmmytipcalculator.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.viinsoft.playground.mvvmmytipcalculator.R;
import com.viinsoft.playground.mvvmmytipcalculator.databinding.SavedTipCalculationItemBinding;
import com.viinsoft.playground.mvvmmytipcalculator.viewmodel.TipCalculationSummaryItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TipSummaryAdapter extends RecyclerView.Adapter<TipSummaryAdapter.ViewHolder> {

    interface Callback {
        void onItemSelected(TipCalculationSummaryItem item);
    }

    private final List<TipCalculationSummaryItem> items = new ArrayList<>();
    private TipSummaryAdapter.Callback callback;

    public TipSummaryAdapter(TipSummaryAdapter.Callback callback) {
        this.callback = callback;
    }

    public void updateItems(List<TipCalculationSummaryItem> updates) {
        items.clear();
        items.addAll(updates);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SavedTipCalculationItemBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.saved_tip_calculation_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private SavedTipCalculationItemBinding binding;

        ViewHolder(@NonNull SavedTipCalculationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TipCalculationSummaryItem item) {
            binding.setItem(item);
            binding.getRoot().setOnClickListener((view) -> {
                if (callback != null) {
                    callback.onItemSelected(item);
                }
            });
            binding.executePendingBindings();
        }
    }
}
