package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.bag.BagSavedDto;
import br.com.gva.quefominha.domain.entity.Bag;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.BagRepository;
import br.com.gva.quefominha.service.BagService;
import lombok.Getter;

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class BagServiceImpl implements BagService {

    @Getter
    @Autowired
    private BagRepository bagRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        BagSavedDto dto = new BagSavedDto();
        return getBagRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        BagSavedDto dto = new BagSavedDto();
        Bag entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
    }

    private Bag localFindById(String id) {
        Optional<Bag> result = getBagRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Bag entity = new Bag();
        return (SAVED) populateDto(
            getBagRepository().save(populateEntity(dto, entity)),
            BagSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Bag entity = localFindById(id);
        return (SAVED) populateDto(
            getBagRepository().save(populateEntity(dto, entity)),
            BagSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getBagRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getBagRepository().existsById(id);
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
        BagSavedDto dto = new BagSavedDto();
        return (Page<DTO>) getBagRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
