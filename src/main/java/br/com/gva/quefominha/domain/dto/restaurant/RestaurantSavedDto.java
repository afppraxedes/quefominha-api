package br.com.gva.quefominha.domain.dto.restaurant;

import java.math.BigDecimal;
import java.util.List;

import br.com.gva.quefominha.domain.entity.Address;
import br.com.gva.quefominha.domain.entity.Category;
import br.com.gva.quefominha.domain.entity.Contact;
import br.com.gva.quefominha.domain.entity.OpeningHours;
import br.com.gva.quefominha.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RestaurantSavedDto {
	
	private String id;
    private String name;
    private String cnpj;
    private String about;
    private BigDecimal rating;
    private String imagePath;
    private List<Product> menu;    
    private List<OpeningHours> openingHours;    
    private Address address;
    private List<Contact> contact;    
    private List<Category> categories;
    
}
