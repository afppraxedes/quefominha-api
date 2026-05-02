package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.category.CategorySavedDto;
import br.com.gva.quefominha.domain.entity.Category;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CategoryRepository;
import br.com.gva.quefominha.service.CategoryService;
import lombok.Getter;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Getter
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getCategoryRepository().findAll().stream()
                .map(entity -> populateDto(entity, new CategorySavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        Category entity = localFindById(id);
        return (DTO) populateDto(entity, new CategorySavedDto());
    }

    private Category localFindById(String id) {
        Optional<Category> result = getCategoryRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Category entity = new Category();
        return (SAVED) populateDto(
            getCategoryRepository().save(populateEntity(dto, entity)),
            CategorySavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Category entity = localFindById(id);
        return (SAVED) populateDto(
            getCategoryRepository().save(populateEntity(dto, entity)),
            CategorySavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getCategoryRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getCategoryRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getCategoryRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new CategorySavedDto()));
    }
}
