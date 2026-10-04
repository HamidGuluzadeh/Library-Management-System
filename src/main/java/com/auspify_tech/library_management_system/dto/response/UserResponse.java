package com.auspify_tech.library_management_system.dto.response;

import com.auspify_tech.library_management_system.model.UserStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(String id,
                           String firstName,
                           String lastName,
                           String phoneNumber,
                           String email,
                           UserStatus status,
                           Instant createdAt,
                           Instant updatedAt) {

}
