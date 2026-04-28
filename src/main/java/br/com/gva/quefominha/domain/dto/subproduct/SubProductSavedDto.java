package br.com.gva.quefominha.domain.dto.subproduct;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;

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
public class SubProductSavedDto {
    
	private String id;
    private String name;
    private String description;
    private BigDecimal unitValue;
    private boolean active;
    public String imagePath;
    private Product product;
    private Set<SubProductGroup> subProductGroup;

    @JsonGetter("product")
    public String getProductId() {
        return getProduct().getId();
    }
    
}