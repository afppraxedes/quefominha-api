package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.item.ItemSavedDto;
import br.com.gva.quefominha.domain.entity.Item;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ItemRepository;
import br.com.gva.quefominha.service.ItemService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class ItemServiceImpl implements ItemService {

	@Getter
	@Autowired
    private ItemRepository itemRepository;

	@Override
    public <DTO> List<DTO> findAll() {
        ItemSavedDto dto = new ItemSavedDto();
        return getItemRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	ItemSavedDto dto = new ItemSavedDto();
        Optional<Item> item = Optional.of(localFindById(id));
        return (DTO) populateDto(item.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Item localFindById(String id){
        Optional<Item> item = getItemRepository().findById(id);
        return item.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Item item = new Item();
        return (SAVED) populateDto(getItemRepository().save(populateEntity(dto, item)), ItemSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Item item = localFindById(id);
        return (SAVED) populateDto(getItemRepository().save(populateEntity(dto, item)), ItemSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getItemRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getItemRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
	
}
