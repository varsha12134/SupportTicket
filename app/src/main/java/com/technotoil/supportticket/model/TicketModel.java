package com.technotoil.supportticket.model;

public class TicketModel {
    private  String userId;


    private String ticketId;
    private String imgId;

    private String subject;
    private String region;
    private String description;
    private String status;
    private String createdOn;
    public String getUserId() {
        return userId;
    }
    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRegion() {
        return region;
    }
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public TicketModel(String ticketId,String imgId,String userId, String subject, String region, String description, String status, String createdOn) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.subject = subject;
        this.region = region;
        this.description = description;
        this.status = status;
        this.createdOn = createdOn;
        this.imgId = imgId;
    }
    public TicketModel() {

    }
}
