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
import br.com.gva.quefominha.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/orders")
public class OrderController {

    @Getter
    @Autowired
    private OrderService orderService;
    
    @Getter
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping
    public ResponseEntity<List<OrderSavedDto>> findAll() {
        return ResponseEntity.ok(getOrderService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getOrderService().findById(id));
    }

    // Histórico de pedidos do customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderSavedDto>> findByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(getOrderServiceImpl().findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid OrderSaveDto orderSaveDto) {
        // CORREÇÃO ClassCastException: cast correto para OrderSavedDto
        OrderSavedDto saved = getOrderService().saveOrUpdate(orderSaveDto, null);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
