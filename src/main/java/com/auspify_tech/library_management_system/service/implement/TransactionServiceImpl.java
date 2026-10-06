package com.auspify_tech.library_management_system.service.implement;

import com.auspify_tech.library_management_system.dto.request.TransactionRequest;
import com.auspify_tech.library_management_system.dto.response.TransactionResponse;
import com.auspify_tech.library_management_system.entity.BookEntity;
import com.auspify_tech.library_management_system.entity.TransactionEntity;
import com.auspify_tech.library_management_system.entity.UserEntity;
import com.auspify_tech.library_management_system.exception.BusinessException;
import com.auspify_tech.library_management_system.exception.ResourceNotFoundException;
import com.auspify_tech.library_management_system.mapper.TransactionMapper;
import com.auspify_tech.library_management_system.model.BorrowStatus;
import com.auspify_tech.library_management_system.model.UserStatus;
import com.auspify_tech.library_management_system.repository.BookRepository;
import com.auspify_tech.library_management_system.repository.TransactionRepository;
import com.auspify_tech.library_management_system.repository.UserRepository;
import com.auspify_tech.library_management_system.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    BookRepository bookRepository;
    UserRepository userRepository;
    TransactionMapper transactionMapper;

    static int DEFAULT_BORROW_DAYS = 14;

    @Override
    @Transactional
    public TransactionResponse borrowBook(TransactionRequest request) {
        UserEntity userEntity = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (userEntity.getStatus() == UserStatus.SUSPENDED) {
            throw new BusinessException("User cannot borrow new books! User is SUSPENDED user due to overdue borrows!");
        }

        if (transactionRepository.hasOverdueBooks(userEntity.getId(), LocalDate.now())) {
            userEntity.setStatus(UserStatus.SUSPENDED);
            userRepository.save(userEntity);

            throw new BusinessException("User has overdue borrows and has been SUSPENDED!");
        }

        BookEntity bookEntity = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        if (bookEntity.getAvailableCopies() == 0) {
            throw new BusinessException("No available copies left for book: " + bookEntity.getTitle());
        }

        if (transactionRepository.existsByUserIdAndBookIdAndStatus(userEntity.getId(), bookEntity.getId(),
                BorrowStatus.BORROWED)) {
            throw new BusinessException("User has already borrowed book ant not returned it yet!")
        }

        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() - 1);
        bookRepository.save(bookEntity);

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .user(userEntity)
                .book(bookEntity)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(DEFAULT_BORROW_DAYS))
                .status(BorrowStatus.BORROWED)
                .build();

        TransactionEntity savedTransactionEntity = transactionRepository.save(transactionEntity);

        return transactionMapper.mapEntityToResponse(savedTransactionEntity);
    }

}
