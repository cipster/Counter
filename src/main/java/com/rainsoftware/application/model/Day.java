package com.rainsoftware.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class Day {
    private Interval intervalOne;
    private Interval intervalTwo;
    private Interval intervalThree;

    public Interval getIntervalOne() {
        return intervalOne;
    }

    public void setIntervalOne(Interval intervalOne) {
        this.intervalOne = intervalOne;
    }

    public Interval getIntervalTwo() {
        return intervalTwo;
    }

    public void setIntervalTwo(Interval intervalTwo) {
        this.intervalTwo = intervalTwo;
    }

    public Interval getIntervalThree() {
        return intervalThree;
    }

    public void setIntervalThree(Interval intervalThree) {
        this.intervalThree = intervalThree;
    }
}
