package com.auspify_tech.library_management_system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record BookRequest(@NotBlank(message = "Title cannot be empty!")
                          String title,
                          @NotBlank(message = "Author cannot be empty!")
                          @Size(max = 100, message = "Author name cannot exceed 100 characters!")
                          String author,
                          @NotBlank(message = "ISBN cannot be empty!")
                          @Size(max = 20, message = "ISBN cannot exceed 20 characters!")
                          String isbn,
                          @NotBlank(message = "Category cannot be empty!")
                          @Size(max = 50, message = "Category cannot exceed 50 characters!")
                          String category,
                          @NotNull(message = "Total copies cannot be null!")
                          @Min(value = 1, message = "Total copies must be at least 1!")
                          Integer totalCopies) {

}
