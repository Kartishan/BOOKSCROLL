package com.karishan.authservice.service.jwt;

import com.karishan.authservice.model.User;
import com.karishan.authservice.model.dto.UserDTO;
import com.karishan.authservice.repository.UserRepository;
import com.karishan.authservice.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User getUserById(UUID id){
        return userRepository.findById(id).get();
    }
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).get();
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = userRepository.findByUsername(connectedUser.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }



    public UserDTO getUserDtoById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return toUserDto(user);
    }

    // Вспомогательный метод для преобразования User в UserDTO
    private UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public String getUsernameById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return user.getUsername();
    }

}
