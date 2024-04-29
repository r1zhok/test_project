package org.task.itms_db.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.itms_db.entity.CustomerEntity;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity newCustomer) {
        return ResponseEntity.ok(service.createCustomer(newCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity newCustomer) {
        return ResponseEntity.ok(service.updateCustomer(id, newCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return ResponseEntity.ok().body("Customer deleted");
    }
}
