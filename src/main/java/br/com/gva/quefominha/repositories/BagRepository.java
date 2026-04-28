package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Bag;

public interface BagRepository extends MongoRepository<Bag, String> {

}
