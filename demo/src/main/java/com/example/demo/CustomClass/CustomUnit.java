package com.example.demo.CustomClass;

public class CustomUnit implements Unit {
    private String type;

    public CustomUnit(String type) {
        this.type = type;
    }
    
    @Override
    public String getUnitType() {
        return type;
    }

}
