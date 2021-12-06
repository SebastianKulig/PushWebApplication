package com.push_app.push.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Initializes Firebase FCM
 */
@Service
public class FCMInitializer {

    @Value("${app.firebase}")
    private String firebaseConfigPath;

    Logger logger = LoggerFactory.getLogger(FCMInitializer.class);

    /**
     * This method is called only once after the initialization of bean. It configures and initializes the FirebaseApp
     */
    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
                    .setDatabaseUrl("FIREBASE_INSTANCE_URL")
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
