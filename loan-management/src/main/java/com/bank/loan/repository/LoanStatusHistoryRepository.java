package com.bank.loan.repository;

import com.bank.loan.model.LoanStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanStatusHistoryRepository extends JpaRepository<LoanStatusHistory, Long> {

    List<LoanStatusHistory> findByLoanId(Long loanId);
}
