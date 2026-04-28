package br.com.gva.quefominha.domain.dto.product;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.gva.quefominha.domain.entity.ProductGroup;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.entity.SubProduct;
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
public class ProductSaveDto {

    @NotBlank(message = "Nome não pode ficar em branco")
    private String name;
    private String description;
    @NotNull(message = "Valor unitário precisa ser preenchido")
    private BigDecimal unitValue;
    @NotNull(message = "Precisa informar se o produto está ativo ou inativo")
    private Boolean active;
    private String imagePath;
    @NotBlank(message = "Informe quantas pessoas o prato serve")
    private String howMuchServe;
    @NotNull(message = "Precisa informar o id do restaurante")
    private Restaurant restaurant;
//    @NotNull(message = "Precisa informar os grupos que o produto faz parte")
    @JsonIgnore
    private Set<ProductGroup> groups;
    @NotNull(message = "Precisa informar os ids dos subprodutos que compoem esse produto")
    private Set<SubProduct> subProducts;
    
}