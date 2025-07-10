package com.employee.payroll.payslip.controller;

import com.employee.payroll.payslip.dto.Projection;
import com.employee.payroll.payslip.dto.SalaryIssueRequestDTO;
import com.employee.payroll.payslip.model.EmployeeSalaryDetail;
import com.employee.payroll.payslip.service.PayRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payRoll")
public class PayRollController {

    @Autowired
    private PayRollService payRollService;

//    @PostMapping("/issueSalary/{employeeId}")
//    public ResponseEntity<?> issueSalary(
//            @PathVariable Integer employeeId,
//            @RequestBody SalaryIssueRequestDTO dto
//    ) {
//        return payRollService.issueSalary(employeeId, dto);
//    }
//
//    @GetMapping("/getAllPayRoll/{year}/{month}/{employeeType}")
//    public ResponseEntity<?> getSummaryByMonthAndYear(
//            @PathVariable Integer year,
//            @PathVariable String month,
//            @PathVariable String employeeType){
//        return payRollService.getSummaryByMonthAndYear(month, year, employeeType);
//    }
//
//    @GetMapping("/certificate/{employeeId}/{year}/{month}")
//    public ResponseEntity<?> getIncomeCertificateByEmployeeAndDate(
//            @PathVariable Long employeeId,
//            @PathVariable Integer year,
//            @PathVariable String month) {
//        return payRollService.getIncomeCertificateByEmployeeAndDate(employeeId, month, year);
//    }
//
//    @GetMapping("/certificate/range/{employeeId}")
//    public ResponseEntity<?> getIncomeCertificateRange(
//            @PathVariable Long employeeId,
//            @RequestParam Integer startYear,
//            @RequestParam Integer startMonth,
//            @RequestParam Integer endYear,
//            @RequestParam Integer endMonth) {
//
//        return payRollService.getIncomeCertificateRange(
//                employeeId, startYear, startMonth, endYear, endMonth);
//    }
//
//    @PostMapping("/markPaid/{payRollId}")
//    public ResponseEntity<?> markPayrollAsPaid(@PathVariable Long payRollId) {
//        return payRollService.updateStatusToPaid(payRollId);
//    }

//    @GetMapping("/viewAllPay")
//    public ResponseEntity<?> viewPayroll(
//            @RequestParam int year,
//            @RequestParam int month,
//            @RequestParam String orgCode
//    ) {
//        return payRollService.getPayrollByYearMonthAndOrg(year, month, orgCode);
//    }

    @GetMapping("/viewEachEmployeePayroll")
    public ResponseEntity<?> getEmployeePaySummary(
            @RequestParam UUID empId,
            @RequestParam int year,
            @RequestParam int month) {

        return payRollService.getPaySummaryByEmpIdYearMonth(empId, year, month);
    }

    @PostMapping("/issueSalary")
    public ResponseEntity<?> testManualSalaryInsert() {
        payRollService.insertEmployeeSalaryDetailsScheduled();
        return ResponseEntity.ok("Triggered salary generation manually.");
    }

    @GetMapping("/getAllPayRoll")
    public ResponseEntity<?> getSummaryByYearMonth(
            @RequestParam String year,
            @RequestParam String month) {
        return payRollService.getSummaryByYearMonth(year, month);
    }

    @GetMapping("/pay-summary/range/{employeeId}")
    public ResponseEntity<?> getSalaryRangeSummary(
            @PathVariable UUID employeeId,
            @RequestParam Integer startYear,
            @RequestParam Integer startMonth,
            @RequestParam Integer endYear,
            @RequestParam Integer endMonth) {

        return payRollService.getSalaryRangeSummary(
                employeeId, startYear, startMonth, endYear, endMonth);
    }


}
