package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSavedDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.RestaurantRepository;
import br.com.gva.quefominha.service.RestaurantService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Getter
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Getter
	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@Getter
	@Autowired
	private ReviewServiceImpl reviewServiceImpl;
	
	@Override
    public <DTO> List<DTO> findAll() {
		return (List<DTO>) getRestaurantRepository().findAll();
//        RestaurantSavedDto dto = new RestaurantSavedDto();
//        return getRestaurantRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }
	
	// Lista os products pelo id do restaurant
	public List<Product> findProductByRestaurantId(String restaurantId) {
		return this.productServiceImpl.findProductByRestaurantId(restaurantId);
	}
	
	// Lista os reviews pelo id do restaurant
	public List<Review> findReviewByRestaurantId(String restaurantId) {
		return this.reviewServiceImpl.findReviewByRestaurantId(restaurantId);
	}

    @Override
    public <DTO> DTO findById(String id) {
    	RestaurantSavedDto dto = new RestaurantSavedDto();
        Optional<Restaurant> restaurant = Optional.of(localFindById(id));
        return (DTO) populateDto(restaurant.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Restaurant localFindById(String id){
        Optional<Restaurant> restaurant = getRestaurantRepository().findById(id);
        return restaurant.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Restaurant restaurant = new Restaurant();
        return (SAVED) populateDto(getRestaurantRepository().save(populateEntity(dto, restaurant)), RestaurantSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Restaurant restaurant = localFindById(id);
        return (SAVED) populateDto(getRestaurantRepository().save(populateEntity(dto, restaurant)), RestaurantSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getRestaurantRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getRestaurantRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}

