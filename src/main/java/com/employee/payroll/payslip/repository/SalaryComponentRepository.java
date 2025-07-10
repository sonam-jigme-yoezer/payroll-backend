package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.model.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, UUID> {
}
