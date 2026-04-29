package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class ContactServiceImpl implements ContactService {

    @Getter
    @Autowired
    private ContactRepository contactRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        ContactSavedDto dto = new ContactSavedDto();
        return getContactRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        ContactSavedDto dto = new ContactSavedDto();
        Contact entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
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

    /**
     * CORREÇÃO findPage: implementação funcional substituindo o retorno null anterior.
     * Usa o método buildPageRequest() herdado de ServiceUtil para padronização.
     * O MongoRepository já oferece findAll(Pageable) nativamente.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        ContactSavedDto dto = new ContactSavedDto();
        return (Page<DTO>) getContactRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
