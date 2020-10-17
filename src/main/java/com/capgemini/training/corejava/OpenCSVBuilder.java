package com.capgemini.training.corejava;

import java.io.Reader;
import java.util.Iterator;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder<T> implements ICSVBuilder<T> {

    public OpenCSVBuilder() {
    }

    @Override
    public Iterable<T> getCSVFileIterable(Reader reader, Class<T> clazz) throws CSVBuilderException {
        try {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader).withSeparator(',').withThrowExceptions(true)
                    .withType(clazz).build();
            Iterator<T> iterator = csvToBean.iterator();
            Iterable<T> csvIterable = () -> iterator;
            return csvIterable;

        } catch (RuntimeException re) {
            throw new CSVBuilderException(re.getMessage());
        }

    }

}
