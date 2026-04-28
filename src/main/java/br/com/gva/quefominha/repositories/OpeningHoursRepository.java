package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.OpeningHours;

public interface OpeningHoursRepository extends MongoRepository<OpeningHours, String> {

}
