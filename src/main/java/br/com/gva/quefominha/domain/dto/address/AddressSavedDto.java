package br.com.gva.quefominha.domain.dto.address;

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

    /**
     * CORREÇÃO: era Customer (entidade completa com role, password, authorities etc.).
     * Ao serializar para JSON, Jackson chamava role.name() quando role=null → HTTP 500.
     * Substituído por CustomerRefDto com apenas o id — suficiente para o frontend
     * identificar o dono do endereço sem expor dados sensíveis.
     */
    private CustomerRefDto customer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CustomerRefDto {
        private String id;
    }
}
