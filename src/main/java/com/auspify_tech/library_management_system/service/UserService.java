package com.auspify_tech.library_management_system.service;

import com.auspify_tech.library_management_system.dto.request.UserRequest;
import com.auspify_tech.library_management_system.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponse> getAllUsers(int page, int size);

    UserResponse getUserById(String userId);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(String userId, UserRequest request);

    void deleteUser(String userId);

}
