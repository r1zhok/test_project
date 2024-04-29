package org.task.itms_db.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.itms_db.entity.CustomerEntity;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Nonnull
    Optional<CustomerEntity> findById(@Nonnull Long id);
}
