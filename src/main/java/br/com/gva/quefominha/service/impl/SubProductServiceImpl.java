package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.subproduct.SubProductSavedDto;
import br.com.gva.quefominha.domain.entity.SubProduct;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.SubProductRepository;
import br.com.gva.quefominha.service.SubProductService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class SubProductServiceImpl implements SubProductService {

    @Getter
    @Autowired
    private SubProductRepository subProductRepository;

    @Override
    public <DTO> List<DTO> findAll() {
		SubProductSavedDto dto = new SubProductSavedDto();
        return getSubProductRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	SubProductSavedDto dto = new SubProductSavedDto();
        Optional<SubProduct> subProduct = Optional.of(localFindById(id));
        return (DTO) populateDto(subProduct.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private SubProduct localFindById(String id){
        Optional<SubProduct> subProduct = getSubProductRepository().findById(id);
        return subProduct.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	SubProduct subProduct = new SubProduct();
        return (SAVED) populateDto(getSubProductRepository().save(populateEntity(dto, subProduct)), SubProductSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	SubProduct subProduct = localFindById(id);
        return (SAVED) populateDto(getSubProductRepository().save(populateEntity(dto, subProduct)), SubProductSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getSubProductRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getSubProductRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}  