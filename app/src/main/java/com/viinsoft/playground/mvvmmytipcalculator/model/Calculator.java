package com.viinsoft.playground.mvvmmytipcalculator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import androidx.lifecycle.LiveData;

public class Calculator {

    private TipCalculationRepository repository;

    public Calculator(TipCalculationRepository repository) {
        this.repository = repository;
        init();
    }

    private void init() {
        if (repository == null) {
            repository = new TipCalculationRepository();
        }
    }

    public TipCalculation calculatorTip(Double checkAmount, int tipPct) {
        double tipAmount = checkAmount * (tipPct / 100.0);
        double roundedTipAmount = new BigDecimal(tipAmount)
                .setScale(3, RoundingMode.HALF_UP).doubleValue();

        double totalAmount = checkAmount + roundedTipAmount;
        return new TipCalculation("", tipPct, checkAmount, roundedTipAmount, totalAmount);
    }

    public void saveTipCalculation(TipCalculation tc) {
        repository.saveTipCalculation(tc);
    }

    public TipCalculation loadTipCalculationByName(String locationName) {
        return repository.loadTipCalulationByName(locationName);
    }

    public LiveData<List<TipCalculation>> loadSavedTipCalculations() {
        return repository.loadSavedTipCalculations();
    }
}
