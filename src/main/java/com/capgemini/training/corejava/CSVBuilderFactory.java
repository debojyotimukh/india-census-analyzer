package com.capgemini.training.corejava;

public class CSVBuilderFactory<T> {
    public static <T> ICSVBuilder<T> createCSVBuilder() {
        return new OpenCSVBuilder<>();
    }
}
