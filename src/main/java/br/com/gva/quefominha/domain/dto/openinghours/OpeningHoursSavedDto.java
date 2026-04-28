package br.com.gva.quefominha.domain.dto.openinghours;

import br.com.gva.quefominha.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OpeningHoursSavedDto {

	private String id;    
    private String openDays; // Ex: Terça
    private String openHours; // Ex: 11am
    private String closeHours; // Ex: 4pm
    private Restaurant restaurant;
	
}
