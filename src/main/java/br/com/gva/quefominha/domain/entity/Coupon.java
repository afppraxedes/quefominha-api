package br.com.gva.quefominha.domain.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.gva.quefominha.domain.enums.CouponType;
import br.com.gva.quefominha.domain.enums.DiscountType;
import br.com.gva.quefominha.domain.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    
    private String code;
    private BigDecimal discount;
    private BigDecimal minValueEligible;
    private Long expirationDate;
    private Long created;
    private Integer quantity;
    private Integer remain;
    private CouponType type;
    private DiscountType discountType;
    private Boolean isEnable;
    private PaymentType paymentStatus;    
}