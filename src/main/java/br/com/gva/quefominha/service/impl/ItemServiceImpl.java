package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.item.ItemSavedDto;
import br.com.gva.quefominha.domain.entity.Item;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ItemRepository;
import br.com.gva.quefominha.service.ItemService;
import lombok.Getter;

@Service
public class ItemServiceImpl implements ItemService {

    @Getter
    @Autowired
    private ItemRepository itemRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getItemRepository().findAll().stream()
                .map(entity -> populateDto(entity, new ItemSavedDto()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        Item entity = localFindById(id);
        return (DTO) populateDto(entity, new ItemSavedDto());
    }

    private Item localFindById(String id) {
        Optional<Item> result = getItemRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Item entity = new Item();
        return (SAVED) populateDto(
            getItemRepository().save(populateEntity(dto, entity)),
            ItemSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Item entity = localFindById(id);
        return (SAVED) populateDto(
            getItemRepository().save(populateEntity(dto, entity)),
            ItemSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getItemRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getItemRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getItemRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, new ItemSavedDto()));
    }
}
