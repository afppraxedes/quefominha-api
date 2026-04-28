package br.com.gva.quefominha.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    
	public Optional<List<Product>> findProductByRestaurantId(String restaurantId);
	
}