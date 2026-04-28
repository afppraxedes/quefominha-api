package br.com.gva.quefominha.domain.enums;

import lombok.Getter;

public enum DiscountType {
    MONEY(1, "Dinheiro"),
    PERCENT(2, "Porcentagem");

    DiscountType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    @Getter
    private int cod;

    @Getter
    private String description;
}