package com.bank.loan.dto;

import lombok.Data;

@Data
public class LoanResponseDTO {

    private Long loanId;
    private Double loanAmount;
    private String status;
    private Integer durationMonths;
}
