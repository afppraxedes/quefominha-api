package br.com.gva.quefominha.domain.dto.subproductgroup;

import java.util.List;

import br.com.gva.quefominha.domain.entity.SubProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubProductGroupSavedDto {
	
	private String id;
    private String title;
    private String subTitle;
    private List<SubProduct> subProducts;
    
}
