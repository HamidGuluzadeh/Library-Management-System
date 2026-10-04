package com.auspify_tech.library_management_system.repository;

import com.auspify_tech.library_management_system.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

}
