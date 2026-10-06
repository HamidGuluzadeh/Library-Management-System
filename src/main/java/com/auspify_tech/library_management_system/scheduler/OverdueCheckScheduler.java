package com.auspify_tech.library_management_system.scheduler;

import com.auspify_tech.library_management_system.entity.TransactionEntity;
import com.auspify_tech.library_management_system.model.BorrowStatus;
import com.auspify_tech.library_management_system.model.UserStatus;
import com.auspify_tech.library_management_system.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OverdueCheckScheduler {
    private final TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkOverdueBorrows() {
        List<TransactionEntity> overdueTransactions =
                transactionRepository.findAllOverdueTransactions(LocalDate.now());

        for (TransactionEntity transactionEntity : overdueTransactions) {
            transactionEntity.setStatus(BorrowStatus.OVERDUE);
            transactionEntity.getUser().setStatus(UserStatus.SUSPENDED);
        }

        transactionRepository.saveAll(overdueTransactions);
    }
}
