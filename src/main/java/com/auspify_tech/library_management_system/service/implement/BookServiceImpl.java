package com.auspify_tech.library_management_system.service.implement;

import com.auspify_tech.library_management_system.dto.request.BookRequest;
import com.auspify_tech.library_management_system.dto.response.BookResponse;
import com.auspify_tech.library_management_system.entity.BookEntity;
import com.auspify_tech.library_management_system.exception.ResourceAlreadyExistsException;
import com.auspify_tech.library_management_system.exception.ResourceNotFoundException;
import com.auspify_tech.library_management_system.mapper.BookMapper;
import com.auspify_tech.library_management_system.repository.BookRepository;
import com.auspify_tech.library_management_system.service.BookService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;

    @Override
    public Page<BookResponse> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepository.findAll(pageable)
                .map(bookMapper::mapEntityToResponse);
    }

    @Override
    public Page<BookResponse> searchBooks(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (Objects.isNull(query) || query.isBlank()) {
            return bookRepository.findAll(pageable)
                    .map(bookMapper::mapEntityToResponse);
        }

        return bookRepository.searchBooks(query.trim(), pageable)
                .map(bookMapper::mapEntityToResponse);
    }

    @Override
    public BookResponse getBookById(String bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        return bookMapper.mapEntityToResponse(bookEntity);
    }

    @Override
    @Transactional
    public BookResponse createBook(BookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new ResourceAlreadyExistsException("Book already exists with ISBN: " + request.isbn());
        }

        BookEntity bookEntity = bookMapper.mapRequestToEntity(request);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        return bookMapper.mapEntityToResponse(savedBookEntity);
    }

    @Override
    @Transactional
    public BookResponse updateBook(String bookId, BookRequest request) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        if (bookRepository.existsByIsbnAndIdNot(request.isbn(), bookId)) {
            throw new ResourceAlreadyExistsException("Book already exists with ISBN: " + request.isbn());
        }

        bookMapper.updateEntityFromRequest(request, bookEntity);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        return bookMapper.mapEntityToResponse(savedBookEntity);
    }

    @Override
    @Transactional
    public void deleteBook(String bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found!");
        }

        bookRepository.deleteById(bookId);
    }
}
