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

import br.com.gva.quefominha.domain.dto.review.ReviewSaveDto;
import br.com.gva.quefominha.domain.dto.review.ReviewSavedDto;
import br.com.gva.quefominha.domain.dto.review.ReviewUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.ReviewService;
import br.com.gva.quefominha.service.impl.ReviewServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/reviews")
public class ReviewController {

	@Getter
	@Autowired
	private ReviewService reviewService;
	
	@Getter
	@Autowired
	private ReviewServiceImpl reviewServiceImpl;
	
	@GetMapping
    public ResponseEntity<List<ReviewSavedDto>> findAll() {    
        return ResponseEntity.ok(getReviewService().findAll());
    }
	
	@GetMapping("/page")
	public ResponseEntity<Page<ReviewSavedDto>> findPage(
	        @RequestParam(defaultValue = "0")  Integer page,
	        @RequestParam(defaultValue = "10") Integer linePerPage,
	        @RequestParam(defaultValue = "ASC") String direction,
	        @RequestParam(defaultValue = "name") String orderBy) {
	    return ResponseEntity.ok(
	        getReviewService().findPage(page, linePerPage, direction, orderBy)
	    );
	}
	
	// Lista os reviews pelo ID do Restaurant
    @GetMapping("/{restaurantId}/reviews")
	public ResponseEntity<List<ReviewSavedDto>> findProductByRestaurantId(@PathVariable String restaurantId) {
		return ResponseEntity.ok(getReviewServiceImpl().findReviewByRestaurantId(restaurantId));
	}

    @GetMapping("/{id}")
    public ResponseEntity<ReviewSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getReviewService().findById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewSavedDto> save(@RequestBody @Valid ReviewSaveDto reviewDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((ReviewSavedDto) getReviewService().saveOrUpdate(reviewDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewSavedDto> update(@PathVariable String id, @RequestBody ReviewUpdateDto review) {
        getReviewService().saveOrUpdate(review, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getReviewService().delete(id);
        return ResponseEntity.noContent().build();
    }
	
}
