package com.push_app.push.controller;

import com.push_app.push.model.PushNotificationRequest;
import com.push_app.push.model.Response;
import com.push_app.push.model.User;
import com.push_app.push.service.PushNotificationService;
import com.push_app.push.utils.EndpointDictionary;
import com.push_app.push.utils.ViewsDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.context.LazyContextVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls application views
 */
@Controller
public class NavigationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    /**
     * This method redirect URI "/" to users.html file
     *
     * @param model contains the request data and provides it to view page
     * @return name of the page
     */
    @GetMapping(EndpointDictionary.MAIN_PAGE_TO_USERS_REDIRECT_ENDPOINT)
    public String getMainPage(Model model) {
        model.addAttribute("users", new LazyContextVariable<List<User>>() {
            @Override
            protected List<User> loadValue() {
                return pushNotificationService.getUsers();
            }
        });
        return "users";
    }

    /**
     * This method redirect URI "/users" to users.html file
     *
     * @param model contains the request data and provides it to view page
     * @return name of the page
     */
    @GetMapping(EndpointDictionary.USERS_VIEW_ENDPOINT)
    public String getUsersPage(Model model) {
        model.addAttribute("users", new LazyContextVariable<List<User>>() {
            @Override
            protected List<User> loadValue() {
                return pushNotificationService.getUsers();
            }
        });
        return ViewsDictionary.USERS_VIEW;
    }

    /**
     * This method redirect URI "/topics" to topics.html file
     *
     * @param model contains the request data and provides it to view page
     * @return name of the page
     */
    @GetMapping(EndpointDictionary.TOPICS_VIEW_ENDPOINT)
    public String getTopicsPage(Model model) {
        pushNotificationService.getTopics(model);
        return ViewsDictionary.TOPICS_VIEWS;
    }

    /**
     * This method redirect URI "/sendMessagePage" to form.html file
     * @param user - user object with the data of the registered user to whom we want to send notifications
     * @param model - contains the request data and provides it to view page
     * @return name of the page
     */
    @PostMapping(EndpointDictionary.SEND_MESSAGE_TO_TOKEN_VIEW_ENDPOINT)
    public String sendMessagePage(@ModelAttribute("user") User user, Model model) {
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
        pushNotificationRequest.setToken(user.getToken());
        model.addAttribute("pushNotificationRequest", pushNotificationRequest);
        return ViewsDictionary.SEND_MESSAGE_TO_TOKEN_FORM_VIEW;
    }

    /**
     * This method redirect URI "/sendMessageOnTopicPage" to formTopic.html file
     * @param topic - the name of the user group to which you want to send notifications
     * @param model - contains the request data and provides it to view page
     * @return name of the page
     */
    @PostMapping(EndpointDictionary.SEND_MESSAGE_TO_TOPIC_VIEW_ENDPOINT)
    public String sendMessageOnTopic(@ModelAttribute("topic") String topic, Model model) {
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
        pushNotificationRequest.setTopic(topic);
        model.addAttribute("pushNotificationRequest", pushNotificationRequest);
        return ViewsDictionary.SEND_MESSAGE_TO_TOPIC_FORM_VIEW;
    }

    /**
     * This method redirect URI "/responses" to responses.html file
     * @param model - contains the request data and provides it to view page
     * @return name of the page
     */
    @GetMapping(EndpointDictionary.RESPONSES_VIEW_ENDPOINT)
    public String getResponsesPage(Model model) {
        model.addAttribute("responses", new LazyContextVariable<List<Response>>() {
            @Override
            protected List<Response> loadValue() {
                return pushNotificationService.getUsers().stream()
                        .filter(user -> user.getResponse() != null)
                        .map(user -> mapUserToResponse(user))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
            }
        });
        return ViewsDictionary.RESPONSES_VIEW;
    }

    /**
     * This method return List with Responses for given user
     * @param user - user object with the data of a registered user for whom we want to get a list of responses
     * @return List of responses
     */
    private List<Response> mapUserToResponse(User user) {
        List<Response> responsesList = new ArrayList<>();
        String username = user.getName();
        user.getResponse().forEach((question, response) -> responsesList.add(new Response(username, question, response)));
        return responsesList;
    }
}
