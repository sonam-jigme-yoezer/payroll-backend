package com.employee.payroll.payslip.repository;

import com.employee.payroll.payslip.model.JobGrade;
import com.employee.payroll.payslip.model.SalaryStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, UUID> {
    List<SalaryStructure> findByGradeAndComponentIdOrderByEffectiveFromDesc(
            JobGrade grade,
            UUID componentId);

    List<SalaryStructure> findByStructureIdAndComponentIdOrderByEffectiveFromDesc(UUID structureId, UUID componentId);

    List<SalaryStructure> findByComponentIdOrderByEffectiveFromDesc(UUID componentId);
}

