package br.com.gva.quefominha.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gva.quefominha.domain.entity.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findAllById(List<Integer> ids);

    /**
     * Busca categorias pelo ID do restaurante referenciado via @DBRef.
     *
     * O Spring Data MongoDB com @DBRef armazena como:
     * { "$ref": "restaurant", "$id": ObjectId("...") }
     *
     * A query correta para buscar dentro de um array de DBRefs é:
     * { "restaurants.$id": ObjectId("...") }
     * — sem $oid wrapper, passando ObjectId diretamente via SpEL.
     */
    @Query("{ 'restaurants.$id': ?0 }")
    List<Category> findByRestaurantId(ObjectId restaurantId);

}
