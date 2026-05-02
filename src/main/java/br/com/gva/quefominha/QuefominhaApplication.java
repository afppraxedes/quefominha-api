package br.com.gva.quefominha;

import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class QuefominhaApplication implements CommandLineRunner {

	/**
	 * CORREÇÃO fuso horário: define o timezone da JVM como America/Sao_Paulo
	 * antes de qualquer operação de data/hora.
	 *
	 * Sem isso, o Spring Boot usa UTC por padrão. O LocalDateTime serializado
	 * pelo MongoDB não carrega informação de timezone — ao salvar no horário
	 * local (BRT = UTC-3), o MongoDB armazena corretamente, mas ao deserializar
	 * a JVM interpreta como UTC e exibe com +3h.
	 *
	 * @PostConstruct garante execução antes do contexto Spring processar qualquer
	 * requisição, mas após a injeção de dependências.
	 */
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

	public static void main(String[] args) {
		SpringApplication.run(QuefominhaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
