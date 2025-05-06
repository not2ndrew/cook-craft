package com.example.demo.Enum;

import com.example.demo.CustomClass.Unit;

public enum MetricUnit implements Unit {
    MILLILITER("ML"), 
    LITER("L"), 
    MILLIGRAM("MG"), 
    GRAM("G");

    private final String type;

    private MetricUnit(String type) {
        this.type = type;
    }

    @Override
    public String getUnitType() {
        return type;
    }
}
