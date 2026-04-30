package br.com.gva.quefominha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gva.quefominha.domain.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

    // Busca pedidos pelo ID do customer (campo customer.$id no MongoDB)
    @Query("{ 'customer.$id': { $oid: ?0 } }")
    List<Order> findByCustomerId(String customerId);

}
