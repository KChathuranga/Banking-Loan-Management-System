package com.bank.loan.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private String status;

    private LocalDateTime changedAt;

    private String changedBy; // username or email
}
