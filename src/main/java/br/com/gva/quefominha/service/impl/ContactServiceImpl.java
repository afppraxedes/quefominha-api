package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.contact.ContactSavedDto;
import br.com.gva.quefominha.domain.entity.Contact;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ContactRepository;
import br.com.gva.quefominha.service.ContactService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class ContactServiceImpl implements ContactService {

	@Getter
    @Autowired
    private ContactRepository contactRepository;
	
	@Override
    public <DTO> List<DTO> findAll() {
		ContactSavedDto dto = new ContactSavedDto();
        return getContactRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	ContactSavedDto dto = new ContactSavedDto();
        Optional<Contact> contact = Optional.of(localFindById(id));
        return (DTO) populateDto(contact.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Contact localFindById(String id){
        Optional<Contact> bag = getContactRepository().findById(id);
        return bag.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Contact contact = new Contact();
        return (SAVED) populateDto(getContactRepository().save(populateEntity(dto, contact)), ContactSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Contact contact = localFindById(id);
        return (SAVED) populateDto(getContactRepository().save(populateEntity(dto, contact)), ContactSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getContactRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getContactRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}
