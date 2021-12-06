package com.push_app.push.service;

import com.google.firebase.database.*;
import com.push_app.push.firebase.FCMService;
import com.push_app.push.model.PushNotificationRequest;
import com.push_app.push.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Manages communication with firebase
 */
@Service
public class PushNotificationService {

    private final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private final FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    /**
     * This method send notification to topic
     * @param request PushNotificationRequest
     */
    public void sendPushNotificationToTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToTopic(getPayloadData(request), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * This method send notification to token
     * @param request PushNotificationRequest
     */
    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(getPayloadData(request), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Get the data to be attached to the notification
     * @param request - PushNotificationRequest object with notification
     * @return  data to be attached to the notification
     */
    private Map<String, String> getPayloadData(PushNotificationRequest request) {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("body", request.getMessage());
        return pushData;
    }

    /**
     * Get all registered users
     * @return list of registered users
     */
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        CountDownLatch cdl = new CountDownLatch(1);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    usersList.add(childSnapshot.getValue(User.class));
                }
                cdl.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    /**
     * Get list of user groups
     * @param model - contains the request data and provides it to view page
     */
    public void getTopics(Model model) {
        Set<String> topicsList = new HashSet<>();
        CountDownLatch cdl = new CountDownLatch(1);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> topic = childSnapshot.getValue(User.class).getTopics();
                    if (topic != null) {
                        topicsList.addAll(topic.values());
                    }
                }
                model.addAttribute("topics", topicsList);
                cdl.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}