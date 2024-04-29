package org.task.itms_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.itms_db.entity.StakeholderEntity;
import org.task.itms_db.entity.UserEntity;

import java.util.Optional;

@Repository
public interface StakeholderRepository extends JpaRepository<StakeholderEntity, Long> {
    Boolean existsByEmail(String email);
    Optional<StakeholderEntity> findByEmail(String email);
}
