package com.capgemini.training.corejava;

import java.io.Reader;

public interface ICSVBuilder<T> {
    public Iterable<T> getCSVFileIterable(Reader reader, Class<T> clazz) throws CSVBuilderException;
    
}
