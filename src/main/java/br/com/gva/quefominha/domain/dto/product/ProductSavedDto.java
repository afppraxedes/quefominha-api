package br.com.gva.quefominha.domain.dto.product;

import java.math.BigDecimal;
import java.util.Set;

import br.com.gva.quefominha.domain.entity.ProductGroup;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.entity.SubProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductSavedDto {
    
    private String id;
    private String name;
    private String description;
    private BigDecimal unitValue;
    private Boolean active;
    private String imagePath;
    private String howMuchServe;
    private Restaurant restaurant;
    private Set<ProductGroup> groups;
    private Set<SubProduct> subProducts;
    
}