package com.auspify_tech.library_management_system.service.implement;

import com.auspify_tech.library_management_system.dto.request.UserRequest;
import com.auspify_tech.library_management_system.dto.response.UserResponse;
import com.auspify_tech.library_management_system.entity.UserEntity;
import com.auspify_tech.library_management_system.exception.ResourceAlreadyExistsException;
import com.auspify_tech.library_management_system.exception.ResourceNotFoundException;
import com.auspify_tech.library_management_system.mapper.UserMapper;
import com.auspify_tech.library_management_system.model.UserStatus;
import com.auspify_tech.library_management_system.repository.UserRepository;
import com.auspify_tech.library_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable)
                .map(userMapper::mapEntityToResponse);
    }

    @Override
    public UserResponse getUserById(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return userMapper.mapEntityToResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("User already exists with email: " + request.email());
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new ResourceAlreadyExistsException("User already exists with phone number: "
                    + request.phoneNumber());
        }

        UserEntity userEntity = userMapper.mapRequestToEntity(request);
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCreatedAt(Instant.now());

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userMapper.mapEntityToResponse(savedUserEntity);
    }

    @Override
    @Transactional
    public UserResponse updateUser(String userId, UserRequest request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (userRepository.existsByEmailAndIdNot(request.email(), userId)) {
            throw new ResourceAlreadyExistsException("Email already exists for another user!");
        }

        if (userRepository.existsByPhoneNumberAndIdNot(request.phoneNumber(), userId)) {
            throw new ResourceAlreadyExistsException("Phone number already exists for another user!");
        }

        userMapper.updateEntityFromRequest(request, userEntity);
        userEntity.setUpdatedAt(Instant.now());

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userMapper.mapEntityToResponse(savedUserEntity);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found!");
        }

        userRepository.deleteById(userId);
    }
}
