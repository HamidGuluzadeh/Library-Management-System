package com.auspify_tech.library_management_system.repository;

import com.auspify_tech.library_management_system.entity.TransactionEntity;
import com.auspify_tech.library_management_system.model.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    @Query("SELECT COUNT(b) > 0 FROM TransactionEntity b WHERE b.user.id = :userId AND " +
            "b.status = 'BORROWED' AND b.dueDate < :today")
    boolean hasOverdueBooks(@Param("userId") String userId, @Param("today") LocalDate today);

    boolean existsByUserIdAndBookIdAndStatus(String userId, String bookId, BorrowStatus status);

    List<TransactionEntity> findByUserIdAndStatus(String userId, BorrowStatus status);

    @Query("SELECT b FROM TransactionEntity b WHERE b.status = 'BORROWED' AND b.dueDate < :today")
    List<TransactionEntity> findAllOverdueTransactions(@Param("today") LocalDate today);

}
