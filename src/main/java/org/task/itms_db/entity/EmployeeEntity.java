package org.task.itms_db.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    private String password;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ua'")
    private String location;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9]"
            + "(?:[A-Za-z0-9-]*[A-Za-z0-9])?",
            message = "{invalid.email}")
    private String email;
    private String position;
    private String projectName;
    private String agencyName;
    private Date startDate;
    private Date birthday;
    private String address;
    private String phoneNumber;
    private String telegram;
    private String whatsApp;
    private String feedbackFrequency;
    @ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<StakeholderEntity> stakeholders;
    @OneToMany(mappedBy = "talentId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FeedbackEntity> feedbacks = new ArrayList<>();
}
