package br.com.gva.quefominha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
    
	List<Category> findAllById(List<Integer> ids);
	
}