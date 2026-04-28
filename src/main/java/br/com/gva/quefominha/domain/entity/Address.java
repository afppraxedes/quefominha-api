package br.com.gva.quefominha.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.gva.quefominha.domain.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @EqualsAndHashCode.Include
    @Id
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
    
//  @JsonBackReference(value = "usuario-endereco")
    @DBRef
    private Customer customer;

    public String getFullAddress(){
        if(formattedAddress != null )
            return formattedAddress;

        StringBuilder builder = new StringBuilder();
        builder
            .append(street).append(" - ")
            .append(neighborhood).append(", ")
            .append(cityName).append(" - ")
            .append(stateName).append(", ")
            .append(zipCode).append(" - ")
            .append(addressNumber).append(" - ")
            .append(complement).append(" - ")
            .append(addressType);
        return builder.toString();
    } 
    
}