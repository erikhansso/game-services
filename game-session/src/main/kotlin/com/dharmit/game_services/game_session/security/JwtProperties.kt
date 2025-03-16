package com.dharmit.game_services.game_session.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.Base64

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    var secret: String = "defaultSecretKeyThatShouldBeChangedInProduction"
        set(value) {
            field = if (!isBase64Encoded(value)) {
                Base64.getEncoder().encodeToString(value.toByteArray())
            } else {
                value
            }
        }
    var expiration: Duration = Duration.ofHours(24)
    
    private fun isBase64Encoded(str: String): Boolean {
        return try {
            Base64.getDecoder().decode(str)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
} 