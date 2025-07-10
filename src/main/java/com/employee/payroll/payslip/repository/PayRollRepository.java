package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.dto.Projection;
import com.employee.payroll.payslip.model.PayRoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayRollRepository extends JpaRepository<PayRoll, Long> {
    @Query(value = """
                SELECT
                    e.id AS employeeInternalId,
                    e.employee_id AS employeeId,
                    e.first_name AS firstName,
                    e.last_name AS lastName,
                    e.full_name AS fullName,
                    e.email AS email,
                    e.cid AS cid,
                    e.date_of_joining AS dateOfJoining,
                    e.name_of_the_employer AS nameOfTheEmployer,
                    e.work_permit_id AS workPermitId,
                    e.employee_type AS employeeType,
                    d.department_name AS departmentName,
            
                    p.id AS payrollId,
                    p.salary AS salary,
                    p.payDay AS payDay,
                    p.status AS status,
                    p.overtime AS overtime,
            
                    i.id AS incomeCertificateId,
                    i.month AS month,
                    i.basicSalary AS basicSalary,
                    i.allowance AS allowance,
                    i.benefits AS benefits,
                    i.others AS others,
                    i.grossSalary AS grossSalary,
                    i.tds AS tds,
                    i.gpf AS gpf,
                    i.gis AS gis,
                    i.revenueRtNo AS revenueRtNo,
                    i.revenueRtDate AS revenueRtDate,
                    i.year AS year
            
                FROM vw_employee_payroll_summary v
                LEFT JOIN pay_roll p ON p.id = v.payroll_id
                LEFT JOIN income_certificate i ON i.pay_roll_id = p.id
                LEFT JOIN employee_master e ON p.employee_id = e.id
                LEFT JOIN department d ON e.department = d.id
                WHERE i.month = :month\s
                AND i.year = :year
                AND (:employeeType = 'ALL' OR e.employee_type = :employeeType)
            """, nativeQuery = true)
    List<Projection.EmployeePayrollSummaryProjection> findByMonthAndYear(String month, Integer year, String employeeType);

    @Query(value = """
                SELECT
                        e.id AS employeeInternalId,
                        e.employee_id AS employeeId,
                        e.first_name AS firstName,
                        e.last_name AS lastName,
                        e.full_name AS fullName,
                        e.email AS email,
                        e.cid AS cid,
                        e.date_of_joining AS dateOfJoining,
                        e.name_of_the_employer AS nameOfTheEmployer,
                        e.work_permit_id AS workPermitId,
                        e.employee_type AS employeeType,
                        d.department_name AS departmentName,
            
                        p.id AS payrollId,
                        p.salary AS salary,
                        p.payDay AS payDay,
                        p.status AS status,
                        p.overtime AS overtime,
            
                        i.id AS incomeCertificateId,
                        i.month AS month,
                        i.basicSalary AS basicSalary,
                        i.allowance AS allowance,
                        i.benefits AS benefits,
                        i.others AS others,
                        i.grossSalary AS grossSalary,
                        i.tds AS tds,
                        i.gpf AS gpf,
                        i.gis AS gis,
                        i.revenueRtNo AS revenueRtNo,
                        i.revenueRtDate AS revenueRtDate,
                        i.year AS year
            
                    FROM vw_employee_payroll_summary v
                    LEFT JOIN pay_roll p ON p.id = v.payroll_id
                    LEFT JOIN income_certificate i ON i.pay_roll_id = p.id
                    LEFT JOIN employee_master e ON p.employee_id = e.id
                    LEFT JOIN department d ON e.department = d.id
                    WHERE e.id = :employeeId AND LOWER(i.month) = LOWER(:month) AND i.year = :year
            """, nativeQuery = true)
    Optional<Projection.EmployeePayrollSummaryProjection> findByEmployeeIdAndMonthYear(
            Long employeeId,
            String month,
            Integer year
    );

    @Query(value = """
                SELECT
                        e.id AS employeeInternalId,
                        e.employee_id AS employeeId,
                        e.first_name AS firstName,
                        e.last_name AS lastName,
                        e.full_name AS fullName,
                        e.email AS email,
                        e.cid AS cid,
                        e.date_of_joining AS dateOfJoining,
                        e.name_of_the_employer AS nameOfTheEmployer,
                        e.work_permit_id AS workPermitId,
                        e.employee_type AS employeeType,
                        d.department_name AS departmentName,
            
                        p.id AS payrollId,
                        p.salary AS salary,
                        p.payDay AS payDay,
                        p.status AS status,
                        p.overtime AS overtime,
            
                        i.id AS incomeCertificateId,
                        i.month AS month,
                        i.basicSalary AS basicSalary,
                        i.allowance AS allowance,
                        i.benefits AS benefits,
                        i.others AS others,
                        i.grossSalary AS grossSalary,
                        i.tds AS tds,
                        i.gpf AS gpf,
                        i.gis AS gis,
                        i.revenueRtNo AS revenueRtNo,
                        i.revenueRtDate AS revenueRtDate,
                        i.year AS year
            
               FROM vw_employee_payroll_summary v
               LEFT JOIN pay_roll p ON p.id = v.payroll_id
               LEFT JOIN income_certificate i ON i.pay_roll_id = p.id
               LEFT JOIN employee_master e ON p.employee_id = e.id
               LEFT JOIN department d ON e.department = d.id
               WHERE e.id = :employeeId
               AND (
               (i.year > :startYear) OR (i.year = :startYear AND\s
               CASE LOWER(i.month)
               WHEN 'january' THEN 1
               WHEN 'february' THEN 2
               WHEN 'march' THEN 3
               WHEN 'april' THEN 4
               WHEN 'may' THEN 5
               WHEN 'june' THEN 6
               WHEN 'july' THEN 7
               WHEN 'august' THEN 8
               WHEN 'september' THEN 9
               WHEN 'october' THEN 10
               WHEN 'november' THEN 11
               WHEN 'december' THEN 12
               ELSE 0
               END >= :startMonth
               )
               )
               AND (
               (i.year < :endYear) OR (i.year = :endYear AND
               CASE LOWER(i.month)
               WHEN 'january' THEN 1
               WHEN 'february' THEN 2
               WHEN 'march' THEN 3
               WHEN 'april' THEN 4
               WHEN 'may' THEN 5
               WHEN 'june' THEN 6
               WHEN 'july' THEN 7
               WHEN 'august' THEN 8
               WHEN 'september' THEN 9
               WHEN 'october' THEN 10
               WHEN 'november' THEN 11
               WHEN 'december' THEN 12
               ELSE 0
               END <= :endMonth
               )
               )
               ORDER BY i.year,\s
               CASE LOWER(i.month)
               WHEN 'january' THEN 1
               WHEN 'february' THEN 2
               WHEN 'march' THEN 3
               WHEN 'april' THEN 4
               WHEN 'may' THEN 5
               WHEN 'june' THEN 6
               WHEN 'july' THEN 7
               WHEN 'august' THEN 8
               WHEN 'september' THEN 9
               WHEN 'october' THEN 10
               WHEN 'november' THEN 11
               WHEN 'december' THEN 12
               ELSE 0
               END
            """, nativeQuery = true)
    List<Projection.EmployeePayrollSummaryProjection> findRangeByEmployeeAndDate(
            @Param("employeeId") Long employeeId,
            @Param("startYear") Integer startYear,
            @Param("startMonth") Integer startMonth,
            @Param("endYear") Integer endYear,
            @Param("endMonth") Integer endMonth
    );

}
