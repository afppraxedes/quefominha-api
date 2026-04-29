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

import br.com.gva.quefominha.domain.dto.product.ProductSavedDto;
import br.com.gva.quefominha.domain.dto.productgroup.ProductGroupSaveDto;
import br.com.gva.quefominha.domain.dto.productgroup.ProductGroupSavedDto;
import br.com.gva.quefominha.domain.dto.productgroup.ProductGroupUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.ProductGroupService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/products/product-groups")
public class ProductGroupController {

    @Getter
    @Autowired
    private ProductGroupService productGroupService;

    @GetMapping
    public ResponseEntity<List<ProductSavedDto>> findAll() {    
        return ResponseEntity.ok(getProductGroupService().findAll());
    }
    
    @GetMapping("/page")
	public ResponseEntity<Page<ProductGroupSavedDto>> findPage(
	        @RequestParam(defaultValue = "0")  Integer page,
	        @RequestParam(defaultValue = "10") Integer linePerPage,
	        @RequestParam(defaultValue = "ASC") String direction,
	        @RequestParam(defaultValue = "name") String orderBy) {
	    return ResponseEntity.ok(
	        getProductGroupService().findPage(page, linePerPage, direction, orderBy)
	    );
	}

    @GetMapping("/{id}")
    public ResponseEntity<ProductGroupSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getProductGroupService().findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductGroupSavedDto> save(@RequestBody @Valid ProductGroupSaveDto productGroupDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((ProductGroupSavedDto) getProductGroupService().saveOrUpdate(productGroupDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductGroupSavedDto> update(@PathVariable String id, @RequestBody ProductGroupUpdateDto productGroup) {
        getProductGroupService().saveOrUpdate(productGroup, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getProductGroupService().delete(id);
        return ResponseEntity.noContent().build();
    }
}