package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.category.CategorySavedDto;
import br.com.gva.quefominha.domain.entity.Category;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CategoryRepository;
import br.com.gva.quefominha.service.CategoryService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class CategoryServiceImpl implements CategoryService {

	@Getter
    @Autowired
    private CategoryRepository categoryRepository;
	
	@Override
    public <DTO> List<DTO> findAll() {
		CategorySavedDto dto = new CategorySavedDto();
        return getCategoryRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	CategorySavedDto dto = new CategorySavedDto();
        Optional<Category> category = Optional.of(localFindById(id));
        return (DTO) populateDto(category.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Category localFindById(String id){
        Optional<Category> category = getCategoryRepository().findById(id);
        return category.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Category category = new Category();
        return (SAVED) populateDto(getCategoryRepository().save(populateEntity(dto, category)), CategorySavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Category category = localFindById(id);
        return (SAVED) populateDto(getCategoryRepository().save(populateEntity(dto, category)), CategorySavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getCategoryRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getCategoryRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}