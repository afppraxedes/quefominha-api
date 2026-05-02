package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.openinghours.OpeningHoursSavedDto;
import br.com.gva.quefominha.domain.entity.OpeningHours;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.OpeningHoursRepository;
import br.com.gva.quefominha.service.OpeningHoursService;
import lombok.Getter;

@Service
public class OpeningHoursServiceImpl implements OpeningHoursService {

    @Getter
    @Autowired
    private OpeningHoursRepository openingHoursRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getOpeningHoursRepository().findAll().stream()
                .map(entity -> populateDto(entity, new OpeningHoursSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        OpeningHours entity = localFindById(id);
        return (DTO) populateDto(entity, new OpeningHoursSavedDto());
    }

    private OpeningHours localFindById(String id) {
        Optional<OpeningHours> result = getOpeningHoursRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        OpeningHours entity = new OpeningHours();
        return (SAVED) populateDto(
            getOpeningHoursRepository().save(populateEntity(dto, entity)),
            OpeningHoursSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        OpeningHours entity = localFindById(id);
        return (SAVED) populateDto(
            getOpeningHoursRepository().save(populateEntity(dto, entity)),
            OpeningHoursSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getOpeningHoursRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getOpeningHoursRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getOpeningHoursRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new OpeningHoursSavedDto()));
    }
}
