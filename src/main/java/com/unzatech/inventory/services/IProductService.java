package com.unzatech.inventory.services;

import com.unzatech.inventory.model.Product;
import com.unzatech.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IProductService {

    @Transactional
    ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);

}
