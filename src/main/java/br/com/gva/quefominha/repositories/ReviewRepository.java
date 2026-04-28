package br.com.gva.quefominha.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {

	public Optional<List<Review>> findReviewByRestaurantId(String restaurantId);
	
}
