package ru.consort.sensor.entities;


import com.google.gson.JsonElement;

/**
 * Created by DaH4uk on 04.08.2016.
 */
public class Parameter {
    private String parameterName;
    private Double parameterValue;

    public Parameter(String parameterName, Double parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public Parameter() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Double getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Double parameterValue) {
        this.parameterValue = parameterValue;
    }
}
