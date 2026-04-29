package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.subproduct.SubProductSavedDto;
import br.com.gva.quefominha.domain.entity.SubProduct;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.SubProductRepository;
import br.com.gva.quefominha.service.SubProductService;
import lombok.Getter;

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class SubProductServiceImpl implements SubProductService {

    @Getter
    @Autowired
    private SubProductRepository subProductRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        SubProductSavedDto dto = new SubProductSavedDto();
        return getSubProductRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        SubProductSavedDto dto = new SubProductSavedDto();
        SubProduct entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
    }

    private SubProduct localFindById(String id) {
        Optional<SubProduct> result = getSubProductRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        SubProduct entity = new SubProduct();
        return (SAVED) populateDto(
            getSubProductRepository().save(populateEntity(dto, entity)),
            SubProductSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        SubProduct entity = localFindById(id);
        return (SAVED) populateDto(
            getSubProductRepository().save(populateEntity(dto, entity)),
            SubProductSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getSubProductRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getSubProductRepository().existsById(id);
    }

    /**
     * CORREÇÃO findPage: implementação funcional substituindo o retorno null anterior.
     * Usa o método buildPageRequest() herdado de ServiceUtil para padronização.
     * O MongoRepository já oferece findAll(Pageable) nativamente.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        SubProductSavedDto dto = new SubProductSavedDto();
        return (Page<DTO>) getSubProductRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
