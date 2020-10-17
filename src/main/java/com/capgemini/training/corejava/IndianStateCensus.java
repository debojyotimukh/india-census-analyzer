package com.capgemini.training.corejava;

import com.opencsv.bean.CsvBindByName;

public class IndianStateCensus {
    @CsvBindByName(column = "State Name", required = true)
    private String stateName;

    @CsvBindByName(column = "Population", required = true)
    private Double population;

    @CsvBindByName(column = "Population per sq km")
    private Double density;

    public IndianStateCensus() {
    }

    public IndianStateCensus(final String stateName, final Double population, final Double density) {
        this.stateName = stateName;
        this.population = population;
        this.density = density;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(final String stateName) {
        this.stateName = stateName;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(final Double population) {
        this.population = population;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(final Double density) {
        this.density = density;
    }

    @Override
    public String toString() {
        return "IndianStateCensus [density=" + density + ", population=" + population + ", stateName=" + stateName
                + "]";
    }

}