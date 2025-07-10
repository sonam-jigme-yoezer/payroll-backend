package com.employee.payroll.payslip.service;

import com.employee.payroll.payslip.dto.Projection;
import com.employee.payroll.payslip.mapper.IncomeCertificateMapper;
import com.employee.payroll.payslip.mapper.PayRollMapper;
import com.employee.payroll.payslip.model.*;
import com.employee.payroll.payslip.repository.*;
import com.employee.payroll.utility.ErrorCodes;
import com.employee.payroll.utility.ErrorResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayRollServiceImpl implements PayRollService {

    @Autowired
    private PayRollMapper payRollMapper;

    @Autowired
    private PayRollRepository payRollRepository;

    @Autowired
    private IncomeCertificateMapper incomeCertificateMapper;

    @Autowired
    private IncomeCertificateRepository incomeCertificateRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryComponentRepository salaryComponentRepository;

    @Autowired
    private EmployeeSalaryDetailRepository employeeSalaryDetailRepository;

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Autowired
    private LeaveAllocationRepository leaveAllocationRepository;

//    @Transactional
//    @Override
//    public ResponseEntity<?> issueSalary(Integer employeeId, SalaryIssueRequestDTO dto) {
//        try {
//            PayRoll payRoll = payRollMapper.toEntity(dto, employeeId);
//            PayRoll savedPayRoll = payRollRepository.save(payRoll);
//
//            IncomeCertificate income = incomeCertificateMapper.toEntity(dto, savedPayRoll);
//            incomeCertificateRepository.save(income);
//
//            return ResponseEntity.ok(new SuccessResponse<>("success", "Salary issued successfully"));
//
//        } catch (IllegalArgumentException e) {
//            return ErrorResponse.buildErrorResponse(ErrorCodes.INVALID_INPUT_DATA, "Invalid status or employee type provided.");
//        } catch (Exception e) {
//            return ErrorResponse.buildErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR, "Failed to issue salary: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<?> getSummaryByMonthAndYear(String month, Integer year, String employeeType) {
//        try {
//            List<Projection.EmployeePayrollSummaryProjection> result = payRollRepository.findByMonthAndYear(month, year, employeeType);
//
//            if (result.isEmpty()) {
//                return ErrorResponse.buildErrorResponse(ErrorCodes.RESOURCE_NOT_FOUND, "No payroll data found for " + month + " " + year);
//            }
//
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ErrorResponse.buildErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<?> getIncomeCertificateByEmployeeAndDate(Long employeeId, String month, Integer year) {
//        try {
//            Optional<Projection.EmployeePayrollSummaryProjection> result =
//                    payRollRepository.findByEmployeeIdAndMonthYear(employeeId, month, year);
//
//            if (result.isPresent()) {
//                return ResponseEntity.ok(result.get());
//            } else {
//                return ErrorResponse.buildErrorResponse(
//                        ErrorCodes.RESOURCE_NOT_FOUND,
//                        "No income certificate found for employee ID: " + employeeId + ", " + month + " " + year);
//            }
//
//        } catch (Exception e) {
//            return ErrorResponse.buildErrorResponse(
//                    ErrorCodes.INTERNAL_SERVER_ERROR,
//                    "Error retrieving income certificate: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<?> getIncomeCertificateRange(Long employeeId, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth) {
//        try {
//            List<Projection.EmployeePayrollSummaryProjection> results =
//                    payRollRepository.findRangeByEmployeeAndDate(employeeId, startYear, startMonth, endYear, endMonth);
//
//            if (results.isEmpty()) {
//                return ErrorResponse.buildErrorResponse(ErrorCodes.RESOURCE_NOT_FOUND,
//                        "No income certificates found for employee ID " + employeeId + " in the specified date range.");
//            }
//
//            return ResponseEntity.ok(results);
//
//        } catch (Exception e) {
//            return ErrorResponse.buildErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR,
//                    "Failed to fetch income certificates: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional
//    public ResponseEntity<?> updateStatusToPaid(Long payRollId) {
//        Optional<PayRoll> optionalPayRoll = payRollRepository.findById(payRollId);
//
//        if (optionalPayRoll.isEmpty()) {
//            return ErrorResponse.buildErrorResponse(ErrorCodes.RESOURCE_NOT_FOUND, "Payroll entry not found with id: " + payRollId);
//        }
//
//        PayRoll payRoll = optionalPayRoll.get();
//
//        if (payRoll.getStatus() == PayRoll.Status.Paid) {
//            return ErrorResponse.buildErrorResponse(ErrorCodes.VALIDATION_ERROR, "Payroll status is already Paid.");
//        }
//
//        payRoll.setStatus(PayRoll.Status.Paid);
//        payRollRepository.save(payRoll);
//
//        return ResponseEntity.ok(new SuccessResponse<>("success", "Payroll status updated to Paid."));
//    }

    @Transactional
// @Scheduled(cron = "0 0 0 1 * ?") // ⛔ Comment this to disable automatic execution
    public void insertEmployeeSalaryDetailsScheduled() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDate today = LocalDate.now();
            LocalDate startOfMonth = today.withDayOfMonth(1);
            LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

            System.out.println("✅ Triggered salary generation at: " + now);

            List<Employee> employees = employeeRepository.findAll();
            List<SalaryComponent> components = salaryComponentRepository.findAll();

            int recordsCreated = 0;

            for (Employee employee : employees) {
                for (SalaryComponent component : components) {
                    boolean exists = employeeSalaryDetailRepository
                            .existsByEmployeeAndComponentAndEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(
                                    employee, component, endOfMonth, startOfMonth);

                    if (!exists) {
                        EmployeeSalaryDetail detail = new EmployeeSalaryDetail();
                        detail.setEmployee(employee);
                        detail.setComponent(component);
                        detail.setComponentValue(fetchComponentValueForEmployee(employee, component));
                        detail.setEffectiveFrom(startOfMonth);
                        detail.setEffectiveTo(endOfMonth);
                        detail.setIsCurrent(true);
                        detail.setCreatedDate(now);

                        employeeSalaryDetailRepository.save(detail);
                        recordsCreated++;

                        System.out.printf("✅ Salary created for emp %s, component %s%n",
                                employee.getEmpId(), component.getComponentId());
                    } else {
                        System.out.printf("⚠️ Salary already exists for emp %s, component %s%n",
                                employee.getEmpId(), component.getComponentId());
                    }
                }
            }

            System.out.println("🎯 Total salary records created: " + recordsCreated);

        } catch (Exception e) {
            System.out.println("❌ Error in manual salary insertion: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private BigDecimal fetchComponentValueForEmployee(Employee employee, SalaryComponent component) {
        // Step 1: Check if employee has a structure
        SalaryStructure employeeStructure = employee.getStructure();

        // Step 2: Try to find structure-specific value (for structure-based components)
        if (employeeStructure != null) {
            List<SalaryStructure> structureBased = salaryStructureRepository
                    .findByStructureIdAndComponentIdOrderByEffectiveFromDesc(
                            employeeStructure.getStructureId(), component.getComponentId());

            Optional<SalaryStructure> validStructureEntry = structureBased.stream()
                    .filter(s -> s.getEffectiveFrom() != null) // Add null check
                    .filter(s -> s.getEffectiveFrom().isBefore(LocalDate.now().plusDays(1)))
                    .findFirst();

            if (validStructureEntry.isPresent()) {
                return validStructureEntry.get().getComponentValue();
            }
        }

        // Step 3: If no structure-based value, fallback to latest fixed component value
        List<SalaryStructure> fixedComponents = salaryStructureRepository
                .findByComponentIdOrderByEffectiveFromDesc(component.getComponentId());

        return fixedComponents.stream()
                .filter(s -> s.getEffectiveFrom() != null) // Add null check
                .filter(s -> s.getEffectiveFrom().isBefore(LocalDate.now().plusDays(1)))
                .findFirst()
                .map(SalaryStructure::getComponentValue)
                .orElse(BigDecimal.ZERO);
    }


    @Override
    public ResponseEntity<?> getPayrollByYearMonthAndOrg(int year, int month, String orgCode) {
        try {
            List<EmployeeSalaryDetail> salaryDetails = employeeSalaryDetailRepository.findByYearMonthAndOrgCode(year, month, orgCode);

            if (salaryDetails.isEmpty()) {
                return ErrorResponse.buildErrorResponse(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "No payroll data found for the provided criteria: Year = " + year + ", Month = " + month + ", Org Code = " + orgCode
                );
            }

            return ResponseEntity.ok(salaryDetails);

        } catch (Exception e) {
            return ErrorResponse.buildErrorResponse(
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    "Error retrieving payroll data: " + e.getMessage()
            );
        }
    }

    @Override
    public ResponseEntity<?> getPayrollByEmployeeIdAndYearMonth(UUID employeeId, int year, int month) {
        try {
            List<EmployeeSalaryDetail> salaryDetails = employeeSalaryDetailRepository
                    .findByEmployeeIdAndYearMonth(employeeId, year, month);

            if (salaryDetails.isEmpty()) {
                return ErrorResponse.buildErrorResponse(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "No payroll data found for Employee ID: " + employeeId + " for Year: " + year + " and Month: " + month
                );
            }

            return ResponseEntity.ok(salaryDetails);

        } catch (Exception e) {
            return ErrorResponse.buildErrorResponse(
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    "Error retrieving payroll data: " + e.getMessage()
            );
        }
    }

    @Override
    public ResponseEntity<?> getSummaryByYearMonth(String year, String month) {
        try {
            String yearMonth = year + "-" + String.format("%02d", Integer.parseInt(month));
            List<Projection.EmployeeNetSalarySummaryProjection> data =
                    employeeSalaryDetailRepository.findByYearMonth(yearMonth);

            if (data.isEmpty()) {
                return ErrorResponse.buildErrorResponse(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "No payroll summary found for Year: " + year + " and Month: " + month
                );
            }

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            return ErrorResponse.buildErrorResponse(
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    "Error retrieving payroll summary: " + e.getMessage()
            );
        }
    }

    public ResponseEntity<?> getPaySummaryByEmpIdYearMonth(UUID empId, int year, int month) {
        try {
            String salaryMonth = String.format("%04d-%02d", year, month);

            Optional<Projection.EmployeePaySummaryProjection> summaryOpt = employeeSalaryDetailRepository.findByEmpIdAndSalaryMonth(empId, salaryMonth);

            if (summaryOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Payroll summary not found for employee " + empId + " for " + salaryMonth);
            }

            return ResponseEntity.ok(summaryOpt.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getSalaryRangeSummary(UUID employeeId, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth) {
        try {
            String start = String.format("%d-%02d", startYear, startMonth);
            String end = String.format("%d-%02d", endYear, endMonth);

            List<Projection.EmployeePaySummaryProjection> results = employeeSalaryDetailRepository
                    .findRangeByEmployeeAndMonthRange(employeeId, start, end);

            if (results.isEmpty()) {
                return ErrorResponse.buildErrorResponse(
                        ErrorCodes.RESOURCE_NOT_FOUND,
                        "No salary records found for employee ID " + employeeId + " in the specified date range.");
            }

            return ResponseEntity.ok(results);

        } catch (Exception e) {
            return ErrorResponse.buildErrorResponse(
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    "Failed to fetch salary data: " + e.getMessage());
        }
    }


}
