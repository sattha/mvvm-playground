package com.viinsoft.playground.mvvmmytipcalculator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TipCalculationRepository {

    // In memory data source
    private Map<String, TipCalculation> savedTips = new HashMap<>();

    public void saveTipCalculation(TipCalculation tc) {
        savedTips.put(tc.getLocationName(), tc);
    }

    public TipCalculation loadTipCalulationByName(String locationName) {
        return savedTips.get(locationName);
    }

    public LiveData<List<TipCalculation>> loadSavedTipCalculations() {
        MutableLiveData<List<TipCalculation>> liveData = new MutableLiveData<>();
        liveData.setValue(new ArrayList<>(savedTips.values()));
        return liveData;
    }
}
