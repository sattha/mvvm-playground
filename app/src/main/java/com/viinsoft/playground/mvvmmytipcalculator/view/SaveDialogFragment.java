package com.viinsoft.playground.mvvmmytipcalculator.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.viinsoft.playground.mvvmmytipcalculator.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SaveDialogFragment extends DialogFragment {

    interface Callback {
        void onSaveTip(String name);
    }

    private static final int viewId = View.generateViewId();
    private SaveDialogFragment.Callback saveTipCallback = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SaveDialogFragment.Callback) {
            saveTipCallback = (Callback) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Context context = getContext();

        if (context == null)
            return super.onCreateDialog(savedInstanceState);

        EditText editText = new EditText(getContext());
        editText.setId(viewId);
        editText.setHint(getString(R.string.save_hint));

        return new AlertDialog.Builder(getContext())
                .setView(editText)
                .setPositiveButton(R.string.action_save, (dialog, which) -> onSave(editText))
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        saveTipCallback = null;
    }

    private void onSave(EditText editText) {
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            saveTipCallback.onSaveTip(text);
        }
    }
}
