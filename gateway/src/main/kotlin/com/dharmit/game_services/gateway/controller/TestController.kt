package com.dharmit.game_services.gateway.controller

import com.dharmit.game_services.gateway.service.MicroserviceProxyService
import com.dharmit.game_services.gateway.service.TestService
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/test")
class TestController(
    private val testService: TestService,
    private val microserviceProxyService: MicroserviceProxyService
) {

    data class TestRequest(val data: String)
    data class TestResponse(val id: Long?, val message: String)
    data class TestListResponse(val id: Long?, val testValue: String?, val created: String, val updated: String)

    @PostMapping
    fun createTest(@RequestBody request: TestRequest): ResponseEntity<TestResponse> {
        val savedEntity = testService.saveTestData(request.data)
        return ResponseEntity.ok(
            TestResponse(
                id = savedEntity.id,
                message = "Data saved successfully"
            )
        )
    }
    
    @GetMapping
    fun getAllTests(): ResponseEntity<List<TestListResponse>> {
        val testEntities = testService.findAllTestData()
        val response = testEntities.map { entity ->
            TestListResponse(
                id = entity.id,
                testValue = entity.testValue,
                created = entity.created.toString(),
                updated = entity.updated.toString()
            )
        }
        return ResponseEntity.ok(response)
    }
    
    @GetMapping("/matchmaking")
    fun getMatchmakingTests(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Any> {
        val token = authHeader.substring(7) // Remove "Bearer " prefix
        val result = microserviceProxyService.callMatchmakingService("/api/test", HttpMethod.GET, null, token)
        return ResponseEntity.ok(result)
    }
    
    @PostMapping("/matchmaking")
    fun createMatchmakingTest(
        @RequestBody request: TestRequest,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Any> {
        val token = authHeader.substring(7) // Remove "Bearer " prefix
        val result = microserviceProxyService.callMatchmakingService("/api/test", HttpMethod.POST, request, token)
        return ResponseEntity.ok(result)
    }
    
    @GetMapping("/game-session")
    fun getGameSessionTests(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Any> {
        val token = authHeader.substring(7) // Remove "Bearer " prefix
        val result = microserviceProxyService.callGameSessionService("/api/test", HttpMethod.GET, null, token)
        return ResponseEntity.ok(result)
    }
    
    @PostMapping("/game-session")
    fun createGameSessionTest(
        @RequestBody request: TestRequest,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Any> {
        val token = authHeader.substring(7) // Remove "Bearer " prefix
        val result = microserviceProxyService.callGameSessionService("/api/test", HttpMethod.POST, request, token)
        return ResponseEntity.ok(result)
    }
} 