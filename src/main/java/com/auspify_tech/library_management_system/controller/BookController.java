package com.auspify_tech.library_management_system.controller;

import com.auspify_tech.library_management_system.dto.SuccessDto;
import com.auspify_tech.library_management_system.dto.request.BookRequest;
import com.auspify_tech.library_management_system.dto.response.BookResponse;
import com.auspify_tech.library_management_system.model.SuccessStatus;
import com.auspify_tech.library_management_system.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<SuccessDto<Page<BookResponse>>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<BookResponse> response = bookService.getAllBooks(page, size);
        SuccessDto<Page<BookResponse>> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessDto<Page<BookResponse>>> searchBooks(@RequestParam(required = false) String query,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<BookResponse> response = bookService.searchBooks(query, page, size);
        SuccessDto<Page<BookResponse>> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<SuccessDto<BookResponse>> getBookById(@PathVariable String bookId) {
        BookResponse response = bookService.getBookById(bookId);
        SuccessDto<BookResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<SuccessDto<BookResponse>> createBook(@RequestBody @Valid BookRequest request) {
        BookResponse response = bookService.createBook(request);
        SuccessDto<BookResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<SuccessDto<BookResponse>> updateBook(@PathVariable String bookId,
                                                               @RequestBody @Valid BookRequest request) {
        BookResponse response = bookService.updateBook(bookId, request);
        SuccessDto<BookResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
