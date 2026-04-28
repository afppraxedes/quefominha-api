package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.api.util.UtilitarioSistema;
import br.com.gva.quefominha.domain.dto.customer.CustomerSaveDto;
import br.com.gva.quefominha.domain.dto.customer.CustomerSavedDto;
import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.enums.Role;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CustomerRepository;
import br.com.gva.quefominha.service.CustomerService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class CustomerServiceImpl implements CustomerService {

	@Getter
    @Autowired
    private CustomerRepository customerRepository;
	
	@Getter
	@Autowired
	MongoTemplate /*mongoTemplate*/ mongoOperations;
	
	@Override
    public <DTO> List<DTO> findAll() {
		return (List<DTO>) getCustomerRepository().findAll();
//		CustomerSavedDto dto = new CustomerSavedDto();
//        return getCustomerRepository().findAll().stream().map(customer -> (DTO) populateDto(customer, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	CustomerSavedDto dto = new CustomerSavedDto();
        Optional<Customer> customer = Optional.of(localFindById(id));
        return (DTO) populateDto(customer.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Customer localFindById(String id){
        Optional<Customer> customer = getCustomerRepository().findById(id);
        return customer.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Customer customer = new Customer();
    	encodePassword(dto);
        return (SAVED) populateDto(getCustomerRepository().save(populateEntity(dto, customer)), CustomerSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	
    	// ESTÁ SALVANDO, MAS IGNOTANDO O "PASSWORD"! Está sendo utilizado diretamente no "Controller"!
    	Customer customer = localFindById(id);
		Document document = new Document();
    	mongoOperations.getConverter().write(customer /*user*/, document);
    	Update update = new Update();
    	document.forEach(update::set);
    	
    	/*return*/ getMongoOperations().findAndModify(
    	            Query.query(Criteria.where("_id").is(id /*user.getId()*/)), update, Customer.class);
    	
        return (SAVED) populateDto(getCustomerRepository().save(populateEntity(dto, customer)), CustomerSavedDto.builder().build());
    	
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getCustomerRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getCustomerRepository().existsById(id);
    }
    
    public Boolean checkCurrentPassword(String id, String currentPassword) {
		Customer customer = localFindById(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Boolean matchedPassword = encoder.matches(currentPassword, customer.getPassword());
		
		return matchedPassword;
	}
	
	public void changePassword(String id, String newPassword) {
		Customer customer = localFindById(id);
		String encodedPassword = UtilitarioSistema.encodePassword(newPassword);
		
		Query query = new Query(new Criteria("_id").is(customer.getId()));
		Update update = new Update().set("password", encodedPassword);
		mongoOperations.updateFirst(query, update, "customer");
	}
    
    private void encodePassword(Object requestDto) {
    	String password = ((CustomerSaveDto) requestDto).getPassword();
		String encodedPassword = UtilitarioSistema.encodePassword(password);
		((CustomerSaveDto) requestDto).setPassword(encodedPassword);
		((CustomerSaveDto) requestDto).setRole(Role.USER);
	}

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}
