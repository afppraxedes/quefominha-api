package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.SubProductGroup;

public interface SubProductGroupRepository extends MongoRepository<SubProductGroup, String> {

}
