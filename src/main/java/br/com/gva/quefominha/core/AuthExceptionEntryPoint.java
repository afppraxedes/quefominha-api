package br.com.gva.quefominha.core;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		final Map<String, Object> mapBodyException = new HashMap<>();

		OffsetDateTime dateTime = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));

		String dateTimeString = String.valueOf(dateTime);

		mapBodyException.put("status", 401);
		mapBodyException.put("timestamp", dateTimeString);
		mapBodyException.put("type", "https://quefominha.com.br/usuario-nao-autorizado");
		mapBodyException.put("title", "usuário não Autorizado");
		mapBodyException.put("detail", "É necessária autenticação completa para acessar este recurso");
		mapBodyException.put("userMessage", "É necessária autenticação completa para acessar este recurso");

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), mapBodyException);

	}
}
