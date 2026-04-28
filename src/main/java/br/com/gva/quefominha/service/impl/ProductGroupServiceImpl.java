package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.productgroup.ProductGroupSavedDto;
import br.com.gva.quefominha.domain.entity.ProductGroup;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ProductGroupRepository;
import br.com.gva.quefominha.service.ProductGroupService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class ProductGroupServiceImpl implements ProductGroupService {

    @Getter
    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Override
    public <DTO> List<DTO> findAll() {
        ProductGroupSavedDto dto = new ProductGroupSavedDto();
        return getProductGroupRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	ProductGroupSavedDto dto = new ProductGroupSavedDto();
        Optional<ProductGroup> productGroup = Optional.of(localFindById(id));
        return (DTO) populateDto(productGroup.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private ProductGroup localFindById(String id){
        Optional<ProductGroup> productGroup = getProductGroupRepository().findById(id);
        return productGroup.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	ProductGroup productGroup = new ProductGroup();
        return (SAVED) populateDto(getProductGroupRepository().save(populateEntity(dto, productGroup)), ProductGroupSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	ProductGroup productGroup = localFindById(id);
        return (SAVED) populateDto(getProductGroupRepository().save(populateEntity(dto, productGroup)), ProductGroupSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getProductGroupRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getProductGroupRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}  