package com.viinsoft.playground.mvvmmytipcalculator.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.viinsoft.playground.mvvmmytipcalculator.R;
import com.viinsoft.playground.mvvmmytipcalculator.databinding.ActivityMainBinding;
import com.viinsoft.playground.mvvmmytipcalculator.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements SaveDialogFragment.Callback, LoadDialogFragment.Callback {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(ViewModelProviders.of(this).get(MainViewModel.class));
        binding.setLifecycleOwner(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id ) {
            case R.id.action_saved: {
                showSaveDialog();
                return true;
            }
            case R.id.action_load: {
                showLoadDialog();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLoadDialog() {
        LoadDialogFragment dialog = new LoadDialogFragment();
        dialog.show(getSupportFragmentManager(), "LoadDialog");
    }

    private void showSaveDialog() {
        SaveDialogFragment dialog = new SaveDialogFragment();
        dialog.show(getSupportFragmentManager(), "SaveDialog");
    }

    @Override
    public void onSaveTip(String name) {
        binding.getVm().saveCurrentTip(name);
        String msg = getString(R.string.saved_name, name);
        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onTipSelected(String name) {
        binding.getVm().loadTipCalculation(name);
        String msg = getString(R.string.loaded_name, name);
        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }
}
