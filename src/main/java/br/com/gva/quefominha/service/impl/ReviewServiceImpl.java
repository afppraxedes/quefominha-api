package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.review.ReviewSavedDto;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ReviewRepository;
import br.com.gva.quefominha.service.ReviewService;
import lombok.Getter;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Getter
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getReviewRepository().findAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        ReviewSavedDto dto = new ReviewSavedDto();
        Review review = localFindById(id);
        return (DTO) populateDto(review, dto);
    }

    /**
     * CORREÇÃO: tratamento de exceção unificado em localFindReviewsByRestaurantId,
     * eliminando a dupla verificação que existia antes (TODO no código original).
     */
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findReviewByRestaurantId(String restaurantId) {
        List<Review> reviews = localFindReviewsByRestaurantId(restaurantId);
        return (List<DTO>) reviews;
    }

    private Review localFindById(String id) {
        Optional<Review> review = getReviewRepository().findById(id);
        return review.orElseThrow(
            () -> new NegocioException(String.format("Review de id %s não encontrado", id))
        );
    }

    private List<Review> localFindReviewsByRestaurantId(String restaurantId) {
        return getReviewRepository().findReviewByRestaurantId(restaurantId)
                .orElseThrow(() -> new NegocioException(
                    String.format("Nenhuma avaliação encontrada para o restaurante de id %s", restaurantId)
                ));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Review review = new Review();
        return (SAVED) populateDto(
            getReviewRepository().save(populateEntity(dto, review)),
            ReviewSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Review review = localFindById(id);
        return (SAVED) populateDto(
            getReviewRepository().save(populateEntity(dto, review)),
            ReviewSavedDto.builder().build()
        );
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
