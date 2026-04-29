package br.com.gva.quefominha.domain.dto.contact;

import br.com.gva.quefominha.domain.entity.Restaurant;
//import br.com.gva.quefominha.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContactSavedDto {

	private String id;
    private String email;
    private String tellphone;
    private String cellphone;
    private Restaurant restaurant;
//    private User user;
	
}
