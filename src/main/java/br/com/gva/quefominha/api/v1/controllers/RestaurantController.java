package br.com.gva.quefominha.api.v1.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSaveDto;
import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSavedDto;
import br.com.gva.quefominha.domain.dto.restaurant.RestaurantUpdateDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.RestaurantService;
import br.com.gva.quefominha.service.impl.RestaurantServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/restaurants")
public class RestaurantController {

    @Getter
    @Autowired
    private RestaurantService restaurantService;

    @Getter
    @Autowired
    private RestaurantServiceImpl restaurantServiceImpl;

    /**
     * GET /restaurants        → retorna todos os restaurantes
     * GET /restaurants?q=termo → filtra por nome (case-insensitive, contains)
     *
     * O frontend envia o parâmetro "q" pela barra de pesquisa.
     */
    @GetMapping
    public ResponseEntity<List<Restaurant>> findAll(
            @RequestParam(value = "q", required = false) String search) {

        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(
                getRestaurantServiceImpl().getRestaurantRepository()
                    .findByNameIgnoreCaseContaining(search)
            );
        }

        return ResponseEntity.ok(getRestaurantService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantSavedDto> findById(@PathVariable String id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(getRestaurantService().findById(id));
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<Product>> findProductByRestaurantId(
            @PathVariable String restaurantId) {
        return ResponseEntity.ok(
            getRestaurantServiceImpl().findProductByRestaurantId(restaurantId));
    }

    @GetMapping("/{restaurantId}/reviews")
    public ResponseEntity<List<Review>> findReviewByRestaurantId(
            @PathVariable String restaurantId) {
        return ResponseEntity.ok(
            getRestaurantServiceImpl().findReviewByRestaurantId(restaurantId));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid RestaurantSaveDto restaurantDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(((RestaurantSavedDto) getRestaurantService()
                        .saveOrUpdate(restaurantDto, null)).getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestBody RestaurantUpdateDto restaurant) {
        getRestaurantService().saveOrUpdate(restaurant, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)
            throws ResourceNotFoundException {
        getRestaurantService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
