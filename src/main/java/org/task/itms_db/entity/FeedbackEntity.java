package org.task.itms_db.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Table(name = "feedback")
@NoArgsConstructor
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "talent_id", nullable = false)
    private EmployeeEntity talentId;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private Date created;
    private Date updated;
    private String answers;

    public FeedbackEntity(String status, Date created, Date updated, EmployeeEntity employee) {
        this.status = status;
        this.created = created;
        this.updated = updated;
        this.talentId = employee;
    }
}