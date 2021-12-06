package com.push_app.push.firebase;

/**
 * Notification settings
 */
public enum NotificationParameter {
    SOUND("default"),
    COLOR("#FFFF00");

    private final String value;

    NotificationParameter(String value) {
        this.value = value;
    }

    /**
     * Get value for color and sound of notification
     * @return String with value of sound or color of notification
     */
    public String getValue() {
        return this.value;
    }
}