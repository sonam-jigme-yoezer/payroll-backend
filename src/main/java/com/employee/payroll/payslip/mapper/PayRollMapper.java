package com.employee.payroll.payslip.mapper;

import com.employee.payroll.payslip.dto.SalaryIssueRequestDTO;
import com.employee.payroll.payslip.model.PayRoll;
import org.springframework.stereotype.Component;

@Component
public class PayRollMapper {

    public PayRoll toEntity(SalaryIssueRequestDTO dto, Integer employeeId) {
        PayRoll payRoll = new PayRoll();
        payRoll.setSalary(dto.getSalary());
        payRoll.setPayDay(dto.getPayDay());
        payRoll.setStatus(PayRoll.Status.Unpaid); // default
        payRoll.setOvertime(dto.getOvertime());
//        try {
//            if (dto.getEmployeeType() != null) {
//                payRoll.setEmployeeType(PayRoll.EmployeeType.valueOf(dto.getEmployeeType()));
//            } else {
//                payRoll.setEmployeeType(PayRoll.EmployeeType.Others); // default to OTHERS if null
//            }
//        } catch (IllegalArgumentException e) {
//            // Set default or rethrow
//            payRoll.setEmployeeType(PayRoll.EmployeeType.Others); // fallback if invalid
//        }
        payRoll.setEmployeeId(employeeId);
        return payRoll;
    }

}
