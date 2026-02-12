package com.bank.loan.dto;

import lombok.Data;

@Data
public class LoanRequestDTO {

    private Long userId;
    private Double loanAmount;
    private Double interestRate;
    private Integer durationMonths;
}
