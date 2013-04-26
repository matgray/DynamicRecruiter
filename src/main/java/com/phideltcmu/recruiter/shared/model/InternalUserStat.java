/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.shared.model;

import java.io.Serializable;

public class InternalUserStat implements Serializable {
    public int getInternalID() {
        return internalID;
    }

    public void setInternalID(int internalID) {
        this.internalID = internalID;
    }

    public int getUniqueAdditions() {
        return uniqueAdditions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniqueAdditions(int uniqueAdditions) {
        this.uniqueAdditions = uniqueAdditions;
    }

    @Override
    public boolean equals(Object o2) {
        return o2 instanceof InternalUserStat && this.getInternalID() == ((InternalUserStat) o2).getInternalID();
    }

    private int internalID;
    private int uniqueAdditions;
    private String name;
}
