package com.dharmit.game_services.gateway.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class MicroserviceProxyService(
    private val webClientBuilder: WebClient.Builder
) {
    @Value("\${microservices.matchmaking.url}")
    private lateinit var matchmakingUrl: String
    
    @Value("\${microservices.game-session.url}")
    private lateinit var gameSessionUrl: String
    
    fun callMatchmakingService(path: String, method: HttpMethod, body: Any?, token: String): Any {
        return callService(matchmakingUrl, path, method, body, token)
    }
    
    fun callGameSessionService(path: String, method: HttpMethod, body: Any?, token: String): Any {
        return callService(gameSessionUrl, path, method, body, token)
    }
    
    private fun callService(baseUrl: String, path: String, method: HttpMethod, body: Any?, token: String): Any {
        val webClient = webClientBuilder.baseUrl(baseUrl).build()
        
        val requestSpec = webClient
            .method(method)
            .uri(path)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        
        val responseSpec = if (body != null && method != HttpMethod.GET) {
            requestSpec.body(BodyInserters.fromValue(body))
        } else {
            requestSpec
        }
        
        return when (method) {
            HttpMethod.GET -> responseSpec.retrieve().bodyToMono(Any::class.java).block()
                ?: throw RuntimeException("Failed to get response from service")
            HttpMethod.POST -> responseSpec.retrieve().bodyToMono(Any::class.java).block()
                ?: throw RuntimeException("Failed to get response from service")
            HttpMethod.PUT -> responseSpec.retrieve().bodyToMono(Any::class.java).block()
                ?: throw RuntimeException("Failed to get response from service")
            HttpMethod.DELETE -> responseSpec.retrieve().bodyToMono(Void::class.java)
                .then(Mono.just(mapOf("message" to "Resource deleted successfully")))
                .block() ?: throw RuntimeException("Failed to get response from service")
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }
    }
} 