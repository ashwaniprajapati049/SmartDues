package com.major_project.NDMS_MajorProject.Entity;

public class StudentDues {
    private String rollNumber;
    private Double dueAmount;

    public StudentDues(String rollNumber, Double dueAmount) {
        this.rollNumber = rollNumber;
        this.dueAmount = dueAmount;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }
}
