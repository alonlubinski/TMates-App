package com.example.tmates;

import android.provider.ContactsContract;

import java.util.ArrayList;

public class User {
    private String uid, name, gender, city, description, email;
    private int age;
    private ArrayList<String> sportsArray;

    // Constructor.
    public User(String uid, String email, String name, String gender, String description, int age, String city, ArrayList<String> sportsArray){
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.description = description;
        this.age = age;
        this.city = city;
        this.sportsArray = sportsArray;
    }

    public User(){

    }

    // Getters and setters.
    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getCity(){
        return this.city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public ArrayList<String> getSportsArray(){
        return this.sportsArray;
    }

    public void setSportsArray(ArrayList<String> sportsArray){
        this.sportsArray = sportsArray;
    }

}
