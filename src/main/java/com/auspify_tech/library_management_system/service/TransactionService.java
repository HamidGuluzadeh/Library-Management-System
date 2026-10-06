package com.auspify_tech.library_management_system.service;

import com.auspify_tech.library_management_system.dto.request.TransactionRequest;
import com.auspify_tech.library_management_system.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse borrowBook(TransactionRequest request);

    TransactionResponse returnBook(String transactionId);

    List<TransactionResponse> getActiveBorrowsByUser(String userId);

    List<TransactionResponse> getOverdueTransactions();

}
