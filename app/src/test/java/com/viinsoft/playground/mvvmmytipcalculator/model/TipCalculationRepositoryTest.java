package com.viinsoft.playground.mvvmmytipcalculator.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import static org.junit.Assert.assertEquals;

public class TipCalculationRepositoryTest {

    private TipCalculationRepository repository;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        repository = new TipCalculationRepository();
    }

    @Test
    public void testSaveTip() {
        TipCalculation tip = new TipCalculation("Pancake Paradise",
                100, 25,
                25.0, 125.0);

        repository.saveTipCalculation(tip);

        assertEquals(tip, repository.loadTipCalulationByName(tip.getLocationName()));
    }

    @Test
    public void testLoadSavedTipCalculation() {
        // Arrange
        TipCalculation tip1 = new TipCalculation("Pancake Paradise",
                100, 25,
                25.0, 125.0);

        TipCalculation tip2 = new TipCalculation("Veggie Sensation",
                100, 25,
                25.0, 125.0);

        // Action
        repository.saveTipCalculation(tip1);
        repository.saveTipCalculation(tip2);

        // Assert
        repository.loadSavedTipCalculations().observeForever(new Observer<List<TipCalculation>>() {
            @Override
            public void onChanged(List<TipCalculation> tipCalculations) {
                assertEquals(2, tipCalculations.size());
            }
        });
    }
}
