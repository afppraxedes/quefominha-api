package br.com.gva.quefominha.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	Optional<Customer> findByEmail(String email);
	
}
