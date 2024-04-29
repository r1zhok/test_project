package org.task.itms_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.itms_db.entity.EmployeeEntity;
import org.task.itms_db.entity.FeedbackEntity;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
}
