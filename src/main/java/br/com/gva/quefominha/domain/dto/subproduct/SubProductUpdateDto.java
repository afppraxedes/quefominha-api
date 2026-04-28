package br.com.gva.quefominha.domain.dto.subproduct;

import java.math.BigDecimal;
import java.util.Set;

import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.entity.SubProductGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubProductUpdateDto {

	private String name;
	private String description;
	private BigDecimal unitValue;
	private boolean active;
	public String imagePath;
	private Product product;
	private Set<SubProductGroup> subProductGroup;

}