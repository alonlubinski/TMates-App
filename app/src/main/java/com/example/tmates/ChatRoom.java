package com.example.tmates;

import java.util.ArrayList;

public class ChatRoom {
    private String cid;
    private User user1, user2;
    private ArrayList<ChatMessage> chatMessageArrayList;

    public ChatRoom(String cid, User user1, User user2, ArrayList<ChatMessage> chatMessageArrayList){
        this.cid = cid;
        this.user1 = user1;
        this.user2 = user2;
        this.chatMessageArrayList = chatMessageArrayList;
    }

    public ChatRoom(){

    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public ArrayList<ChatMessage> getChatMessageArrayList() {
        return chatMessageArrayList;
    }

    public void setChatMessageArrayList(ArrayList<ChatMessage> chatMessageArrayList) {
        this.chatMessageArrayList = chatMessageArrayList;
    }
}
