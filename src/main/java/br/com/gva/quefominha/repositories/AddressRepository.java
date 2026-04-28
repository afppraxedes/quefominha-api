package br.com.gva.quefominha.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Address;

public interface AddressRepository extends MongoRepository<Address, String> {

	public Optional<List<Address>> findByCustomer(String id);
	
	public Optional<List<Address>> findByCustomerAndZipCode(String id, String zipcode);
	
	public Optional<Address> findByCustomerAndPrimary(String id, Boolean primary);
	
}
