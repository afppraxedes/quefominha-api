package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.subproductgroup.SubProductGroupSavedDto;
import br.com.gva.quefominha.domain.entity.SubProductGroup;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.SubProductGroupRepository;
import br.com.gva.quefominha.service.SubProductGroupService;
import lombok.Getter;

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class SubProductGroupServiceImpl implements SubProductGroupService {

    @Getter
    @Autowired
    private SubProductGroupRepository subProductGroupRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        SubProductGroupSavedDto dto = new SubProductGroupSavedDto();
        return getSubProductGroupRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        SubProductGroupSavedDto dto = new SubProductGroupSavedDto();
        SubProductGroup entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
    }

    private SubProductGroup localFindById(String id) {
        Optional<SubProductGroup> result = getSubProductGroupRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        SubProductGroup entity = new SubProductGroup();
        return (SAVED) populateDto(
            getSubProductGroupRepository().save(populateEntity(dto, entity)),
            SubProductGroupSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        SubProductGroup entity = localFindById(id);
        return (SAVED) populateDto(
            getSubProductGroupRepository().save(populateEntity(dto, entity)),
            SubProductGroupSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getSubProductGroupRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getSubProductGroupRepository().existsById(id);
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
        SubProductGroupSavedDto dto = new SubProductGroupSavedDto();
        return (Page<DTO>) getSubProductGroupRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
