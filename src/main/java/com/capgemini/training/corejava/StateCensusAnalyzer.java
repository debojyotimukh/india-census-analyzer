package com.capgemini.training.corejava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.capgemini.training.iohelper.CSVBuilderException;
import com.capgemini.training.iohelper.CSVBuilderFactory;
import com.capgemini.training.iohelper.ICSVBuilder;
import com.google.gson.Gson;

import org.apache.commons.io.FilenameUtils;

public final class StateCensusAnalyzer {

    private static <T> int getCount(Iterable<T> csvIterable) throws StateCensusAnalyzerException {
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }

    private static <T> String sortBy(String filepath, Comparator<T> comparator, Class<T> clazz)
            throws StateCensusAnalyzerException {
        if (!FilenameUtils.getExtension(filepath).equalsIgnoreCase("csv"))
            throw new StateCensusAnalyzerException("Wrong file extension, expected csv!");

        if (!checkSeparator(filepath, ','))
            throw new StateCensusAnalyzerException("Wrong Delimiter!");

        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));) {
            ICSVBuilder<T> csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<T> list = csvBuilder.getLst(reader, clazz);
            List<T> sortedList = list.stream().sorted(comparator).collect(Collectors.toList());

            return new Gson().toJson(sortedList);

        } catch (IOException ioe) {
            throw new StateCensusAnalyzerException(ioe.getMessage());
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyzerException(e.getMessage());
        }
    }

    private static boolean checkSeparator(final String filepath, final char expectedSeperator) {
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

            ICSVBuilder<CSVStates> csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterable<CSVStates> csvIterator = csvBuilder.getCSVFileIterable(reader, CSVStates.class);

            return getCount(csvIterator);

        } catch (NullPointerException npe) {
            throw new StateCensusAnalyzerException(npe.getMessage());

        } catch (IOException e) {
            throw new StateCensusAnalyzerException("Failed to read state codes!");
        } catch (CSVBuilderException csvbe) {
            throw new StateCensusAnalyzerException(csvbe.getMessage());
        }
    }

    public int getStateCensusCount(String filepath) throws StateCensusAnalyzerException {
        if (!FilenameUtils.getExtension(filepath).equalsIgnoreCase("csv"))
            throw new StateCensusAnalyzerException("Wrong file extension, expected csv!");

        if (!checkSeparator(filepath, ','))
            throw new StateCensusAnalyzerException("Wrong Delimiter!");

        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));) {

            ICSVBuilder<IndianStateCensus> csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterable<IndianStateCensus> csvIterator = csvBuilder.getCSVFileIterable(reader, IndianStateCensus.class);

            return getCount(csvIterator);

        } catch (NullPointerException npe) {
            throw new StateCensusAnalyzerException("File not Found!");
        } catch (IOException ioe) {
            throw new StateCensusAnalyzerException(ioe.getMessage());
        } catch (CSVBuilderException csvbe) {
            throw new StateCensusAnalyzerException(csvbe.getMessage());
        }
    }

    public String sortCensusDataByState(String filepath) {
        String sortedByStateName = null;
        try {
            sortedByStateName = sortBy(filepath, Comparator.comparing(IndianStateCensus::getStateName),
                    IndianStateCensus.class);
        } catch (StateCensusAnalyzerException e) {
            e.getMessage();
        }
        return sortedByStateName;
    }

    public String sortCensusDataByStateCode(String filepath) {
        String sortedByCode = null;
        try {
            sortedByCode = sortBy(filepath, Comparator.comparing(CSVStates::getCode), CSVStates.class);
        } catch (StateCensusAnalyzerException e) {
            e.getMessage();
        }
        return sortedByCode;
    }

    public String sortCensusDataByPopulation(String filepath) {
        String sortedByPopulation = null;
        try {
            sortedByPopulation = sortBy(filepath, Comparator.comparing(IndianStateCensus::getPopulation).reversed(),
                    IndianStateCensus.class);
        } catch (StateCensusAnalyzerException e) {
            e.getMessage();
        }
        return sortedByPopulation;
    }

    public String sortCensusDataByDensity(String filepath) {
        String sortedByPopulationDensity = null;
        try {
            sortedByPopulationDensity = sortBy(filepath, Comparator.comparing(IndianStateCensus::getDensity).reversed(),
                    IndianStateCensus.class);
        } catch (StateCensusAnalyzerException e) {
            e.getMessage();
        }
        return sortedByPopulationDensity;
    }
}
