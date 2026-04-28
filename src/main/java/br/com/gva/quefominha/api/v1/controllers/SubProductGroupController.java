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

import br.com.gva.quefominha.domain.dto.subproductgroup.SubProductGroupSaveDto;
import br.com.gva.quefominha.domain.dto.subproductgroup.SubProductGroupSavedDto;
import br.com.gva.quefominha.domain.dto.subproductgroup.SubProductGroupUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.SubProductGroupService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/products/subproduct-groups")
public class SubProductGroupController {

	
	@Getter
    @Autowired
    private SubProductGroupService subProductGroupService;

	@GetMapping
    public ResponseEntity<List<SubProductGroupSavedDto>> findAll() {    
        return ResponseEntity.ok(getSubProductGroupService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubProductGroupSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getSubProductGroupService().findById(id));
    }

    @PostMapping
    public ResponseEntity<SubProductGroupSavedDto> save(@RequestBody @Valid SubProductGroupSaveDto subProductGroupDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((SubProductGroupSavedDto) getSubProductGroupService().saveOrUpdate(subProductGroupDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubProductGroupSavedDto> update(@PathVariable String id, @RequestBody SubProductGroupUpdateDto subProductGroup) {
        getSubProductGroupService().saveOrUpdate(subProductGroup, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getSubProductGroupService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
