package com.employee.payroll.payslip.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "leave_allocations", schema = "erp")
public class LeaveAllocation {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "allocation_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;

    @Column(name = "allocation_year")
    private Short allocationYear;

    @Column(name = "opening_balance")
    private BigDecimal openingBalance;

    @Column(name = "annual_accrual")
    private BigDecimal annualAccrual;

    @Column(name = "adjustments")
    private BigDecimal adjustments;

    @Column(name = "utilized_balance")
    private BigDecimal utilizedBalance;

    @Column(name = "expired_balance")
    private BigDecimal expiredBalance;

    @Column(name = "closing_balance")
    private BigDecimal closingBalance;

    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @Column(name = "modified_date")
    private OffsetDateTime modifiedDate;

    // Getters & Setters...
}

