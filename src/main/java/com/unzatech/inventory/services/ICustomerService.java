package com.unzatech.inventory.services;

import com.unzatech.inventory.model.Customer;
import com.unzatech.inventory.response.CustomerResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICustomerService {
    public ResponseEntity<CustomerResponseRest> list();
    public ResponseEntity<CustomerResponseRest> find(Long id);
    public ResponseEntity<CustomerResponseRest> save(Customer customer);
    public ResponseEntity<CustomerResponseRest> update(Customer customer, Long id);
    public ResponseEntity<CustomerResponseRest> deleteById(Long id);
}
