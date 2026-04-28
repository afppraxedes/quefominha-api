package br.com.gva.quefominha.domain.dto.category;

import java.util.List;

import br.com.gva.quefominha.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategorySavedDto {

	private String id;
    private String categoryName;
    private String categoryPhoto;
    private Boolean disabled;
    private Boolean isSpecial;
    private String specialPhoto;
    private List<Restaurant> restaurants;
	
}
