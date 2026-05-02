package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.productgroup.ProductGroupSavedDto;
import br.com.gva.quefominha.domain.entity.ProductGroup;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ProductGroupRepository;
import br.com.gva.quefominha.service.ProductGroupService;
import lombok.Getter;

@Service
public class ProductGroupServiceImpl implements ProductGroupService {

    @Getter
    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getProductGroupRepository().findAll().stream()
                .map(entity -> populateDto(entity, new ProductGroupSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        ProductGroup entity = localFindById(id);
        return (DTO) populateDto(entity, new ProductGroupSavedDto());
    }

    private ProductGroup localFindById(String id) {
        Optional<ProductGroup> result = getProductGroupRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        ProductGroup entity = new ProductGroup();
        return (SAVED) populateDto(
            getProductGroupRepository().save(populateEntity(dto, entity)),
            ProductGroupSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        ProductGroup entity = localFindById(id);
        return (SAVED) populateDto(
            getProductGroupRepository().save(populateEntity(dto, entity)),
            ProductGroupSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getProductGroupRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getProductGroupRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getProductGroupRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new ProductGroupSavedDto()));
    }
}
