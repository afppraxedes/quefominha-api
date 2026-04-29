package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSavedDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.RestaurantRepository;
import br.com.gva.quefominha.service.RestaurantService;
import lombok.Getter;

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
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getRestaurantRepository().findAll();
    }

    public List<Product> findProductByRestaurantId(String restaurantId) {
        return productServiceImpl.findProductByRestaurantId(restaurantId);
    }

    public List<Review> findReviewByRestaurantId(String restaurantId) {
        return reviewServiceImpl.findReviewByRestaurantId(restaurantId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        RestaurantSavedDto dto = new RestaurantSavedDto();
        Restaurant restaurant = localFindById(id);
        return (DTO) populateDto(restaurant, dto);
    }

    private Restaurant localFindById(String id) {
        Optional<Restaurant> restaurant = getRestaurantRepository().findById(id);
        return restaurant.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Restaurant restaurant = new Restaurant();
        return (SAVED) populateDto(
            getRestaurantRepository().save(populateEntity(dto, restaurant)),
            RestaurantSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Restaurant restaurant = localFindById(id);
        return (SAVED) populateDto(
            getRestaurantRepository().save(populateEntity(dto, restaurant)),
            RestaurantSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getRestaurantRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getRestaurantRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        RestaurantSavedDto dto = new RestaurantSavedDto();
        return (Page<DTO>) getRestaurantRepository().findAll(pageRequest)
                .map(restaurant -> populateDto(restaurant, dto));
    }
}
