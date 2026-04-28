package br.com.gva.quefominha.domain.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.gva.quefominha.domain.enums.PaymentMethod;
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
public class Bag {

    @EqualsAndHashCode.Include
    @Id
    private String id;

    private Double totalBagValue;
    private Boolean isCLosed;
    
    @JsonIgnore
    private Customer customer;
    
    private List<Item> items;
    private PaymentMethod paymentMethod;

    
}