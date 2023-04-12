package com.unzatech.inventory.services;

import com.unzatech.inventory.model.Product;
import com.unzatech.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IProductService {
    public ResponseEntity<ProductResponseRest> search();
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> update(Product product, Long id, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
    public ResponseEntity<ProductResponseRest> searchNyName(String name);
    public ResponseEntity<ProductResponseRest> deleteById(Long id);
}
