package com.capgemini.training.corejava;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {
    @CsvBindByName(column = "State Name")
    private String state;

    @CsvBindByName(column = "State Code")
    private String code;

    public CSVStates() {
    }

    public CSVStates(String state, String code) {
        this.state = state;
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CSVStates [code=" + code + ", state=" + state + "]";
    }

}
