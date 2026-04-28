package br.com.gva.quefominha.domain.dto.restaurant;

import java.math.BigDecimal;
import java.util.List;

import br.com.gva.quefominha.domain.entity.Address;
import br.com.gva.quefominha.domain.entity.Product;
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
public class RestaurantUpdateDto {

	@NotBlank(message = "Nome não pode ficar em branco")
    private String name;
	@NotBlank(message = "Nome não pode ficar em branco")
    private String cnpj;
	private String about;
	private String imagePath;
//	@NotNull(message = "precisa informar os produtos que fazem parte do restaurante")
//    private List<Product> menu;    
//	@NotNull(message = "precisa informar os ids dos dias e horários de funcionamento")
//  private List<OpeningHours> openingHours;    
	@NotNull(message = "precisa informar o id do endereço do restaurante")
    private Address address;
//	@NotNull(message = "precisa informar os ids das informações de contato")
//  private List<Contact> contact;    
//	@NotNull(message = "precisa informar os ids das categorias")
//  private List<Category> categories;
	
}
