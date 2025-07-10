package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.dto.Projection;
import com.employee.payroll.payslip.model.Employee;
import com.employee.payroll.payslip.model.EmployeeSalaryDetail;
import com.employee.payroll.payslip.model.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeSalaryDetailRepository extends JpaRepository<EmployeeSalaryDetail, UUID> {
    List<EmployeeSalaryDetail> findByEmployeeAndComponentAndEffectiveFromBetween(
            Employee employee,
            SalaryComponent component,
            LocalDate startDate,
            LocalDate endDate);

    boolean existsByEmployeeAndComponentAndCreatedDateBetween(
            Employee employee,
            SalaryComponent component,
            LocalDateTime start,
            LocalDateTime end);

    @Query("SELECT esd FROM EmployeeSalaryDetail esd " +
            "JOIN esd.employee e " +
            "JOIN e.organization o " +
            "WHERE EXTRACT(YEAR FROM esd.effectiveFrom) = :year " +
            "AND EXTRACT(MONTH FROM esd.effectiveFrom) = :month " +
            "AND o.orgCode = :orgCode")
    List<EmployeeSalaryDetail> findByYearMonthAndOrgCode(
            @Param("year") int year,
            @Param("month") int month,
            @Param("orgCode") String orgCode
    );

    @Query(value = "SELECT esd FROM EmployeeSalaryDetail esd " +
            "WHERE esd.employee.empId = :employeeId " +
            "AND EXTRACT(YEAR FROM esd.effectiveFrom) = :year " +
            "AND EXTRACT(MONTH FROM esd.effectiveFrom) = :month")
    List<EmployeeSalaryDetail> findByEmployeeIdAndYearMonth(@Param("employeeId") UUID employeeId,
                                                            @Param("year") int year,
                                                            @Param("month") int month);

    boolean existsByEmployeeAndComponentAndEffectiveFromBetween(
            Employee employee,
            SalaryComponent component,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("SELECT COUNT(d) > 0 FROM EmployeeSalaryDetail d WHERE " +
            "d.employee = :employee AND " +
            "d.component = :component AND " +
            "d.effectiveFrom <= :endDate AND " +
            "d.effectiveTo >= :startDate")
    boolean existsByEmployeeAndComponentAndEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(
            @Param("employee") Employee employee,
            @Param("component") SalaryComponent component,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
    SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
    FROM EmployeeSalaryDetail e
    WHERE e.employee = :employee
      AND e.component = :component
      AND :date BETWEEN e.effectiveFrom AND e.effectiveTo
""")
    boolean existsForEmployeeComponentOnDate(
            @Param("employee") Employee employee,
            @Param("component") SalaryComponent component,
            @Param("date") LocalDate date
    );

    @Query(value = """
        SELECT 
            e.emp_id AS empId,
            e.emp_code AS empCode,
            e.first_name AS firstName,
            e.middle_name AS middleName,
            e.last_name AS lastName,
            e.date_of_birth AS dateOfBirth,
            e.gender AS gender,
            e.marital_status AS maritalStatus,
            e.nationality AS nationality,
            e.hire_date AS hireDate,
            e.employment_status AS employmentStatus,
            org.org_name AS organizationName,
            br.branch_name AS branchName,
            dept.dept_name AS departmentName,
            esd.effective_from AS effectiveFrom,
            esd.effective_to AS effectiveTo,
            to_char(esd.effective_from, 'YYYY-MM') AS salaryMonth,
            SUM(CASE WHEN sc.component_type = 'Earning' THEN esd.component_value ELSE 0 END) AS grossEarnings,
            SUM(CASE WHEN sc.component_type = 'Deduction' THEN esd.component_value ELSE 0 END) AS totalDeductions,
            SUM(CASE WHEN sc.component_type = 'Earning' THEN esd.component_value ELSE 0 END) -
            SUM(CASE WHEN sc.component_type = 'Deduction' THEN esd.component_value ELSE 0 END) AS netSalary
        FROM erp.employee_salary_details esd
        JOIN erp.salary_components sc ON esd.component_id = sc.component_id
        JOIN erp.employees e ON esd.emp_id = e.emp_id
        LEFT JOIN erp.organizations org ON e.org_id = org.org_id
        LEFT JOIN erp.branches br ON e.branch_id = br.branch_id
        LEFT JOIN erp.departments dept ON e.dept_id = dept.dept_id
        WHERE to_char(esd.effective_from, 'YYYY-MM') = :yearMonth
        GROUP BY e.emp_id, e.emp_code, e.first_name, e.middle_name, e.last_name, e.date_of_birth, 
                 e.gender, e.marital_status, e.nationality, e.hire_date, e.employment_status, 
                 org.org_name, br.branch_name, dept.dept_name, esd.effective_from, esd.effective_to
        """, nativeQuery = true)
    List<Projection.EmployeeNetSalarySummaryProjection> findByYearMonth(@Param("yearMonth") String yearMonth);

    @Query(value = """
        SELECT * FROM erp.vw_each_employee_pay_summary
        WHERE emp_id = :empId AND salary_month = :salaryMonth
        """, nativeQuery = true)
    Optional<Projection.EmployeePaySummaryProjection> findByEmpIdAndSalaryMonth(
            @Param("empId") UUID empId,
            @Param("salaryMonth") String salaryMonth);

    @Query(value = """
    SELECT * FROM erp.vw_each_employee_pay_summary
    WHERE emp_id = :empId
      AND salary_month BETWEEN :start AND :end
    ORDER BY salary_month
    """, nativeQuery = true)
    List<Projection.EmployeePaySummaryProjection> findRangeByEmployeeAndMonthRange(
            @Param("empId") UUID empId,
            @Param("start") String startMonth,
            @Param("end") String endMonth);


    @Query(value = """
    SELECT 
        COALESCE(SUM(CASE WHEN sc.component_type = 'Earning' THEN esd.component_value ELSE 0 END), 0) -
        COALESCE(SUM(CASE WHEN sc.component_type = 'Deduction' THEN esd.component_value ELSE 0 END), 0)
    FROM erp.employee_salary_details esd
    JOIN erp.salary_components sc ON esd.component_id = sc.component_id
    WHERE esd.emp_id = :empId 
      AND esd.effective_from = :startDate 
      AND esd.effective_to = :endDate
""", nativeQuery = true)
    BigDecimal getNetSalaryForEmployee(
            @Param("empId") UUID empId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );



}
