package br.com.gva.quefominha.domain.dto.customer;

import java.util.List;

import br.com.gva.quefominha.domain.entity.Address;
import br.com.gva.quefominha.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerSavedDto {
	
	private String id;
    private String login;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String cpf;
    private String birthDate;
    private List<Address> addresses;   
    private Boolean smsValidator;
    private String lastPassword;
    private String facebookId;
    private String googleId;
    private String appleId;
    private String reason;
    private String photo;
    private Integer rating;
    private List<String> creditCards;
    private List<String> devices;
    private Boolean isAnonymous;
    private Role role;
    
}
