package com.bank.loan.service;

import com.bank.loan.dto.LoanRequestDTO;
import com.bank.loan.exception.LoanNotFoundException;
import com.bank.loan.exception.UserNotFoundException;
import com.bank.loan.model.EmiSchedule;
import com.bank.loan.model.Loan;
import com.bank.loan.model.User;
import com.bank.loan.repository.EmiScheduleRepository;
import com.bank.loan.repository.LoanRepository;
import com.bank.loan.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImplementation implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final EmiService emiService;
    private final EmiScheduleRepository emiRepository;


    public LoanServiceImplementation(LoanRepository loanRepository,
                                     UserRepository userRepository,
                                     EmiService emiService,
                                     EmiScheduleRepository emiRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.emiService = emiService;
        this.emiRepository = emiRepository;
    }

    @Override
    public Loan applyLoan(LoanRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getUserId()));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setLoanAmount(dto.getLoanAmount());
        loan.setInterestRate(dto.getInterestRate());
        loan.setDurationMonths(dto.getDurationMonths());
        loan.setStatus("PENDING");
        loan.setAppliedDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    @Override
    public Loan approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));

        loan.setStatus("APPROVED");
        loan.setApprovedDate(LocalDate.now());

        Loan savedLoan = loanRepository.save(loan);

        emiService.generateEmiSchedule(savedLoan);

        return savedLoan;
    }

    @Override
    public Loan rejectLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus("REJECTED");

        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<EmiSchedule> getEmiSchedule(Long loanId) {
        return emiRepository.findByLoanId(loanId);
    }
}
