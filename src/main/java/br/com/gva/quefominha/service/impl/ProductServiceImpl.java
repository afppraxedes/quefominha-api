package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.product.ProductSavedDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.ProductRepository;
import br.com.gva.quefominha.service.ProductService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class ProductServiceImpl implements ProductService {

	@Getter
    @Autowired
    private ProductRepository productRepository;

	@Override
    public <DTO> List<DTO> findAll() {
		return (List<DTO>) getProductRepository().findAll();
//        ProductSavedDto dto = new ProductSavedDto();
//        return getProductRepository().findAll().stream().map(product -> (DTO) populateDto(product, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
        ProductSavedDto dto = new ProductSavedDto();
        Optional<Product> product = Optional.of(localFindById(id));
        return (DTO) populateDto(product.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }
    
    // Lista produtos por ID do Restaurant
    public <DTO> List<DTO> findProductByRestaurantId(String restaurantId) {
        // ProductSavedDto dto = new ProductSavedDto();
        Optional<List<Product>> products = Optional.of(localFindProductsByRestaurantId(restaurantId));
        return (List<DTO>) (products.orElseThrow(() ->  new NegocioException(String.format("Objeto Restauranttt de id %s não encontrado", restaurantId))));
    }

    private Product localFindById(String id){
        Optional<Product> product = getProductRepository().findById(id);
        return product.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }
    
    // Busca uma lista de produtos por ID do Restaurant
    // TODO: O "Tratamento de Exceção" está sendo efetuado 2 vezes (aqui e em "findProductByRestaurantId"). Rever isto!!!!!
    private List<Product> localFindProductsByRestaurantId(String id){
        Optional<List<Product>> products = getProductRepository().findProductByRestaurantId(id);
        return products.orElseThrow(() ->  new NegocioException(String.format("Objeto Restaurant de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    Product product = new Product();
        return (SAVED) populateDto(getProductRepository().save(populateEntity(dto, product)), ProductSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Product product = localFindById(id);
        return (SAVED) populateDto(getProductRepository().save(populateEntity(dto, product)), ProductSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getProductRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getProductRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
}  

