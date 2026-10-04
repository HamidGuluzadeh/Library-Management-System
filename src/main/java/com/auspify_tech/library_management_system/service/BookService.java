package com.auspify_tech.library_management_system.service;

import com.auspify_tech.library_management_system.dto.request.BookRequest;
import com.auspify_tech.library_management_system.dto.response.BookResponse;
import org.springframework.data.domain.Page;

public interface BookService {

    Page<BookResponse> getAllBooks(int page, int size);

    Page<BookResponse> searchBooks(String query, int page, int size);

    BookResponse getBookById(String bookId);

    BookResponse createBook(BookRequest request);

    BookResponse updateBook(String bookId, BookRequest request);

    void deleteBook(String bookId);

}
