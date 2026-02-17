package com.bank.loan.service;

import com.bank.loan.dto.LoanRequestDTO;
import com.bank.loan.exception.LoanNotFoundException;
import com.bank.loan.exception.UserNotFoundException;
import com.bank.loan.model.EmiSchedule;
import com.bank.loan.model.Loan;
import com.bank.loan.model.LoanStatusHistory;
import com.bank.loan.model.User;
import com.bank.loan.repository.EmiScheduleRepository;
import com.bank.loan.repository.LoanRepository;
import com.bank.loan.repository.LoanStatusHistoryRepository;
import com.bank.loan.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImplementation implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final EmiService emiService;
    private final EmiScheduleRepository emiRepository;
    private final LoanStatusHistoryRepository historyRepository;

    public LoanServiceImplementation(LoanRepository loanRepository,
                                     UserRepository userRepository,
                                     EmiService emiService,
                                     EmiScheduleRepository emiRepository,
                                     LoanStatusHistoryRepository historyRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.emiService = emiService;
        this.emiRepository = emiRepository;
        this.historyRepository = historyRepository;
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

        Loan savedLoan = loanRepository.save(loan);
        logStatusChange(savedLoan, "PENDING", "SYSTEM");
        return savedLoan;
    }

    @Override
    public Loan approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));

        loan.setStatus("ACTIVE");
        loan.setApprovedDate(LocalDate.now());

        Loan savedLoan = loanRepository.save(loan);
        logStatusChange(savedLoan, "ACTIVE", "LOAN_OFFICER");
        emiService.generateEmiSchedule(savedLoan);
        return savedLoan;

    }


    @Override
    public Loan rejectLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus("REJECTED");

        Loan savedLoan = loanRepository.save(loan);
        logStatusChange(savedLoan, "REJECTED", "LOAN_OFFICER");
        return savedLoan;

    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<EmiSchedule> getEmiSchedule(Long loanId) {
        return emiRepository.findByLoanId(loanId);
    }

    @Override
    public void payEmi(Long emiId) {

        EmiSchedule emi = emiRepository.findById(emiId)
                .orElseThrow(() -> new RuntimeException("EMI not found"));

        if (emi.getPaid()) {
            throw new RuntimeException("EMI already paid");
        }

        emi.setPaid(true);
        emiRepository.save(emi);

        Loan loan = emi.getLoan();

        boolean allPaid = emiRepository.findByLoanId(loan.getId())
                .stream()
                .allMatch(EmiSchedule::getPaid);

        if (allPaid) {
            loan.setStatus("CLOSED");
            loanRepository.save(loan);
            logStatusChange(loan, "CLOSED", "SYSTEM");
        }

    }

    private void logStatusChange(Loan loan, String status, String changedBy) {

        LoanStatusHistory history = new LoanStatusHistory();
        history.setLoan(loan);
        history.setStatus(status);
        history.setChangedAt(LocalDateTime.now());
        history.setChangedBy(changedBy);

        historyRepository.save(history);
    }

    @Override
    public List<LoanStatusHistory> getLoanHistory(Long loanId) {
        return historyRepository.findByLoanId(loanId);
    }
    @Override
    public List<Loan> getLoansForOfficer() {
        return loanRepository.findByStatusIn(
                List.of("PENDING", "ACTIVE")
        );
    }
}
