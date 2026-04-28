package br.com.gva.quefominha.populators.impl;

import org.springframework.beans.BeanUtils;

import br.com.gva.quefominha.domain.dto.product.ProductSaveDto;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.populators.Populator;

public class ProductSavePopulator  implements Populator<ProductSaveDto, Product>{

    @Override
    public Product populate(ProductSaveDto source, Product target) {
        BeanUtils.copyProperties(source, target);
        return target;  
    }    
}