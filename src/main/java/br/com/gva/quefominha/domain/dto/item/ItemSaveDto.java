package br.com.gva.quefominha.domain.dto.item;

import br.com.gva.quefominha.domain.entity.Bag;

import br.com.gva.quefominha.domain.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemSaveDto {

	@NotNull(message = "Precisa informar o id do produto")
	private Product product;
	@NotNull(message = "Quantiade deve ter um valor especificado")
	private Integer quantity;
	@NotNull(message = "Precisa informar o id do sacola de compras")
	private Bag cart;

}
