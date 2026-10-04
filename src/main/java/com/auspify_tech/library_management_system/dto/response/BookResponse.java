package com.auspify_tech.library_management_system.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record BookResponse(String id,
                           String title,
                           String author,
                           String isbn,
                           String category,
                           Integer totalCopies,
                           Integer availableCopies,
                           Instant createdAt,
                           Instant updatedAt) {

}
