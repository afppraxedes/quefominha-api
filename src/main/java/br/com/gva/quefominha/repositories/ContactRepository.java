package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {

}
