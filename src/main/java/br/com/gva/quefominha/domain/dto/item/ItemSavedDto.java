package br.com.gva.quefominha.domain.dto.item;

import br.com.gva.quefominha.domain.entity.Bag;
import br.com.gva.quefominha.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemSavedDto {

	private String id;
    private Product product;
    private Integer quantity;
    private Bag cart;
	
}
