package com.employee.payroll.payslip.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SalaryIssueRequestDTO {

    // PayRoll fields
    private BigDecimal salary;
    private LocalDate payDay;
    private Double overtime; // in hours
//    private String employeeType;

    // IncomeCertificate fields
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
}
