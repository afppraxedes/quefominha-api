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

import br.com.gva.quefominha.domain.dto.subproduct.SubProductSaveDto;
import br.com.gva.quefominha.domain.dto.subproduct.SubProductSavedDto;
import br.com.gva.quefominha.domain.dto.subproduct.SubProductUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.SubProductService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/subproducts")
public class SubProductController {

    @Getter
    @Autowired
    private SubProductService subProductService;

    @GetMapping
    public ResponseEntity<List<SubProductSavedDto>> findAll() {    
        return ResponseEntity.ok(getSubProductService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubProductSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getSubProductService().findById(id));
    }

    @PostMapping
    public ResponseEntity<SubProductSavedDto> save(@RequestBody @Valid SubProductSaveDto subProductDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((SubProductSavedDto) getSubProductService().saveOrUpdate(subProductDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubProductSavedDto> update(@PathVariable String id, @RequestBody SubProductUpdateDto subProduct) {
        getSubProductService().saveOrUpdate(subProduct, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getSubProductService().delete(id);
        return ResponseEntity.noContent().build();
    }
}