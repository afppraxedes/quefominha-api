package br.com.gva.quefominha.api.v1.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.gva.quefominha.domain.dto.product.ProductSaveDto;
import br.com.gva.quefominha.domain.dto.product.ProductSavedDto;
import br.com.gva.quefominha.domain.dto.product.ProductUpdateDto;
import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSavedDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.ProductService;
import br.com.gva.quefominha.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/products")
public class ProductController {

	@Getter
    @Autowired
    private ProductService productService;
	
	@Getter
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<List<ProductSavedDto>> findAll() {    
        return ResponseEntity.ok(getProductService().findAll());
    }
    
    // Lista os produtos pelo ID do Restaurant
    @GetMapping("/{restaurantId}/menu")
	public ResponseEntity<List<RestaurantSavedDto>> findProductByRestaurantId(@PathVariable String restaurantId) {
		return ResponseEntity.ok(getProductServiceImpl().findProductByRestaurantId(restaurantId));
	}

    @GetMapping("/page")
	public ResponseEntity<Page<ProductSavedDto>> findPage(
	        @RequestParam(defaultValue = "0")  Integer page,
	        @RequestParam(defaultValue = "10") Integer linePerPage,
	        @RequestParam(defaultValue = "ASC") String direction,
	        @RequestParam(defaultValue = "name") String orderBy) {
	    return ResponseEntity.ok(
	        getProductService().findPage(page, linePerPage, direction, orderBy)
	    );
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getProductService().findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductSavedDto> save(@RequestBody @Valid ProductSaveDto productDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((ProductSavedDto) getProductService().saveOrUpdate(productDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSavedDto> update(@PathVariable String id, @RequestBody ProductUpdateDto product) {
        getProductService().saveOrUpdate(product, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getProductService().delete(id);
        return ResponseEntity.noContent().build();
    }
}