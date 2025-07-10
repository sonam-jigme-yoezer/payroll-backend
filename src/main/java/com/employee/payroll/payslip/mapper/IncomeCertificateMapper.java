package com.employee.payroll.payslip.mapper;

import com.employee.payroll.payslip.dto.SalaryIssueRequestDTO;
import com.employee.payroll.payslip.model.IncomeCertificate;
import com.employee.payroll.payslip.model.PayRoll;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IncomeCertificateMapper {

    public IncomeCertificate toEntity(SalaryIssueRequestDTO dto, PayRoll payRoll) {
        IncomeCertificate income = new IncomeCertificate();
        income.setMonth(dto.getMonth());
        income.setBasicSalary(dto.getBasicSalary());
        income.setAllowance(dto.getAllowance());
        income.setBenefits(dto.getBenefits());
        income.setOthers(dto.getOthers());
        income.setGrossSalary(dto.getGrossSalary());
        income.setTds(dto.getTds());
        income.setGpf(dto.getGpf());
        income.setGis(dto.getGis());
        income.setRevenueRtNo(dto.getRevenueRtNo());
        income.setRevenueRtDate(dto.getRevenueRtDate());
        income.setYear(LocalDate.now().getYear()); // default year
        income.setPayRoll(payRoll);
        return income;
    }

}
