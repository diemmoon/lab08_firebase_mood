package com.example.smileapp;

public class User {
    private String name;
    private String email;
    private int happy;
    private int unhappy;
    private int normal;

    public User() {
    }

    public User(String name, String email, int happy, int unhappy, int normal) {
        this.name = name;
        this.email = email;
        this.happy = happy;
        this.unhappy = unhappy;
        this.normal = normal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getUnhappy() {
        return unhappy;
    }

    public void setUnhappy(int unhappy) {
        this.unhappy = unhappy;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }
}
