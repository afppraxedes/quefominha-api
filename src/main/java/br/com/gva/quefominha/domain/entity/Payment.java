package br.com.gva.quefominha.domain.entity;

import br.com.gva.quefominha.domain.enums.PaymentMethod;
import br.com.gva.quefominha.domain.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private PaymentMethod paymentMethod;
    private PaymentType paymentType;

}
