package br.com.gva.quefominha.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.review.ReviewSaveDto;
import br.com.gva.quefominha.domain.dto.review.ReviewSavedDto;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ReviewRepository;
import br.com.gva.quefominha.service.ReviewService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class ReviewServiceImpl implements ReviewService {

    @Getter
    @Autowired
    private ReviewRepository reviewRepository;

    @Getter
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getReviewRepository().findAll();
    }

    @Override
    public <DTO> DTO findById(String id) {
        ReviewSavedDto dto = new ReviewSavedDto();
        Review review = localFindById(id);
        return (DTO) populateDto(review, dto);
    }

    public List<Review> findReviewByRestaurantId(String restaurantId) {
        return getReviewRepository().findReviewByRestaurantId(restaurantId);
    }

    private Review localFindById(String id) {
        Optional<Review> review = getReviewRepository().findById(id);
        return review.orElseThrow(
            () -> new NegocioException(String.format("Review de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Review review = new Review();
        Review saved = getReviewRepository().save(populateEntity(dto, review));

        // Recalcula e persiste a média de rating no restaurante
        updateRestaurantRating(saved);

        return (SAVED) populateDto(saved, ReviewSavedDto.builder().build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Review review = localFindById(id);
        Review saved = getReviewRepository().save(populateEntity(dto, review));

        updateRestaurantRating(saved);

        return (SAVED) populateDto(saved, ReviewSavedDto.builder().build());
    }

    /**
     * Calcula a média de todas as avaliações do restaurante e persiste
     * diretamente na entidade Restaurant via MongoTemplate.
     * Isso mantém o rating sempre atualizado sem precisar de um job/scheduler.
     */
    private void updateRestaurantRating(Review savedReview) {
        if (savedReview.getRestaurant() == null || savedReview.getRestaurant().getId() == null) {
            return;
        }

        String restaurantId = savedReview.getRestaurant().getId();
        List<Review> reviews = getReviewRepository().findReviewByRestaurantId(restaurantId);

        if (reviews == null || reviews.isEmpty()) return;

        BigDecimal average = reviews.stream()
            .map(Review::getRating)
            .filter(r -> r != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(reviews.size()), 1, RoundingMode.HALF_UP);

        Query query = new Query(Criteria.where("_id").is(restaurantId));
        Update update = new Update().set("rating", average);
        mongoTemplate.updateFirst(query, update, "restaurant");
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getReviewRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getReviewRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        ReviewSavedDto dto = new ReviewSavedDto();
        return (Page<DTO>) getReviewRepository().findAll(pageRequest)
                .map(review -> populateDto(review, dto));
    }
}
