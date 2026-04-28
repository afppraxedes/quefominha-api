package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
	
	@Override
    public <DTO> List<DTO> findAll() {
		return (List<DTO>) getReviewRepository().findAll();
//		ReviewSavedDto dto = new ReviewSavedDto();
//        return getReviewRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	ReviewSavedDto dto = new ReviewSavedDto();
        Optional<Review> review = Optional.of(localFindById(id));
        return (DTO) populateDto(review.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }
    
    // Lista reviews por ID do Restaurant
    public <DTO> List<DTO> findReviewByRestaurantId(String restaurantId) {
        // ProductSavedDto dto = new ProductSavedDto();
        Optional<List<Review>> reviews = Optional.of(localFindReviewsByRestaurantId(restaurantId));
        return (List<DTO>) (reviews.orElseThrow(() ->  new NegocioException(String.format("Objeto Restauranttt de id %s não encontrado", restaurantId))));
    }

    private Review localFindById(String id){
        Optional<Review> review = getReviewRepository().findById(id);
        return review.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }
    
    // Busca uma lista de reviews por ID do Restaurant
    // TODO: O "Tratamento de Exceção" está sendo efetuado 2 vezes (aqui e em "findReviewByRestaurantId"). Rever isto!!!!!
    private List<Review> localFindReviewsByRestaurantId(String id){
        Optional<List<Review>> reviews = getReviewRepository().findReviewByRestaurantId(id);
        return reviews.orElseThrow(() ->  new NegocioException(String.format("Objeto Restaurant de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Review review = new Review();
        return (SAVED) populateDto(getReviewRepository().save(populateEntity(dto, review)), ReviewSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Review review = localFindById(id);
        return (SAVED) populateDto(getReviewRepository().save(populateEntity(dto, review)), ReviewSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getReviewRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getReviewRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}
