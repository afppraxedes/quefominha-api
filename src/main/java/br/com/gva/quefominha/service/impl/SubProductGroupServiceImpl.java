package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.subproductgroup.SubProductGroupSavedDto;
import br.com.gva.quefominha.domain.entity.SubProductGroup;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.SubProductGroupRepository;
import br.com.gva.quefominha.service.SubProductGroupService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class SubProductGroupServiceImpl implements SubProductGroupService {

	@Getter
    @Autowired
    private SubProductGroupRepository subProductGroupRepository;

	@Override
    public <DTO> List<DTO> findAll() {
		SubProductGroupSavedDto dto = new SubProductGroupSavedDto();
        return getSubProductGroupRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	SubProductGroupSavedDto dto = new SubProductGroupSavedDto();
        Optional<SubProductGroup> subProductGroup = Optional.of(localFindById(id));
        return (DTO) populateDto(subProductGroup.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private SubProductGroup localFindById(String id){
        Optional<SubProductGroup> subProductGroup = getSubProductGroupRepository().findById(id);
        return subProductGroup.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	SubProductGroup subProductGroup = new SubProductGroup();
        return (SAVED) populateDto(getSubProductGroupRepository().save(populateEntity(dto, subProductGroup)), SubProductGroupSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	SubProductGroup subProductGroup = localFindById(id);
        return (SAVED) populateDto(getSubProductGroupRepository().save(populateEntity(dto, subProductGroup)), SubProductGroupSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getSubProductGroupRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getSubProductGroupRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
	
}
