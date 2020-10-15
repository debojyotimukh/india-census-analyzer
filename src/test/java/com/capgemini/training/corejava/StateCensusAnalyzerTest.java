package com.capgemini.training.corejava;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

public class StateCensusAnalyzerTest {
    public static final String FILE_PATH = "src/test/resources/IndianStateCensusData.csv";
    public StateCensusAnalyzer censusAnalyzer;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        censusAnalyzer = new StateCensusAnalyzer();
    }

    @Test
    public void countEntriesCheck() throws StateCensusAnalyzerException {
        censusAnalyzer.load(FILE_PATH);
        Assert.assertEquals(37, censusAnalyzer.countEntries());
    }

    @Test
    public void wrongCSVFileExceptionTest() throws StateCensusAnalyzerException {
        final String WRONG_PATH = "../censusanalyzer/dat/data.csv";
        exception.expect(StateCensusAnalyzerException.class);
        censusAnalyzer.load(WRONG_PATH);
        Assert.assertEquals(37, censusAnalyzer.countEntries());

    }

    @Test
    public void wrongFileTypeButCorrectDataWillThrowException() throws StateCensusAnalyzerException {
        final String WRONG_EXT_FILE_PATH="src/test/resources/IndianStateCensusData_copy.txt";
        exception.expect(StateCensusAnalyzerException.class);
        censusAnalyzer.load(WRONG_EXT_FILE_PATH);
        Assert.assertEquals(37, censusAnalyzer.countEntries());
    }

    @Test
    public void correctFileButWrongDelimiterShouldThrowException() throws StateCensusAnalyzerException {
        final String WRONG_EXT_FILE_PATH="src/test/resources/Wrong_Delimited_data.csv";
        exception.expect(StateCensusAnalyzerException.class);
        censusAnalyzer.load(WRONG_EXT_FILE_PATH);
    }
}
