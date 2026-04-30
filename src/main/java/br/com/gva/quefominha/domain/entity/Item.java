package br.com.gva.quefominha.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
// CORREÇÃO: o Set<Item> em Order usava @EqualsAndHashCode baseado em "id",
// que chega null do frontend. Com todos os ids null, o Set considerava
// todos os itens duplicatas e mantinha apenas um.
// Solução: usar List<Item> no Order (sem deduplicação) ou basear o equals
// em product.name + quantity. Optamos por manter Set mas com equals
// em campos que identificam unicamente o item no pedido.
@EqualsAndHashCode(of = {"product", "quantity"})
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String id;
    private Product product;
    private Integer quantity;

    @JsonIgnore
    private Bag cart;
}
