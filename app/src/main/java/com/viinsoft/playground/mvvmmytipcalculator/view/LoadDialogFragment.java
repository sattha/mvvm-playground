package com.viinsoft.playground.mvvmmytipcalculator.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.viinsoft.playground.mvvmmytipcalculator.R;
import com.viinsoft.playground.mvvmmytipcalculator.viewmodel.MainViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class LoadDialogFragment extends DialogFragment {

    interface Callback {
        void onTipSelected(String name);
    }

    private LoadDialogFragment.Callback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoadDialogFragment.Callback) {
            callback = (Callback) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Context context = getContext();

        if (context == null) {
            return super.onCreateDialog(savedInstanceState);
        }

        return new AlertDialog.Builder(context)
                .setView(createView(context))
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    private View createView(Context context) {

        RecyclerView rv = LayoutInflater
                .from(context)
                .inflate(R.layout.saved_tip_calculation_list, null)
                .findViewById(R.id.recycleSavedCalculation);

        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        TipSummaryAdapter adapter = new TipSummaryAdapter(item -> {
            callback.onTipSelected(item.getLocationName());
            dismiss();
        });

        rv.setAdapter(adapter);

        // get data from view model
        MainViewModel viewModel =  ViewModelProviders
                .of(Objects.requireNonNull(getActivity()))
                .get(MainViewModel.class);

        viewModel.loadSaveTipCalculationsSummary().observe(this, adapter::updateItems);
        return rv;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
