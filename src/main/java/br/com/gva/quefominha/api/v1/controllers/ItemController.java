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

import br.com.gva.quefominha.domain.dto.item.ItemSaveDto;
import br.com.gva.quefominha.domain.dto.item.ItemSavedDto;
import br.com.gva.quefominha.domain.dto.item.ItemUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.ItemService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/items")
public class ItemController {

	@Getter
	@Autowired
	private ItemService itemService;

	@GetMapping
    public ResponseEntity<List<ItemSavedDto>> findAll() {    
        return ResponseEntity.ok(getItemService().findAll());
    }
	
	@GetMapping("/page")
	public ResponseEntity<Page<ItemSavedDto>> findPage(
	        @RequestParam(defaultValue = "0")  Integer page,
	        @RequestParam(defaultValue = "10") Integer linePerPage,
	        @RequestParam(defaultValue = "ASC") String direction,
	        @RequestParam(defaultValue = "name") String orderBy) {
	    return ResponseEntity.ok(
	        getItemService().findPage(page, linePerPage, direction, orderBy)
	    );
	}

    @GetMapping("/{id}")
    public ResponseEntity<ItemSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getItemService().findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemSavedDto> save(@RequestBody @Valid ItemSaveDto itemDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((ItemSavedDto) getItemService().saveOrUpdate(itemDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemSavedDto> update(@PathVariable String id, @RequestBody ItemUpdateDto item) {
        getItemService().saveOrUpdate(item, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getItemService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
