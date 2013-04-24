/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.shared.model;

import java.io.Serializable;

public class Person implements Serializable {
    private String firstName = null;
    private String lastName = null;
    private String andrewID = null;
    private String phoneNumber = null;
    private String major = null;
    private String classYear = null;
    private String recommencedBy = null;
    private String status = null;
    private String notes = null;
    private String originalReferrer = null;

    public Person() {
    }

    public Person(String firstName, String lastName, String andrewID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.andrewID = andrewID;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAndrewID() {
        return andrewID;
    }

    public void setAndrewID(String andrewID) {
        this.andrewID = andrewID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getRecommencedBy() {
        return recommencedBy;
    }

    public void setRecommencedBy(String recommencedBy) {
        this.recommencedBy = recommencedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOriginalReferrer() {
        return originalReferrer;
    }

    public void setOriginalReferrer(String originalReferrer) {
        this.originalReferrer = originalReferrer;
    }
}
