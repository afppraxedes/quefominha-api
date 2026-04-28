package br.com.gva.quefominha.domain.dto.bag;

import java.util.List;

import br.com.gva.quefominha.domain.entity.Item;
import br.com.gva.quefominha.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BagSaveDto {

	@NotNull(message = "Precisa informar o valor total")
  private Double totalBagValue;
  @NotNull(message = "Precisa informar se pedido está fechado")
  private Boolean isCLosed;
  @NotNull(message = "Precisa informar os ids dos itens")
  private List<Item> items;
  @NotNull(message = "Precisa informar o meio de pagamento")
  private PaymentMethod paymentMethod;
	
}
