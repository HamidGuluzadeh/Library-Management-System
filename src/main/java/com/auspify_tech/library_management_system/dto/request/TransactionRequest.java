package com.auspify_tech.library_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TransactionRequest(@NotBlank(message = "User ID cannot be empty!")
                                 String userId,
                                 @NotBlank(message = "Book ID cannot be empty!")
                                 String bookId) {

}
