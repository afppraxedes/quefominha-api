package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.order.OrderSavedDto;
import br.com.gva.quefominha.domain.entity.Order;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.OrderRepository;
import br.com.gva.quefominha.service.OrderService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class OrderServiceImpl implements OrderService {

    @Getter
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public <DTO> List<DTO> findAll() {
        OrderSavedDto dto = new OrderSavedDto();
        return getOrderRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	OrderSavedDto dto = new OrderSavedDto();
        Optional<Order> order = Optional.of(localFindById(id));
        return (DTO) populateDto(order.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Order localFindById(String id){
        Optional<Order> order = getOrderRepository().findById(id);
        return order.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Order order = new Order();
        return (SAVED) populateDto(getOrderRepository().save(populateEntity(dto, order)), OrderSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Order order = localFindById(id);
        return (SAVED) populateDto(getOrderRepository().save(populateEntity(dto, order)), OrderSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getOrderRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getOrderRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}