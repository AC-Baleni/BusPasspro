package com.example.buspasspro.HomePage.Updates;

public class Update {
    private String title;
    private String message;
    private String startTime;
    private String endTime;
    private String updateID; // Added for storing Firestore document ID
    private String companyName;
    private String logoUrl;

    public Update() {
        // Default constructor required for Firestore
    }

    public Update(String title, String message, String startTime, String endTime, String updateID,String companyName,String logoUrl) {
        this.title = title;
        this.message = message;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updateID = updateID;
        this.companyName = companyName;
        this.logoUrl = logoUrl;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
