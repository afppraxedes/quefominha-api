package br.com.gva.quefominha.domain.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.entity.Item;
import br.com.gva.quefominha.domain.entity.Payment;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderUpdateDto {

	private LocalDateTime data;
	private Status status;
	private Customer customer;
	private Restaurant restaurant;
	private BigDecimal subtotal;
	private BigDecimal deliveryTax;
	private BigDecimal amount;
	private Set<Item> items;
	private Payment payment;
	
}
