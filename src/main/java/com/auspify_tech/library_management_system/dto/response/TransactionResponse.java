package com.auspify_tech.library_management_system.dto.response;

import com.auspify_tech.library_management_system.model.BorrowStatus;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record TransactionResponse(String id,
                                  String userId,
                                  String firstName,
                                  String lastName,
                                  String bookId,
                                  String bookTitle,
                                  LocalDate issueDate,
                                  LocalDate dueDate,
                                  LocalDate returnDate,
                                  BorrowStatus status,
                                  Instant createdAt) {

}
