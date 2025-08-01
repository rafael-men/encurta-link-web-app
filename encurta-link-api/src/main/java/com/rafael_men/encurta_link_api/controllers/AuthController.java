package com.rafael_men.encurta_link_api.controllers;

import com.rafael_men.encurta_link_api.dto.*;
import com.rafael_men.encurta_link_api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/encurta/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;


    @Operation(summary = "Registro")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerRequest) {
        try {
            logger.info("Tentativa de registro para username: {}", registerRequest.getUsername());

            UserDto userDto = authService.register(registerRequest);

            logger.info("Usuário registrado com sucesso: {}", userDto.getUsername());
            return ResponseEntity.ok(new ApiResponseDto(true, "Usuário registrado com sucesso!"));

        } catch (RuntimeException e) {
            logger.warn("Erro no registro: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponseDto(false, e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno no registro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto(false, "Erro interno do servidor"));
        }
    }


    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginRequest) {
        try {
            logger.info("Tentativa de login para username: {}", loginRequest.getUsername());

            JwtResponseDto jwtResponse = authService.login(loginRequest);

            logger.info("Login realizado com sucesso para username: {}", loginRequest.getUsername());
            return ResponseEntity.ok(jwtResponse);

        } catch (RuntimeException e) {
            logger.warn("Falha no login para username {}: {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseDto(false, "Credenciais inválidas"));
        } catch (Exception e) {
            logger.error("Erro interno no login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto(false, "Erro interno do servidor"));
        }
    }
}
    
