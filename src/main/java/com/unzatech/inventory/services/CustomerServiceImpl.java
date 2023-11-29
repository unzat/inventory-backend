package com.unzatech.inventory.services;

import com.unzatech.inventory.dao.ICustomerDao;
import com.unzatech.inventory.model.Customer;
import com.unzatech.inventory.response.CustomerResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService{
    @Autowired
    private ICustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerResponseRest> list() {
        CustomerResponseRest response = new CustomerResponseRest();
        try {
            List<Customer> customer = (List<Customer>) customerDao.findAll();
            response.getCustomerResponse().setCustomer(customer);
            response.setMetadata("Respuesta ok","00","Respuesta exitosa");
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponseRest> find(Long id) {
        CustomerResponseRest response = new CustomerResponseRest();
        List<Customer> list = new ArrayList<>();
        try {
            Optional<Customer> customer = customerDao.findById(id);
            if (customer.isPresent()){
                list.add(customer.get());
                response.getCustomerResponse().setCustomer(list);
                response.setMetadata("Respuesta ok","00","Cliente encontrado");
            }else{
                response.setMetadata("Respuesta no ok","-1","Cliente no encontrado");
                return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar clientes por id");
            e.getStackTrace();
            return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponseRest> save(Customer customer) {
        CustomerResponseRest response = new CustomerResponseRest();
        List<Customer> list = new ArrayList<>();
        try {
            // save customer
            Customer customerSaved = customerDao.save(customer);

            if (customerSaved != null) {
                list.add(customerSaved);
                response.getCustomerResponse().setCustomer(list);
                response.setMetadata("Respuesta ok","00","Cliente guardado");
            } else {
                response.setMetadata("Respuesta no ok","-1","Cliente no guardado");
                return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al grabar cliente");
            e.getStackTrace();
            return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CustomerResponseRest>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponseRest> update(Customer customer, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CustomerResponseRest> deleteById(Long id) {
        return null;
    }
}
