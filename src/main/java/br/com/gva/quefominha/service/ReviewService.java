package br.com.gva.quefominha.service;

import java.util.List;

import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.utils.ServiceUtil;

public interface ReviewService extends ServiceUtil<Review> {

	List<Review> findReviewByRestaurantId(String restaurantId);
	
}
