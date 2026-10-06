package com.auspify_tech.library_management_system.controller;

import com.auspify_tech.library_management_system.dto.SuccessDto;
import com.auspify_tech.library_management_system.dto.request.TransactionRequest;
import com.auspify_tech.library_management_system.dto.response.TransactionResponse;
import com.auspify_tech.library_management_system.model.SuccessStatus;
import com.auspify_tech.library_management_system.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/borrow")
    public ResponseEntity<SuccessDto<TransactionResponse>> borrowBook(@RequestBody @Valid TransactionRequest request) {
        TransactionResponse response = transactionService.borrowBook(request);
        SuccessDto<TransactionResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);
    }

    @PostMapping("/{transactionId}/return")
    public ResponseEntity<SuccessDto<TransactionResponse>> returnBook(@PathVariable String transactionId) {
        TransactionResponse response = transactionService.returnBook(transactionId);
        SuccessDto<TransactionResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessDto<List<TransactionResponse>>> getActiveBorrowsByUser(@PathVariable String userId) {
        List<TransactionResponse> response = transactionService.getActiveBorrowsByUser(userId);
        SuccessDto<List<TransactionResponse>> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<SuccessDto<List<TransactionResponse>>> getOverdueTransactions() {
        List<TransactionResponse> response = transactionService.getOverdueTransactions();
        SuccessDto<List<TransactionResponse>> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }
}
