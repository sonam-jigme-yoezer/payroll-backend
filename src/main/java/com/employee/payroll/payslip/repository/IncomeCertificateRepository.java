package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.model.IncomeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeCertificateRepository extends JpaRepository<IncomeCertificate, Long> {
}
