package br.com.gva.quefominha.domain.entity;

import java.util.List;

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
public class Category {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    private String categoryName;
    private String categoryPhoto;
    private Boolean disabled;
    private Boolean isSpecial;
    private String specialPhoto;
    
    @DBRef
    private List<Restaurant> restaurants;
    
}