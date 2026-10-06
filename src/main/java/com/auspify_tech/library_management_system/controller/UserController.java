package com.auspify_tech.library_management_system.controller;

import com.auspify_tech.library_management_system.dto.SuccessDto;
import com.auspify_tech.library_management_system.dto.request.UserRequest;
import com.auspify_tech.library_management_system.dto.response.UserResponse;
import com.auspify_tech.library_management_system.model.SuccessStatus;
import com.auspify_tech.library_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<SuccessDto<Page<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<UserResponse> response = userService.getAllUsers(page, size);
        SuccessDto<Page<UserResponse>> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessDto<UserResponse>> getUserById(@PathVariable String userId) {
        UserResponse response = userService.getUserById(userId);
        SuccessDto<UserResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<SuccessDto<UserResponse>> createUser(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.createUser(request);
        SuccessDto<UserResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SuccessDto<UserResponse>> updateUser(@PathVariable String userId,
                                                               @RequestBody @Valid UserRequest request) {
        UserResponse response = userService.updateUser(userId, request);
        SuccessDto<UserResponse> successDto = new SuccessDto<>(SuccessStatus.SUCCESS, response);
        return new ResponseEntity<>(successDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
