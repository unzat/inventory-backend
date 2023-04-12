package com.unzatech.inventory.dao;

import com.unzatech.inventory.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductDao extends CrudRepository<Product, Long> {
}
