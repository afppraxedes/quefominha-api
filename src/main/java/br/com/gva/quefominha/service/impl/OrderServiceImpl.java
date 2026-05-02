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

@Service
public class OrderServiceImpl implements OrderService {

    @Getter
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getOrderRepository().findAll().stream()
                .map(order -> populateDto(order, new OrderSavedDto()))
                .collect(Collectors.toList());
    }

    // Histórico de pedidos por customer — usado pelo OrderController
    public List<OrderSavedDto> findByCustomerId(String customerId) {
        return getOrderRepository().findByCustomerId(customerId).stream()
                .map(order -> (OrderSavedDto) populateDto(order, new OrderSavedDto()))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        return (DTO) populateDto(localFindById(id), new OrderSavedDto());
    }

    private Order localFindById(String id) {
        Optional<Order> order = getOrderRepository().findById(id);
        return order.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Order order = new Order();
        return (SAVED) populateDto(
            getOrderRepository().save(populateEntity(dto, order)),
            OrderSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Order order = localFindById(id);
        return (SAVED) populateDto(
            getOrderRepository().save(populateEntity(dto, order)),
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

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        return (Page<DTO>) getOrderRepository().findAll(pageRequest)
                .map(order -> populateDto(order, new OrderSavedDto()));
    }
}
