package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.bag.BagSavedDto;
import br.com.gva.quefominha.domain.entity.Bag;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.BagRepository;
import br.com.gva.quefominha.service.BagService;
import lombok.Getter;

@Service
public class BagServiceImpl implements BagService {

    @Getter
    @Autowired
    private BagRepository bagRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getBagRepository().findAll().stream()
                .map(entity -> populateDto(entity, new BagSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        Bag entity = localFindById(id);
        return (DTO) populateDto(entity, new BagSavedDto());
    }

    private Bag localFindById(String id) {
        Optional<Bag> result = getBagRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Bag entity = new Bag();
        return (SAVED) populateDto(
            getBagRepository().save(populateEntity(dto, entity)),
            BagSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Bag entity = localFindById(id);
        return (SAVED) populateDto(
            getBagRepository().save(populateEntity(dto, entity)),
            BagSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getBagRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getBagRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getBagRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new BagSavedDto()));
    }
}
