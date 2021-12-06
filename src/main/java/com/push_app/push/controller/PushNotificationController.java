package com.push_app.push.controller;

import com.push_app.push.model.PushNotificationRequest;
import com.push_app.push.service.PushNotificationService;
import com.push_app.push.utils.EndpointDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controls the sending of notifications
 */
@Controller
public class PushNotificationController {

    @Autowired
    private NavigationController navigationController;

    private final PushNotificationService pushNotificationService;

    /**
     * @param pushNotificationService
     */
    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    /**
     * This method sends a notification to the specified topic/group and redirect to topics.html file
     *
     * @param pushNotificationRequest - PushNotificationRequest object we want to send
     * @param model                   - contains the request data and provides it to view page
     * @return name of the page
     */
    @PostMapping(EndpointDictionary.SEND_NOTIFICATION_TO_TOPIC_ENDPOINT)
    public String sendNotification(@ModelAttribute("pushNotificationRequest") PushNotificationRequest pushNotificationRequest, Model model) {
        pushNotificationService.sendPushNotificationToTopic(pushNotificationRequest);
        return navigationController.getTopicsPage(model);
    }

    /**
     * This method sends a notification to the specified token and redirect to users.html file
     *
     * @param pushNotificationRequest - PushNotificationRequest object we want to send
     * @param model                   - contains the request data and provides it to view page
     * @return name of the page
     */
    @PostMapping(EndpointDictionary.SEND_NOTIFICATION_TO_TOKEN_ENDPOINT)
    public String sendTokenNotification(@ModelAttribute("pushNotificationRequest") PushNotificationRequest pushNotificationRequest, Model model) {
        model.addAttribute("pushNotificationRequest", pushNotificationRequest);
        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
        return navigationController.getUsersPage(model);
    }
}