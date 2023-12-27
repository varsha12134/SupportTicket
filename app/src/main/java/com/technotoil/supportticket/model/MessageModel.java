package com.technotoil.supportticket.model;

public class MessageModel {


    private String ticketId;
    private String senderId;
    private String messageText;
    private String createOn;
    private String  chatImgId ;



    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getMessageText() {
        return messageText;
    }
    public String getChatImgId() {
        return chatImgId;
    }

    public void setChatImgId(String chatImgId) {
        this.chatImgId = chatImgId;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    private  String receiverId;

    public MessageModel( String ticketId,String  chatImgId, String senderId,String receiverId, String messageText, String createOn) {
        this.ticketId = ticketId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.createOn = createOn;
        this.chatImgId = chatImgId;
    }
    private MessageModel(){

    }
}
