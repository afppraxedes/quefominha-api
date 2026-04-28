package br.com.gva.quefominha.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class Review {

	@EqualsAndHashCode.Include
	@Id
	private String id;
	private String name;
	private LocalDateTime date;
	private BigDecimal rating;
	private String comments;
	@DBRef
	private Restaurant restaurant;
	
}
