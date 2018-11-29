package com.viinsoft.playground.mvvmmytipcalculator.model;

import java.util.Arrays;

public class TipCalculation {
    private String locationName;
    private int tipPct;
    private double checkAmount;
    private double tipAmount;
    private double totalAmount;

    public TipCalculation(String locationName, int tipPct, double checkAmount, double tipAmount, double totalAmount) {
        this.locationName = locationName;
        this.tipPct = tipPct;
        this.checkAmount = checkAmount;
        this.tipAmount = tipAmount;
        this.totalAmount = totalAmount;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getTipPct() {
        return tipPct;
    }

    public double getCheckAmount() {
        return checkAmount;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipCalculation that = (TipCalculation) o;
        return tipPct == that.tipPct &&
                Double.compare(that.checkAmount, checkAmount) == 0 &&
                Double.compare(that.tipAmount, tipAmount) == 0 &&
                Double.compare(that.totalAmount, totalAmount) == 0 &&
                equals(locationName, that.locationName);
    }


    @Override
    public int hashCode() {
        return hashCode(locationName, tipPct, checkAmount, tipAmount, totalAmount);
    }

    private boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private int hashCode(Object... a) {
        return Arrays.hashCode(a);
    }
}
