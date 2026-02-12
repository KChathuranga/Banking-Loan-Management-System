package com.bank.loan.service;

import com.bank.loan.dto.LoanRequestDTO;
import com.bank.loan.model.EmiSchedule;
import com.bank.loan.model.Loan;

import java.util.List;

public interface LoanService {

    Loan applyLoan(LoanRequestDTO dto);

    Loan approveLoan(Long loanId);

    Loan rejectLoan(Long loanId);

    List<Loan> getAllLoans();

    List<EmiSchedule> getEmiSchedule(Long loanId);

}
