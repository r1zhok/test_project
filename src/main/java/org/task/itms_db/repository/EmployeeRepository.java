package org.task.itms_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.itms_db.entity.EmployeeEntity;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Boolean existsByEmail(String email);
    Optional<EmployeeEntity> findByEmail(String email);
    boolean existsById(Long id);
}
