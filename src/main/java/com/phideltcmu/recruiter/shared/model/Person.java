package com.phideltcmu.recruiter.shared.model;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    public Person() {
    }

    public Person(String firstName, String lastName, String andrewID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.andrewID = andrewID;
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

    public Date getClassYear() {
        return classYear;
    }

    public void setClassYear(Date classYear) {
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

    private String firstName;
    private String lastName;
    private String andrewID;
    private String phoneNumber;
    private String major;
    private Date classYear;
    private String recommencedBy;
    private String status;

}
