package com.employee.payroll.payslip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "pay_roll")
public class PayRoll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal salary;

    private LocalDate payDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.Unpaid;

    private Double overtime;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "employee_type", nullable = false)
//    private EmployeeType employeeType;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    public enum Status {
        Paid,
        Unpaid
    }

//    public enum EmployeeType {
//        Staff,
//        Contract,
//        Part_time,
//        Others
//    }
}

