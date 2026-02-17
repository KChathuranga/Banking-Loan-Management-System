package com.bank.loan.controller;

import com.bank.loan.dto.LoanRequestDTO;
import com.bank.loan.dto.LoanResponseDTO;
import com.bank.loan.model.EmiSchedule;
import com.bank.loan.model.Loan;
import com.bank.loan.model.LoanStatusHistory;
import com.bank.loan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Loan Management APIs", description = "APIs for managing bank loans")
@RestController
@RequestMapping("/api/loans")
@CrossOrigin
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Apply for a new loan")
    @PostMapping
    public ResponseEntity<LoanResponseDTO> applyLoan(@RequestBody LoanRequestDTO dto) {
        Loan loan = loanService.applyLoan(dto);

        LoanResponseDTO response = mapToResponse(loan);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Approve a loan")
    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanResponseDTO> approveLoan(@PathVariable Long id) {
        Loan loan = loanService.approveLoan(id);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @Operation(summary = "Reject a loan")
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

    @Operation(summary = "Get EMI schedule for a loan")
    @GetMapping("/{id}/emis")
    public ResponseEntity<List<EmiSchedule>> getEmis(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getEmiSchedule(id));
    }

    @PutMapping("/emis/{emiId}/pay")
    public ResponseEntity<String> payEmi(@PathVariable Long emiId) {
        loanService.payEmi(emiId);
        return ResponseEntity.ok("EMI paid successfully");
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<LoanStatusHistory>> getLoanHistory(@PathVariable Long id) {
        return ResponseEntity.ok(
                loanService.getLoanHistory(id)
        );
    }

    @GetMapping("/officer")
    public ResponseEntity<List<LoanResponseDTO>> getLoansForOfficer() {

        List<LoanResponseDTO> loans = loanService.getLoansForOfficer()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(loans);
    }


}
