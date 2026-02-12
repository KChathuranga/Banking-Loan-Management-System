package com.bank.loan.service;

import com.bank.loan.model.Loan;

public interface EmiService {
    void generateEmiSchedule(Loan loan);
}
