package com.dharmit.game_services.gateway.service

import com.dharmit.game_services.gateway.entity.User
import com.dharmit.game_services.gateway.repository.RoleRepository
import com.dharmit.game_services.gateway.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found with username: $username") }
    }

    @Transactional
    fun createUser(username: String, password: String, roleNames: List<String>): User {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("Username already exists")
        }

        val roles = roleNames.mapNotNull { roleName ->
            roleRepository.findByName(roleName).orElse(null)
        }.toMutableSet()

        if (roles.isEmpty()) {
            throw IllegalArgumentException("No valid roles provided")
        }

        val user = User(
            username = username,
            password = passwordEncoder.encode(password),
            roles = roles
        )

        return userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }
} 