package br.com.gva.quefominha.domain.dto.bag;

import java.util.List;

import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.entity.Item;
import br.com.gva.quefominha.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BagSavedDto {

	private String id;
    private Double totalBagValue;
    private Boolean isCLosed;
    private Customer customer;
    private List<Item> items;
    private PaymentMethod paymentMethod;
	
}
