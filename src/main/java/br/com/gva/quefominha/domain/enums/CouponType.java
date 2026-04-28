package br.com.gva.quefominha.domain.enums;

import lombok.Getter;

public enum CouponType {
    PAID_BY_SYSTEM(1, "Pago pelo sistema"),
    PAID_BY_USER(2, "Pago pelo usuário");
    
    CouponType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    @Getter
    private int cod;

    @Getter
    private String description;

    
}