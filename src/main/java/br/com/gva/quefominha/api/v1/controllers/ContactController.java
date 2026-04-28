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

import br.com.gva.quefominha.domain.dto.contact.ContactSaveDto;
import br.com.gva.quefominha.domain.dto.contact.ContactSavedDto;
import br.com.gva.quefominha.domain.dto.contact.ContactUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.ContactService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/contacts")
public class ContactController {

	@Getter
	@Autowired
	private ContactService contactService;

	@GetMapping
    public ResponseEntity<List<ContactSavedDto>> findAll() {    
        return ResponseEntity.ok(getContactService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getContactService().findById(id));
    }

    @PostMapping
    public ResponseEntity<ContactSavedDto> save(@RequestBody @Valid ContactSaveDto contactDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((ContactSavedDto) getContactService().saveOrUpdate(contactDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactSavedDto> update(@PathVariable String id, @RequestBody ContactUpdateDto contact) {
        getContactService().saveOrUpdate(contact, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getContactService().delete(id);
        return ResponseEntity.noContent().build();
    }
	
}
