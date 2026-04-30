package br.com.gva.quefominha.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.gva.quefominha.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private LocalDateTime data;
    private Status status;

    @DBRef
    private Customer customer;

    @DBRef
    private Restaurant restaurant;

    private BigDecimal subtotal;
    private BigDecimal deliveryTax;
    private BigDecimal amount;

    // CORREÇÃO: era Set<Item> — o Set deduplicava itens com id=null
    // (todos os itens novos chegam sem id do frontend),
    // fazendo o pedido salvar apenas 1 item.
    // List preserva todos os itens na ordem de inserção.
    private List<Item> items;

    private Payment payment;
}
