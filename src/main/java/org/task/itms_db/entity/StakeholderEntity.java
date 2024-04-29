package org.task.itms_db.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "stakeholder")
public class StakeholderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private Long organizationId;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ua'")
    private String location;
    @Column(nullable = false)
    private Date birthday;
    private String mobile;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9]"
            + "(?:[A-Za-z0-9-]*[A-Za-z0-9])?",
            message = "{invalid.email}")
    private String email;
    @ManyToMany
    @JoinTable(
            name = "employees_and_stakeholders",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "stakeholder_id")
    )
    private Set<EmployeeEntity> employees;
    @ManyToMany
    @JoinTable(
            name = "customers_and_stakeholders",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "stakeholder_id")
    )
    private Set<CustomerEntity> customers;
}
