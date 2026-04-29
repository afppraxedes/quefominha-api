package br.com.gva.quefominha.api.v1.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
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
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.CustomerService;
import br.com.gva.quefominha.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;

/**
 * CORREÇÃO 4 (continuação): a lógica de persistência MongoDB que existia
 * diretamente no controller foi removida. Controllers não devem conter
 * lógica de negócio ou acesso a dados — isso é responsabilidade do Service.
 * O método update() agora delega inteiramente ao CustomerServiceImpl.
 */
@RestController
@RequestMapping("/api/v1/auth/customers")
public class CustomerController {

    @Getter
    @Autowired
    private CustomerService customerService;

    @Getter
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping
    public ResponseEntity<List<CustomerSavedDto>> findAll() {
        return ResponseEntity.ok(getCustomerService().findAll());
    }
    
//    @GetMapping("/page")
//    public ResponseEntity<Page<CustomerSavedDto>> findPage(
//            @RequestParam(defaultValue = "0")  Integer page,
//            @RequestParam(defaultValue = "10") Integer linePerPage,
//            @RequestParam(defaultValue = "ASC") String direction,
//            @RequestParam(defaultValue = "fullName") String orderBy) {
//        return ResponseEntity.ok(
//            getCustomerService().findPage(page, linePerPage, direction, orderBy)
//        );
//    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getCustomerService().findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerSavedDto> save(@RequestBody @Valid CustomerSaveDto customerDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(((CustomerSavedDto) getCustomerService().saveOrUpdate(customerDto, null)).getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerSavedDto> update(
            @RequestBody CustomerUpdateDto customer,
            @PathVariable String id) {
        // Delega inteiramente ao service — sem lógica MongoDB no controller
        getCustomerService().saveOrUpdate(customer, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getCustomerService().delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "password", value = "/{id}/check-current-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> checkCurrentPassword(
            @PathVariable String id,
            @RequestParam String password) {
        try {
            Boolean validation = getCustomerServiceImpl().checkCurrentPassword(id, password);
            return ResponseEntity.ok(validation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String id, @RequestBody String newPassword) {
        getCustomerServiceImpl().changePassword(id, newPassword);
    }
}
