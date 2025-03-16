package com.dharmit.game_services.game_session.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        
        try {
            val jwt = authHeader.substring(7)
            val username = jwtTokenUtil.getUsernameFromToken(jwt)
            
            if (username.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val roles = jwtTokenUtil.getRolesFromToken(jwt)
                val authorities = roles.map { SimpleGrantedAuthority(it) }
                
                val authToken = UsernamePasswordAuthenticationToken(
                    username, null, authorities
                )
                
                SecurityContextHolder.getContext().authentication = authToken
            }
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: ${e.message}")
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: ${e.message}")
        }
        
        filterChain.doFilter(request, response)
    }
} 