package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.bag.BagSavedDto;
import br.com.gva.quefominha.domain.dto.openinghours.OpeningHoursSavedDto;
import br.com.gva.quefominha.domain.entity.Bag;
import br.com.gva.quefominha.domain.entity.OpeningHours;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.OpeningHoursRepository;
import br.com.gva.quefominha.service.OpeningHoursService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class OpeningHoursServiceImpl implements OpeningHoursService {

	@Getter
	@Autowired
	private OpeningHoursRepository openingHoursRepository;
	
	@Override
    public <DTO> List<DTO> findAll() {
		OpeningHoursSavedDto dto = new OpeningHoursSavedDto();
        return getOpeningHoursRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	OpeningHoursSavedDto dto = new OpeningHoursSavedDto();
        Optional<OpeningHours> openingHours = Optional.of(localFindById(id));
        return (DTO) populateDto(openingHours.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private OpeningHours localFindById(String id){
        Optional<OpeningHours> bag = getOpeningHoursRepository().findById(id);
        return bag.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	OpeningHours openingHours = new OpeningHours();
        return (SAVED) populateDto(getOpeningHoursRepository().save(populateEntity(dto, openingHours)), OpeningHoursSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	OpeningHours bag = localFindById(id);
        return (SAVED) populateDto(getOpeningHoursRepository().save(populateEntity(dto, bag)), OpeningHoursSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getOpeningHoursRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getOpeningHoursRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
	
}
