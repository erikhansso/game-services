package com.dharmit.game_services.gateway.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    private val jwtProperties: JwtProperties
) {
    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
    
    fun generateToken(userDetails: UserDetails): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtProperties.expiration.toMillis())
        
        return Jwts.builder()
            .subject(userDetails.username)
            .issuedAt(now)
            .expiration(expiryDate)
            .claim("roles", userDetails.authorities.map { it.authority })
            .signWith(getSigningKey())
            .compact()
    }
    
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
    
    fun getUsernameFromToken(token: String): String {
        return extractClaims(token).subject
    }
    
    fun getRolesFromToken(token: String): List<String> {
        val claims = extractClaims(token)
        @Suppress("UNCHECKED_CAST")
        return claims["roles"] as? List<String> ?: emptyList()
    }
    
    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractClaims(token).expiration
        return expiration.before(Date())
    }
    
    private fun extractClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
} 