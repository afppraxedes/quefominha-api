package br.com.gva.quefominha.domain.dto.review;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.gva.quefominha.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewSavedDto {

	private String id;
	private String name;
	private LocalDateTime date;
	private BigDecimal rating;
	private String comments;
	private Restaurant restaurant;
	
}
