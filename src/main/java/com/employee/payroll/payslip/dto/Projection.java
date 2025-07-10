package com.employee.payroll.payslip.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Projection {

    public interface EmployeePayrollSummaryProjection {
        Long getEmployeeInternalId();
        Integer getEmployeeId();
        String getFirstName();
        String getLastName();
        String getFullName();
        String getEmail();
        String getCid();
        LocalDate getDateOfJoining();
        String getNameOfTheEmployer();
        String getWorkPermitId();
        String getDepartmentName();

        Long getPayrollId();
        BigDecimal getSalary();
        LocalDate getPayDay();
        String getStatus();
        Double getOvertime();
        String getEmployeeType();

        Long getIncomeCertificateId();
        String getMonth();
        BigDecimal getBasicSalary();
        BigDecimal getAllowance();
        BigDecimal getBenefits();
        BigDecimal getOthers();
        BigDecimal getGrossSalary();
        BigDecimal getTds();
        BigDecimal getGpf();
        BigDecimal getGis();
        String getRevenueRtNo();
        LocalDate getRevenueRtDate();
        Integer getYear();
    }

    public interface EmployeeNetSalarySummaryProjection {
        UUID getEmpId();
        String getEmpCode();
        String getFirstName();
        String getMiddleName();
        String getLastName();
        LocalDate getDateOfBirth();
        String getGender();
        String getMaritalStatus();
        String getNationality();
        LocalDate getHireDate();
        String getEmploymentStatus();
        String getOrganizationName();
        String getBranchName();
        String getDepartmentName();
        LocalDate getEffectiveFrom();
        LocalDate getEffectiveTo();
        String getSalaryMonth();
        BigDecimal getGrossEarnings();
        BigDecimal getTotalDeductions();
        BigDecimal getNetSalary();
    }

    public interface EmployeePaySummaryProjection {
        UUID getEmpId();
        String getEmpCode();
        String getOrgName();
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getGender();
        String getNationality();
        String getHireDate();        // Assuming you map as String, or use LocalDate and configure mapping
        String getEmploymentStatus();

        String getEffectiveFrom();
        String getEffectiveTo();
        String getSalaryMonth();

        Double getBasicPay();
        Double getAllowance();
        Double getHra();
        Double getTds();
        Double getPf();
        Double getGis();
        Double getGpf();
        Double getBonus();
        Double getOtherAllowance();
    }

}
