package br.com.gva.quefominha.domain.dto.address;

import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.enums.AddressType;
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
public class AddressUpdateDto {

	@NotBlank(message = "Nome do destinatário não pode ficar em branco")
	private String recipientName;
	@NotNull(message = "CEP não pode ser nulo")
	private String zipCode;
	@NotBlank(message = "Rua não pode ficar em branco")
    private String street;
	@NotBlank(message = "Bairro não pode ficar em branco")
    private String neighborhood;
	@NotBlank(message = "Cidade não pode ficar em branco")
    private String cityName;
	@NotBlank(message = "Estado não pode ficar em branco")
    private String stateName;
    private String complement;
    @NotBlank(message = "Númro não pode ficar em branco")
    private String addressNumber;
    private Boolean primary;
    private Double latitude;
    private Double longitude;
    @NotNull(message = "Tipo de endereço não pode ficar em branco")
    private AddressType addressType;
    private String descriptionTypeOther;
    private String reference;
    private String formattedAddress;
    private Customer customer;
	
}
