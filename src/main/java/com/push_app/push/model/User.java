package com.push_app.push.model;

import java.util.HashMap;

/**
 * Represent User
 */
public class User {
    private String email;
    private String name;
    private String token;
    private HashMap<String, String> topics;
    private HashMap<String, String> response;

    public User() {
    }

    /**
     * Create User object
     * @param email - user's email address
     * @param name - username
     * @param token - the token assigned to user by firebase FCM
     * @param topics - groups to which the user belongs
     */
    public User(String email, String name, String token, HashMap<String, String> topics) {
        this.email = email;
        this.name = name;
        this.token = token;
        this.topics = topics;
    }

    /**
     * Get username
     * @return username
     */
    public String getName() {
        return name;
    }

    /**
     * Set username
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user's email address
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email address
     * @param email - user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get token assigned to user by firebase FCM
     * @return token assigned to user by firebase FCM
     */
    public String getToken() {
        return token;
    }

    /**
     * Set token assigned to user by firebase FCM
     * @param token - token assigned to user by firebase FCM
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get groups to which the user belongs
     * @return groups to which the user belongs
     */
    public HashMap<String, String> getTopics() {
        return topics;
    }

    /**
     * Set groups to which the user belongs
     * @param topics - groups to which the user belongs
     */
    public void setTopics(HashMap<String, String> topics) {
        this.topics = topics;
    }

    /**
     * Get all answers assigned to the user
     * @return answers assigned to the user
     */
    public HashMap<String, String> getResponse() {
        return response;
    }

    /**
     * Set answers to user
     * @param response - answers to notifications
     */
    public void setResponses(HashMap<String, String> response) {
        this.response = response;
    }
}
