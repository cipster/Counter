package com.rainsoftware.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class PersonCountMetric {
    private Poarta poarta;
    private Day dayOne;
    private Day dayTwo;
    private Day dayThree;
    private Day dayFour;
    private Day dayFive;
    private Day daySix;
    private Day daySeven;
    private Day dayEight;

    public Poarta getPoarta() {
        return poarta;
    }

    public void setPoarta(Poarta poarta) {
        this.poarta = poarta;
    }

    public Day getDayOne() {
        return dayOne;
    }

    public void setDayOne(Day dayOne) {
        this.dayOne = dayOne;
    }

    public Day getDayTwo() {
        return dayTwo;
    }

    public void setDayTwo(Day dayTwo) {
        this.dayTwo = dayTwo;
    }

    public Day getDayThree() {
        return dayThree;
    }

    public void setDayThree(Day dayThree) {
        this.dayThree = dayThree;
    }

    public Day getDayFour() {
        return dayFour;
    }

    public void setDayFour(Day dayFour) {
        this.dayFour = dayFour;
    }

    public Day getDayFive() {
        return dayFive;
    }

    public void setDayFive(Day dayFive) {
        this.dayFive = dayFive;
    }

    public Day getDaySix() {
        return daySix;
    }

    public void setDaySix(Day daySix) {
        this.daySix = daySix;
    }

    public Day getDaySeven() {
        return daySeven;
    }

    public void setDaySeven(Day daySeven) {
        this.daySeven = daySeven;
    }

    public Day getDayEight() {
        return dayEight;
    }

    public void setDayEight(Day dayEight) {
        this.dayEight = dayEight;
    }
}
