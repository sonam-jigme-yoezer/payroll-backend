package com.employee.payroll.payslip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "employee_salary_details", schema = "erp")
public class EmployeeSalaryDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "salary_detail_id")
    private UUID salaryDetailId;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private SalaryComponent component;

    @Column(name = "component_value")
    private BigDecimal componentValue;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "revision_reference")
    private UUID revisionReference;

    @Column(name = "arrear_amount")
    private BigDecimal arrearAmount;

    @Column(name = "is_current")
    private Boolean isCurrent;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;


    // Getters and Setters
}
