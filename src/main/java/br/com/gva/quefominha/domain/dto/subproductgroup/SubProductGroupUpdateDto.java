package br.com.gva.quefominha.domain.dto.subproductgroup;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class SubProductGroupUpdateDto {
	
	@NotBlank(message = "O título não pode estar em branco")
    private String title;
	@NotBlank(message = "O subtitulo não pode estar em branco")
    private String subTitle;
//	@NotNull(message = "Precisa informar os ids dos subprodutos")
	@JsonIgnore
    private List<SubProduct> subProducts;
	
}
