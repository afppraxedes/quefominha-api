package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.contact.ContactSavedDto;
import br.com.gva.quefominha.domain.entity.Contact;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ContactRepository;
import br.com.gva.quefominha.service.ContactService;
import lombok.Getter;

@Service
public class ContactServiceImpl implements ContactService {

    @Getter
    @Autowired
    private ContactRepository contactRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getContactRepository().findAll().stream()
                .map(entity -> populateDto(entity, new ContactSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        Contact entity = localFindById(id);
        return (DTO) populateDto(entity, new ContactSavedDto());
    }

    private Contact localFindById(String id) {
        Optional<Contact> result = getContactRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Contact entity = new Contact();
        return (SAVED) populateDto(
            getContactRepository().save(populateEntity(dto, entity)),
            ContactSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Contact entity = localFindById(id);
        return (SAVED) populateDto(
            getContactRepository().save(populateEntity(dto, entity)),
            ContactSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getContactRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getContactRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getContactRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new ContactSavedDto()));
    }
}
