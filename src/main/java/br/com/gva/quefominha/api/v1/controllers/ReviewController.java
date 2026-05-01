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

import br.com.gva.quefominha.domain.dto.review.ReviewSaveDto;
import br.com.gva.quefominha.domain.dto.review.ReviewSavedDto;
import br.com.gva.quefominha.domain.dto.review.ReviewUpdateDto;
import br.com.gva.quefominha.domain.entity.Review;
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

    // TODO: todos os métodos novos, estou incluindo na interface "reviewService". Verificar
    // se é a melhor abordagem!
//    @Getter
//    @Autowired
//    private ReviewServiceImpl reviewServiceImpl;

    @GetMapping
    public ResponseEntity<List<ReviewSavedDto>> findAll() {
        return ResponseEntity.ok(getReviewService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewSavedDto> findById(@PathVariable String id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(getReviewService().findById(id));
    }

    /**
     * CORREÇÃO: endpoint era GET /reviews/{restaurantId}/reviews (URL duplicada).
     * Corrigido para GET /reviews/restaurant/{restaurantId}.
     *
     * Nota: as reviews também são acessíveis via RestaurantController:
     * GET /restaurants/{restaurantId}/reviews — que é o endpoint usado pelo frontend.
     * Este endpoint aqui é para uso administrativo/interno.
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>> findByRestaurantId(
            @PathVariable String restaurantId) {
        return ResponseEntity.ok(
            getReviewService().findReviewByRestaurantId(restaurantId)
        );
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ReviewSaveDto reviewDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(((ReviewSavedDto) getReviewService()
                        .saveOrUpdate(reviewDto, null)).getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestBody ReviewUpdateDto review) {
        getReviewService().saveOrUpdate(review, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)
            throws ResourceNotFoundException {
        getReviewService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
