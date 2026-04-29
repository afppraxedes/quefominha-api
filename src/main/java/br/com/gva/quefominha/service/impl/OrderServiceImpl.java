package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.order.OrderSavedDto;
import br.com.gva.quefominha.domain.entity.Order;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.OrderRepository;
import br.com.gva.quefominha.service.OrderService;
import lombok.Getter;

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class OrderServiceImpl implements OrderService {

    @Getter
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        OrderSavedDto dto = new OrderSavedDto();
        return getOrderRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        OrderSavedDto dto = new OrderSavedDto();
        Order entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
    }

    private Order localFindById(String id) {
        Optional<Order> result = getOrderRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Order entity = new Order();
        return (SAVED) populateDto(
            getOrderRepository().save(populateEntity(dto, entity)),
            OrderSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Order entity = localFindById(id);
        return (SAVED) populateDto(
            getOrderRepository().save(populateEntity(dto, entity)),
            OrderSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getOrderRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getOrderRepository().existsById(id);
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
        OrderSavedDto dto = new OrderSavedDto();
        return (Page<DTO>) getOrderRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
