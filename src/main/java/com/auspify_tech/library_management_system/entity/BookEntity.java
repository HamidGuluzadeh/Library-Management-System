package com.auspify_tech.library_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    String id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "author", nullable = false, length = 100)
    String author;

    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    String isbn;

    @Column(name = "category", nullable = false, length = 50)
    String category;

    @Column(name = "total_copies", nullable = false)
    Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    Integer availableCopies;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;
}
