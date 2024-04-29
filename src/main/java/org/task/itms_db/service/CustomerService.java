package org.task.itms_db.service;

import org.springframework.stereotype.Service;
import org.task.itms_db.entity.CustomerEntity;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerEntity getCustomer(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Користувач не найдений"));
    }

    public List<CustomerEntity> getAllCustomers() {
        return repository.findAll();
    }

    public CustomerEntity createCustomer(CustomerEntity newCustomer) {
        return repository.save(newCustomer);
    }

    public CustomerEntity updateCustomer(Long id, CustomerEntity newCustomer) {
        newCustomer.setId(id);
        return repository.save(newCustomer);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
