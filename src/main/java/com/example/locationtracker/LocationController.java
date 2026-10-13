package com.example.locationtracker;

import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LocationController {

	private final EmailService emailService;

	private static final Logger log = LoggerFactory.getLogger(LocationController.class);

	public LocationController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("/location")
	public ResponseEntity<String> receiveLocation(@RequestBody LocationRequest request) {
		log.info("===> Captured Request Payload: {}", request);
		emailService.sendLocationEmail(request.latitude(), request.longitude());
		log.info("===> Captured Request Send Successfully: {}", request);
		return ResponseEntity.ok("Thank You!");
	}
}
