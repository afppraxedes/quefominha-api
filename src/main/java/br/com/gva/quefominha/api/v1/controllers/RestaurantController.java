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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gva.quefominha.domain.dto.product.ProductSavedDto;
import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSaveDto;
import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSavedDto;
import br.com.gva.quefominha.domain.dto.restaurant.RestaurantUpdateDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.RestaurantService;
import br.com.gva.quefominha.service.impl.RestaurantServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/restaurants")
//@AllArgsConstructor
//@CrossOrigin
public class RestaurantController {

	@Getter
	@Autowired
	private RestaurantService restaurantService;
	
	// TODO: está apenas como teste utilizando apenas um campo. Vou verificar como 
	// passar um objeto "filtro" para "agrupar" a ordenação por mais de um campo.
	// Após os testes, vou colocar em "ServiceUtil".
	@Getter
	@Autowired
	private RestaurantServiceImpl restaurantServiceImpl;

	@GetMapping
	public ResponseEntity<List<RestaurantSavedDto>> findAll() {    
	    return ResponseEntity.ok(getRestaurantService().findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RestaurantSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
	    return ResponseEntity.ok(getRestaurantService().findById(id));
	}

	// TODO: implementar o método! Está com "findById" apenas para remover o erro no frontend por enquanto!
	// A "URI" "/menu" está apenas para visualizar o restaurante selecionado.
	// Está vindo um erro de "método não suportado" (GET). Verificar no "front"
	// como está sendo a "chamada" para a visualização de um "Restaurante"! 
//	@GetMapping("/{id}/menu")
//	public ResponseEntity<RestaurantSavedDto> findMenuById(@PathVariable String id) {
//		return ResponseEntity.ok(getRestaurantService().findById(id));
//	}
	
	// Busca os produtos pelo ID do Restaurant
	@GetMapping("/{restaurantId}/menu")
	public ResponseEntity<List<Product>> findProductByRestaurantId(@PathVariable String restaurantId) {
		return ResponseEntity.ok(getRestaurantServiceImpl().findProductByRestaurantId(restaurantId));
	}
	
	// TODO: implementar o método! Está com "findById" apenas para remover o erro no frontend por enquanto!
	// A "URI" "/reviews" está apenas para visualizar o restaurante selecionado.
	// Está vindo um erro de "método não suportado" (GET). Verificar no "front"
	// como está sendo a "chamada" para a visualização de um "Restaurante"! 
//	@GetMapping("/{id}/reviews")
//	public ResponseEntity<RestaurantSavedDto> findReviewsById(@PathVariable String id) {
//		return ResponseEntity.ok(getRestaurantService().findById(id));
//	}
	
	// Busca os produtos pelo ID do Restaurant
	@GetMapping("/{restaurantId}/reviews")
	public ResponseEntity<List<Review>> findReviewByRestaurantId(@PathVariable String restaurantId) {
		return ResponseEntity.ok(getRestaurantServiceImpl().findReviewByRestaurantId(restaurantId));
	}
	
	
	@PostMapping
	public ResponseEntity<ProductSavedDto> save(@RequestBody @Valid RestaurantSaveDto restaurantDto){
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/{id}").buildAndExpand(((RestaurantSavedDto) getRestaurantService().saveOrUpdate(restaurantDto, null)).getId()).toUri();
	    return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RestaurantSavedDto> update(@PathVariable String id, @RequestBody RestaurantUpdateDto restaurant) {
	    getRestaurantService().saveOrUpdate(restaurant, id);
	    return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
	    getRestaurantService().delete(id);
	    return ResponseEntity.noContent().build();
	}
  
}