package br.com.gva.quefominha.domain.dto.restaurant;

import java.math.BigDecimal;
import java.util.List;

import br.com.gva.quefominha.domain.entity.Address;
import br.com.gva.quefominha.domain.entity.OpeningHours;
import br.com.gva.quefominha.domain.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RestaurantSaveDto {

	@NotBlank(message = "Nome não pode ficar em branco")
	private String name;
	@NotBlank(message = "CNPJ não pode ficar em branco")
	private String cnpj;
	private String about;
	private String imagePath;
//	private List<Product> menu;
	private List<OpeningHours> openingHours;
	private Address address;
//	private List<Contact> contact;
//	private List<Category> categories;

}
