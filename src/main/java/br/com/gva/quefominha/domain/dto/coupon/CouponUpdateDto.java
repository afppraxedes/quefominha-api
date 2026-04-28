package br.com.gva.quefominha.domain.dto.coupon;

import java.math.BigDecimal;

import br.com.gva.quefominha.domain.enums.CouponType;
import br.com.gva.quefominha.domain.enums.DiscountType;
import br.com.gva.quefominha.domain.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CouponUpdateDto {

	@NotBlank(message = "Código não pode ficar em branco")
  private String code;
  @NotNull(message = "Valor do desconto precisa ser preenchido")
  private BigDecimal discount;
  @NotNull(message = "Valor mínimo precisa ser preenchido")
  private BigDecimal minValueEligible;
  @NotNull(message = "Data de expiração precisa ser preenchido")
  private Long expirationDate;
  @NotNull(message = "Criação precisa ser preenchido")
  private Long created;
  @NotNull(message = "Quantidade precisa ser preenchido")
  private Integer quantity;
  @NotNull(message = "Quantidade restante precisa ser preenchido")
  private Integer remain;
  @NotNull(message = "Tipo de cupom precisa ser preenchido")
  private CouponType type;
  @NotNull(message = "Tipo de desconto precisa ser preenchido")
  private DiscountType discountType;
  @NotNull(message = "Precisa informar se está ativo")
  private Boolean isEnable;
  @NotNull(message = "Tipo de pagamento precisa ser preenchido")
  private PaymentType paymentStatus;
	
}
