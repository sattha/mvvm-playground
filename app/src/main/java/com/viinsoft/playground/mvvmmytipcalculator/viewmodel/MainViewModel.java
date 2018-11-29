package com.viinsoft.playground.mvvmmytipcalculator.viewmodel;

import android.app.Application;

import com.viinsoft.playground.mvvmmytipcalculator.R;
import com.viinsoft.playground.mvvmmytipcalculator.model.Calculator;
import com.viinsoft.playground.mvvmmytipcalculator.model.TipCalculation;
import com.viinsoft.playground.mvvmmytipcalculator.model.TipCalculationRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getName();

    private final Application app;
    private TipCalculation lastTipCalculation;
    private Calculator calculator;

    public MutableLiveData<String> inputCheckAmount = new MutableLiveData<>();
    public MutableLiveData<String> inputTipPercentage = new MutableLiveData<>();
    public MutableLiveData<String> outputCheckAmount = new MutableLiveData<>();
    public MutableLiveData<String> outputTipAmount = new MutableLiveData<>();
    public MutableLiveData<String> outputTotalDollarAmount = new MutableLiveData<>();
    public MutableLiveData<String> locationName = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
        init();
    }

    public MainViewModel(@NonNull Application app, @NonNull Calculator calculator) {
        super(app);
        this.app = app;
        this.calculator = calculator;
        init();
    }

    private void init() {
        if (calculator == null) {
            calculator = new Calculator(new TipCalculationRepository());
        }
        updateOutput(new TipCalculation("", 0, 0, 0, 0));
    }

    private void updateOutput(final TipCalculation tc) {
        lastTipCalculation = tc;
        outputCheckAmount.postValue(app.getString(R.string.dollar_amount, tc.getCheckAmount()));
        outputTipAmount.postValue(app.getString(R.string.dollar_amount, tc.getTipAmount()));
        outputTotalDollarAmount.postValue(app.getString(R.string.dollar_amount, tc.getTotalAmount()));
        locationName.postValue(tc.getLocationName());
    }

    public void calculateTip()  {

        Double checkAmount = parseDoubleOrNull(inputCheckAmount.getValue());
        Integer tipPercentage = parseIntegerOrNull(inputTipPercentage.getValue());

        if (checkAmount == null || tipPercentage == null) {
            return;
        }

        updateOutput(calculator.calculatorTip(checkAmount, tipPercentage));
    }

    public void saveCurrentTip(String name) {
        lastTipCalculation.setLocationName(name);
        calculator.saveTipCalculation(lastTipCalculation);
        updateOutput(lastTipCalculation);
    }

    public LiveData<List<TipCalculationSummaryItem>> loadSaveTipCalculationsSummary() {
        return Transformations.map(calculator.loadSavedTipCalculations(), input -> {

            List<TipCalculationSummaryItem> outputs = new ArrayList<>();

            for (TipCalculation item : input) {
                outputs.add(new TipCalculationSummaryItem(
                        item.getLocationName(),
                        getApplication().getString(R.string.dollar_amount, item.getTotalAmount())));
            }

            return outputs;
        });
    }

    public void loadTipCalculation(String name) {
        TipCalculation tc = calculator.loadTipCalculationByName(name);

        if (tc == null)
            return;

        inputCheckAmount.postValue(tc.getCheckAmount() + "");
        inputTipPercentage.postValue(tc.getTipPct() + "");

        updateOutput(tc);
    }

    private Double parseDoubleOrNull(String value) {

        if (value == null) {
            return null;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    private Integer parseIntegerOrNull(String value) {

        if (value == null) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
