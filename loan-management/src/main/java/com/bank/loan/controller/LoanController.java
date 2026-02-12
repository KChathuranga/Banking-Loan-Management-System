package com.bank.loan.controller;

import com.bank.loan.dto.LoanRequestDTO;
import com.bank.loan.dto.LoanResponseDTO;
import com.bank.loan.model.Loan;
import com.bank.loan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponseDTO> applyLoan(@RequestBody LoanRequestDTO dto) {
        Loan loan = loanService.applyLoan(dto);

        LoanResponseDTO response = mapToResponse(loan);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanResponseDTO> approveLoan(@PathVariable Long id) {
        Loan loan = loanService.approveLoan(id);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanResponseDTO> rejectLoan(@PathVariable Long id) {
        Loan loan = loanService.rejectLoan(id);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> loans = loanService.getAllLoans()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(loans);
    }

    private LoanResponseDTO mapToResponse(Loan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setLoanId(loan.getId());
        dto.setLoanAmount(loan.getLoanAmount());
        dto.setStatus(loan.getStatus());
        dto.setDurationMonths(loan.getDurationMonths());
        return dto;
    }
}
