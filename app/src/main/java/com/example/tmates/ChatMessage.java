package com.example.tmates;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ChatMessage {
    private String uid, text;
    private @ServerTimestamp Date timestamp;

    public ChatMessage(String uid, String text, Date timestamp) {
        this.uid = uid;
        this.text = text;
        this.timestamp = timestamp;
    }

    public ChatMessage(){
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
