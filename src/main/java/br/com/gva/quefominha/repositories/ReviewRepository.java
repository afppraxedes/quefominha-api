package br.com.gva.quefominha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gva.quefominha.domain.entity.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {

    // CORREÇÃO: a derivação de nome (findReviewByRestaurantId) não funciona
    // com @DBRef no MongoDB pois o campo armazenado é uma referência ($ref/$id),
    // não o id diretamente. A @Query com $oid resolve o lookup correto.
    @Query("{ 'restaurant.$id': { $oid: ?0 } }")
    List<Review> findReviewByRestaurantId(String restaurantId);

}
