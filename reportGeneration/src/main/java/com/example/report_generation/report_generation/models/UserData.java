package com.example.report_generation.report_generation.models;

import java.util.Date;
import java.util.Map;

public class UserData {

    private String id;
    private int prediction;
    private Map<String, String> record;
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrediction() {
        return prediction;
    }

    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    public Map<String, String> getRecord() {
        return record;
    }

    public void setRecord(Map<String, String> record) {
        this.record = record;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "\ndataID: " + this.id +
                "\ndate: " + this.date +
                "\nprediction: " + this.prediction +
                "\nrecord: " + this.record;
    }
}
