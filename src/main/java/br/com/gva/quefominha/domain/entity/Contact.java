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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String email;
    private String tellphone;
    private String cellphone;
    

    @DBRef
    @JsonIgnore
    private Restaurant restaurant;

//    @DBRef
//    @JsonIgnore
//    private User user;
    
}
