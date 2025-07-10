package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.model.Employee;
import com.employee.payroll.payslip.model.IncomeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
