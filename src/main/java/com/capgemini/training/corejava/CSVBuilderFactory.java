package com.capgemini.training.corejava;

public class CSVBuilderFactory<T> {
    /**
     * Factory method to create CSV builder
     * @param <T>
     * @return generic CSV builder
     */
    public static <T> ICSVBuilder<T> createCSVBuilder() {
        return new OpenCSVBuilder<>();
    }
}
