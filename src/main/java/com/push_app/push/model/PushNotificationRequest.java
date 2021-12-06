package com.push_app.push.model;

/**
 * Represents PushNotificationRequest
 */
public class PushNotificationRequest {

    private String title;
    private String message;
    private String topic;
    private String token;


    public PushNotificationRequest() {
    }

    /**
     * Create PushNotificationRequest
     * @param title - title of notification
     * @param messageBody - message to be included in the notification
     * @param topicName - topic to which you want to send the notification
     */
    public PushNotificationRequest(String title, String messageBody, String topicName) {
        this.title = title;
        this.message = messageBody;
        this.topic = topicName;
    }

    /**
     * Get notification title
     * @return notification title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set notification title
     * @param title - notification title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get notification message
     * @return notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set notification message
     * @param message - notification message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get notification topic
     * @return notification topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Set notification topic
     * @param topic - notification topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Get notification token
     * @return notification token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set notification token
     * @param token - notification token
     */
    public void setToken(String token) {
        this.token = token;
    }
}