package com.capgemini.training.corejava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVReader;
import org.apache.commons.io.FilenameUtils;

public final class StateCensusAnalyzer {
    private String dataPath;

    public static void main(final String[] args) {
        System.out.println("Welcome to India State Census Analyzer!");
    }

    public void load(final String filePath) throws StateCensusAnalyzerException {
        final String[] expectedHeader = { "State Name", "TIN", "Population", "State Code" };
        if (!FilenameUtils.getExtension(filePath).equalsIgnoreCase("CSV"))
            throw new StateCensusAnalyzerException("Wrong file extension!");

        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVReader csvReader = new CSVReader(reader);) {
            if (!checkDelimiter(filePath, ','))
                throw new StateCensusAnalyzerException("Illegal separator!");
            if (!checkHeader(csvReader, expectedHeader))
                throw new StateCensusAnalyzerException("Wrong column header!");

            this.dataPath = filePath;
        } catch (final IOException e) {
            throw new StateCensusAnalyzerException();
        } catch (final NullPointerException e2) {
            throw new StateCensusAnalyzerException("File does not exists");
        }
    }

    private static boolean checkHeader(final CSVReader csvReader, final String[] expectedHeader) throws IOException {

        final String[] header = csvReader.peek();
        for (int i = 0; i < header.length; i++) {
            if (!header[i].equalsIgnoreCase(expectedHeader[i]))
                return false;
        }
        return true;
    }

    private static boolean checkDelimiter(final String filepath, final char c) throws IOException {
        final BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filepath));
        final String[] columns = bufferedReader.readLine().split(String.valueOf(c));
        return columns.length == 4;
    }

    public int countEntries() throws StateCensusAnalyzerException {
        int count = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(dataPath));
                CSVReader csvReader = new CSVReader(reader);) {

            while (csvReader.readNext() != null)
                count++;

        } catch (final Exception e) {
            throw new StateCensusAnalyzerException();
        }
        return count;
    }
}
