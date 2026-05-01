package br.com.gva.quefominha.domain.entity;

import java.math.BigDecimal;
import java.util.List;

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
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    private String name;
    private String cnpj;
    private String about;

    // CORREÇÃO: @JsonIgnore removido — rating precisa chegar no frontend
    // para exibição da média de estrelas nos cards de restaurante.
    // A média é calculada e persistida pelo ReviewServiceImpl ao salvar uma avaliação.
    private BigDecimal rating;

    private String imagePath;

    @JsonIgnore
    private List<Product> menu;

    @DBRef
    private List<OpeningHours> openingHours;

    @DBRef
    private Address address;

    @DBRef
    @JsonIgnore
    private List<Contact> contact;

    @DBRef
    @JsonIgnore
    private List<Category> categories;
}
