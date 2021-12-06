package com.push_app.push.model;

/**
 * Represents Response
 */
public class Response {
    private String username;
    private String message;
    private String messageResponse;

    /**
     * Get username from response
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username in response
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get message from response
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message in response
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get response message from response
     * @return response message
     */
    public String getMessageResponse() {
        return messageResponse;
    }

    /**
     * Set response message in response
     * @param messageResponse
     */
    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }

    /**
     * Create Response object
     * @param username - username of the user who sent the reply
     * @param message - message sent to user via notification
     * @param messageResponse - user reply to notification message
     */
    public Response(String username, String message, String messageResponse) {
        this.username = username;
        this.message = message;
        this.messageResponse = messageResponse;
    }
}
