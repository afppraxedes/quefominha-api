package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.product.ProductSavedDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ProductRepository;
import br.com.gva.quefominha.service.ProductService;
import lombok.Getter;

@Service
public class ProductServiceImpl implements ProductService {

    @Getter
    @Autowired
    private ProductRepository productRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getProductRepository().findAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        ProductSavedDto dto = new ProductSavedDto();
        Product product = localFindById(id);
        return (DTO) populateDto(product, dto);
    }

    /**
     * CORREÇÃO: tratamento de exceção unificado em localFindProductsByRestaurantId,
     * eliminando a dupla verificação que existia antes (TODO no código original).
     */
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findProductByRestaurantId(String restaurantId) {
        List<Product> products = localFindProductsByRestaurantId(restaurantId);
        return (List<DTO>) products;
    }

    private Product localFindById(String id) {
        Optional<Product> product = getProductRepository().findById(id);
        return product.orElseThrow(
            () -> new NegocioException(String.format("Produto de id %s não encontrado", id))
        );
    }

    private List<Product> localFindProductsByRestaurantId(String restaurantId) {
        return getProductRepository().findProductByRestaurantId(restaurantId)
                .orElseThrow(() -> new NegocioException(
                    String.format("Nenhum produto encontrado para o restaurante de id %s", restaurantId)
                ));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Product product = new Product();
        return (SAVED) populateDto(
            getProductRepository().save(populateEntity(dto, product)),
            ProductSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Product product = localFindById(id);
        return (SAVED) populateDto(
            getProductRepository().save(populateEntity(dto, product)),
            ProductSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getProductRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getProductRepository().existsById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        ProductSavedDto dto = new ProductSavedDto();
        return (Page<DTO>) getProductRepository().findAll(pageRequest)
                .map(product -> populateDto(product, dto));
    }
}
