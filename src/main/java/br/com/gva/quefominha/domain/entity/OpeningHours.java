package br.com.gva.quefominha.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class OpeningHours {

    @EqualsAndHashCode.Include
    @Id
    private String id;    
    private String openDays; // Ex: Terça
    private String openHours; // Ex: 11am
    private String closeHours; // Ex: 4pm

    @DBRef
    @JsonIgnore
    private Restaurant restaurant;
}
