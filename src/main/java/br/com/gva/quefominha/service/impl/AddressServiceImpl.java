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
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.address.AddressSaveDto;
import br.com.gva.quefominha.domain.dto.address.AddressSavedDto;
import br.com.gva.quefominha.domain.dto.customer.CustomerSavedDto;
import br.com.gva.quefominha.domain.entity.Address;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.AddressRepository;
import br.com.gva.quefominha.service.AddressService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class AddressServiceImpl implements AddressService {

	@Getter
    @Autowired
    private AddressRepository addressRepository;
	
	@Getter
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Getter
	@Autowired
	MongoTemplate mongoOperations;
	

	@Override
    public <DTO> List<DTO> findAll() {
		return (List<DTO>) getAddressRepository().findAll();
//        AddressSavedDto dto = new AddressSavedDto();
//        return getAddressRepository().findAll().stream().map(product -> (DTO) populateDto(product, dto)).collect(Collectors.toList());
    }
	
	public <DTO> List<DTO> listByCustomer(String id) {
		Optional<List<Address>> addressList = Optional.ofNullable(getAddressRepository().findByCustomer(id)
				.orElseThrow(() -> new NegocioException(String.format("Objeto de id %s não encontrado", id))));
		
		return (List<DTO>) addressList.get();
	}

    @Override
    public <DTO> DTO findById(String id) {
    	AddressSavedDto dto = new AddressSavedDto();
        Optional<Address> address = Optional.of(localFindById(id));
        return (DTO) populateDto(address.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }
    
    private Address localFindById(String id){
        Optional<Address> address = getAddressRepository().findById(id);
        return address.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Address address = new Address();
    	String customerId = ((AddressSaveDto) dto).getCustomer().getId();
    	//CustomerSavedDto customer = this.findByCustomer(customerId);
        setAddressAsPrimaryFirstRecord(dto, customerId);
        return (SAVED) populateDto(getAddressRepository().save(populateEntity(dto, address)), AddressSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Address address = localFindById(id);
		Document document = new Document();
    	mongoOperations.getConverter().write(address /*user*/, document);
    	Update update = new Update();
    	document.forEach(update::set);
    	
    	/*return*/ getMongoOperations().findAndModify(
    	            Query.query(Criteria.where("_id").is(id /*user.getId()*/)), update, Address.class);
    	
        return (SAVED) populateDto(getAddressRepository().save(populateEntity(dto, address)), AddressSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
        	Address adrress = localFindById(id);
        	String customerId = adrress.getCustomer().getId();
            getAddressRepository().deleteById(id); 
            setPrimaryAddressAfterDelete(customerId, adrress);
        }
    }

    public boolean existsItem(String id){
        return getAddressRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
	
	private void setPrimaryAddressAfterDelete(String customerId, Address foundAdrress) {
		List<Address> adrressList = listByCustomer(customerId);
		Integer listSize = adrressList.size();
		
		if(listSize >= 1) {
			Address address = adrressList.get(0);
			address.setPrimary(true);
			getAddressRepository().save(address);
		}
	}
	
	private void setAddressAsNotPrimary(String customerId, Boolean primary) {
		List<Address> addressList = listByCustomer(customerId);
		for(Address address : addressList) {
			if(address.getPrimary()) {
				address.setPrimary(false);
			} 

			getAddressRepository().save(address);
		}
	}
	
//	@Transactional
	public void updatePrimaryAddress(String id, Boolean primary) {
		Address address = localFindById(id);
		setAddressAsNotPrimary(address.getCustomer().getId(), primary);
		address.setPrimary(primary);
		getAddressRepository().save(address);
	}
	
	private void setAddressAsPrimaryFirstRecord(Object dto, String customerId) {
		
		Boolean primary = ((AddressSaveDto) dto).getPrimary();
		
		if(totalRecordsPerCustomer(customerId) == 0 || (totalRecordsPerCustomer(customerId) == 0 && !primary)) {
			((AddressSaveDto) dto).setPrimary(true);
		} else {
			if(primary) {
				((AddressSaveDto) dto).setPrimary(true);
			} else {
				((AddressSaveDto) dto).setPrimary(false);
			}
		}
	}
	
	// TODO: verificar se a consulta está correta!
	public <DTO> DTO searchPrimaryAddressByCustomer(String id, Boolean primary) {
		Optional<Address> addressList = 
				getAddressRepository().findByCustomerAndPrimary(id, primary);
		
		if(!addressList.isPresent()) {
			throw new NegocioException(String.format("Não existe um endereço vinculado como principal com o cliente de código %d", id));
		}
		
		return (DTO) addressList.get();
	}
			
	// TODO: verificar se a consulta está correta!
	public List<Address> listAddressByZipcodeAndCustomer(String id, String zipcode) {
		Optional<List<Address>> addressList = 
				getAddressRepository().findByCustomerAndZipCode(id, zipcode);
		
		if(addressList.isEmpty()) {
			throw new NegocioException(String.format("Não existe um endereço vinculado com o clinete de código %d", id));
		}
		
		return addressList.get();
	}
	
	// TODO: verificar se a consulta está correta!
	public CustomerSavedDto findByCustomer(String id) {
		return getCustomerServiceImpl().findById(id);
	}
	
	private Integer totalRecordsPerCustomer(String id) {
		Optional<List<Address>> addressList = getAddressRepository().findByCustomer(id);
		return addressList.get().size();
	}
}



