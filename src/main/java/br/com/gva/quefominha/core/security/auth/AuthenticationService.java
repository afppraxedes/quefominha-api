package br.com.gva.quefominha.core.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.core.security.config.JwtService;
import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.enums.Role;
import br.com.gva.quefominha.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final CustomerRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	
	public AuthenticationResponse register(RegisterRequest request) {
		Customer customer = Customer.builder()
				.fullName(request.getFullName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
				.phone(request.getPhone() != null ? request.getPhone() : "")
				.build();

		repository.save(customer);

		String jwtToken = jwtService.generateToken(customer);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		Customer customer = repository.findByEmail(request.getEmail()).orElseThrow();

		String jwtToken = jwtService.generateToken(customer);

		// Para customizar o "response do token", basta inserir a respectiva propriedade em "AuthenticationResponse" e,
		// utilizando a busca acima (repository.findByEmail) teremos acesso a qualquer propridade de Cusmomer!
		// Caso queira customizar o "payload" do "jwt", devemos efetuar esse procedimento em "JwtService" (lá está comentado)!
		return AuthenticationResponse.builder().token(jwtToken)/*.id(customer.getId())*/.build();
	}
}
