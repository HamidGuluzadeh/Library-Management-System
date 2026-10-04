package com.auspify_tech.library_management_system.repository;

import com.auspify_tech.library_management_system.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    @Query("SELECT b FROM BookEntity b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<BookEntity> searchBooks(@Param("query") String query, Pageable pageable);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, String bookId);

}
