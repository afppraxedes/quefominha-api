package br.com.gva.quefominha.core.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String phone;
    private String cpf; 
    private String login;
    private String email;
    private String password;
  
}
