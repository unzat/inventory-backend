package com.unzatech.inventory.controller;

import com.unzatech.inventory.enums.DocumentType;
import com.unzatech.inventory.model.Category;
import com.unzatech.inventory.model.Customer;
import com.unzatech.inventory.response.CategoryResponseRest;
import com.unzatech.inventory.response.CustomerResponse;
import com.unzatech.inventory.response.CustomerResponseRest;
import com.unzatech.inventory.services.ICategoryService;
import com.unzatech.inventory.services.ICustomerService;
import com.unzatech.inventory.util.CategoryExcelExporter;
import com.unzatech.inventory.util.CustomerExcelExporter;
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
public class CustomerRestController {
    @Autowired
    private ICustomerService service;

    /**
     * get all customers
     * @return
     */
    @GetMapping("/customers")
    public ResponseEntity<CustomerResponseRest> searchCustomers() {
        ResponseEntity<CustomerResponseRest> response = service.list();
        return response;
    }

    /**
     * find customer
     * @param id
     * @return
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseRest> searchCustomersById(@PathVariable Long id) {
        ResponseEntity<CustomerResponseRest> response = service.find(id);
        return response;
    }

    /**
     * save customer
     * @return
     * @throws IOException
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseRest> save(@RequestBody Customer customer) {
        ResponseEntity<CustomerResponseRest> response = service.save(customer);
        return response;
    }

    /**
     * update customer
     * @param customer
     * @param id
     * @return
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseRest> update(@RequestBody Customer customer, @PathVariable Long id) {
        ResponseEntity<CustomerResponseRest> response = service.update(customer, id);
        return response;
    }

    /**
     * delete customer
     * @param id
     * @return
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<CustomerResponseRest> response = service.deleteById(id);
        return response;
    }

    /**
     * export customers to excel
     * @param response
     * @throws IOException
     */
    @GetMapping("/customers/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result-categories";
        response.setHeader(headerKey, headerValue);
        ResponseEntity<CustomerResponseRest> customerResponse = service.list();
        CustomerExcelExporter excelExporter = new CustomerExcelExporter(customerResponse.getBody().getCustomerResponse().getCustomer());
        excelExporter.export(response);
    }
}
