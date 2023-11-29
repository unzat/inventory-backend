package com.unzatech.inventory.dao;

import com.unzatech.inventory.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerDao extends CrudRepository<Customer, Long> {
}
