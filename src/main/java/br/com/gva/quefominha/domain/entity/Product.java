package br.com.gva.quefominha.domain.entity;

import java.math.BigDecimal;
import java.util.Set;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    
    private String name;
    private String description;
    private BigDecimal unitValue;
    private Boolean active;
    private String imagePath;
    private String howMuchServe;

    @DBRef
//    @JsonIgnore
    private Restaurant restaurant;

    @DBRef
    private Set<ProductGroup> groups;

    @DBRef
    private Set<SubProduct> subProducts; 

}