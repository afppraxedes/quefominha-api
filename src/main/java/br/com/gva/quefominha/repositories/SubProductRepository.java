package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.SubProduct;

public interface SubProductRepository extends MongoRepository<SubProduct, String>{
    
}