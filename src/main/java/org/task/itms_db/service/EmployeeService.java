package org.task.itms_db.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.task.itms_db.entity.EmployeeEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public EmployeeEntity getEmployee(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Користувач не найдений"));
    }

    public List<EmployeeEntity> getAllEmployees() {
        return repository.findAll();
    }

    public EmployeeEntity createEmployee(EmployeeEntity newEmployee) throws EmailExistException {
        if (!repository.existsByEmail(newEmployee.getEmail())) {
            return repository.save(newEmployee);
        }
        throw new EmailExistException("Такий email вже існує");
    }

    public EmployeeEntity updateEmployee(Long id, EmployeeEntity newEmployee) throws EmailExistException {
        Optional<EmployeeEntity> oldEmployee = repository.findByEmail(newEmployee.getEmail());
        if (!oldEmployee.isPresent() || oldEmployee.get().getId().equals(id)) {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        }
        throw new EmailExistException("Такий email вже існує");
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
