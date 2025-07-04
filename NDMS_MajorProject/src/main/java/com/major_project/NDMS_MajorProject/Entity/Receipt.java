package com.major_project.NDMS_MajorProject.Entity;

import jakarta.persistence.*;

@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rollNumber;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double dueAmount;

    @Column(nullable = false, unique = true)
    private String receiptNumber;

    @Column(nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private String receiptDate;

    @Column(nullable = false)
    private Double yearlyCommitment;
    @Column(name = "admin_updated", nullable = false)
    private Boolean adminUpdated = false;


    // ==== Getters and Setters ====

    public boolean isAdminUpdated() {
        return adminUpdated;
    }

    public void setAdminUpdated(boolean adminUpdated) {
        this.adminUpdated = adminUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Double getYearlyCommitment() {
        return yearlyCommitment;
    }

    public void setYearlyCommitment(Double yearlyCommitment) {
        this.yearlyCommitment = yearlyCommitment;
    }
}
