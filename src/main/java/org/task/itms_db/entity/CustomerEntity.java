package org.task.itms_db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "customers")
    private Set<StakeholderEntity> stakeholders;
}
