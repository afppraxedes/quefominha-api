package br.com.gva.quefominha.api.v1.controllers;

import java.net.URI;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gva.quefominha.domain.dto.customer.CustomerSaveDto;
import br.com.gva.quefominha.domain.dto.customer.CustomerSavedDto;
import br.com.gva.quefominha.domain.dto.customer.CustomerUpdateDto;
import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.CustomerService;
import br.com.gva.quefominha.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/customers")
public class CustomerController {

	@Getter
	@Autowired
	private CustomerService customerService;
	
	@Getter
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	MongoTemplate /*mongoTemplate*/ mongoOperations;
	
	@GetMapping
    public ResponseEntity<List<CustomerSavedDto>> findAll() {    
        return ResponseEntity.ok(getCustomerService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getCustomerService().findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerSavedDto> save(@RequestBody @Valid CustomerSaveDto customerDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((CustomerSavedDto) getCustomerService().saveOrUpdate(customerDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // TODO: PASSAR A INSTRUÇÃO PARA O "CustomerServiceImpl"! NESSE MOMENTO ESTÁ 
    // DANDO PROBLEMA COM O CAMPO "PASSWORD", ONDE ESTÁ SENDO "IGNORADO" NO BANCO DE DADOS
    // UTILIZANDO A INSTRUÇÃO DO "ServiceImpl"!
    @PutMapping("/{id}")
    public ResponseEntity<CustomerSavedDto> update(@RequestBody CustomerUpdateDto customer, @PathVariable String id) {
//        getCustomerService().saveOrUpdate(customer, id);
    	
    	Document document = new Document();
    	mongoOperations.getConverter().write(customer /*user*/, document);
    	Update update = new Update();
    	document.forEach(update::set);
    	/*return*/ mongoOperations.findAndModify(
    	            Query.query(Criteria.where("_id").is(id /*user.getId()*/)), update, Customer.class);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getCustomerService().delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(params = "password", value = "/{id}/check-current-password")
//	@PreAuthorize("hasAuthority('ROLE_USUARIO') and #oauth2.hasScope('read')")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Boolean> checkCurrentPassword(@PathVariable String id, @RequestParam String password) {
		try {
			Boolean validation = getCustomerServiceImpl().checkCurrentPassword(id, password);
			return ResponseEntity.ok(validation);
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); 
		}
	}
    
	@PutMapping("/{id}/change-password")
//	@PreAuthorize("hasAuthority('ROLE_USUARIO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@PathVariable String id, @RequestBody String newPassword) {
		getCustomerServiceImpl().changePassword(id, newPassword);
	}
}
