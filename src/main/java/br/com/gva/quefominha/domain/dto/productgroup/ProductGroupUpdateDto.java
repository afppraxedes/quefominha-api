package br.com.gva.quefominha.domain.dto.productgroup;

import java.math.BigDecimal;
import java.util.List;

import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.enums.DiscountType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductGroupUpdateDto{

	@NotBlank(message = "Título não pode ficar em branco")
  private String title;
  private String subtitle;
  private boolean hasDiscount;
  private DiscountType discountType;
  private BigDecimal discountValue;
  private List<Product> products;
    
}
