package br.com.gva.quefominha.domain.enums;

import lombok.Getter;

public enum PaymentMethod {
    CASH(1, "Dinheiro"), 
    CREDIT_CARD(2, "Cartão de Crédito"),
    DEBIT_CARD(3, "Cartão de Débito"),
    PIX(4, "Pix");
	
	PaymentMethod(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	@Getter
	private int cod;
	
	@Getter
	private String description;
}