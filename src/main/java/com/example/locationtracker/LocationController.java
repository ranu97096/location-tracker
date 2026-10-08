package com.example.locationtracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LocationController {

	private final EmailService emailService;

	public LocationController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("/location")
	public ResponseEntity<String> receiveLocation(@RequestBody LocationRequest request) {
		emailService.sendLocationEmail(request.latitude(), request.longitude());
		return ResponseEntity.ok("Thank You!");
	}
}
