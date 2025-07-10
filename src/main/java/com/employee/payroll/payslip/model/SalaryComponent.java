package com.employee.payroll.payslip.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "salary_components", schema = "erp")
public class SalaryComponent {

    @Id
    @Column(name = "component_id")
    private UUID componentId;

    @Column(name = "org_id")
    private UUID orgId;

    @Column(name = "component_name")
    private String componentName;

    @Column(name = "component_code")
    private String componentCode;

    @Column(name = "component_type")
    private String componentType;

    @Column(name = "calculation_basis")
    private String calculationBasis;

    @Column(name = "calculation_formula")
    private String calculationFormula;

    @Column(name = "tax_applicable")
    private Boolean taxApplicable;

    @Column(name = "statutory_requirement")
    private Boolean statutoryRequirement;

    @Column(name = "display_order")
    private Short displayOrder;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;



}

