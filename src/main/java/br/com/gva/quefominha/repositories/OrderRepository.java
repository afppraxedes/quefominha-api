package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    
}