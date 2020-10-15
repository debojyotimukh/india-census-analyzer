package com.capgemini.training.corejava;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVReader;

/**
 * Hello world!
 */
public final class StateCensusAnalyzer {
    private String dataPath;

    public static void main(String[] args) {
        System.out.println("Welcome to India State Census Analyzer!");
    }

    public void load(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVReader csvReader = new CSVReader(reader);) {
            this.dataPath = filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countEntries() {
        int count = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(dataPath));
                CSVReader csvReader = new CSVReader(reader);) {

            while (csvReader.readNext() != null)
                count++;

        } catch (Exception e) {
            // duck
        }
        return count;
    }
}
