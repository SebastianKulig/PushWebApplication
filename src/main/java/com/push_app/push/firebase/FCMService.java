package com.push_app.push.firebase;

import com.google.firebase.messaging.*;
import com.push_app.push.model.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Prepares notifications
 */
@Service
public class FCMService {

    private final Logger logger = LoggerFactory.getLogger(FCMService.class);

    /**
     * This method sends a notification to the specified topic
     * @param data - object added to the notification we want to send
     * @param request - PushNotificationRequest object we want to send
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void sendMessageToTopic(Map<String, String> data, PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToTopic(data, request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
    }

    /**
     * This method sends a notification to the specified token
     * @param data - object added to the notification we want to send
     * @param request - PushNotificationRequest object we want to send
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void sendMessageToToken(Map<String, String> data, PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(data, request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response);
    }

    /**
     * This method sends an asynchronous message to Firebase FCM
     * @param message - message to be sent
     * @return - result from Firebase FCM
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    /**
     * Build configuration for android notification
     * @param topic - the name of the user group to which you want to send notification
     * @return
     */
    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    /**
     * Build FCM APNS
     * @param topic - the name of the user group to which you want to send notification
     * @return ApnsConfig
     */
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    /**
     * This method prepares a notification to be sent to the specified token
     * @param data - object added to the notification we want to send
     * @param request - PushNotificationRequest object we want to send
     * @return Message
     */
    private Message getPreconfiguredMessageToToken(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
                .build();
    }

    /**
     * This method prepares a notification to be sent to the specified topic
     * @param data - object added to the notification we want to send
     * @param request - PushNotificationRequest object we want to send
     * @return Message
     */
    private Message getPreconfiguredMessageToTopic(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(data).setTopic(request.getTopic())
                .build();
    }

    /**
     * This method prepares the notification by adding the configurations
     * @param request - PushNotificationRequest object we want to send
     * @return Message
     */
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage()));
    }
}