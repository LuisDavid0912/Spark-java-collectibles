package com.ramoncollectibles.model;

// Simple Plain Old Java Object (POJO) to represent a User
public class User {
    private String id; // Using String for ID for simplicity in this example
    private String name;
    private String email;

    // Constructor
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters are necessary for Gson to convert the object to JSON
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters are necessary for Gson to convert JSON back to an object
    // and for updating users
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Optional: toString() for debugging
    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}