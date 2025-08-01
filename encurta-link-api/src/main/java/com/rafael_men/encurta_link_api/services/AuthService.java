package com.rafael_men.encurta_link_api.services;

import com.rafael_men.encurta_link_api.dto.*;
import com.rafael_men.encurta_link_api.models.User;
import com.rafael_men.encurta_link_api.repositories.UserRepository;
import com.rafael_men.encurta_link_api.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private UserDtoMapperConverter userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public UserDto register(RegisterDto registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username já está em uso!");
        }


        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        User savedUser = userRepository.save(user);


        return userMapper.toDTO(savedUser);
    }

    public JwtResponseDto login(LoginDto loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }


        UserDto userDto = userMapper.toDTO(user);


        String jwt = jwtService.generateToken(userDto);

        return new JwtResponseDto(jwt, userDto);
    }
}