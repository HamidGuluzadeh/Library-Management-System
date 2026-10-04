package com.auspify_tech.library_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRequest(@NotBlank(message = "First name cannot be empty!")
                          @Size(max = 15, message = "First name cannot exceed 15 characters!")
                          String firstName,
                          @NotBlank(message = "Last name cannot be empty!")
                          @Size(max = 25, message = "Last name cannot exceed 25 characters!")
                          String lastName,
                          @NotBlank(message = "Phone number cannot be empty!")
                          @Size(max = 15, message = "Phone number cannot exceed 15 characters!")
                          String phoneNumber,
                          @NotBlank(message = "Email cannot be empty!")
                          @Size(max = 30, message = "Email cannot exceed 30 characters!")
                          String email) {

}
