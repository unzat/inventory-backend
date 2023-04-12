package com.unzatech.inventory.controller;

import com.unzatech.inventory.model.Product;
import com.unzatech.inventory.response.CategoryResponseRest;
import com.unzatech.inventory.response.ProductResponseRest;
import com.unzatech.inventory.services.IProductService;
import com.unzatech.inventory.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
    @Autowired
    private IProductService service;

    /**
     * save products
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(@RequestParam("picture")MultipartFile picture,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("price") BigDecimal price,
                                                    @RequestParam("quantity") int quantity,
                                                    @RequestParam("categoryId") Long categoryId) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = service.save(product, categoryId);

        return response;
    }

    /**
     * search products by id
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchProductsById(@PathVariable Long id) {
        ResponseEntity<ProductResponseRest> response = service.searchById(id);
        return response;
    }

    /**
     * search products by name
     * @param name
     * @return
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchProductsByName(@PathVariable String name) {
        ResponseEntity<ProductResponseRest> response = service.searchNyName(name);
        return response;
    }

    /**
     * search products
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> search(){
        ResponseEntity<ProductResponseRest> response = service.search();
        return response;
    }

    /**
     * delete product by id
     * @param id
     * @return
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id) {
        ResponseEntity<ProductResponseRest> response = service.deleteById(id);
        return response;
    }
}
