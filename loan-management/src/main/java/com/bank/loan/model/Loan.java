package com.bank.loan.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double loanAmount;
    private Double interestRate;
    private Integer durationMonths;
    private String status;

    private LocalDate appliedDate;
    private LocalDate approvedDate;
}
