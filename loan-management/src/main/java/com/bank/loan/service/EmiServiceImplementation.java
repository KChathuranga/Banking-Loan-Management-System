package com.bank.loan.service;

import com.bank.loan.model.EmiSchedule;
import com.bank.loan.model.Loan;
import com.bank.loan.repository.EmiScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmiServiceImplementation implements EmiService {

    private final EmiScheduleRepository emiRepository;

    public EmiServiceImplementation(EmiScheduleRepository emiRepository) {
        this.emiRepository = emiRepository;
    }

    @Override
    public void generateEmiSchedule(Loan loan) {

        double principal = loan.getLoanAmount();
        double annualInterest = loan.getInterestRate();
        int months = loan.getDurationMonths();

        double monthlyRate = annualInterest / (12 * 100);

        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                (Math.pow(1 + monthlyRate, months) - 1);

        for (int i = 1; i <= months; i++) {
            EmiSchedule emiSchedule = new EmiSchedule();
            emiSchedule.setLoan(loan);
            emiSchedule.setInstallmentNumber(i);
            emiSchedule.setDueDate(LocalDate.now().plusMonths(i));
            emiSchedule.setAmount(Math.round(emi * 100.0) / 100.0);
            emiSchedule.setPaid(false);

            emiRepository.save(emiSchedule);
        }
    }
}
