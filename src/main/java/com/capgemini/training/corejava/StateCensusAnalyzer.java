package com.capgemini.training.corejava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.apache.commons.io.FilenameUtils;

public final class StateCensusAnalyzer {

    private <T> int getCount(Iterable<T> csvIterable) throws StateCensusAnalyzerException {
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }

    private boolean checkSeparator(final String filepath, final char expectedSeperator) {
        boolean isWrongDelimiter = false;
        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            isWrongDelimiter = bufferedReader.readLine().contains(String.valueOf(expectedSeperator));
        } catch (IOException e) {
            // duck, will be thrown again by the calling method
        }

        return isWrongDelimiter;
    }

    public int getStateCodesCount(String filepath) throws StateCensusAnalyzerException {
        if (!FilenameUtils.getExtension(filepath).equalsIgnoreCase("csv"))
            throw new StateCensusAnalyzerException("Wrong file extension, expected csv");

        if (!checkSeparator(filepath, ','))
            throw new StateCensusAnalyzerException("Wrong Delimiter!");

        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));) {

            CsvToBean<CSVStates> csvToBean = new CsvToBeanBuilder<CSVStates>(reader).withSeparator(',')
                    .withThrowExceptions(true).withType(CSVStates.class).build();
            Iterator<CSVStates> iterator = csvToBean.iterator();
            Iterable<CSVStates> csvIterable = () -> iterator;
            return getCount(csvIterable);

        } catch (NullPointerException npe) {
            throw new StateCensusAnalyzerException(npe.getMessage());

        } catch (IOException e) {
            throw new StateCensusAnalyzerException("Failed to read state codes!");
        }
    }

    public int getStateCensusCount(String filepath) throws StateCensusAnalyzerException {
        if (!FilenameUtils.getExtension(filepath).equalsIgnoreCase("csv"))
            throw new StateCensusAnalyzerException("Wrong file extension, expected csv!");

        if (!checkSeparator(filepath, ','))
            throw new StateCensusAnalyzerException("Wrong Delimiter!");

        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));) {

            CsvToBean<IndianStateCensus> csvToBean = new CsvToBeanBuilder<IndianStateCensus>(reader).withSeparator(',')
                    .withType(IndianStateCensus.class).withThrowExceptions(true).build();
            Iterator<IndianStateCensus> iterator = csvToBean.iterator();
            Iterable<IndianStateCensus> csvIterable = () -> iterator;

            return getCount(csvIterable);

        } catch (NullPointerException npe) {
            throw new StateCensusAnalyzerException("File not Found!");

        } catch (IOException ioe) {
            throw new StateCensusAnalyzerException(ioe.getMessage());
        } catch (RuntimeException re) {
            throw new StateCensusAnalyzerException(re.getMessage());
        }

    }

    public static void main(String[] args) throws StateCensusAnalyzerException {

        System.out.println("Welcome to India State Census Analyzer!");

    }
}
