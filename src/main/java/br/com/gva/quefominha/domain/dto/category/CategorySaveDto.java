package br.com.gva.quefominha.domain.dto.category;

import java.util.List;

import br.com.gva.quefominha.domain.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategorySaveDto {  
  
	@NotBlank(message = "Nome da categoria não pode ficar em branco")
  private String categoryName;
  private String categoryPhoto;
	@NotNull(message = "Precisa informar se a categoria está desabilitada")
  private Boolean disabled;
	@NotNull(message = "Precisa informar se e categoria é especial")
  private Boolean isSpecial;
  private String specialPhoto;
  private List<Restaurant> restaurants;
	
}
