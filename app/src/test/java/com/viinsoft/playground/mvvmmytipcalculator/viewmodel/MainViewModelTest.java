package com.viinsoft.playground.mvvmmytipcalculator.viewmodel;

import android.app.Application;

import com.viinsoft.playground.mvvmmytipcalculator.R;
import com.viinsoft.playground.mvvmmytipcalculator.model.Calculator;
import com.viinsoft.playground.mvvmmytipcalculator.model.TipCalculation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    MainViewModel viewModel;

    @Mock
    Calculator mockCalculator;
    @Mock
    Application mockApp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(mockApp, mockCalculator);
    }

    private void stubResource(double given, String returnStub) {
        when(mockApp.getString(R.string.dollar_amount, given)).thenReturn(returnStub);
    }

    @Test
    public void testCalculateTip() {
        // Arrange
        viewModel.inputCheckAmount.setValue("10.00");
        viewModel.inputTipPercentage.setValue("15");
        TipCalculation stub = new TipCalculation("", 15, 10.00, 1.50, 11.50);
        when(mockCalculator.calculatorTip(10.00, 15)).thenReturn(stub);
        stubResource(10.00, "$10.00");
        stubResource(1.50, "$1.50");
        stubResource(11.50, "$11.50");

        // Act
        viewModel.calculateTip();

        // Asset
        assertEquals("$10.00", viewModel.outputCheckAmount.getValue());
        assertEquals("$1.50", viewModel.outputTipAmount.getValue());
        assertEquals("$11.50", viewModel.outputTotalDollarAmount.getValue());
    }

    @Test
    public void testCalculateTipBadTipPercentage() {
        // Arrange
        viewModel.inputCheckAmount.setValue("10.00");
        viewModel.inputTipPercentage.setValue("");

        // Act
        viewModel.calculateTip();

        // Asset
        verify(mockCalculator, never()).calculatorTip(anyDouble(), anyInt());
    }

    @Test
    public void testCalculateTipBadCheckAmount() {
        // Arrange
        viewModel.inputCheckAmount.setValue(null);
        viewModel.inputTipPercentage.setValue("");

        // Act
        viewModel.calculateTip();

        // Asset
        verify(mockCalculator, never()).calculatorTip(anyDouble(), anyInt());
    }


    @Test
    public void testSaveCurrentTip() {
        // Arrange
        TipCalculation stub = new TipCalculation("", 15, 10.00, 1.50, 11.50);
        String stubLocationName = "Green Eggs and Bacon";

        viewModel.inputCheckAmount.setValue("10.00");
        viewModel.inputTipPercentage.setValue("15");
        when(mockCalculator.calculatorTip(10.00, 15)).thenReturn(stub);

        // Act
        viewModel.calculateTip();
        viewModel.saveCurrentTip(stubLocationName);

        // Assert
        stub.setLocationName(stubLocationName);
        verify(mockCalculator).saveTipCalculation(stub);
        assertEquals(stubLocationName, viewModel.locationName.getValue());
    }
}

