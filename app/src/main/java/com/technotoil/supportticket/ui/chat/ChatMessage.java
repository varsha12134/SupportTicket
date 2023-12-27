package com.technotoil.supportticket.ui.chat;

import android.net.Uri;

public class ChatMessage {
    private String message;
    private String userId;
    private Uri image;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public ChatMessage(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    public ChatMessage(Uri image, String userId) {
        this.image = image;
        this.userId = userId;
    }
    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }
}
