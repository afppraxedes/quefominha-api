package br.com.gva.quefominha.api.v1.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gva.quefominha.domain.dto.order.OrderSaveDto;
import br.com.gva.quefominha.domain.dto.order.OrderSavedDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.OrderService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/orders")
public class OrderController {

    @Getter
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderSavedDto>> findAll() {
        return ResponseEntity.ok(getOrderService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getOrderService().findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid OrderSaveDto orderSaveDto) {
        /*
         * CORREÇÃO ClassCastException:
         *
         * O erro original era:
         *   ((CustomerSavedDto) getOrderService().saveOrUpdate(orderSaveDto, null)).getId()
         *
         * O método saveOrUpdate() retorna o tipo genérico <SAVED>, que neste caso
         * é sempre OrderSavedDto (pois OrderServiceImpl.save() retorna populateDto(..., OrderSavedDto)).
         * O cast para CustomerSavedDto era claramente um erro de copiar/colar do CustomerController
         * — a JVM lançava ClassCastException em runtime porque OrderSavedDto e CustomerSavedDto
         * são tipos incompatíveis.
         *
         * Corrigido: cast para OrderSavedDto e import de CustomerSavedDto removido.
         * O MongoTemplate foi removido também — não havia uso neste controller.
         */
        OrderSavedDto saved = getOrderService().saveOrUpdate(orderSaveDto, null);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
