package br.com.gva.quefominha.domain.dto.address;

import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressSavedDto {

	private String id; 
	private String recipientName;
	private String zipCode;
    private String street;
    private String neighborhood;
    private String cityName;
    private String stateName;
    private String complement;
    private String addressNumber;
    private Boolean primary;
    private Double latitude;
    private Double longitude;
    private AddressType addressType;
    private String descriptionTypeOther;
    private String reference;
    private String formattedAddress;
    private Customer customer;
	
}
