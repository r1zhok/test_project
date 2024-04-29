package org.task.itms_db.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.itms_db.entity.EmployeeEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployee(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getAllEmployee() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeEntity> createEmployee(@RequestBody EmployeeEntity newEmployee) throws EmailExistException {
        return ResponseEntity.ok(service.createEmployee(newEmployee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity newEmployee) throws EmailExistException {
        return ResponseEntity.ok(service.updateEmployee(id, newEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted");
    }
}
