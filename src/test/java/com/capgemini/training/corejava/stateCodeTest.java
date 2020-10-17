package com.capgemini.training.corejava;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class stateCodeTest {
    public StateCensusAnalyzer censusAnalyzer;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        censusAnalyzer = new StateCensusAnalyzer();
    }

    @Test
    public void wrongFileExceptionTest() throws StateCensusAnalyzerException {
        final String WRONG_PATH = "../censusanalyzer/dat/data.csv";
        exception.expect(StateCensusAnalyzerException.class);
        Assert.assertEquals(37, censusAnalyzer.getStateCensusCount(WRONG_PATH));

    }

    @Test
    public void wrongFileTypeButCorrectDataWillThrowException() throws StateCensusAnalyzerException {
        final String WRONG_TYPE_FILE_PATH = "src/test/resources/IndianStateCodes.txt";
        exception.expect(StateCensusAnalyzerException.class);
        exception.expectMessage("Wrong file extension, expected csv!");
        Assert.assertEquals(37, censusAnalyzer.getStateCensusCount(WRONG_TYPE_FILE_PATH));
    }

    @Test
    public void correctFileButWrongDelimiterShouldThrowException() throws StateCensusAnalyzerException {
        final String WRONG_SEPARATOR_FILE_PATH = "src/test/resources/IndianStateCodes_wrong_delimiter.csv";
        exception.expect(StateCensusAnalyzerException.class);
        exception.expectMessage("Wrong Delimiter!");
        censusAnalyzer.getStateCensusCount(WRONG_SEPARATOR_FILE_PATH);
    }

    @Test
    public void correctFileButWrongHeaderShouldThrowException() throws StateCensusAnalyzerException {
        final String WRONG_HEADER_FILE_PATH = "src/test/resources/IndianStateCodes_wrong_header.csv";
        exception.expect(StateCensusAnalyzerException.class);
        exception.expectMessage("Error capturing CSV header!");
        Assert.assertEquals(36, censusAnalyzer.getStateCensusCount(WRONG_HEADER_FILE_PATH));
    }

    @Test
    public void checkNumberOfEntriesFromStateCodeData() throws StateCensusAnalyzerException {
        final String FILE_PATH = "src/test/resources/IndianStateCodes.csv";
        Assert.assertEquals(36, censusAnalyzer.getStateCodesCount(FILE_PATH));
    }

}
