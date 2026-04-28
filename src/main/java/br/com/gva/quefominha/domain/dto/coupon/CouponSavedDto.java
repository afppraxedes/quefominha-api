package br.com.gva.quefominha.domain.dto.coupon;

import java.math.BigDecimal;

import br.com.gva.quefominha.domain.enums.CouponType;
import br.com.gva.quefominha.domain.enums.DiscountType;
import br.com.gva.quefominha.domain.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CouponSavedDto {

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
