package br.com.gva.quefominha.domain.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.gva.quefominha.domain.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class ProductGroup {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String title;
    private String subtitle;
    private boolean hasDiscount;
    private DiscountType discountType;
    private BigDecimal discountValue;

    @DBRef
    private List<Product> products;
    
}