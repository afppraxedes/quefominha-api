package br.com.gva.quefominha.domain.enums;

import lombok.Getter;

public enum PaymentType {
    CANCELLED(1, "Cancelado"),
    COMPLETED(2, "Concluído"),
    PAYMENT_REJECTED(3, "Pagamento Rejeitado"),
    WAITING(4, "Aguardando");

    PaymentType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    @Getter
    private int cod;

    @Getter
    private String description;
}