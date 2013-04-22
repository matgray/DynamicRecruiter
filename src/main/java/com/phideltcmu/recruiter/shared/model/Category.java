package com.phideltcmu.recruiter.shared.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String value;

    public Category() {
    }

    public Category(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
