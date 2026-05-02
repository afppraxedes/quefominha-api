package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

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

@Service
public class SubProductGroupServiceImpl implements SubProductGroupService {

    @Getter
    @Autowired
    private SubProductGroupRepository subProductGroupRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getSubProductGroupRepository().findAll().stream()
                .map(entity -> populateDto(entity, new SubProductGroupSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        SubProductGroup entity = localFindById(id);
        return (DTO) populateDto(entity, new SubProductGroupSavedDto());
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

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getSubProductGroupRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new SubProductGroupSavedDto()));
    }
}
