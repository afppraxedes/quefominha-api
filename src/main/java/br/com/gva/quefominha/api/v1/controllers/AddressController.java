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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gva.quefominha.domain.dto.address.AddressSaveDto;
import br.com.gva.quefominha.domain.dto.address.AddressSavedDto;
import br.com.gva.quefominha.domain.dto.address.AddressUpdateDto;
import br.com.gva.quefominha.domain.entity.Address;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.AddressService;
import br.com.gva.quefominha.service.impl.AddressServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/address")
public class AddressController {

	@Getter
	@Autowired
	private AddressService addressService;
	
	@Getter
	@Autowired
	private AddressServiceImpl addressServiceImpl;

	@GetMapping
    public ResponseEntity<List<AddressSavedDto>> findAll() {    
        return ResponseEntity.ok(getAddressService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getAddressService().findById(id));
    }

    @PostMapping
    public ResponseEntity<AddressSavedDto> save(@RequestBody @Valid AddressSaveDto addressDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((AddressSavedDto) getAddressService().saveOrUpdate(addressDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressSavedDto> update(@RequestBody AddressUpdateDto address, @PathVariable String id) {
    	getAddressServiceImpl().saveOrUpdate(address, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getAddressService().delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(params = "id", value = "customer")
    public ResponseEntity<List<AddressSavedDto>> listByCustomer(@RequestParam String id) {
    	return ResponseEntity.ok(getAddressServiceImpl().listByCustomer(id));
//    	return ResponseEntity.noContent().build();
    }
    
    // TODO: COLOCAR COMO RETORNO (ServiceImpl) O MODEL "AddressSavedDto"!
    // TODO: tenho que alterar para um "@PathVariable"!
    @GetMapping(value = "search-primary")
    public ResponseEntity<Address /*AddressSavedDto*/> searchPrimaryAddressByCustomer(@RequestParam String id, @RequestParam Boolean primary) {
    	return ResponseEntity.ok(getAddressServiceImpl().searchPrimaryAddressByCustomer(id, primary));
    }
    
    // TODO: COLOCAR COMO RETORNO (ServiceImpl) O MODEL "AddressSavedDto"!
    @GetMapping(value = "list-zipcode")
	public List<Address> listAddressByZipcodeAndCustomer(
			@RequestParam(value = "id") String id, @RequestParam(value = "zipcode") String zipcode) {
		List<Address> enderecos = getAddressServiceImpl().listAddressByZipcodeAndCustomer(id, zipcode);
		return enderecos;
		//return this.enderecoModelAssembler.toCollectionModel(enderecos);
	}
    
    @PutMapping("/{id}/primary")
    public ResponseEntity<Void> updatePrimaryAddress(@PathVariable String id, @RequestBody Boolean primary) {
    	getAddressServiceImpl().updatePrimaryAddress(id, primary);
    	return ResponseEntity.noContent().build();
    }
	
}
