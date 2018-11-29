package com.viinsoft.playground.mvvmmytipcalculator.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator(new TipCalculationRepository());
    }

    @Test
    public void testCalculateTips() {
        // Arrange
        TipCalculation expc1 = new TipCalculation("", 15, 10, 1.50, 11.50);
        TipCalculation expc2 = new TipCalculation("", 18, 10, 1.80, 11.80);
        TipCalculation expc3 = new TipCalculation("", 20, 10, 2.00, 12.00);

        // Act
        TipCalculation actual1 = calculator.calculatorTip(expc1.getCheckAmount(), expc1.getTipPct());
        TipCalculation actual2 = calculator.calculatorTip(expc2.getCheckAmount(), expc2.getTipPct());
        TipCalculation actual3 = calculator.calculatorTip(expc3.getCheckAmount(), expc3.getTipPct());

        // Assert
        assertEquals(expc1, actual1);
        assertEquals(expc2, actual2);
        assertEquals(expc3, actual3);
    }
}