package com.unzatech.inventory.controller;

import com.unzatech.inventory.model.Product;
import com.unzatech.inventory.response.CategoryResponseRest;
import com.unzatech.inventory.response.ProductResponse;
import com.unzatech.inventory.response.ProductResponseRest;
import com.unzatech.inventory.services.IProductService;
import com.unzatech.inventory.util.CategoryExcelExporter;
import com.unzatech.inventory.util.ProductExcelExporter;
import com.unzatech.inventory.util.Util;
import jakarta.servlet.http.HttpServletResponse;
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

    /**
     * update product
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @param id
     * @return
     * @throws IOException
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> update(@RequestParam("picture")MultipartFile picture,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("price") BigDecimal price,
                                                    @RequestParam("quantity") int quantity,
                                                    @RequestParam("categoryId") Long categoryId,
                                                    @PathVariable Long id) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = service.update(product, id, categoryId);

        return response;
    }

    /**
     * export products excel
     * @param response
     * @throws IOException
     */
    @GetMapping("/products/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result-products";
        response.setHeader(headerKey, headerValue);
        ResponseEntity<ProductResponseRest> productResponse = service.search();
        ProductExcelExporter excelExporter = new ProductExcelExporter(productResponse.getBody().getProductResponse().getProduct());
        excelExporter.export(response);
    }
}
