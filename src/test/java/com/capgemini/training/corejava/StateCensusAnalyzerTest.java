package com.capgemini.training.corejava;

import org.junit.Test;

import org.junit.Assert;
import org.junit.Before;

public class StateCensusAnalyzerTest {
    public static final String FILE_PATH = "src/test/resources/IndianStateCensusData.csv";
    public StateCensusAnalyzer censusAnalyzer;

    @Before
    public void init() {
        censusAnalyzer = new StateCensusAnalyzer();
    }

    @Test
    public void countEntriesCheck() {
        censusAnalyzer.load(FILE_PATH);
        Assert.assertEquals(37, censusAnalyzer.countEntries());
    }
}
