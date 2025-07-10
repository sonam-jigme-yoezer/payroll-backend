package com.employee.payroll.payslip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "income_certificate")
public class IncomeCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;

    private BigDecimal basicSalary;

    private BigDecimal allowance;

    private BigDecimal benefits;

    private BigDecimal others;

    private BigDecimal grossSalary;

    private BigDecimal tds;

    private BigDecimal gpf;

    private BigDecimal gis;

    private String revenueRtNo;

    private LocalDate revenueRtDate;

    private Integer year;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_roll_id", referencedColumnName = "id", nullable = false)
    private PayRoll payRoll;
}
