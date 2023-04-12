package com.unzatech.inventory.response;

import com.unzatech.inventory.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<Product> product;
}
