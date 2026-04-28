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

import br.com.gva.quefominha.domain.dto.bag.BagSaveDto;
import br.com.gva.quefominha.domain.dto.bag.BagSavedDto;
import br.com.gva.quefominha.domain.dto.bag.BagUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.BagService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/bags")
public class BagController {

	@Getter
	@Autowired
	private BagService bagService;

	@GetMapping
    public ResponseEntity<List<BagSavedDto>> findAll() {    
        return ResponseEntity.ok(getBagService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BagSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getBagService().findById(id));
    }

    @PostMapping
    public ResponseEntity<BagSavedDto> save(@RequestBody @Valid BagSaveDto bagDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((BagSavedDto) getBagService().saveOrUpdate(bagDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BagSavedDto> update(@PathVariable String id, @RequestBody BagUpdateDto bag) {
        getBagService().saveOrUpdate(bag, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getBagService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
