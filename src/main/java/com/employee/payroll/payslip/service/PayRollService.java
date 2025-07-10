package com.employee.payroll.payslip.service;

import com.employee.payroll.payslip.dto.Projection;
import com.employee.payroll.payslip.dto.SalaryIssueRequestDTO;
import com.employee.payroll.payslip.model.EmployeeSalaryDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface PayRollService {
//    ResponseEntity<?> issueSalary(Integer employeeId, SalaryIssueRequestDTO dto);
//
//    ResponseEntity<?> getSummaryByMonthAndYear(String month, Integer year, String employeeType);
//
//    ResponseEntity<?> getIncomeCertificateByEmployeeAndDate(Long employeeId, String month, Integer year);
//
//    ResponseEntity<?> getIncomeCertificateRange(Long employeeId, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth);
//
//    ResponseEntity<?> updateStatusToPaid(Long payRollId);

    void insertEmployeeSalaryDetailsScheduled();

    ResponseEntity<?> getPayrollByYearMonthAndOrg(int year, int month, String orgCode);

    ResponseEntity<?> getPayrollByEmployeeIdAndYearMonth(UUID employeeId, int year, int month);

    ResponseEntity<?> getSummaryByYearMonth(String year, String month);

    ResponseEntity<?> getPaySummaryByEmpIdYearMonth(UUID empId, int year, int month);

    ResponseEntity<?> getSalaryRangeSummary(UUID employeeId, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth);
}
