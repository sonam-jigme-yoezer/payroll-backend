package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.model.LeaveAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveAllocationRepository extends JpaRepository<LeaveAllocation, UUID> {
}
