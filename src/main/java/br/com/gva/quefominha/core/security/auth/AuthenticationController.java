package br.com.gva.quefominha.core.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/security")
@RequiredArgsConstructor
public class AuthenticationController {

	@Getter
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	@Getter
	@Autowired
	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		LOG.info("Register : " + request.getFullName());
		return ResponseEntity.ok(getService().register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		LOG.info("Authenticating... ");
		return ResponseEntity.ok(getService().authenticate(request));
	}

}
