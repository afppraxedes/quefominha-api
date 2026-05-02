package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.subproduct.SubProductSavedDto;
import br.com.gva.quefominha.domain.entity.SubProduct;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.SubProductRepository;
import br.com.gva.quefominha.service.SubProductService;
import lombok.Getter;

@Service
public class SubProductServiceImpl implements SubProductService {

    @Getter
    @Autowired
    private SubProductRepository subProductRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getSubProductRepository().findAll().stream()
                .map(entity -> populateDto(entity, new SubProductSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        SubProduct entity = localFindById(id);
        return (DTO) populateDto(entity, new SubProductSavedDto());
    }

    private SubProduct localFindById(String id) {
        Optional<SubProduct> result = getSubProductRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        SubProduct entity = new SubProduct();
        return (SAVED) populateDto(
            getSubProductRepository().save(populateEntity(dto, entity)),
            SubProductSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        SubProduct entity = localFindById(id);
        return (SAVED) populateDto(
            getSubProductRepository().save(populateEntity(dto, entity)),
            SubProductSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getSubProductRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getSubProductRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getSubProductRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new SubProductSavedDto()));
    }
}
