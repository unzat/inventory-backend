package com.unzatech.inventory.response;

import com.unzatech.inventory.model.Customer;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private List<Customer> customer;
}
