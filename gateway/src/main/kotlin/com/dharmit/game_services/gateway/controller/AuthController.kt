package com.dharmit.game_services.gateway.controller

import com.dharmit.game_services.gateway.service.AuthService
import com.dharmit.game_services.gateway.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {

    data class LoginRequest(val username: String, val password: String)
    data class LoginResponse(val token: String, val username: String)
    
    data class RegisterRequest(val username: String, val password: String)
    data class RegisterResponse(val username: String, val message: String)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val token = authService.authenticate(request.username, request.password)
        return ResponseEntity.ok(LoginResponse(token, request.username))
    }
    
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        val user = userService.createUser(request.username, request.password, listOf("ROLE_USER"))
        return ResponseEntity.ok(RegisterResponse(user.username, "User registered successfully"))
    }
} 