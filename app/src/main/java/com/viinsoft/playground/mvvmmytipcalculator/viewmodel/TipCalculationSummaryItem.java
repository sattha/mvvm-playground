package com.viinsoft.playground.mvvmmytipcalculator.viewmodel;

public class TipCalculationSummaryItem {

    private String locationName;
    private String totalDollarAmount;

    public TipCalculationSummaryItem(String locationName, String totalDollarAmount) {
        this.locationName = locationName;
        this.totalDollarAmount = totalDollarAmount;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTotalDollarAmount() {
        return totalDollarAmount;
    }

    public void setTotalDollarAmount(String totalDollarAmount) {
        this.totalDollarAmount = totalDollarAmount;
    }
}
