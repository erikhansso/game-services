package com.dharmit.game_services.gateway.service

import com.dharmit.game_services.gateway.security.JwtTokenUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
) {

    fun authenticate(username: String, password: String): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        
        val userDetails = authentication.principal as UserDetails
        return jwtTokenUtil.generateToken(userDetails)
    }
} 