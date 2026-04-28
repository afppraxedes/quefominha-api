package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.bag.BagSavedDto;
import br.com.gva.quefominha.domain.entity.Bag;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.BagRepository;
import br.com.gva.quefominha.service.BagService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class BagServiceImpl implements BagService {

	@Getter
    @Autowired
    private BagRepository bagRepository;
	
	@Override
    public <DTO> List<DTO> findAll() {
		BagSavedDto dto = new BagSavedDto();
        return getBagRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	BagSavedDto dto = new BagSavedDto();
        Optional<Bag> bag = Optional.of(localFindById(id));
        return (DTO) populateDto(bag.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Bag localFindById(String id){
        Optional<Bag> bag = getBagRepository().findById(id);
        return bag.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Bag bag = new Bag();
        return (SAVED) populateDto(getBagRepository().save(populateEntity(dto, bag)), BagSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Bag bag = localFindById(id);
        return (SAVED) populateDto(getBagRepository().save(populateEntity(dto, bag)), BagSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getBagRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getBagRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
	
}
