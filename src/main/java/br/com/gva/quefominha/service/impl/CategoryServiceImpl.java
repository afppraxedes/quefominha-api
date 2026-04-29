package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class CategoryServiceImpl implements CategoryService {

    @Getter
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        CategorySavedDto dto = new CategorySavedDto();
        return getCategoryRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        CategorySavedDto dto = new CategorySavedDto();
        Category entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
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

    /**
     * CORREÇÃO findPage: implementação funcional substituindo o retorno null anterior.
     * Usa o método buildPageRequest() herdado de ServiceUtil para padronização.
     * O MongoRepository já oferece findAll(Pageable) nativamente.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        CategorySavedDto dto = new CategorySavedDto();
        return (Page<DTO>) getCategoryRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
