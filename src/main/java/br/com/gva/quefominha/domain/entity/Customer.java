package br.com.gva.quefominha.domain.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gva.quefominha.domain.enums.Role;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class Customer implements UserDetails {
    
    @EqualsAndHashCode.Include
    @Id
    private String id;
    
    private String login;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String cpf;
    private String birthDate;
//  @JsonManagedReference(value="usuario-endereco")
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
    
}