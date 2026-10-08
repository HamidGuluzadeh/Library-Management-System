package com.auspify_tech.library_management_system.controller;

import com.auspify_tech.library_management_system.dto.SuccessDto;
import com.auspify_tech.library_management_system.dto.request.UserRequest;
import com.auspify_tech.library_management_system.dto.response.UserResponse;
import com.auspify_tech.library_management_system.model.SuccessStatus;
import com.auspify_tech.library_management_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "İstifadəçilərin idarə edilməsi üzrə əməliyyatlar")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Bütün istifadəçilərin siyahısını səhifələmə ilə gətir")
    public ResponseEntity<SuccessDto<Page<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<UserResponse> response = userService.getAllUsers(page, size);
        SuccessDto<Page<UserResponse>> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "ID-yə görə istifadəçi məlumatlarını gətir")
    public ResponseEntity<SuccessDto<UserResponse>> getUserById(@PathVariable String userId) {
        UserResponse response = userService.getUserById(userId);
        SuccessDto<UserResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    @Operation(summary = "Sistemə yeni istifadəçi əlavə et")
    public ResponseEntity<SuccessDto<UserResponse>> createUser(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.createUser(request);
        SuccessDto<UserResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "ID-yə görə mövcud istifadəçi məlumatlarını yenilə")
    public ResponseEntity<SuccessDto<UserResponse>> updateUser(@PathVariable String userId,
                                                               @RequestBody @Valid UserRequest request) {
        UserResponse response = userService.updateUser(userId, request);
        SuccessDto<UserResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "ID-yə görə istifadəçini sistemdən sil")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
